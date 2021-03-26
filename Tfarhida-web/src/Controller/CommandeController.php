<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Entity\Produit;
use App\Entity\Panier;

use App\Form\CommandeType;
use App\Repository\CommandeRepository;
use MercurySeries\FlashyBundle\FlashyNotifier;
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
    public function ajouteruneCommande(\Symfony\Component\HttpFoundation\Request $request,FlashyNotifier $flashyNotifier)
    {
        $em = $this->getDoctrine()->getManager();
        $idproduit = $request->get("id");
        $idpanier=2;
        $produit = $em->find(Produit::class, $idproduit);
        $panier =$em->find(Panier::class,$idpanier);
        /*verifier si le produit est deja dans le panier*/
        $commande=$em->getRepository(Commande::class)->findOneBy(array('panier'=>$panier,'produit'=>$produit));

        /*si le produit n'est pas dans le panier*/
        if($commande== null)
        {
            $c=new Commande();
            $c->setProduit($produit);
            $c->setPanier($panier);
            $c->setQuantiteProduit($c->getQuantiteProduit()+1);
            $c->setPrixcommande($produit->getPrix() * $c->getQuantiteProduit());

            $panier->setNbproduit($panier->getNbproduit()+1);
            $panier->setSomme($panier->getSomme() + $produit->getPrix() );
            $em->persist($c);
            $em->flush();
        }
            else {
                $commande->setQuantiteProduit($commande->getQuantiteProduit()+1);
                $commande->setPrixcommande($produit->getPrix() * $commande->getQuantiteProduit());
                $em->persist($commande);
                $panier->setNbproduit($panier->getNbproduit()+1);
                $panier->setSomme($panier->getSomme() + $produit->getPrix());


                $em->persist($panier);
                $em->flush();


            }


        $flashyNotifier->success('ajoute au panier');
        return $this->redirectToRoute("produitListe");


    }



    /**
     * @param CommandeRepository $repo,$id
     * @Route("suppPanier/{id}", name="suppPanier")
     * @return Response
     */
    public function supprimerProduit( $id,CommandeRepository $repo )
    {
        $em=$this->getDoctrine()->getManager();
        $emm=$this->getDoctrine()->getRepository(Commande::class);
        $produit=$em->find(Produit::class,$id);
        $panierId=2;
        $panier=$em->find(Panier::class,$panierId);
        $commande=$emm->findOneBy(array('produit'=>$produit,'panier'=>$panier));

        $panier->setSomme($panier->getSomme() - $produit->getPrix() * $commande->getQuantiteProduit());
        $panier->setNbproduit($panier->getNbproduit() - $commande->getQuantiteProduit());
        $em->persist($panier);
        $em->remove($commande);
        $em->flush();

        return $this->redirectToRoute("panierListe");


    }






    /**
     * @param CommandeRepository $repo,$id
     * @Route("/modifierCommande", name="modifierCommande",defaults={"id"})
     * @return Response
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
