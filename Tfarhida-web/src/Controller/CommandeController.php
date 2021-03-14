<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Entity\Produit;
use App\Entity\Panier;

use App\Form\CommandeType;
use App\Repository\CommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Doctrine\ORM\Query;


class CommandeController extends AbstractController
{

    /**
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     * @Route("/ajouterCommande", name="ajouterCommande",defaults={"id"})
     *
     */
    public function ajouteruneCommande(\Symfony\Component\HttpFoundation\Request $request)
    {

        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $produit = $em->find(Produit::class, $id);
        $panier =$em->find(Panier::class,2);

        $commande=new Commande();
        $commande->setProduit($produit);
        $commande->setPanier($panier);
        $commande->getQuantiteProduit(1);
        $panier->setNbproduit($panier->getNbproduit()+1);
        $commande->setPrixcommande($produit->getPrix());
        $panier->setSomme($panier->getSomme() + $commande->getPrixcommande());

        $em->persist($commande);
        $em->flush();
        return $this->redirectToRoute("produitListe");


    }


    /**
     * @param CommandeRepository $repo,$id
     * @Route("suppPanier/{id}", name="suppPanier")
     * @return \http\Env\Response
     */
    public function supprimerProduit( $id,CommandeRepository $repo )
    {
        $em=$this->getDoctrine()->getManager();
        $emm=$this->getDoctrine()->getRepository(Commande::class);
        $produit=$em->find(Produit::class,$id);
        $panierId=2;
        $panier=$em->find(Panier::class,$panierId);
        $commande=$emm->findOneBy(array('produit'=>$produit,'panier'=>$panier));

        $panier->setSomme($panier->getSomme() - $produit->getPrix() * 1);
        $panier->setNbproduit($panier->getNbproduit() - 1);
        $em->persist($panier);
        $em->remove($commande);
        $em->flush();

        return $this->redirectToRoute("panierListe");


    }






    /**
     * @param CommandeRepository $repo,$id
     * @Route("/modifierCommande", name="modifierCommande",defaults={"id"})
     * @return \http\Env\Response
     */
    public function modifiercommande( $id,CommandeRepository $repo )
    {
        $em=$this->getDoctrine()->getManager();
        $emm=$this->getDoctrine()->getRepository(Commande::class);
        $produit=$em->find(Produit::class,$id);
        $panierId=2;
        $panier=$em->find(Panier::class,$panierId);
        $commande=$emm->findOneBy(array('produit'=>$produit,'panier'=>$panier));

        $panier->setSomme($panier->getSomme() - $produit->getPrix() * 1);
        $panier->setNbproduit($panier->getNbproduit() - 1);
        $em->persist($panier);
        $em->remove($commande);
        $em->flush();

        return $this->redirectToRoute("panierListe");


    }















}
