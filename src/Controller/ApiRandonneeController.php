<?php


namespace App\Controller;

use App\Entity\PostLike;
use App\Entity\Randonnee;
use App\Entity\User;
use App\Form\RandonneeeType;
use App\Repository\PostLikeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use App\Repository\RandonneeRepository;
use Symfony\Component\Routing\Annotation\Route;


class ApiRandonneeController extends AbstractController
{

    /**
     * @Route("/api/randonnee", name="randonnee_api")
     */
    public function indexAction(){
        $randonnes=$this->getDoctrine()->getRepository(Randonnee::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($randonnes);
        return new JsonResponse($formatted);
    }
    /**
     * @Route("/api/randonnee/show/{id}", name="randonnee_show_api")
     */
    public function show($id) {
        $randonnee = $this->getDoctrine()->getRepository(Randonnee::class)->find($id);

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($randonnee);
        return new JsonResponse($formatted);    }
    /**
     * @Route("/api/rand/Supp/{id}", name="rand_api")
     *
     */
    function Delete($id, RandonneeRepository $repository){
        $randonnee = $repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $id=$randonnee->getId();
        $em->remove($randonnee);
        $response=array();
        array_push($response,['code'=>200,'respose'=>'success','id'=>$id]);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($response);
        return new JsonResponse($formatted);

    }
    /**
     *
     * @Route("/api/randonnee/Add", name="addrandonnee_api")
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

            $response=array();
            array_push($response,['code'=>200,'respose'=>'success']);
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize($response);
            return new JsonResponse($formatted);
        }
        $response=array();
        array_push($response,['code'=>200,'respose'=>'success']);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($response);
        return new JsonResponse($formatted);
    }
    /**
     *
     * @Route("/api/randonnee/edit/{id}", name="modifrand_api")
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
            $response=array();
            array_push($response,['code'=>200,'respose'=>'success']);
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize($response);
            return new JsonResponse($formatted);        }
        $response=array();
        array_push($response,['code'=>200,'respose'=>'success']);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($response);
        return new JsonResponse($formatted);

    }
    public function like1(Randonnee $post, PostLikeRepository $liikeRepository):Response
    {
        $manager = $this->getDoctrine()->getManager();

        // $user = $this->getUser();
        /* if (!$user) return $this->json([
             'code' => 403,
             'message' => "Unauthorized"
         ], 403);*/
        $user=$manager->getRepository(User::class)->find(1);
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
            $response=array();
            array_push($response,['code'=>200,'respose'=>'success']);
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize($response);
            return new JsonResponse($formatted);
        }

        $like = new PostLike();
        $like->setRandonnee($post)
            ->setUser($user);
        $manager->persist($like);
        $manager->flush();

        $response=array();
        array_push($response,['code'=>200,'respose'=>'success']);
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($response);
        return new JsonResponse($formatted);
    }

}