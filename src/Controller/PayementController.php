<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class PayementController extends AbstractController
{
    /**
     * @Route("/payement", name="payement")
     */
    public function index(): Response
    {
        return $this->render('paiement/index.html.twig', [
            'controller_name' => 'PayementController',
        ]);
    }
}
