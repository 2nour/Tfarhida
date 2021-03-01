<?php

namespace App\Controller;

use App\Entity\Produit;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProduitController extends AbstractController
{
    /**
     * @Route("/produit", name="produit")
     */
    public function index(): Response
    {
        return $this->render('produit/index.html.twig', [
            'controller_name' => 'ProduitController',
        ]);
    }

    /**
     * @param Produit $produit
     * @Route("/ajouterProduit/{}", name="ajouterProduit")
     */
    public function ajouterProduit(Produit $produit)
    {
       return $this->render('produit/ajouter.html.twig', [
           'controller_name' => 'ProduitController',]);

    }
}
