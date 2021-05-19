<?php

namespace  App\Controller;

use App\Entity\Chambre;
use App\Entity\Maison;
use App\Entity\Photo;
use App\Form\ChambreType;
use App\Repository\ChambreRepository;
use App\Repository\MaisonRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;

class ChambreController extends AbstractController
{
    /**
     * @Route("/chambre", name="chambre")
     */
    public function index(): Response
    {
        return $this->render('chambre/index.html.twig', [
            'controller_name' => 'ChambreController',
        ]);
    }

    /**
     * @param ChambreRepository $repository
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/Chambre",name="mais")
     */

    public function affiche(ChambreRepository $repository,  \Symfony\Component\HttpFoundation\Request $request){
        $repo=$this->getDoctrine()->getRepository(Maison::class);
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $m = $repo->find($id);
        $chambre=$repository->findBy(['maison'=>$m]);
        return $this->render('chambre/Affiche.html.twig',
            ['chambre'=>$chambre, 'maison'=>$m]);
    }

    /**
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/addChambre", name="add" , defaults={"id"})
     */

    public function addChambre(\Symfony\Component\HttpFoundation\Request $request, MaisonRepository $repository){
        $chambre = new Chambre();
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $mai = $em->find(Maison::class, $id);
        $maison=$repository->find($mai);
        $form=$this->createForm(ChambreType::class, $chambre);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {
            $file =$chambre->getPhoto();
            $filename=md5(uniqid()).'.'.$file->guessExtension();

            // sauvgarder l'image dans le dossier indiquer par le param 'product_image_directory' dans services.yaml
            try {
                $file->move($this->getParameter('chambre_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }

            $chambre->setMaison($mai);
            // sauvgarder uniquement le nom de limage dans la bdd
            $chambre->setPhoto($filename);
            $user=$this->getUser()->getUsername();
            $chambre->setUserId($user);
            $em->persist($chambre);
            $em->flush();
            return $this->redirectToRoute("add",["id"=>$maison->getId()]);

        }

        return $this->render('chambre/newChambre.html.twig',
            ['f'=>$form->createView(), 'maison'=>$maison]
        );
    }

    /**
     * @param ChambreRepository $repository
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/afficheChambre", name="h", defaults={"id"})
     */

    public function afficheCh(ChambreRepository $repository, \Symfony\Component\HttpFoundation\Request $request){
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $m = $em->find(Chambre::class, $id);
        $chambre=$repository->find($m);
        return $this->render('chambre/chambre.html.twig',
            ['chambre'=>$chambre]);
    }

    /**
     * @param ChambreRepository $repository
     * @param $id
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route("/dChambre/{id}",name="dChambre")
     */
    public function delete(ChambreRepository $repository,$id, Request $request){
        $em=$this->getDoctrine()->getManager();
        $chambre=$repository->find($id);
        $maison = $chambre->getMaison();
        $em->remove($chambre);
        $em->flush();
        return $this->redirectToRoute("mais",["id" => $maison->getId()]);
    }

    /**
     * @param Request $request
     * @param ChambreRepository $repository
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     * @Route("/upCh", name="upCh", defaults={"idCh"})
     */
    public function update(Request $request,ChambreRepository $repository){
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("idCh");
        $ch = $em->find(Chambre::class, $id);
        $chambre=$repository->find($ch);
        //   $maison=$repository->find($id);
        $form=$this->createForm(ChambreType::class,$chambre);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $file =$chambre->getPhoto();
            $filename=md5(uniqid()).'.'.$file->guessExtension();

            // sauvgarder l'image dans le dossier indiquer par le param 'product_image_directory' dans services.yaml
            try {
                $file->move($this->getParameter('chambre_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }

            $em=$this->getDoctrine()->getManager();
            $chambre->setPhoto($filename);
            $em->flush();
            return $this->redirectToRoute("h",["id"=>$chambre->getId()]);
        }
        return $this->render("chambre/Update.html.twig",[
            'f'=>$form->createView(), 'chambre'=>$chambre
        ]);
    }

    //Partie JSON

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param MaisonRepository $repository
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/afficheChJson/{maison_id}", name="afficheChJson")
     */
    public function afficheJson(Request $request, NormalizerInterface $normalizer, ChambreRepository $repository, $maison_id){

        $repo=$this->getDoctrine()->getRepository(Maison::class);
        $m = $repo->find($maison_id);
        $chambres=$repository->findBy(['maison'=>$m]);
        $jsonContent = $normalizer->normalize($chambres, 'json',['groups'=>'chambres']);
        $retour=json_encode($jsonContent);
        return new Response($retour);
    }

    /**
     * @param NormalizerInterface $normalizer
     * @param ChambreRepository $repository
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/afficheChambreJson", name="afficheChambreJson")
     */
    public function afficheChJson(NormalizerInterface $normalizer, ChambreRepository $repository){
        $chambres = $repository->findAll();
        $jsonContent = $normalizer->normalize($chambres, 'json',['groups'=>'$chambres']);
        $retour=json_encode($jsonContent);
        return new Response($retour);
    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/deleteChambreJSON/{id}", name="deleteChambreJSON")
     */
    public function deleteChambreJSON(Request $request,NormalizerInterface $normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        // $id = $request->get("idM");
        $chambre = $em->getRepository(Chambre::class)->find($id);
        $em->remove($chambre);
        $em->flush();
        $jsonContent = $normalizer->normalize($chambre, 'json', ['groups' => 'chambre']);
        return new Response("Chambre supprimé".json_encode($jsonContent));
    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/UpdateChambreJSON/{id}", name="UpdateChambreJSON")
     */
    public function updateChambreJSON(Request $request,NormalizerInterface $normalizer,$id)
    {
        $em = $this->getDoctrine()->getManager();
        $chambre = $em->getRepository(Chambre::class)->find($id);
        if (empty($chambre)) {
            return new JsonResponse(['message' => 'Place not found'], Response::HTTP_NOT_FOUND);
        }

        $form = $this->createForm(ChambreType::class, $chambre);

        $form->submit($request->request->all());

        if ($form->isValid()) {
            $em = $this->get('doctrine.orm.entity_manager');
            // l'entité vient de la base, donc le merge n'est pas nécessaire.
            // il est utilisé juste par soucis de clarté
            $em->merge($chambre);
            $em->flush();
            return $chambre;
        } else {
            return $form;
        }
    }


    /**
     * @param Request $request
     * @param SerializerInterface $serializer
     * @return Response
     * @Route("/ajoutChambreJSON/{maison_id}/{nom}/{typeLit}/{nbr_pers}/{photo}/{prix}", name="ajoutChambreJSON")
     */

    public function addChambreJSON(Request $request, SerializerInterface $serializer, $maison_id, $nom, $typeLit, $nbr_pers, $photo, $prix){
        $chambre = new Chambre();
        $em = $this->getDoctrine()->getManager();
     //   $chambre->setMaison($idM);
        $mai = $em->find(Maison::class, $maison_id);
        $chambre->setMaison($mai);
        $chambre->setNom($nom);
        $chambre->setTypeLit($typeLit);
        $chambre->setNbrPers($nbr_pers);
        $chambre->setPhoto($photo);
        $chambre->setPrix($prix);
        $em->persist($chambre);
        $em->flush();

        return new Response('Chambre added successfully');
    }


}