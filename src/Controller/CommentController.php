<?php

namespace App\Controller;


use App\Entity\commentaire;
use App\Entity\Produit;
use App\Form\CommentType;
use App\Repository\CommentaireRepository;
use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class CommentController extends AbstractController
{
    /**
     * @Route("/comment", name="comment")
     */
    public function index(): Response
    {
        return $this->render('comment/index.html.twig', [
            'controller_name' => 'CommentController',
        ]);
    }

    /**
     * @param \Symfony\Component\HttpFoundation\Request $request,$id
     * @Route("voirProduit", name="voirProduit",defaults={"id"})
     * @return Response
     *
     */
    public function ajouterCommentaireAuproduit(\Symfony\Component\HttpFoundation\Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $produit = $em->find(Produit::class, $id);



        $comment =new commentaire();
        $form=$this->createFormBuilder(new CommentType,$comment);
        $form->handleRequest($request);


        if($form->isSubmitted() && $form->isValid())
        {
                $produit->addComment($comment);
                $em=$this->getDoctrine()->getManager();
                $em->persist($comment);
                $em->flush();
                return $this->redirectToRoute("produitListe");
        }

        return $this->render('produit/voirproduit.html.twig',array('f' => $form->createView()));

    }

    /**
     * @param ProduitRepository $repo
     * @return Response
     * @Route("commProduit", name="commProduit",defaults={"id"})
     */
    public function afficherCommentProduit(CommentaireRepository $commentaireRepository,\Symfony\Component\HttpFoundation\Request $request)
    {

        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $produit = $em->find(Produit::class, $id);
        if($produit->getComments())

        {
           $comment= $produit->getComments();
        }

        return $this->render("produit/voirproduit.html.twig");

    }

    /**
     * @param ProduitRepository $repo,$id
     * @param CommandeRepository $commandeRepository
     * @Route("commentaireSupprimer/{id}", name="commentaireSupprimer")
     * @return Response
     */
    public function supprimerCommentaire($id )
    {
        $em=$this->getDoctrine()->getManager();
        $comment=$em->find(commentaire::class,$id);
        $em->remove($comment);
        $em->flush();


        return $this->redirectToRoute('voirProduit',['id'=>$comment->getProduit()->getId(),'produit'=>$comment->getProduit(),]);
       // return $this->render("produit/voirproduit.html.twig",['id'=>$comment->getProduit()->getId(),'produit'=>$comment->getProduit(),]);

    }

}
