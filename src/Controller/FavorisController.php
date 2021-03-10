<?php

namespace App\Controller;

use App\Entity\Favoris;
use App\Entity\User;
use App\Form\FavorisType;
use App\Entity\Maison;
use App\Repository\FavorisRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

class FavorisController extends AbstractController
{
    /**
     * @Route("/favoris", name="favoris")
     */
    public function index(): Response
    {
        return $this->render('favoris/index.html.twig', [
            'controller_name' => 'FavorisController',
        ]);
    }


    // public function affiche(FavorisRepository $repository){
    // $repo=$this->getDoctrine()->getRepository(Maison::class);
    // $favoris=$repository->findAll();
    // return $this->render('favoris/Affiche.html.twig',
    //    ['favoris'=>$favoris]);
    // }

    private $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }

    /**
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/favoris", name="favoris", defaults={"idMaison", "action"})
     */

    public function gererFavoris(\Symfony\Component\HttpFoundation\Request $request, FavorisRepository $repository){

        $em = $this->getDoctrine()->getManager();
        $idMaison = $request->get("idMaison");
        $action = $request->get("action");

      //TODO recuperer id user après connexion
        $user = $em->find(User::class, 3);
        $mai = $em->find(Maison::class, $idMaison);
        if($action=='add') {
            $favori = new Favoris();
            $favori->setUser($user);
            $favori->setMaison($mai);
            if (!$user->contientFavori($favori)) {
                $em->persist($favori);
                $em->flush();
            }
        }else if($action=='del'){
            $favori = $mai -> getFavoriWithIdUser($user->getId());
            $mai -> removeFavori($favori);
            $em->remove($favori);
            $em->flush();
        }
        return $this->redirectToRoute("aff");
    }

    /**
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @param FavorisRepository $repository
     * @return Response
     * @Route("/affFavoris",name="affFav")
     */

    public function affiche(\Symfony\Component\HttpFoundation\Request $request, FavorisRepository $repository){
       $repos=$this->getDoctrine()->getRepository(User::class);

    //  TODO récuperer user connecte
       $idUser = 3 ;//TODO $this->session->get('idUser);
       $u = $repos->find($idUser);
       $favoris=$u -> getFavoris();
        return $this->render('favoris/Affiche.html.twig',
            ['favoris'=>$favoris]);
    }

    /**
     * @param FavorisRepository $repository
     * @param $id
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route("/remove/{id}", name="remove")
     */
    public function deleteFavoris(FavorisRepository $repository, $id){
        $favoris=$repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($favoris);
        $em->flush();
        return $this->redirectToRoute("affFav");

    }


}
