<?php

namespace  App\Maison\TestBundle\Controller;

use App\Entity\Chambre;
use App\Entity\Maison;
use App\Entity\Photo;
use App\Form\ChambreType;
use App\Repository\ChambreRepository;
use App\Repository\MaisonRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


class ChambreController extends AbstractController
{
    /**
     * @Route("/chambre", name="chambre")
     */
    public function index(): Response
    {
        return $this->render('chambre/index.html.twig', [
            'controller_name' => 'ChambreController',
        ]);
    }

    /**
     * @param ChambreRepository $repository
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/Chambre",name="mais")
     */

    public function affiche(ChambreRepository $repository,  \Symfony\Component\HttpFoundation\Request $request){
        $repo=$this->getDoctrine()->getRepository(Maison::class);
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $m = $repo->find($id);
        $chambre=$repository->findBy(['maison'=>$m]);
        return $this->render('chambre/Affiche.html.twig',
            ['chambre'=>$chambre]);
    }

    /**
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/addChambre", name="add" , defaults={"id"})
     */

    public function addChambre(\Symfony\Component\HttpFoundation\Request $request){
        $chambre = new Chambre();
        $form=$this->createForm(ChambreType::class, $chambre);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {
            $file =$chambre->getPhoto();
            $filename=md5(uniqid()).'.'.$file->guessExtension();

            // sauvgarder l'image dans le dossier indiquer par le param 'product_image_directory' dans services.yaml
            try {
                $file->move($this->getParameter('product_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }
            $em = $this->getDoctrine()->getManager();
            if ($request->get('submitAction') == 'ajouter')
            {
                $id = $request->get("id");
                $mai = $em->find(Maison::class, $id);
                $chambre->setMaison($mai);
               // sauvgarder uniquement le nom de limage dans la bdd
                $chambre->setPhoto($filename);
                $em->persist($chambre);
                $em->flush();
            }
            elseif ($request->get('submitAction') == 'valider')
            {
                $this->redirectToRoute("read",["id"]);
            }
        }

        return $this->render('chambre/newChambre.html.twig',
            ['f'=>$form->createView()]
        );
    }

    /**
     * @param ChambreRepository $repository
     * @param \Symfony\Component\HttpFoundation\Request $request
     * @return Response
     * @Route("/afficheChambre", name="h", defaults={"id"})
     */

    public function afficheCh(ChambreRepository $repository, \Symfony\Component\HttpFoundation\Request $request){
        $em = $this->getDoctrine()->getManager();
        $id = $request->get("id");
        $m = $em->find(Chambre::class, $id);
        $chambre=$repository->find($m);
        return $this->render('chambre/chambre.html.twig',
            ['chambre'=>$chambre]);
    }
}
