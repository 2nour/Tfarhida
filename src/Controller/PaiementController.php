<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

class PaiementController extends AbstractController
{
    /**
     * @Route("/paiement", name="paiement")
     */
    public function index(): Response
    {
        return $this->render('paiement/index.html.twig', [
            'controller_name' => 'PaiementController',
        ]);
    }

    /**
     * @Route("/success", name="success")
     */
    public function success(): Response
    {
        return $this->render('organisation/SuiviOrganisation.html.twig', [
            'controller_name' => 'PaiementController',
        ]);
    }
    /**
     * @Route("/error", name="error")
     */
    public function error(): Response
    {
        return $this->render('payement/error.html.twig', [
            'controller_name' => 'PaiementController',
        ]);
    }

    /**
     * @Route("/create-checkout-session", name="checkout")
     */
    public function  checkout( ): Response
    {
        \Stripe\Stripe::setApiKey('sk_test_51IZiOALBv1NOmN8DgFnPewtVyWxH67WR151V4bAUFQpts31ugQIWu7fNc5OJqrp7hydGlf2ZNYkQKen0TYEtWh3U00qCljgwkj');
        $session = \Stripe\Checkout\Session::create([
            'payment_method_types' => ['card'],
            'line_items' => [[
                'price_data' => [
                    'currency' => 'usd',
                    'product_data' => [
                        'name' => 'T-shirt',
                    ],
                    'unit_amount' => 2000,
                ],
                'quantity' => 1,
            ]],
            'mode' => 'payment',
            'success_url' => $this->generateUrl('success',[], UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url' => $this->generateUrl('error',[], UrlGeneratorInterface::ABSOLUTE_URL),
        ]);
        return new JsonResponse([ 'id' => $session->id ]);
    }
}
