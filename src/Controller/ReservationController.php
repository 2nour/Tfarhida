<?php

namespace App\Controller;
use App\Controller\Post;
use App\Entity\Randonnee;
use App\Entity\Search;
use App\Form\ReservationType;
use App\Entity\Reservation;
use App\Form\SearchType;
use App\Notifications\CreationReservationNotification;
use App\Repository\RandonneeRepository;
use App\Repository\ReservationRepository;
use Doctrine\ORM\Tools\Pagination\Paginator;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;

class ReservationController extends AbstractController
{

    /**
     * @Route("/", name="reservation")
     */
    public function home()
    {
        return $this->render('reservation\index.html.twig');
    }
    /**
     * @Route("/reservationAdmin", name="reservationAdmin")
     */
    public function Admin(ReservationRepository $repository)
    {
        if(isset($_GET["idAffected"])){
            $repository = $this->getDoctrine()->getRepository(Reservation::class);
            $repository->AffecterReservation();
        }
        if(isset($_GET["idRefuser"])){
            $repository = $this->getDoctrine()->getRepository(Reservation::class);
            $repository->RefuserReservation();
        }
        $resrvation=$repository->findAll();
        return $this->render('reservation/reservationAdmin.html.twig',
            ['reservation'=>$resrvation]);
    }

    /**
     * @Route("/reservation/show/{id}", name="reservation_show")
     */
    public function show($id) {
        $reservation = $this->getDoctrine()->getRepository(Reservation::class)->find($id);

        return $this->render('reservation/show.html.twig', array('reservation' => $reservation));
    }



    /**
     * @param ReservationRepository $repository
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/AfficheReservationRandonne", name="AfficheReservation_randonne")
     *
     */
    public function Affiche(ReservationRepository $repository){

        if(isset($_POST["submit"])){
            $repository = $this->getDoctrine()->getRepository(Reservation::class);
            $reservation=$repository->findRandonnee($_POST["search"]);
            return $this->render('reservation/Afficheres.html.twig',
                ['reservation'=>$reservation]);
        }
        $reservation=$repository->findAll();
        return $this->render('reservation/Afficheres.html.twig',
            ['reservation'=>$reservation]);

    }


    /**
     *
     * @Route("/reservation/listr", name="liste_reservation")
     * Method{{"GET","POST"}}
     */

    public function listreservation(ReservationRepository $reservationRepository)
    {
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        $reservation = $reservationRepository->findAll();


        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('reservation/listereservation.html.twig',[
            'reservation' => $reservation,

        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => false
        ]);
    }





    /**
     * @Route("/reservation/delete/{id}",name="deletereservation")

    public function delete(Request $request, $id) {
        $reservation = $this->getDoctrine()->getRepository(Reservation::class)->find($id);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($reservation);
        $entityManager->flush();

        $response = new Response();
        $response->send();
        return $this->redirectToRoute('AfficheReservation');
    }


    /**
     * @Route("/reservation/delete/{id}",name="deletereservation")
     *
     */

    public function delete($id, ReservationRepository $repository) {
        $reservation = $repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($reservation);
        $em->flush();
        return $this->redirectToRoute('AfficheReservation_randonne');

    }


    /**
     *
     * @Route("/reservation/Add", name="add_reservation")
     * Method{{"GET","POST"}}
     */

    public function addReservation(Request $request,ReservationRepository $repository)
    {
        $reservation= new Reservation();
        $form =$this->createFormBuilder($reservation);
        $form = $this->createForm(ReservationType::class,$reservation);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $reservation = $form->getData();
            $reservation->setEtat("En cours");
            $em = $this->getDoctrine()->getManager();
            $em->persist($reservation);
            $em->flush();
            $resrvation=$repository->findAll();
            return $this->render('reservation/Afficheres.html.twig',
                ['reservation'=>$resrvation]);
        }
        return $this->render('reservation/Addres.html.twig',[
            'form'=>$form->createView()
        ]);
    }

    /**
     *
     * @Route("/reservation/edit/{id}", name="modif")
     * Method{{"GET","POST"}}
     */
    public function UpdateReservation(Request $request, $id,ReservationRepository $repository)
    {
        $reservation = new Reservation();
        $reservation = $this->getDoctrine()->getRepository(Reservation::class)->find($id);

        $form = $this->createForm(ReservationType::class,$reservation);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){

            $em = $this->getDoctrine()->getManager();
            $em->flush();
            $resrvation=$repository->findAll();
            return $this->render('reservation/Afficheres.html.twig',
                ['reservation'=>$resrvation]);
        }
        return $this->render('reservation/editres.html.twig',[
            'form'=>$form->createView()
        ]);
    }

    /**
     * @Route("/loca", name="loca")
     */
    public function index()
    {
        return $this->render('reservation/localisation.html.twig');
    }

}
