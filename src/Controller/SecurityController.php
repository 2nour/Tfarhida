<?php

namespace App\Controller;

use App\Entity\Panier;
use App\Entity\Produit;
use App\Entity\User;
use App\Form\AjouterProduitFormType;
use App\Form\EditRoleType;
use App\Form\EditUserType;
use App\Form\RegistrationType;
use App\Form\ResetPassType;
use App\Repository\MaisonRepository;
use App\Repository\ProduitRepository;
use App\Repository\UserRepository;
use Cassandra\Type\UserType;
use phpDocumentor\Reflection\Type;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;

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
    public function registration(Request $request, UserPasswordEncoderInterface $encoder, \Swift_Mailer $mailer){
        $user = new User();
        $form = $this->createForm(RegistrationType::class, $user);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $hash = $encoder->encodePassword($user, $user->getPassword());
            $user->setPassword($hash);
            $panier  =new Panier();

            //generer le token d'activation
            $user->setActivationToken(md5(uniqid()));

            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
            $panier->setUser($user);
            $panier->setSomme(0);
            $panier->setNbproduit(0);
            $em->persist($panier);
            $em->flush();



            $message = (new \Swift_Message('Activation de votre compte'))
                ->setFrom('votre@adresse.tn')
                ->setTo($user->getEmail())
                ->setBody(
                    $this->renderView('emails/activation.html.twig', [
                        'token'=> $user->getActivationToken()
                    ]),
                    'text/html'
                );
            $mailer->send($message);


            return $this->redirectToRoute('security_login');
        }

        return $this->render('security/registration.html.twig',[
            'form' => $form->createView()]);

    }

    /**
     * @Route ("supp/{id}", name="d")
     */
    function Delete($id, UserRepository $repository){

        $user= $repository->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();
        return $this->redirectToRoute('show_user');
    }

    /**
     * @Route ("update/{id}", name="update" , methods={"GET","POST"})
     * @param User $user
     * @param UserRepository $repository
     * @param $id
     * @param Request $request
     * @return RedirectResponse|Response
     */
    public function update(User $user, UserRepository $repository, $id, Request $request){
//    dd($request);


        $form=$this->createForm(EditUserType::class, $user);
        //$form->add('update',SubmitType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted()&& $form->isValid()){
            // dd($form);
            $em= $this->getDoctrine()->getManager();
            $em->persist($user);

            $em->flush();
            return $this->redirectToRoute('show_user');

        }

        return $this->render('security/edituser.html.twig',
            [
                'formUser'=>$form->createView()
            ]);
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
     * @Route ("/admin", name="admins")
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
     * @Route("/modifier/{id}", name="modifier_utilisateur")
     */
    public function editUser(User $user, Request $request){
        $form = $this->createForm(EditRoleType::class, $user);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();

            $this->addFlash('message', 'Utilisateur modifié avec succés');
            return $this->redirectToRoute('show_user');
        }

        return $this->render('security/editRole.html.twig', [
            'userRoleForm' => $form->createView()
        ]);

    }


    /**
     * @Route ("Activation/{token}", name="activation")
     * @param $token
     * @param UserRepository $userRepo
     * @return RedirectResponse
     * comment
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

    /**
     * @param Request $request
     * @param UserRepository $userRepo
     * @param \Swift_Mailer $mailer
     * @param TokenGeneratorInterface $tokenGenerator
     * @return RedirectResponse|Response
     * @Route("/oubli-pass", name="app_forgotten_password")
     */
    public function forgottenPass(Request $request, UserRepository $userRepo, \Swift_Mailer $mailer, TokenGeneratorInterface $tokenGenerator)
    {
        //on créé le form
        $form = $this->createForm(ResetPassType::class);

        //on traite le formulaire
        $form->handleRequest($request);

        //si le form est valide
        if ($form->isSubmitted() && $form->isValid()){

            //on reccupere les données
            $donnees = $form->getData();

            //on cherche si un utilisateur  a cet mail
            $user = $userRepo->findOneByEmail($donnees['email']);

            //si l'utilisateur n'existe pas
            if (!$user){
                //on envois un message flash
                $this->addFlash('danger', 'cette adresse est non disponible ');

                return $this->redirectToRoute('security_login');
            }

            //on genere un token
            $token = $tokenGenerator->generateToken();

            try {
                $user->setResetToken($token);
                $em = $this->getDoctrine()->getManager();
                $em->persist($user);
                $em->flush();

            }catch (\Exception $e){
                $this->addFlash('warning','Une erreur est survenue : '. $e->getMessage());
                return $this->redirectToRoute('security_login');

            }

            //on genere l'url de  reinisialisation
            $url = $this->generateUrl('app_reset_password',['token'=> $token,
                UrlGeneratorInterface::ABSOLUTE_URL]);

            //on envois le msg
            $message = (new \Swift_Message('Mot de passe oublié'))
                ->setFrom('votre@adresse.fr')
                ->setTo($user->getEmail())
                ->setBody("<p>Bonjour</p><p>Une demande de reinitialisation de mot de passe a ete effectuee pour le site esprit.tn. veuillez clique sur le lien suivant : " . $url .'</p>', 'text/html');
            $mailer->send($message);

            //on crée le msg flash
            $this->addFlash('message', 'un e-mail de réinitialisation de mot de passe vou a été envoyé');

            return  $this->redirectToRoute('security_login');
        }
        //on envoie vers la page de demande de l'email
        return $this->render('security/forgotten_password.html.twig', [
            'emailForm' => $form->createView()
        ]);
    }

    /**
     * @Route ("/reset-pass/{token}", name="app_reset_password")
     */
    public function resetPassword($token, Request $request, UserPasswordEncoderInterface $passwordEncoder){
        //on cherche l'utilisateur avec le token fournis

        $user = $this->getDoctrine()->getRepository(User::class)->findOneBy(['reset_token'=> $token]);

        if (!$user){
            $this->addFlash('danger', 'Token inconnu');
            return $this->redirectToRoute('security_login');
        }

        //si le formulaire est envoyer en methode POST
        if ($request->isMethod('POST')){
            $user->setResetToken(null);
            $user->setPassword($passwordEncoder->encodePassword($user, $request->request->get('password')));
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();

            $this->addFlash('message', 'Mot de passe modifié avec succes');

            return $this->redirectToRoute('security_login');

        }
        else{
            return $this->render('security/reset_password.html.twig', ['token'=> $token]);
        }

    }


    /**
     * @Route ("/profil", name="profil")
     */
    public function indexUser():Response
    {

        return $this->render('user/user.html.twig');

    }

