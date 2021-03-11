<?php

namespace App\Controller;


use App\Entity\Produit;
use App\Entity\Panier;
use App\Repository\PanierRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class PanierController extends AbstractController
{
    /**
     * @Route("/panier", name="panier")
     */
    public function index(): Response
    {
        return $this->render('panier/index.html.twig', [
            'controller_name' => 'PanierController',
        ]);
    }


    /**
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     * @Route("produitPanier", name="produitPanier",defaults={"id"})
     */
    public function ajouterProduitauPanier(\Symfony\Component\HttpFoundation\Request $request)
    {

        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $produit = $em->find(Produit::class, $id);
        $panier =$em->find(Panier::class,1);


        $panier->addProduit($produit);
        $qtt=$panier->getNbproduit() + 1;
        $panier->setNbproduit($qtt);
        $somme= $produit->getPrix() * $produit->getQuantite() +$panier->getSomme();
        $panier->setSomme($somme);

        $em->persist($panier);
        $em->persist($produit);
        $em->flush();
        return $this->redirectToRoute("produitListe");


        return $this->render('produit/liste.html.twig');

    }



    /**
     * @param PanierRepository $repo
     * @Route("/panierListe", name="panierListe")
     * @return \http\Env\Response
     */
    public function afficherlisteProduit(PanierRepository $repo)
    {

        $panier=$repo->findAll();
        $produits= new ArrayCollection();
        $produits= $panier->getProduits();

        return $this->render("panier/panier.html.twig", ['produits'=>$produits]);

    }




}
