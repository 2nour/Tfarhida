<?php

namespace App\Controller;

use App\Data\SearchData;
use App\Entity\Chambre;
use App\Entity\Commande;
use App\Entity\Organisation;
use App\Entity\Panier;
use App\Entity\Produit;
use App\Entity\comment;
use App\Entity\User;

use App\Form\ChambreType;
use App\Form\CommentType;

use App\Form\AjouterProduitFormType;
use App\Form\SearchForm;
use App\Repository\ChambreRepository;
use App\Repository\CommandeRepository;
use App\Repository\CommentRepository;
use App\Repository\MaisonRepository;
use App\Repository\PanierRepository;
use App\Repository\ProduitRepository;
use App\Repository\UserRepository;
use Knp\Component\Pager\Pagination\PaginationInterface;
use Knp\Component\Pager\PaginatorInterface;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Component\HttpFoundation\Request;
use phpDocumentor\Reflection\Types\Integer;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;

class ProduitController extends AbstractController
{
    /**
     * @Route("/produit", name="produit")
     */
    public function index(): Response
    {
        return $this->render('produit/index.html.twig', [
            'controller_name' => 'ProduitController',
        ]);
    }


    /**
     * @Route("produitListee/{id}", name="produitListee")
     * @return Response
     */
    public function afficherlistesProduit(Request $request,$id)
    {
        $em = $this->getDoctrine()->getManager();
        $produits=$em->getRepository(Produit::class)->findAll();
        $org = $em->getRepository(Organisation::class)->find($id);
        return $this->render('org_produit/AfficheProduit.html.twig',
            ['produits'=> $produits,'org'=>$org]);

    }

