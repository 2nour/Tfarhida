<?php

namespace  App\Controller;

use App\Entity\commentaire;
use App\Entity\Maison;
use App\Entity\User;
use App\Form\CommentaireType;
use App\Form\MaisonsType;
use App\Repository\ChambreRepository;
use App\Repository\FavorisRepository;
use App\Repository\MaisonRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;


class MaisonController extends AbstractController
{

    /**
     * @Route("/maison", name="maison")
     */

    public function index(): Response
    {
        return $this->render('maison/index.html.twig', [
            'controller_name' => 'MaisonController',
        ]);
    }

    /**
     * @param MaisonRepository $repository
     * @return Response
     * @Route("/afficheMaison", name="aff")
     */
    public function afficher(MaisonRepository $repository, UserRepository $userRepository){
        // $repo=$this->getDoctrine()->getRepository(Maison::class);
        $user=$this->getUser()->getUsername();
        //$u=$em->getRepository(User::class)->findBy(array('username'=>$user));
        $u=$userRepository->findOneBy(array('username'=>$user));
        $userId=$userRepository->find($u->getId());
        $maison=$repository->findAll();
        return $this->render('maison/Affiche.html.twig',
            ['maison'=>$maison, 'user'=>$userId]);
    }

    /**
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     * @Route("/addMaison", name="ajout")
     */
    public function addmaison(\Symfony\Component\HttpFoundation\Request $request){
        $maison= new Maison();
        $form=$this->createForm(MaisonsType::class, $maison);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $file =$maison->getPhoto();
            $filename=md5(uniqid()).'.'.$file->guessExtension();

            // sauvgarder l'image dans le dossier indiquer par le param 'product_image_directory' dans services.yaml
            try {
                $file->move($this->getParameter('maison_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }

            $em=$this->getDoctrine()->getManager();

            // sauvgarder uniquement le nom de l'image dans la bdd
            $maison->setPhoto($filename);
            $em->persist($maison);
            $em->flush();
            return $this->redirectToRoute("aff");
            //   return $this->redirectToRoute("add",["idMaison"=>$maison->getId()]);
        }
        return $this->render('maison/addMaison.html.twig',
            ['f'=>$form->createView()]
        );
    }

    /**
     * @param MaisonRepository $repository
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/read", name="read", defaults={"id"})
     */
    public function afficheMaison(MaisonRepository $repository, \Symfony\Component\HttpFoundation\Request $request, UserRepository $userRepository){
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $mai = $em->find(Maison::class, $id);
        $maison=$repository->find($mai);
        $nbr=$maison->getNbrComment();
        $comment = new commentaire();
        $user=$this->getUser()->getUsername();
        $u=$userRepository->findOneBy(array('username'=>$user));
        $userId=$userRepository->find($u->getId());
        $uesr2= $userId->getUsername();
        $form=$this->createForm(CommentaireType::class, $comment);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {
            $user=$this->getUser()->getUsername();
            //$u=$em->getRepository(User::class)->findBy(array('username'=>$user));
            $comment->setMaison($mai);
            $comment->setUser($userId);
            $em->persist($comment);
            $maison->setNbrComment($nbr+1);
            $em->flush();
            return $this->redirectToRoute("read",["id"=>$maison->getId()]);
        }
        // return $this->redirectToRoute("add",["idMaison"=>$maison->getId()]);
        if($maison->getCommentaires())
        {
            $comment = $maison->getCommentaires();
        }
        return $this->render('maison/Detail.html.twig',
            ['maison'=>$maison, 'comment'=>$comment, 'f'=>$form->createView(), 'user'=>$uesr2]);
    }



    /**
     * @param MaisonRepository $repository
     * @param $id
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route("/sup/{id}",name="d")
     */
    public function delete(MaisonRepository $repository,$id){
        $maison=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($maison);
        $em->flush();
        return $this->redirectToRoute("aff");
    }

    /**
     * @param Request $request
     * @param $id
     * @param MaisonRepository $repository
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     * @Route("/update", name="u" , defaults={"idM"})
     */

    public function update(Request $request,MaisonRepository $repository){
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("idM");
        $mai = $em->find(Maison::class, $id);
        $maison=$repository->find($mai);
        //   $maison=$repository->find($id);
        $form=$this->createForm(MaisonsType::class,$maison);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $file =$maison->getPhoto();
            $filename=md5(uniqid()).'.'.$file->guessExtension();

            // sauvgarder l'image dans le dossier indiquer par le param 'product_image_directory' dans services.yaml
            try {
                $file->move($this->getParameter('maison_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }

            $em=$this->getDoctrine()->getManager();
            $maison->setPhoto($filename);
            $em->flush();
            return $this->redirectToRoute("read",["id"=>$maison->getId()]);
        }
        return $this->render("maison/Update.html.twig",[
            'f'=>$form->createView(), 'maison'=>$maison
        ]);
    }


    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/searchMaison", name="searchMaison")
     */
    public function searchMaison(Request $request, NormalizerInterface $normalizer)
    {
        $repository = $this->getDoctrine()->getRepository(Maison::class);
        $requestString=$request->get('searchValue');
        $maisons = $repository->findMaisonByNomOrAdresse($requestString);
        $jsonContent = $normalizer->normalize($maisons, 'json',['groups'=>'maisons']);
        $retour=json_encode($jsonContent);
        return new Response($retour);
    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param MaisonRepository $repository
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/afficheMJson", name="afficheMJson")
     */
    public function afficheJson(NormalizerInterface $normalizer, MaisonRepository $repository){
        $maisons = $repository->findAll();
        $jsonContent = $normalizer->normalize($maisons, 'json',['groups'=>'maisons']);
        $retour=json_encode($jsonContent);
        return new Response($retour);

    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/deleteMaisonJSON/{id}", name="deleteMaisonJSON")
     */
    public function deleteMaisonJSON(Request $request,NormalizerInterface $normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
       // $id = $request->get("idM");
        $maison = $em->getRepository(Maison::class)->find($id);
        $em->remove($maison);
        $em->flush();
        $jsonContent = $normalizer->normalize($maison, 'json', ['groups' => 'maison']);
        return new Response("Maison supprimé".json_encode($jsonContent));
    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/UpdateMaisonJSON/{id}", name="UpdateMaisonJSON")
     */
    public function updateMaisonJSON(Request $request,NormalizerInterface $normalizer,$id)
    {
        $em = $this->getDoctrine()->getManager();
        $maison = $em->getRepository(Maison::class)->find($id);
        $maison->setNom($request->get('nom'));
        $maison->setAdresse($request->get('adresse'));
        $maison->setNbrChambre($request->get('nbr_chambre'));
        $maison->setTel($request->get('tel'));
        $maison->setPhoto($request->get('photo'));
        $maison->setDescription($request->get('description'));

        $em->flush();
        $jsonContent = $normalizer->normalize($maison, 'json', ['groups' => 'maison']);
        return new Response("Maison modifiée".json_encode($jsonContent));
    }

    /**
     * @param Request $request
     * @param SerializerInterface $serializer
     * @return Response
     * @Route("/ajoutMaisonJSON", name="ajoutMaisonJSON")
     */

    public function addMaisonJSON(Request $request, SerializerInterface $serializer){
        $em = $this->getDoctrine()->getManager();
        $content = $request->getContent();
        $data = $serializer->deserialize($content, Maison::class, 'json');
        $em->persist($data);
        $em->flush();
        return new Response('maison added successfully');
    }


}