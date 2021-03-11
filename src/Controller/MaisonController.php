<?php

namespace App\Controller;


use App\Entity\Maison;
use App\Entity\Search;
use App\Form\MaisonsType;
use App\Repository\ChambreRepository;
use App\Repository\FavorisRepository;
use App\Repository\MaisonRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;



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
    public function afficher(MaisonRepository $repository){
       // $repo=$this->getDoctrine()->getRepository(Maison::class);
        $maison=$repository->findAll();
        return $this->render('maison/Affiche.html.twig',
        ['maison'=>$maison]);
    }

    /**
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/ajoutMaison", name="ajout")
     */
    public function addMaison(\Symfony\Component\HttpFoundation\Request $request){
        $maison= new Maison();
        $form=$this->createForm(MaisonsType::class, $maison);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $file =$maison->getPhoto();
            $filename=md5(uniqid()).'.'.$file->guessExtension();

            // sauvgarder l'image dans le dossier indiquer par le param 'product_image_directory' dans services.yaml
            try {
                $file->move($this->getParameter('product_image_directory'), $filename);
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
        return $this->render('maison/newMaison.html.twig',
            ['f'=>$form->createView()]
        );
    }


    /**
     * @param MaisonRepository $repository
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/read", name="read", defaults={"id"})
     */
    public function afficheMaison(MaisonRepository $repository, \Symfony\Component\HttpFoundation\Request $request){
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $m = $em->find(Maison::class, $id);
        $maison=$repository->find($m);
       // return $this->redirectToRoute("add",["idMaison"=>$maison->getId()]);
        return $this->render('maison/Detail.html.twig',
            ['maison'=>$maison]);
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
                $file->move($this->getParameter('product_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }

            $em=$this->getDoctrine()->getManager();
            $maison->setPhoto($filename);
            $em->flush();
            return $this->redirectToRoute("aff");
        }
        return $this->render("maison/Update.html.twig",[
            'f'=>$form->createView()
        ]);
    }

}
