<?php


namespace App\Controller;


use App\Entity\Reservation;
use App\Form\ReservationType;
use App\Repository\ReservationRepository;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;

class ApiReservationController extends AbstractController
{
    /**
     * @param ReservationRepository $repository
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/api/index", name="AfficheReservation_api")
     *
     */
    public function Affiche(ReservationRepository $repository){
        $repository = $this->getDoctrine()->getRepository(Reservation::class);
        $reservation=$repository->findAll();

        if(isset($_POST["submit"])){
            $repository = $this->getDoctrine()->getRepository(Reservation::class);
            $reservation=$repository->findRandonnee($_POST["search"]);
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize($reservation);
            return new JsonResponse($formatted);
        }
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reservation);
        return new JsonResponse($formatted);

    }
    /**
     *
     * @Route("/api/reservation/listr", name="liste_reservation")
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
        $response=array();
        array_push($response,['code'=>200,'respose'=>'success']);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reservation);
        return new JsonResponse($formatted);
    }
    /**
     * @Route("/api/reservation/delete/{id}",name="deletereservation_api")
     */
    public function delete(Request $request, $id) {
        $reservation = $this->getDoctrine()->getRepository(Reservation::class)->find($id);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($reservation);
        $entityManager->flush();


        $response=array();
        array_push($response,['code'=>200,'respose'=>'success','id'=>$id]);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reservation);
        return new JsonResponse($formatted);
    }
    /**
     *
     * @Route("/api/reservation/Add/{numr}/{dater}/{ob}/{mon}/{nbr}/{id}", name="add_reservation_api")
     * Method{{"POST"}}
     */

    public function addReservation(Request $request,$numr,$dater,$ob,$mon,$nbr,$id)
    {
        $reservation= new Reservation();
        $reservation->setNumreservation($numr);
        $reservation->setDatereservation($dater);
        $reservation->setObservation($ob);
        $reservation->setMontant($mon);
        $reservation->setNombrepersonne($nbr);
        $reservation->setIdRandonnee($id);
        $reservation->setEtat("en cours");
        $em=$this->getDoctrine()->getManager();




        $em->persist($reservation);
        $em->flush();
        $response=array();
        array_push($response,['code'=>200,'respose'=>'success']);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reservation);
        return new JsonResponse($formatted);
    }
    /**
     *
     * @Route("/api/reservation/edit/{id}", name="modif_api")
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
            $response=array();
            array_push($response,['code'=>200,'respose'=>'success']);
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize($reservation);
            return new JsonResponse($formatted);
        }
        $response=array();
        array_push($response,['code'=>200,'respose'=>'success']);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($reservation);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("api/reservationmail", name="api_reservation_mail")
     */

    public function getReservationByEmail(Request $request) {

        $email = $request->get('email');
        $reservation = $this->getDoctrine()->getManager()->getRepository(Reservation::class)->findOneBy(['email'=>$email]);
        if($reservation) {
            $password = $reservation->getPassword();
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize($password);
            return new JsonResponse($formatted);
        }
        return new Response("Reservation not found");




    }
}