<?php

namespace App\Controller;

use App\Data\SearchData;
use App\Entity\Commande;
use App\Entity\Organisation;
use App\Entity\Panier;
use App\Entity\Produit;
use App\Entity\commentaire;
use App\Form\CommentType;

use App\Form\AjouterProduitFormType;
use App\Form\SearchForm;
use App\Repository\CommandeRepository;
use App\Repository\PanierRepository;
use App\Repository\ProduitRepository;
use Knp\Component\Pager\Pagination\PaginationInterface;
use Knp\Component\Pager\PaginatorInterface;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Component\HttpFoundation\Request;
use phpDocumentor\Reflection\Types\Integer;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
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
     * @Route("produitListe/{id}", name="produitListe")
     * @return Response
     */
    public function afficherlisteProduit(Request $request,$id)
    {
       $em = $this->getDoctrine()->getManager();
       $produits=$em->getRepository(Produit::class)->findAll();
        $org = $em->getRepository(Organisation::class)->find($id);
        return $this->render('org_produit/AfficheProduit.html.twig',
            ['controller_name' => 'ProduitController','produits'=> $produits,'org'=>$org]);

    }


}

