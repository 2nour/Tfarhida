<?php

namespace App\Controller;


use App\Entity\comment;
use App\Entity\Produit;
use App\Entity\User;
use App\Form\CommentType;
use App\Repository\CommentRepository;
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

        $user=$this->getUser()->getUsername();
        $u=$em->getRepository(User::class)->findByOne(array('username'=>$user));





        $comment =new comment();
        $form=$this->createFormBuilder(new CommentType,$comment);
        $form->handleRequest($request);


        if($form->isSubmitted() && $form->isValid())
        {
                $produit->addComment($comment);
                $u->addComment();
                $comment->setUser($u->getId());
                $em=$this->getDoctrine()->getManager();
                $em->persist($comment);
                $em->flush();
                return $this->redirectToRoute("produitListe");
        }

        return $this->render('produit/voirproduit.html.twig',array('f' => $form->createView(),'user'=>$u));

    }

    /**
     * @param ProduitRepository $repo
     * @return Response
     * @Route("commProduit", name="commProduit",defaults={"id"})
     */
    public function afficherCommentProduit(CommentRepository $commentaireRepository, \Symfony\Component\HttpFoundation\Request $request)
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
     * @param $id
     *
     * @Route("commentaireSupprimer/{id}", name="commentaireSupprimer")
     * @return Response
     */
    public function supprimerCommentaire($id )
    {
        $em=$this->getDoctrine()->getManager();
        $comment=$em->find(comment::class,$id);
        $em->remove($comment);
        $em->flush();


        return $this->redirectToRoute('voirProduit',['id'=>$comment->getProduit()->getId(),'produit'=>$comment->getProduit(),]);
       // return $this->render("produit/voirproduit.html.twig",['id'=>$comment->getProduit()->getId(),'produit'=>$comment->getProduit(),]);

    }

}