// Partie JSON
//
//    /**
//     * @param Request $request
//     * @param NormalizerInterface $normalizer
//     * @param MaisonRepository $repository
//     * @return Response
//     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
//     * @Route("/afficheUserJson", name="afficheUserJson")
//     */
//    public function afficheUserJson(NormalizerInterface $normalizer, UserRepository $repository){
//        $users = $repository->findAll();
//        $jsonContent = $normalizer->normalize($users, 'json',['groups'=>'users']);
//        $retour=json_encode($jsonContent);
//        return new Response($retour);
//
//    }
//
//    /**
//     * @param Request $request
//     * @param NormalizerInterface $normalizer
//     * @param $id
//     * @return Response
//     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
//     * @Route("/deleteUserJSON/{id}", name="deleteUserJSON")
//     */
//    public function deleteUserJSON(Request $request,NormalizerInterface $normalizer, $id)
//    {
//        $em = $this->getDoctrine()->getManager();
//        $user = $em->getRepository(User::class)->find($id);
//        $em->remove($user);
//        $em->flush();
//        $jsonContent = $normalizer->normalize($user, 'json', ['groups' => 'user']);
//        return new Response("Utilisateur supprimé".json_encode($jsonContent));
//    }
//
//    /**
//     * @param Request $request
//     * @param NormalizerInterface $normalizer
//     * @param $id
//     * @return Response
//     * @throws \Symfony\Component\Serializer\Exception\ExceptionInterface
//     * @Route("/UpdateUserJSON/{id}", name="UpdateUserJSON")
//     */
//    public function updateUerJSON(Request $request,NormalizerInterface $normalizer,$id)
//    {
//        $em = $this->getDoctrine()->getManager();
//        $user = $em->getRepository(User::class)->find($id);
//        if (empty($user)) {
//            return new JsonResponse(['message' => 'Place not found'], Response::HTTP_NOT_FOUND);
//        }
//
//        $form = $this->createForm(EditUserType::class, $user);
//
//        $form->submit($request->request->all());
//
//        if ($form->isValid()) {
//            $em = $this->get('doctrine.orm.entity_manager');
//            // l'entité vient de la base, donc le merge n'est pas nécessaire.
//            // il est utilisé juste par soucis de clarté
//            $em->merge($user);
//            $em->flush();
//            return $user;
//        } else {
//            return $form;
//        }
//    }
//
//
//    /**
//     * @param Request $request
//     * @param SerializerInterface $serializer
//     * @return Response
//     * @Route("/ajoutUserJSON", name="ajoutUserJSON")
//     */
//
//    public function addUserJSON(Request $request, SerializerInterface $serializer){
//        $em = $this->getDoctrine()->getManager();
//        $content = $request->getContent();
//        $data = $serializer->deserialize($content, User::class, 'json');
//        $em->persist($data);
//        $em->flush();
//        return new Response('User added successfully');
//    }
//


    /**
     * @param Request $request
     * @param SerializerInterface $serializer
     * @return Response
     * @Route("/ajoutUserJSON/{email}/{username}/{password}/{confirmpassword}/{roles}", name="ajoutUserJSON")
     */

    public function ajoutUserJSON(Request $request, SerializerInterface $serializer, $email, $username, $password, $confirmpassword, $roles){
        $User = new User();
        $em = $this->getDoctrine()->getManager();
        $User->setEmail($email);
        $User->setUsername($username);
//        $pass=$passwordEncoder->encodePassword(
//            $User,
//            $password);
        $User->setPassword($password);
        $User->setConfirmPassword($confirmpassword);
        $User->setRoles(array($roles));
        $content = $request->getContent();
        $em->persist($User);
        $em->flush();
        return new Response('User added successfully');
    }

    /**
     * @Route ("user/signinJson/{email}/{password}", name="app_login")
     */

    public function signinAction(Request $request, $email , $password)
    {

//          $email = $request->query->get("email");
//          $password = $request->query->get("password");

        $em = $this->getDoctrine()->getManager();
        $user = $em->getRepository(User::class)->findOneBy(['email' => $email]);

        if ($user) {
            if ($password == $user->getPassword()) {
                $serializer = new Serializer([new ObjectNormalizer()]);
                $formatted = $serializer->normalize($user);
                return new JsonResponse($formatted);
            } else {

                return new Response("password n'existe pas");
            }


        } else {

            return new Response("failed");
        }
    }





    /**
     * @Route ("user/getPasswordByEmail/{email}", name="app_password")
     */
    public function getPasswordByEmail(Request $request, $email){

        // $email = $request->get('email');
        $user = $this->getDoctrine()->getManager()->getRepository(User::class)->findOneBy(['email'=>$email]);
        if($user) {
            $password = $user->getPassword();
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize($password);
            return new JsonResponse($formatted);

        }

        return new Response("user not found");
    }








}
