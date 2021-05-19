<?php

namespace App\Controller;

use App\Entity\CommentaireR;
use App\Entity\Randonnee;
use App\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class CommentaireController extends AbstractController
{
    /**
     * @Route("/comment", name="comment")
     */
    public function index(): Response
    {
        return $this->render('comment/index.html.twig', [
            'controller_name' => 'CommentaireController',
        ]);
    }

    /**
     * @Route("/comment/add", name="comment_add")
     */
    public function add(Request $request)
    {
        $randonnee_id = $request->request->get('randonnee_id');
        $randonnee = $this->getDoctrine()
            ->getRepository(Randonnee::class)
            ->find((int)$randonnee_id);
        $user=$this->getDoctrine()->getRepository(User::class)->find(1);
        $commentaire = new CommentaireR();
        $commentaire->setContenue($request->request->get('body'));
        $datec=new \DateTime();
        $commentaire->setRandonne($randonnee);
        $commentaire->setDatedecommentaire("01-04-2021");
        $commentaire->setUser($user);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($commentaire);
        $entityManager->flush();
        return $this->redirectToRoute('AfficheRandonnee');
    }




}
