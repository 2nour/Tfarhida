<?php

namespace App\Repository;

use App\Entity\Randonnee;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Randonnee|null find($id, $lockMode = null, $lockVersion = null)
 * @method Randonnee|null findOneBy(array $criteria, array $orderBy = null)
 * @method Randonnee[]    findAll()
 * @method Randonnee[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class RandonneeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Randonnee::class);
    }

    // /**
    //  * @return Randonnee[] Returns an array of Randonnee objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('r')
            ->andWhere('r.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('r.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?Randonnee
    {
        return $this->createQueryBuilder('r')
            ->andWhere('r.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */

    public function findRandonnee($var){
        return $this->createQueryBuilder('Randonnee')
            ->where('Randonnee.villearrivee LIKE :varS')
            ->orWhere('Randonnee.villedepart LIKE :varS')
            ->orWhere('Randonnee.activite LIKE :varS')
            ->orWhere('Randonnee.description LIKE :varS')
            ->orWhere('Randonnee.duree LIKE :varS')
            ->orWhere('Randonnee.difficulte LIKE :varS')
            ->orWhere('Randonnee.budget LIKE :varS')
            ->orWhere('Randonnee.datedepart LIKE :varS')
            ->orWhere('Randonnee.dateretour LIKE :varS')
            ->setParameter('varS', '%'.$var.'%')
            ->getQuery()
            ->getResult();
    }
}
