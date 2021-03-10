<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\Session;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

class SessionController extends AbstractController
{
    /**
     * @Route("/session", name="session")
     */
    public function index(): Response
    {
        return $this->render('session/index.html.twig', [
            'controller_name' => 'SessionController',
        ]);
    }

    private $session;

    public function __construct(SessionInterface $session)
    {
        $this->session = $session;
    }

    public function someMethod()
    {
        // stores an attribute in the session for later reuse
        $this->session->set('id', '1');
        $this->session->set('nom', 'Nourhene');

        // gets an attribute by name
        $foo = $this->session->get('id');
        $foo = $this->session->get('nom');

        // the second argument is the value returned when the attribute doesn't exist
        $filters = $this->session->get('filters', []);

        // ...
    }

    /**
     * @param Request $request
     * @return Response
     * @Route("/sess", name="sess")
     */
    public function indexAction(Request $request){
        $session = new Session();

        //set and get session attributes
        $session->set('nom', 'Amen');
        $user = $session->get('nom');

        return $this->render('session/Affiche.html.twig', [
            'user'=>$user
        ]);
    }
}
