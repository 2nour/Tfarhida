<?php

namespace App\Controller;

use App\Entity\Panier;
use App\Entity\Produit;
use App\Entity\Commande;

use App\Entity\User;
use App\Form\CommandeType;
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
     * @Route("/pan", name="panier_index", methods={"GET"})
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
     * @return Response
     */
    public function afficherPanier(Request $request)
    {

      $em=$this->getDoctrine()->getManager();
        $username=$this->getUser()->getUsername();
        $userr=$em->getRepository(User::class)->findOneBy(array('username'=>$username));
        $user=$em->find(User::class,$userr->getId());

        $panierId=$user->getPanier();
        if($panierId== null){
            $panier =new Panier();
            $panier->setUser($user);
            $panier->setNbproduit(0);
            $panier->setSomme(0);
            $em->persist($panier);
            $em->flush();
            $panierId=$user->getPanier();


        }

        $panier=$em->find(Panier::class,$panierId);
        $query =$em->createQuery(
          "SELECT produit
         FROM  App\Entity\Produit produit
         where  produit.id IN (
         select IDENTITY(commande.produit) from App\Entity\Commande commande
         where commande.panier =?1)");
        $query->setParameter(1,$panierId);
        $produits =$query->getResult();
        $commandes=$em->getRepository(Commande::class)->findAll();



        try {
            return $this->render("panier/panier.html.twig", ['produits'=>$produits,'panier' =>$panier,'commandes'=>$commandes
            ]);
        } catch (\Doctrine\ORM\NoResultException $e) {
            return null;
        }



    }



}
