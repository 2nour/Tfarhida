<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\RegistrationType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

class SecurityController extends AbstractController
{

    /**
     * @param UserRepository $repository
     * @return Response
     * @Route ("/show",name="show_user")
     */
    public function Affiche(UserRepository $repository){
        $users=$repository->findAll();
        return $this->render('security/user.html.twig',[
            'users'=>$users
        ]);

    }

    /**
     * @Route ("/inscription", name="security_registration")
     */
    public function registration(Request $request, UserPasswordEncoderInterface $encoder){
        $user = new User();
        $form = $this->createForm(RegistrationType::class, $user);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $hash = $encoder->encodePassword($user, $user->getPassword());
            $user->setPassword($hash);

            //generer le token d'activation
            $user->setActivationToken(md5(uniqid()));

            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();

            //$message = (new \swift_Message('Activation de votre compte'));
            //->setForm('votre@adresse.fr');
            //->setTo($user->getEmail());
            //->setBody(
              //  $this->renderView(
                //    'emails/activation.html.twig',['token'=>$user->getActivationToken()]
              //  )
                //'text/html'
            //);
           // $mailer->send($message);

            return $this->redirectToRoute('security_login');
        }

        return $this->render('security/registration.html.twig',[
            'form' => $form->createView()]);

    }

    /**
     * @return Response
     * @Route ("/connexion", name="security_login")
     */
    public function login(){
        return $this->render('security/login.html.twig');

    }

    /**
     * @Route ("/deconnexion", name="security_logout")
     */
    public function logout(){


    }

    /**
     * @Route ("/server", name="server")
     */
    public function indexServer():Response
    {

        return $this->render('server/serve.html.twig');


    }

    /**
     * @Route ("/admin", name="server")
     * @param UserRepository $repository
     * @return Response
     */
    public function indexAdmin(UserRepository  $repository):Response
    {
        $users=$repository->findAll();
        return $this->render('dashboard/admin.html.twig',[
            'users'=>$users
        ]);

    }

    /**
     * @Route ("Activation/{token}", name="activation")
     * @param $token
     * @param UserRepository $userRepo
     * @return RedirectResponse
     */
    public function Activation($token, UserRepository $userRepo): Response
    {
        //on verifie si un utilisateur a ce token
        $user =$userRepo->findOneBy(['Activation_token' => $token]);

        //si aucun utilisateur n'existe avec ce token
        if(!$user){
            throw $this->createNotFoundException('cet utilisateur n\'existe pas');
        }

        //on supprime le token
        $user->setActivationToken(null);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($user);
        $entityManager->flush();

        //on message flush flush
        $this->addFlash('message', 'vous avez bien activé votre compte');

        //on retourne a l'acceuil

        return$this->redirectToRoute('home');

    }


}
