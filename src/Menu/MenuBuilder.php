<?php


namespace App\Menu;

use Knp\Menu\FactoryInterface;
use Knp\Menu\ItemInterface;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\RequestStack;
use App\Entity\Maison;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use App\Repository\MaisonRepository;
use Symfony\Component\Security\Core\Security;

class MenuBuilder extends AbstractController
{
    private $factory;
    private $security;

    /**
     * MenuBuilder constructor.
     * @param FactoryInterface $factory
     * @param Security $security
     */

    public function __construct(FactoryInterface $factory, Security $security)
    {
        $this->factory = $factory;
        $this->security = $security;

    }

    public function createMainMenu(RequestStack $requestStack)
    {
        $menu = $this->factory->createItem('root');

        $menu->addChild('Home', ['route' => 'home']);

        // ... add more children


        return $menu;
    }

}