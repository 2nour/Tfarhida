<?php

namespace App\DataFixtures;

use App\Entity\Produit;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Persistence\ObjectManager;

class AppFixtures extends Fixture
{
    public function load(ObjectManager $manager)
    {
        for ($i = 0; $i < 20; $i++) {
            $product = new Produit();
            $product->setNom("tente");
            $product->setDescription('tente pour deux personnes');
            $product->setImage("61e33fc7785f99bfd138480e412d894a.jpeg");
            $product->setQuantite(20);
            $product->setMarque("tan");
            $product->setPrix(100);
            $manager->persist($product);
        }


        $manager->flush();
    }
}
