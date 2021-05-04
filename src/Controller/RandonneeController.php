<?php

namespace App\Controller;
//use App\Entity\PostLiike;
//use App\Entity\Useer;
use App\Form\RandonneeeType;
use App\Entity\Randonnee;

use App\Entity\Reservation;
use App\Form\ReservationType;
//use App\Repository\PostLiikeRepository;
use App\Repository\RandonneeRepository;
use App\Repository\ReservationRepository;
use Doctrine\Persistence\ObjectManager;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

class RandonneeController extends AbstractController
{


    /**
     * @param RandonneeRepository $repository
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/AfficheRandonnee", name="AfficheRandonnee")
     *
     */
    public function Affiche(RandonneeRepository $repository){

        $em = $this->getDoctrine()->getManager();
        $randonnee=$em->getRepository(Randonnee::class)->findAll();

        return $this->render('organisation/afficheActivite.html.twig',

        ['controller_name' => 'RandonneeController','randonnee'=> $randonnee]);

    }

    /**
     * @Route("/Supp/{id}", name="rand")
     *
     */
    function Delete($id, RandonneeRepository $repository){
        $randonnee = $repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($randonnee);
        $em->flush();
        return $this->redirectToRoute('randonnee');

    }


    /**
     * @Route("/searchVilleArrivee ", name="searchVilleArrivee")
     */
    public function searchVilleArrivee(Request $request,NormalizerInterface $Normalizer)
 {
 $repository = $this->getDoctrine()->getRepository(Randonnee::class);
 $requestString=$request->get('searchValue');
 $VilleArrivee = $repository->findRandonneeByVilleArrivee($requestString);
 $jsonContent = $Normalizer->normalize($VilleArrivee, 'json',['groups'=>'villes']);
 $retour=json_encode($jsonContent);
 return new Response($retour);
 }


     /**
     *
     * @Route("/randonnee/Add", name="addrandonnee")
     * Method{{"GET","POST"}}
     */
    public function addRandonnee(Request $request)
    {
        $randonnee = new Randonnee();
        $form = $this->createForm(RandonneeeType::class,$randonnee);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {
            $dated=$request->request->get("datedepart");
            $datea=$request->request->get("datearrive");


            $file =$randonnee->getImage();
            $filename = md5(uniqid().'.'.$file->guessExtension());
            try {
                $file->move($this->getParameter('randonnee_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }
            $em=$this->getDoctrine()->getManager();
            $randonnee->setImage($filename);
            $da=date("Y-m-d", strtotime($datea) );
            $dd=date("Y-m-d", strtotime($dated) );

            $randonnee->setDatedepart(new  \DateTime($dd));
            $randonnee->setDateretour(new \DateTime($da));
            $em->persist($randonnee);
            $em->flush();
            $this->addFlash(
                'notice',
                'Super ! une nouvelle randonnée a été ajoutée !!!'
            );

            return $this->redirectToRoute('randonnee');
        }
        return $this->render('randonnee/Addrand.html.twig',[
            'form'=>$form->createView()
        ]);
    }

    /**
     *
     * @Route("/randonnee/edit/{id}", name="modifrand")
     * Method{{"GET","POST"}}
     */
    public function UpdateRandonnee(Request $request, $id)
    {
        $randonnee = new Randonnee();
        $randonnee = $this->getDoctrine()->getRepository(Randonnee::class)->find($id);

        $form = $this->createForm(RandonneeeType::class, $randonnee);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){

            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('randonnee');
        }
        return $this->render('randonnee/editrand.html.twig',[
            'form'=>$form->createView()
        ]);
    }

    /**
     * permt de liker ou unliker randonne
     *
     * @Route("/Randonnee/{id}/like", name="post_like")
     * @param Post $post
     * @param PostLiikeRepository $liikeRepository
    /*

    public function like1(Randonnee $post, PostLiikeRepository $liikeRepository):Response
    {
        $manager = $this->getDoctrine()->getManager();

       // $user = $this->getUser();
       /* if (!$user) return $this->json([
            'code' => 403,
            'message' => "Unauthorized"
        ], 403);*/
        //$user=$manager->getRepository(Useer::class)->find(3);
       // if ($post->isLikedByUser($user)){
          //  $like = $manager->getRepository(PostLiike::class)->findOneBy([
              //  'post' => $post,
               // 'useer' => $user
           // ]);
           // $manager->remove($like);
           // $manager->flush();
           // return $this->json([
            //    'code' => 200,
    //  'message' => 'Like bien supprimé',
    //  'likes' => $liikeRepository->count(['post' => $post] )
    //   ], 200);
    //    }

    //  $like = new PostLiike();
    //   $like->setPost($post)
    //       ->setUseer($user);
    //   $manager->persist($like);
    //  $manager->flush();

    //  return $this->json([
    //   'code' => 200,
    //   'message' => "like bien ajoutee",
    //    'likes' => $liikeRepository->count(['post' => $post])
    //    ], 200);
//}





}
