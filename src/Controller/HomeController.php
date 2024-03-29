<?php

namespace App\Controller;

use App\Entity\Commentaire;
use App\Entity\Maison;
use App\Entity\User;
use App\Form\CommentaireType;
use App\Form\SearchType;
use App\Repository\CommentaireRepository;
use App\Repository\CommentRepository;
use App\Repository\MaisonRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\PanierRepository;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;

class HomeController extends AbstractController
{
    /**
     * @Route("/", name="home")
     */
    public function index(PanierRepository $panierRepository): Response
    {


        return $this->render('base.html.twig',[

        ]);
    }

    /**
     * @param CommentaireRepository $repository
     * @param Request $request
     * @return Response
     * @Route("/affichComment", name="affichComment", defaults={"id"})
     */
    public function affiche(CommentaireRepository $repository, Request $request, UserRepository
     $userRepository){
        $repo=$this->getDoctrine()->getRepository(Maison::class);
        $idMaison = $request->get("idMaison");
        $maison = $repo->find($idMaison);
        if($maison->getCommentaires())
            $comment = $maison->getCommentaires();
        return $this->render('maison/Detail.html.twig',
            ['comment'=>$comment]);
    }

    /**
     * @param Request $request
     * @param CommentaireRepository $repository
     * @return Response
     * @Route("/comment", name="comment", defaults={"idMaison"})
     */

    public function addComment(\Symfony\Component\HttpFoundation\Request $request, CommentaireRepository $repository, UserRepository $userRepository){
        $comment = new comment();
        $form=$this->createForm(CommentaireType::class, $comment);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $idMaison = $request->get("idMaison");
            //TODO recuperer id user après connexion
           // $users = $em->find(User::class, 2);
            $user=$this->getUser()->getUsername();
            //$u=$em->getRepository(User::class)->findBy(array('username'=>$user));
            $u=$userRepository->findOneBy(array('username'=>$user));
            $userId=$userRepository->find($u->getId());
            $mai = $em->find(Maison::class, $idMaison);
            $comment->setMaison($mai);
            $comment->setUser($userId);
            $em->persist($comment);
            $em->flush();
            //return $this->redirectToRoute("affich_Comment");
        }
        return $this->render("comment/newComment.html.twig",[
            'f'=>$form->createView()
        ]);
    }

    /**
     * @param CommentaireRepository $repository
     * @param $id
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route("/dComment/{id}",name="dComment")
     */
    public function delete(CommentaireRepository $repository, $id){
        $comment=$repository->find($id);
        $maison = $comment->getMaison();
        $em=$this->getDoctrine()->getManager();
        $em->remove($comment);
        $nbrComment = $maison->getNbrComment();
        $maison->setNbrComment($nbrComment-1);
        $em->flush();
        return $this->redirectToRoute("read",["id"=>$maison->getId()]);
    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param MaisonRepository $repository
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/afficheCommentMaisonJson", name="afficheCommentMaisonJson")
     */
    public function afficheCommentMaisonJson(NormalizerInterface $normalizer, CommentaireRepository $repository){
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
     * @Route("/deleteCommentMaisonPJSON/{id}", name="deleteCommentMaisonPJSON")
     */
    public function deleteCommentMaisonJSON(Request $request,NormalizerInterface $normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $comment = $em->getRepository(Commentaire::class)->find($id);
        $em->remove($comment);
        $em->flush();
        $jsonContent = $normalizer->normalize($comment, 'json', ['groups' => 'comment']);
        return new Response("Commentaire supprimé".json_encode($jsonContent));
    }


    /**
     * @param Request $request
     * @param SerializerInterface $serializer
     * @return Response
     * @Route("/ajoutCommentMaisonPJSON", name="ajoutCommentMaisonPJSON")
     */

    public function addCommentMaisonJSON(Request $request, SerializerInterface $serializer){
        $em = $this->getDoctrine()->getManager();
        $content = $request->getContent();
        $data = $serializer->deserialize($content, Commentaire::class, 'json');
        $em->persist($data);
        $em->flush();
        return new Response('Comment added successfully');
    }

}
