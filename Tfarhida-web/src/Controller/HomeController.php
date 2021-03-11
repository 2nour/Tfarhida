<?php

namespace App\Controller;

use App\Repository\PanierRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class HomeController extends AbstractController
{
    /**
     * @Route("/home", name="home")
     */
    public function index(PanierRepository $panierRepository): Response
    {
        $panier = $panierRepository->find(1);
        $qtt=$panier->getNbproduit();

        return $this->render('base.html.twig',[
           'items' =>$qtt
        ]);
    }
}
