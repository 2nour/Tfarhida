<?php

namespace App\Controller;

use App\Entity\Organisation;
use App\Entity\OrgProduit;
use App\Form\OrganisationType;
//use http\Env\Request;
use App\Repository\OrganisationRepository;
use App\Repository\OrgProduitRepository;
use phpDocumentor\Reflection\Type;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\EventDispatch\EventDispatcherInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;

class OrganisationController extends AbstractController
{
    /**
     * @Route("/organisation", name="organisation")
     */
    public function index(): Response
    {
        return $this->render('base.html.twig', [
            'controller_name' => 'OrganisationController',
        ]);
    }

    /**
     * @Route ("/addorganisation", name="addorganisation")
     */
    public function addOrganisation(Request $request, OrgProduitRepository $repository)
    {
        $organisation=new Organisation();
        $form = $this->createForm(OrganisationType::class, $organisation);
        $form->handleRequest($request);
        if ($form->isSubmitted() ){
            $organisation ->setetat("En attente");
            $em = $this->getDoctrine()->getManager();
            $em->persist($organisation);
            $em->flush();
            $this->addFlash(
                'notice',
                'Une demande de programme a été effectuée avec succée  !!!'
            );
            return $this->redirectToRoute('produitListes',['id'=> $organisation->getId()]);
        }else {

            return $this->render("organisation/index.html.twig",['form'=>$form->createView()]);
    }}
    /**
     * @Route("/readOrganisation",name="readOrganisation")
     */
    public function readOrganisation(Request $request){

        $listOrganisation=$this->getDoctrine()
        ->getRepository(Organisation::class)
            ->findAll();
        return $this->render('organisation/SuiviOrganisation.html.twig',
            ['controller_name' => 'OrganisationController','organisation'=>$listOrganisation]);
    }



    /**
     * @Route("deleteOrganisation/{id}",name="deleteOrganisation")
     */
public function deleteOrganisation($id){
    $organisation=$this->getDoctrine()
    ->getRepository(Organisation::class)->find($id);
     $em=$this->getDoctrine()->getManager();
     $em->remove($organisation);
     $em->flush();
return $this->redirectToRoute('readOrganisation');
}



    /**
     * @Route ("/updateOrganisation/{id}", name="updateOrganisation")
     */
    public function updateOrganisation(Request $request, $id)
    {

            $organisation = $this->getDoctrine()
            ->getRepository(Organisation::class)
                ->find($id);
        $form = $this->createForm(OrganisationType::class, $organisation);
        $form->handleRequest($request);
        if ($form->isSubmitted() && ($form->isValid()) ){
            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('readOrganisation');
        }else {

            return $this->render("organisation/updateOrganisation.html.twig",['form'=>$form->createView()]);
        }}

    /**
     * @Route("/ProgrammeAdmin", name="ProgrammeAdmin")
     */
    public function ProgrammeAdmin(OrganisationRepository  $repository)
    {
        if(isset($_GET["idAffected"])){
            $repository = $this->getDoctrine()->getRepository(Organisation::class);
            $repository->AccepterProgramme();
        }
        if(isset($_GET["idRefuser"])){
            $repository = $this->getDoctrine()->getRepository(Organisation::class);
            $repository->RefuserProgramme();
        }
        $organisation=$repository->findAll();
        return $this->render('organisation/programmeAdmin.html.twig',
            ['organisation'=>$organisation]);
    }
//json
    /**
     * @param Request $request
     * @param SerializerInterface $serializer
     * @return Response
     * @Route("/addOrganisationJson", name="addOrganisationJson")
     */
    public function addOrganisationJson(Request $request, SerializerInterface $serializer){
        $em=$this->getDoctrine()->getManager();
        $content=$request->getContent();
        $data =$serializer->deserialize($content, Organisation::class, 'json');
        $em->persist($data);
        $em->flush();
        return new Response('Organisation ajoutée avec succée');
    }

    /**
     * @Route("deleteOrganisationJson/{id}",name="deleteOrganisationJson")
     */
    public function deleteOrganisationJson (NormalizerInterface $normalizer,$id){
        $organisation=$this->getDoctrine()
            ->getRepository(Organisation::class)->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($organisation);
        $em->flush();
        $jsonContent =$normalizer->normalize($organisation,'json',['groups'=>'organisation']);
        return new Response("Organisation supprimée".json_encode($jsonContent));
    }
    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/readOrganisationJson",name="readOrganisationJson")
     */
    public function readOrganisationJson(Request $request, NormalizerInterface $normalizer){

        $listOrganisation=$this->getDoctrine()->getRepository(Organisation::class)
            ->findAll();
        $json=$normalizer->normalize($listOrganisation,'json',['groups'=>'$listOrganisations']);
        $retour=json_encode($json);
        return new Response($retour);
    }
}



