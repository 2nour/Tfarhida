<?php

namespace App\Controller;

use App\Entity\Produit;
use App\Form\AjouterProduitFormType;
use App\Repository\ProduitRepository;
use http\Env\Request;
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
     * @Route("produitAjouter", name="ajouterProduit")
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
        }

       return $this->render('produit/ajouter.html.twig', [
           'form'=>$form->createView()]);

    }

    /**
     * @param ProduitRepository $repo
     * @Route("produitListe", name="listeProduit")
     * @return \http\Env\Response
     */
    public function afficherlisteProduit(ProduitRepository $repo)
    {

        $produit=$repo->findAll();

         return $this->render("produit/liste.html.twig", ['produits'=>$produit]);

    }

    /**
     * @param ProduitRepository $repo,$id
     * @Route("produit/{id}", name="voirProduit")
     * @return \http\Env\Response
     */
    public function afficherProduitById(Integer $id,ProduitRepository $repo )
    {

        $produit=$repo->findBy($id);

        return $this->render("produit/liste.html.twig", ['produit'=>$produit]);

    }

    /**
     * @param ProduitRepository $repo,$id
     * @Route("produitSupprimer/{id}", name="suppProduit")
     * @return \http\Env\Response
     */
    public function supprimerProduit(Integer $id,ProduitRepository $repo )
    {

        $produit=$repo->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($produit);
        $em->flush();

        return $this->redirectToRoute("listeProduit");

    }

}
