<?php

namespace App\Controller;

use App\Entity\User;
use App\Entity\Chambre;
use App\Entity\Maison;
use App\Entity\ReservationMaison;
use App\Form\EditUserType;
use App\Form\ReservationMaisonType;
use App\Form\ReservationMaisonType2Type;
use App\Repository\MaisonRepository;
use App\Repository\ReservationMaisonRepository;
use App\Repository\UserRepository;
use Mgilet\NotificationBundle\Entity\NotifiableNotification;
use Mgilet\NotificationBundle\Entity\Notification;
use Mgilet\NotificationBundle\NotifiableInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;


class SessionController extends AbstractController
{

    /**
     * @Route("/session", name="session")
     */
    public function index(): Response
    {
        return $this->render('session/index.html.twig', [
            'controller_name' => 'SessionController',
        ]);
    }

    /**
     * @param ReservationMaisonRepository $repository
     * @return Response
     * @Route("/reservation", name="reservation", defaults={"id"})
     */
    public function Hebergeur(ReservationMaisonRepository $repository, Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $id = $request->get("id");
        $mai = $em->find(Maison::class, $id);
        $maison=$repository->find($mai);

        if($maison->getId())
        {
            $chambre = $maison->getChambre()->getId();
            $resrvation=$repository->findBy(array('chambre'=>$chambre));
        }

        return $this->render('session/Reservation.html.twig',
            ['reservation'=>$resrvation]);
    }

    /**
     * @param Request $request
     * @Route("/etats", name="etats", defaults={"idReserv"})
     */
    public function Etats(Request $request, ReservationMaisonRepository $repository)
    {
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("idReserv");
        $ch = $em->find(ReservationMaison::class, $id);
        $resrvation = $repository->find($ch);
        $form = $this->createForm(ReservationMaisonType2Type::class, $resrvation);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em->flush();
            return $this->redirectToRoute('reservation');
        }
        return $this->render("session/Etats.html.twig",[
            'f'=>$form->createView()
        ]);
    }



    /**
     * @param ReservationMaisonRepository $repository
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/AfficheReservation", name="AfficheReservation")
     *
     */
    public function Affiche(ReservationMaisonRepository $repository){

        $repos=$this->getDoctrine()->getRepository(User::class);
        $user=$this->getUser()->getUsername();
        $u=$repos->findOneBy(array('username'=>$user));
        $idUser=$repos->find($u->getId());
        $reservation= $idUser->getReservationMaisons() ;
        return $this->render('session/AfficheReservation.html.twig',
            ['reservation'=>$reservation]);
    }


    /**
     * @param ReservationMaisonRepository $repository
     * @param $id
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route("/removeReservation/{id}", name="removeReservation")
     */
    public function deleteFavoris(ReservationMaisonRepository $repository, $id){
        $reservation=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($reservation);
        $em->flush();
        return $this->redirectToRoute("AfficheReservation");

    }

    /**
     *
     * @Route("/Ajout_reservation", name="ajout_reservation", defaults={"idChambre"})
     * Method{{"GET","POST"}}
     */

    public function addReservation(Request $request,ReservationMaisonRepository $repository, UserRepository $userRepository)
    {
        $em = $this->getDoctrine()->getManager();
        $idChambre = $request->get("idChambre");
        $chambre = $em->find(Chambre::class, $idChambre);

        $reservation= new ReservationMaison();
        $form = $this->createForm(ReservationMaisonType::class,$reservation);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            //TODO recuperer id user après connexion
           // $user = $em->find(User::class, 3);
            $user=$this->getUser()->getUsername();
            //$u=$em->getRepository(User::class)->findBy(array('username'=>$user));
            $u=$userRepository->findOneBy(array('username'=>$user));
            $userId=$userRepository->find($u->getId());
            $reservation->setUser($userId);
            $reservation->setChambre($chambre);
            $reservation->setEtats("En cours");
            $em->persist($reservation);
            $em->flush();
            $resrvation=$repository->findAll();
            return $this->redirectToRoute("h",["id"=>$reservation->getChambre()->getId()]);
        }
        return $this->render('session/AddReservation.html.twig',[
            'form'=>$form->createView()
        ]);
    }

    // Partie JSON

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param MaisonRepository $repository
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/afficheReservationMaisonJson", name="afficheReservationMaisonJson")
     */
    public function afficheReservationMaisonJson(NormalizerInterface $normalizer, ReservationMaisonRepository $repository){
        $reservations = $repository->findAll();
        $jsonContent = $normalizer->normalize($reservations, 'json',['groups'=>'reservations']);
        $retour=json_encode($jsonContent);
        return new Response($retour);

    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/deleteReservationMaisonJSON/{id}", name="deleteReservationMaisonJSON")
     */
    public function deleteReservationMaisonJSON(Request $request,NormalizerInterface $normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $reservation = $em->getRepository(ReservationMaison::class)->find($id);
        $em->remove($reservation);
        $em->flush();
        $jsonContent = $normalizer->normalize($reservation, 'json', ['groups' => 'reservation']);
        return new Response("Reservation supprimé".json_encode($jsonContent));
    }

    /**
     * @param Request $request
     * @param SerializerInterface $serializer
     * @return Response
     * @Route("/ajoutReservationMaisonJSON", name="ajoutReservationMaisonJSON")
     */

    public function addReservationMaisonJSON(Request $request, SerializerInterface $serializer){
        $em = $this->getDoctrine()->getManager();
        $content = $request->getContent();
        $data = $serializer->deserialize($content, ReservationMaison::class, 'json');
        $em->persist($data);
        $em->flush();
        return new Response('Reservation added successfully');
    }

}