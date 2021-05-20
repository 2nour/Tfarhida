<?php

namespace App\Controller;

use App\Entity\Chambre;
use App\Entity\Commande;
use App\Entity\Produit;
use App\Entity\Panier;
use App\Entity\User;

use App\Form\ChambreType;
use App\Form\CommandeType;
use App\Repository\ChambreRepository;
use App\Repository\CommandeRepository;
use App\Repository\MaisonRepository;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Doctrine\ORM\Query;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;


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

        $produit = $em->find(Produit::class, $idproduit);
        $username=$this->getUser()->getUsername();
        $userr=$em->getRepository(User::class)->findOneBy(array('username'=>$username));
        $panierId=$userr->getPanier();



        /*verifier si le produit est deja dans le panier*/
        $commande=$em->getRepository(Commande::class)->findOneBy(array('panier'=>$panierId,'produit'=>$produit));

        /*si le produit n'est pas dans le panier*/
        if($commande== null)
        {
            $c=new Commande();
            $c->setProduit($produit);
            $c->setPanier($panierId);
            $c->setQuantiteProduit(1);
            $c->setPrixcommande($produit->getPrix());

            $panierId->setNbproduit($panierId->getNbproduit()+1);
            $panierId->setSomme($panierId->getSomme()+$produit->getPrix());
            $em->persist($c);
            $em->flush();
        }
            else {
                /*si la commande existe ajoouter un produit a cette commande*/
                $commande->setQuantiteProduit($commande->getQuantiteProduit()+1);
                $commande->setPrixcommande($produit->getPrix() * $commande->getQuantiteProduit());
                $em->persist($commande);
                $panierId->setNbproduit($panierId->getNbproduit()+1);
                $panierId->setSomme($panierId->getSomme() + $produit->getPrix());


                $em->persist($panierId);
                $em->flush();


            }


        $flashyNotifier->success('ajoute au panier');
        return $this->redirectToRoute("produitListe");


    }

//    public function  getPanier(){
//        $username=$this->getUser()->getUsername();
//        $em = $this->getDoctrine()->getManager();
//        $panier =new Panier();
//        $userr=$em->getRepository(User::class)->findOneBy(array('username'=>$username));
//        $panierId=$userr->getPanier();
//        if($panierId == null){
//
//            $panier->setUser($userr);
//            $panier->setNbproduit(0);
//            $panier->setSomme(0);
//            $em->persist($panier);
//            $em->flush();
//            return $panier;
//        }
//        else{
//            return $panierId;
//        }
//
//    }


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
        $username=$this->getUser()->getUsername();
        $userr=$em->getRepository(User::class)->findOneBy(array('username'=>$username));
        $panier=$userr->getPanier();

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

    //Partie JSON

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param MaisonRepository $repository
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/afficheCommandeJson", name="afficheCommandeJson")
     */
    public function afficheCommandeJson(NormalizerInterface $normalizer, ChambreRepository $repository){
        $commandes = $repository->findAll();
        $jsonContent = $normalizer->normalize($commandes, 'json',['groups'=>'commandes']);
        $retour=json_encode($jsonContent);
        return new Response($retour);

    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/deleteCommandeJSON/{id}", name="deleteCommandeJSON")
     */
    public function deleteCommandeJSON(Request $request,NormalizerInterface $normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $commande = $em->getRepository(Commande::class)->find($id);
        $em->remove($commande);
        $em->flush();
        $jsonContent = $normalizer->normalize($commande, 'json', ['groups' => 'commande']);
        return new Response("Commande supprimé".json_encode($jsonContent));
    }

    /**
     * @param Request $request
     * @param NormalizerInterface $normalizer
     * @param $id
     * @return Response
     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
     * @Route("/UpdateCommandeJSON/{id}", name="UpdateCommandeJSON")
     */
    public function updateCommandeJSON(Request $request,NormalizerInterface $normalizer,$id)
    {
        $em = $this->getDoctrine()->getManager();
        $commande = $em->getRepository(Commande::class)->find($id);
        if (empty($commande)) {
            return new JsonResponse(['message' => 'Place not found'], Response::HTTP_NOT_FOUND);
        }

        $form = $this->createForm(CommandeType::class, $commande);

        $form->submit($request->request->all());

        if ($form->isValid()) {
            $em = $this->get('doctrine.orm.entity_manager');
            // l'entité vient de la base, donc le merge n'est pas nécessaire.
            // il est utilisé juste par soucis de clarté
            $em->merge($commande);
            $em->flush();
            return $commande;
        } else {
            return $form;
        }
    }


    /**
     * @param Request $request
     * @param SerializerInterface $serializer
     * @return Response
     * @Route("/ajoutCommandeJSON", name="ajoutCommandeJSON")
     */

    public function addCommandeJSON(Request $request, SerializerInterface $serializer){
        $em = $this->getDoctrine()->getManager();
        $content = $request->getContent();
        $data = $serializer->deserialize($content, Commande::class, 'json');
        $em->persist($data);
        $em->flush();
        return new Response('Commande added successfully');
    }














}