    /**
     * @param Request $request
     * @Route("produitAjouter", name="produitAjouter")
     * @return \http\Env\Response
     */
    public function ajouterProduit(\Symfony\Component\HttpFoundation\Request $request,FlashyNotifier $flashyNotifier)
    {
        $produit =new Produit();
        $form=$this->createForm(AjouterProduitFormType::class,$produit);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $file =$produit->getImage();
            $filename=md5(uniqid()).'.'.$file->guessExtension();

            // sauvgarder l'image dans le dossier indiquer par le param 'product_image_directory' dans services.yaml
            try {
                $file->move($this->getParameter('product_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }


            $em=$this->getDoctrine()->getManager();
        // sauvgarder uniquement le nom de l'image dans la bdd
            $produit->setImage($filename);
            foreach($form["categories"]->getData()->getValues() as $categories) {
                $produit->addCategory($categories);
            }
            $em->persist($produit);
            $em->flush();
            return $this->redirectToRoute("produitListe");

        }

       return $this->render('produit/ajouter.html.twig', [
           'form'=>$form->createView()]);

    }

    /**
     * @param ProduitRepository $repo
     * @Route("produitListe", name="produitListe")
     * @return Response
     */
    public function afficherlisteProduit(Request $request,ProduitRepository $produitRepository)
    {
        $data= new SearchData();
        $data->page = $request->get('page', 1);
        $form =$this->createForm(SearchForm::class,$data);
        $form->handleRequest($request);
        $produits=$produitRepository->findSearch($data);





         return $this->render("produit/liste.html.twig", ['produits'=>$produits,"form"=>$form->createView()]);

    }

    /**
     * @param ProduitRepository $repo,$id
     * @Route("voirProduit", name="voirProduit",defaults={"id"})
     * @return Response
     */
    public function afficherProduitById( ProduitRepository $repo, \Symfony\Component\HttpFoundation\Request $request,UserRepository $userRepository)
    {
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $p = $em->find(Produit::class, $id);
        $produit=$repo->find($p);
        $user=$this->getUser()->getUsername();
        //$u=$em->getRepository(User::class)->findBy(array('username'=>$user));
        $u=$userRepository->findOneBy(array('username'=>$user));



        $comment =new comment();
        $form=$this->createForm( CommentType::class,$comment);
        $form->handleRequest($request);


        if($form->isSubmitted() && $form->isValid())
        {
            $produit->addComment($comment);

            $comment->setUser($u);
            $em=$this->getDoctrine()->getManager();
            $em->persist($comment);
            $em->flush();
        }
        if($produit->getComments())

        {
            $comment= $produit->getComments();
        }
        return $this->render("produit/voirproduit.html.twig", ['produit'=>$produit,'comments'=>$comment,'f'=>$form->createView(),'user'=>$u->getUsername()]);

    }






    /**
     * @param ProduitRepository $repo,$id
     * @param CommandeRepository $commandeRepository
     * @Route("produitSupprimer/{id}", name="suppProduit")
     * @return Response
     */
    public function supprimerProduit( $id,ProduitRepository $repo,CommandeRepository $commandeRepository )
    {

        $produit=$repo->find($id);
        $em=$this->getDoctrine()->getManager();

        $commande= $em->getRepository(Commande::class)->findOneBy(array('produit'=>$produit));

        if($commande)
        {
            $panier=$commande->getPanier();
           $panier->setSomme($panier->getSomme() - $commande->getPrixcommande()*$commande->getQuantiteProduit());
           $panier->setnbproduit($panier->getnbproduit()-$commande->getQuantiteProduit());


        }
        $em->remove($produit);
        $em->flush();

        return $this->redirectToRoute("produitListe");

    }



    /**
     * @param Request $request
     * @param $id
     * @param ProduitRepository $repository
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     * @Route("/updateProduit", name="updateProduit" , defaults={"id"})
     */

    public function update(Request $request,ProduitRepository $repository){
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $produit = $em->find(Produit::class, $id);

        $form=$this->createForm(AjouterProduitFormType::class,$produit);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $file =$produit->getImage();
            $filename=md5(uniqid()).'.'.$file->guessExtension();

            // sauvgarder l'image dans le dossier indiquer par le param 'product_image_directory' dans services.yaml
            try {
                $file->move($this->getParameter('product_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }

            $em=$this->getDoctrine()->getManager();
            $produit->setImage($filename);
            $em->flush();

            $comment =new comment();
            $f=$this->createForm( CommentType::class,$comment);
            $f->handleRequest($request);


            if($f->isSubmitted() && $f->isValid())
            {
                $produit->addComment($comment);
                $em=$this->getDoctrine()->getManager();
                $em->persist($comment);
                $em->flush();
            }

            if($produit->getComments())

            {
                $comment= $produit->getComments();
            }

            return $this->redirectToRoute("voirProduit",['id'=>$produit->getId(),'produit'=>$produit,'f'=>$f->createView(),'comments'=>$comment]);
        }
        return $this->render("produit/update.html.twig",['produit'=>$produit,
            'form'=>$form->createView()
        ]);
    }


    // Partie JSON

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/UpdateProduitJSON/{id}", name="UpdateProduitJSON")
     *
     */
    public function updateProduitJSON(Request $request, Produit $produit)
    {
        $produitUpdate = $this->getDoctrine()->getManager()->getRepository(Produit::class)->find($produit->getId());
        $data = json_decode($request->getContent());
        foreach ($data as $key => $value){
            if($key && !empty($value)) {
                $name = ucfirst($key);
                $setter = 'set'.$name;
                $produitUpdate->$setter($value);
            }
        }

        $this->getDoctrine()->getManager()->flush();
        $data = [
            'status' => 200,
            'message' => 'Le produit a bien ete mis a jour'
        ];
        return new JsonResponse($data);
    }


    // Partie JSON

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/afficheProduitJson", name="afficheProduitJson")
     */
    public function afficheProduitJson(NormalizerInterface $normalizer){
        $produit = $this->getDoctrine()->getRepository(Produit::class)->findAll();
        $jsonContent = $normalizer->normalize($produit, 'json',['groups'=>'produit']);
        $retour=json_encode($jsonContent);
        return new Response($retour);

    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/deleteProduitJSON/{id}", name="deleteProduitJSON")
     */
    public function deleteProduitJSON(Request $request,NormalizerInterface $normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $produit = $em->getRepository(Produit::class)->find($id);
        $em->remove($produit);
        $em->flush();
        $jsonContent = $normalizer->normalize($produit, 'json', ['groups' => 'produit']);
        return new Response("Produit supprimé".json_encode($jsonContent));
    }




    /**
     * @param Request $request
     * @param SerializerInterface $serializer
     * @return Response
     * @Route("/ajoutProduitJSON", name="ajoutProduitJSON")
     */

    public function addProduitJSON(Request $request, SerializerInterface $serializer){
        $em = $this->getDoctrine()->getManager();
        $content = $request->getContent();
        $data = $serializer->deserialize($content, Produit::class, 'json');
        $em->persist($data);
        $em->flush();
        return new Response('Produit added successfully');
    }
}

