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
            $product->setImage("efafff0ff15b74226a6cb0a872e2d62e.jpeg");
            $product->setQuantite(20);
            $product->setMarque("tan");
            $product->setPrix(100);
            $manager->persist($product);
        }


        $manager->flush();
    }
}
