<?php

namespace App\Controller;
use App\Entity\CommentaireR;
use App\Entity\PostLike;
use App\Entity\User;
use App\Entity\Commentaire;
use App\Form\CommentaireFormType;
use App\Form\RandonneeeType;
use App\Entity\Randonnee;
use App\Entity\Reservation;
use App\Form\ReservationType;
use App\Repository\CommentaireRRepository;
use App\Repository\RandonneeRepository;
use App\Repository\PostLikeRepository;
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
use Symfony\Component\Security\Core\Authentication\Token\Storage\TokenStorageInterface;


class RandonneeController extends AbstractController
{
    private $user;

    public function __construct(TokenStorageInterface $tokenStorage)
    {
        $this->user = $tokenStorage->getToken()->getUser();
    }
    /**
     * @Route("/randonnee", name="randonnee")
     */
    public function home(RandonneeRepository $repository)
    {
        if(isset($_POST["submitAdmin"])){
            $repository = $this->getDoctrine()->getRepository(Randonnee::class);
            $randonnee=$repository->findRandonnee($_POST["search"]);
            return $this->render('randonnee/index.html.twig',
                ['randonnee'=>$randonnee]);
        }
        $randonnee=$repository->findAll();
        return $this->render('randonnee\listerand.html.twig',
            ['randonnee'=>$randonnee]);

    }

    /**
     * @param RandonneeRepository $repository
     * @param CommentaireRRepository $repo
     * @return \Symfony\Component\HttpFoundation\Response
     * @Route("/AfficheRandonnee", name="AfficheRandonnee")
     *
     */
    public function Affiche(RandonneeRepository $repository,CommentaireRRepository $repo){
        $repo = $this->getDoctrine()->getRepository(CommentaireR::class);

        if(isset($_POST["submit"])){
            $repository = $this->getDoctrine()->getRepository(Randonnee::class);

            $randonnee=$repository->findRandonnee($_POST["search"]);
            $commentaires=$repo->findAll();
            return $this->render('randonnee/Afficherand.html.twig',
                ['randonnee'=>$randonnee,'commentaires'=>$commentaires]);
        }
        $repo = $this->getDoctrine()->getRepository(CommentaireR::class);

        $randonnee=$repository->findAll();
        $commentaires=$repo->findAll();
        return $this->render('randonnee/Afficherand.html.twig',
            ['randonnee'=>$randonnee,
             'commentaires' =>$commentaires]);

    }
    /**
     * @Route("/randonnee/show/{id}", name="randonnee_show")
     */
    public function show($id) {
        $randonnee = $this->getDoctrine()->getRepository(Randonnee::class)->find($id);

        return $this->render('randonnee/show.html.twig', array('randonnee' => $randonnee));
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


            $file =$randonnee->getImage();
            $filename = md5(uniqid().'.'.$file->guessExtension());
            try {
                $file->move($this->getParameter('randonnee_image_directory'), $filename);
            } catch (FileException $e) {
                // ... handle exception if something happens during file upload
            }
            $em=$this->getDoctrine()->getManager();
            $randonnee->setImage($filename);




            $em->persist($randonnee);
            $em->flush();

            return $this->redirectToRoute('randonnee');
        }
        return $this->render('randonnee\Addrand.html.twig',[
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
     * @param PostLikeRepository $liikeRepository
     */

    public function like1(Randonnee $post, PostLikeRepository $liikeRepository):Response
    {
        $manager = $this->getDoctrine()->getManager();

         $user1 = $this->getUser();
        /* if (!$user) return $this->json([
             'code' => 403,
             'message' => "Unauthorized"
         ], 403);*/
        $user=$manager->getRepository(User::class)->findOneBy(["username"=>$user1->getUsername()]);
        if ($post->isLikedByUser($user)){
            $like = $manager->getRepository(PostLike::class)->findOneBy([
                'randonnee' => $post,
                'user' => $user
            ]);
            $manager->remove($like);
            $manager->flush();
           /* return $this->json([
                'code' => 200,
                'message' => 'Like bien supprimé',
                'likes' => $liikeRepository->count(['randonnee' => $post] )
            ], 200);*/
            return $this->redirectToRoute('AfficheRandonnee');
        }

        $like = new PostLike();
        $like->setRandonnee($post)
            ->setUser($user);
        $manager->persist($like);
        $manager->flush();

        return $this->redirectToRoute('AfficheRandonnee');
    }






}
