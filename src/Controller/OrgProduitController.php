<?php

namespace App\Controller;

use App\Entity\Organisation;
use App\Entity\OrgProduit;
use App\Entity\Produit;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class OrgProduitController extends AbstractController
{
    /**
     * @Route("/org/produit", name="org_produit")
     */
    public function index(): Response
    {
        return $this->render('org_produit/index.html.twig', [
            'controller_name' => 'OrgProduitController',
        ]);
    }
    /**
     * @Route("/AfficheProduit", name="AfficheProduit")
     */
    public function AfficheProduit(): Response
    {

        return $this->render('org_produit/AfficheProduit.html.twig', [
            'controller_name' => 'OrgProduitController',
        ]);
    }
    /**
     * @Route("/selectProduit/{id}/{id_Org}", name="selectProduit", defaults={"id"})
     */
    public function selectProduit(\Symfony\Component\HttpFoundation\Request $request,$id,$id_Org)
    {

        $em = $this->getDoctrine()->getManager();
        //$id = $request->get('id');
        $produit = $em->find(Produit::class, $id);
        $organisation =$em->find(Organisation::class,$id_Org);
        //$orgProduit=new OrgProduit();
        //$orgProduit->setIdProduit($produit);
        //$orgProduit->setIdOrganisation( $organisation);
        $organisation->addProduit($produit);
        $em->flush();
        return $this->redirectToRoute("readOrganisation");
    }

}
