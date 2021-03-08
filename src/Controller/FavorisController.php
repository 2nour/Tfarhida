<?php

namespace App\Controller;

use App\Entity\Favoris;
use App\Form\FavorisType;
use App\Entity\Maison;
use App\Repository\FavorisRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class FavorisController extends AbstractController
{
    /**
     * @Route("/favoris", name="favoris")
     */
    public function index(): Response
    {
        return $this->render('favoris/index.html.twig', [
            'controller_name' => 'FavorisController',
        ]);
    }


    // public function affiche(FavorisRepository $repository){
    // $repo=$this->getDoctrine()->getRepository(Maison::class);
    // $favoris=$repository->findAll();
    // return $this->render('favoris/Affiche.html.twig',
    //    ['favoris'=>$favoris]);
    // }

    /**
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/favoris", name="favoris", defaults={"id"})
     */

    public function addFavoris(\Symfony\Component\HttpFoundation\Request $request, FavorisRepository $repository){
        $favoris = new Favoris();
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $mai = $em->find(Maison::class, $id);
        $favoris->setMaison($mai);
        $em->persist($favoris);
        $em->flush();
        return $this->redirectToRoute("aff");


    }
}
