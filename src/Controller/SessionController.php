<?php

namespace App\Controller;

use App\Entity\Maison;
use App\Entity\User;
use App\Entity\Chambre;
use App\Entity\ReservationMaison;
use App\Form\ReservationMaisonType;
use App\Form\ReservationMaisonType2Type;
use App\Repository\ChambreRepository;
use App\Repository\ReservationMaisonRepository;
use App\Repository\UserRepository;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;


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
        $user=$this->getUser()->getUsername();
       // $reservation = $repository->find($user);
        $reservation=$repository->findBy(array('hebergeur_id'=>$user));
        return $this->render('session/Reservation.html.twig',
            ['reservation'=>$reservation]);
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

    public function addReservation(Request $request,ChambreRepository $chambreRepository, ReservationMaisonRepository $repository, UserRepository $userRepository)
    {
        $em = $this->getDoctrine()->getManager();
        $idChambre = $request->get("idChambre");
        $chambre = $em->find(Chambre::class, $idChambre);
        $ch = $chambreRepository->find($idChambre);
        $nomCh = $ch->getNom();
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
            $reservation->setNomChambre($nomCh);
            $reservation->setNomUser($user);
            $idHebergeur=$reservation->getChambre()->getUserId();
            $reservation->setHebergeurId($idHebergeur);
            $dateArrivee = $reservation->getDateArrivee();
            $dateDepart = $reservation->getDateDepart();
            //$nbrJour = $dateDepart->diff( $dateArrivee);
            //$dureesejour = (strtotime($dateDepart) - strtotime($dateArrivee));

            $date1 =  $dateDepart->format('d/m/Y');
            $date2 = $dateArrivee->format('d/m/Y');
            $dateD = (int) $date1;
            $dateA = (int) $date2;
            $dureesejour = ($dateD-$dateA);
            $prixNuit = $ch->getPrix();
            $reservation->setTotalPrix($dureesejour*$prixNuit);
            $reservation->setEtats("En cours");
            $dateNow = new \DateTime('now');

           if($dateDepart <= $dateArrivee || $dateArrivee <= $dateNow){

                $this->addFlash('erreur', 'Choisir une date valide');
                //return new Response($response);
               return $this->redirectToRoute("ajout_reservation",["idChambre"=>$reservation->getChambre()->getId()]);
            }else{
               $em->persist($reservation);
               $em->flush();
               $this->addFlash('success', 'Demande envoyée avec succès');
               return $this->redirectToRoute("h",["id"=>$reservation->getChambre()->getId()]);
           }

            //$resrvation=$repository->findAll();

        }
        return $this->render('session/AddReservation.html.twig',[
            'form'=>$form->createView()
        ]);
    }

    // Partie JSON

    /**
     * @param NormalizerInterface $normalizer
     * @param ReservationMaisonRepository $repository
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/afficheReservationMaisonJson/{user}", name="afficheReservationMaisonJson")
     */
    public function afficheReservationMaisonJson(NormalizerInterface $normalizer, ReservationMaisonRepository $repository, $user){
      //  $reservations = $repository->findAll();
        $repo=$this->getDoctrine()->getRepository(User::class);
        $u = $repo->find($user);
        $reservations=$repository->findBy(['user'=>$u]);
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
     * @param $id
     * @param $user_id
     * @param $date_arrivee
     * @param $etats
     * @param $date_depart
     * @param $total_prix
     * @return Response
     * @Route("/ajoutReservationMaisonJSON/{id}/{user_id}/{date_arrivee}/{etats}/{date_depart}/{total_prix}/{nomCh}/{nomUser}/{hebergeur_id}", name="ajoutReservationMaisonJSON")
     */

    public function addReservationMaisonJSON(Request $request, ChambreRepository $repository, SerializerInterface $serializer, $id, $user_id, $date_arrivee, $etats, $date_depart, $total_prix, $nomCh, $nomUser, $hebergeur_id){
        $reservation = new ReservationMaison();
        $em = $this->getDoctrine()->getManager();

        $ch = $em->find(Chambre::class, $id);
        $reservation->setChambre($ch);
        $user = $em->find(User::class, $user_id);
        $reservation -> setUser($user);
        $reservation -> setDateArrivee(new \DateTime($date_arrivee));
        $reservation -> setEtats($etats);
        $reservation -> setDateDepart(new \DateTime($date_depart));
        $reservation -> setTotalPrix($total_prix);
        $reservation -> setNomChambre($nomCh);
        $reservation -> setNomUser($nomUser);
        $reservation -> setHebergeurId($hebergeur_id);

        $dateNow = new \DateTime('now');

        if($date_depart > $date_arrivee){
            $em->persist($reservation);
            $em->flush();
            $response = new Response();
            $response->setStatusCode(Response::HTTP_OK);
            $response->setContent('Réservation ajoutée avec succès');
            $response->send();
            return new JsonResponse($response);


        }else if($date_depart <= $date_arrivee){
            $response = new Response();
            $response->setStatusCode(Response::HTTP_NOT_FOUND);
            $response->setContent('Choisir une date valide');
            $response->send();
            return new JsonResponse($response);
        }
    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/UpdateReservationJSON/{id}/{etats}", name="UpdateReservationJSON", methods={"PUT"})
     *
     */

    public function updateMaisonJSON(Request $request, ReservationMaisonRepository $repository, $etats, $id)
    {
        $reservationUpdate = $repository->find($id);
        $reservationUpdate->setEtats($etats);
        $this->getDoctrine()->getManager()->flush();
        $data = [
            'status' => 200,
            'message' => 'Le réservation a bien ete mis a jour'
        ];
        return new JsonResponse($data);
    }



}