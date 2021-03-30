<?php

namespace App\DataFixtures;
use App\Entity\PostLike;
use App\Entity\Randonnee;
use App\Entity\User;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Persistence\ObjectManager;

class AppFixtures extends Fixture
{
    public function load(ObjectManager $manager)
    {
        $users = [];

        $user = new User();
        $user->setemail('user@symfony.com')
           // ->setRoles("ROLE_USER")

            ->setPassword("password");

        $manager->persist($user);
        $users[] = $user;

        for ($i = 0; $i < 20; $i++) {
            $user = new User();
            $user->setemail("email".$i."@esprit.tn")
              //  ->setRoles("ROLE_USER")
                ->setPassword("password");


            $manager->persist($user);
            $users[] = $user;
        }

        for ($i = 0; $i < 20; $i++) {
            $randonnee = new Randonnee();
            $randonnee
                /*  ->setDateretour(new \DateTime('now'))

                  ->setDatedepart( new \DateTime('now'))*/
                ->setDescription("ville".$i)
                ->setActivite('activite'.$i)
                ->setVilledepart("ville".$i)
                ->setVillearrivee("ville".$i)
                ->setImage("image".$i)
                ->setDifficulte("fort")
                ->setBudget($i)
                ->setDuree($i)
            ;
            $manager->persist($randonnee);

            for ($j = 0; $j < mt_rand(0,10); $j++) {
                $like = new PostLike();
                $like->setPost($randonnee)
                    ->setUser($users[0]);

                $manager->persist($like);
            }
        }

        $manager->flush();
    }
    }
