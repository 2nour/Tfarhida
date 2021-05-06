<?php

namespace App\Controller;

use App\Entity\Materiel;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class MaterielController extends AbstractController
{
    /**
     * @Route("/materiel", name="materiel")
     */
    public function index(): Response
    {
        return $this->render('materiel/index.html.twig', [
            'controller_name' => 'MaterielController',
        ]);
    }
    /**
     * @Route ("/selectMateriel", name="selectMateriel")
     */

    public function selectMateriel (Request $request)
    {

        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $produit = $em->find(Produit::class, $id);
        $panier =$em->find(Panier::class,2);

        $materiel=new Materiel();
        $materiel->setProduit($produit);
        $materiel->setPanier($panier);
        $materiel->getQuantiteProduit(1);
        $panier->setNbproduit($panier->getNbproduit()+1);
        $materiel->setPrixcommande($produit->getPrix());
        $panier->setSomme($panier->getSomme() + $materiel->getPrixcommande());

        $em->persist($materiel);
        $em->flush();
        return $this->redirectToRoute("readOrganisation");


    }

}
