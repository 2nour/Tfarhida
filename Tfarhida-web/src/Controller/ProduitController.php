<?php

namespace App\Controller;

use App\Entity\Panier;
use App\Entity\Produit;
use App\Entity\commentaire;
use App\Form\CommentType;

use App\Form\AjouterProduitFormType;
use App\Repository\PanierRepository;
use App\Repository\ProduitRepository;
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
    public function ajouterProduit(\Symfony\Component\HttpFoundation\Request $request)
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
     * @return \http\Env\Response
     */
    public function afficherlisteProduit(ProduitRepository $repo)
    {

        $produit=$repo->findAll();

         return $this->render("produit/liste.html.twig", ['produits'=>$produit]);

    }

    /**
     * @param ProduitRepository $repo,$id
     * @Route("voirProduit", name="voirProduit",defaults={"id"})
     * @return \http\Env\Response
     */
    public function afficherProduitById( ProduitRepository $repo, \Symfony\Component\HttpFoundation\Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $p = $em->find(Produit::class, $id);
        $produit=$repo->find($p);

        $comment =new commentaire();
        $form=$this->createForm( CommentType::class,$comment);
        $form->handleRequest($request);


        if($form->isSubmitted() && $form->isValid())
        {
            $produit->addComment($comment);
            $em=$this->getDoctrine()->getManager();
            $em->persist($comment);
            $em->flush();
            return $this->redirectToRoute("produitListe");
        }
        if($produit->getComments())

        {
            $comment= $produit->getComments();
        }
        return $this->render("produit/voirproduit.html.twig", ['produit'=>$produit,'comments'=>$comment,'form'=>$form->createView()]);

    }






    /**
     * @param ProduitRepository $repo,$id
     * @Route("produitSupprimer/{id}", name="suppProduit")
     * @return \http\Env\Response
     */
    public function supprimerProduit( $id,ProduitRepository $repo )
    {

        $produit=$repo->find($id);
        $em=$this->getDoctrine()->getManager();
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
        $mai = $em->find(Produit::class, $id);

        $form=$this->createForm(AjouterProduitFormType::class,$mai);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $file =$mai->getImage();
            $filename=md5(uniqid()).'.'.$file->guessExtension();

            // sauvgarder l'image dans le dossier indiquer par le param 'product_image_directory' dans services.yaml
            try {
                $file->move($this->getParameter('product_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }

            $em=$this->getDoctrine()->getManager();
            $mai->setImage($filename);
            $em->flush();
            return $this->redirectToRoute("produitListe");
        }
        return $this->render("produit/liste.html.twig",[
            'form'=>$form->createView()
        ]);
    }

}

