<?php

namespace App\Controller;

use App\Data\SearchData;
use App\Entity\Commande;
use App\Entity\Panier;
use App\Entity\Produit;
use App\Entity\comment;
use App\Form\CommentType;

use App\Form\AjouterProduitFormType;
use App\Form\SearchForm;
use App\Repository\CommandeRepository;
use App\Repository\PanierRepository;
use App\Repository\ProduitRepository;
use Knp\Component\Pager\Pagination\PaginationInterface;
use Knp\Component\Pager\PaginatorInterface;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Component\HttpFoundation\Request;
use phpDocumentor\Reflection\Types\Integer;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

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
            $flashyNotifier->success('yeeeeeeeeey');
            return $this->redirectToRoute("produitListe");
            $flashyNotifier->success('yeeeeeeeeey');

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
    public function afficherProduitById( ProduitRepository $repo, \Symfony\Component\HttpFoundation\Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $p = $em->find(Produit::class, $id);
        $produit=$repo->find($p);

        $comment =new comment();
        $form=$this->createForm( CommentType::class,$comment);
        $form->handleRequest($request);


        if($form->isSubmitted() && $form->isValid())
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
        return $this->render("produit/voirproduit.html.twig", ['produit'=>$produit,'comments'=>$comment,'f'=>$form->createView()]);

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

}

