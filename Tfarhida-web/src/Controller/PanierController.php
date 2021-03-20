<?php

namespace App\Controller;

use App\Entity\Panier;
use App\Entity\Produit;
use App\Entity\Commande;

use App\Form\PanierType;
use App\Repository\PanierRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller;
use Doctrine\ORM\EntityRepository;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;


class PanierController extends AbstractController
{
    /**
     * @Route("/panierListe", name="panier_index", methods={"GET"})
     */
    public function index(PanierRepository $panierRepository): Response
    {
        return $this->render('panier/index.html.twig', [
            'paniers' => $panierRepository->findAll(),
        ]);
    }


    /**
     *
     * @Route("/panierListe", name="panierListe")
     * @return \http\Env\Response
     */
    public function afficherPanier()
    {

      $panierId=2;
      $em=$this->getDoctrine()->getManager();
      $panier=$em->find(Panier::class,$panierId);
      $query =$em->createQuery(
          "SELECT produit
         FROM  App\Entity\Produit produit
         where produit.id IN (
         select IDENTITY(commande.produit) from App\Entity\Commande commande
         where commande.panier =?1)");
        $query->setParameter(1,$panierId);
        $produits =$query->getResult();
        try {
            return $this->render("panier/panier.html.twig", ['produits'=>$produits,'panier' =>$panier]);
        } catch (\Doctrine\ORM\NoResultException $e) {
            return null;
        }



    }



}
