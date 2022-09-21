<?php

namespace App\Controller;


use App\Entity\Chambre;
use App\Entity\comment;
use App\Entity\Produit;
use App\Entity\User;
use App\Form\ChambreType;
use App\Form\CommentType;
use App\Repository\ChambreRepository;
use App\Repository\CommentRepository;
use App\Repository\MaisonRepository;
use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;

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

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param MaisonRepository $repository
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/afficheCommentProduitJson", name="afficheCommentProduitJson")
     */
    public function afficheCommentProduitJson(NormalizerInterface $normalizer, CommentRepository $repository){
        $comment = $repository->findAll();
        $jsonContent = $normalizer->normalize($comment, 'json',['groups'=>'comments']);
        $retour=json_encode($jsonContent);
        return new Response($retour);

    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/deleteCommentProduitJSON/{id}", name="deleteCommentProduitJSON")
     */
    public function deleteCommentProduitJSON(Request $request,NormalizerInterface $normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $comment = $em->getRepository(comment::class)->find($id);
        $em->remove($comment);
        $em->flush();
        $jsonContent = $normalizer->normalize($comment, 'json', ['groups' => 'comment']);
        return new Response("Commentaire supprimé".json_encode($jsonContent));
    }


    /**
     * @param Request $request
     * @param SerializerInterface $serializer
     * @return Response
     * @Route("/ajoutCommentProduitJSON", name="ajoutCommentProduitJSON")
     */

    public function addCommentProduitJSON(Request $request, SerializerInterface $serializer){
        $em = $this->getDoctrine()->getManager();
        $content = $request->getContent();
        $data = $serializer->deserialize($content, comment::class, 'json');
        $em->persist($data);
        $em->flush();
        return new Response('Comment added successfully');
    }

}
