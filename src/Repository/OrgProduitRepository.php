<?php

namespace App\Repository;

use App\Entity\OrgProduit;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method OrgProduit|null find($id, $lockMode = null, $lockVersion = null)
 * @method OrgProduit|null findOneBy(array $criteria, array $orderBy = null)
 * @method OrgProduit[]    findAll()
 * @method OrgProduit[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class OrgProduitRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, OrgProduit::class);
    }

    // /**
    //  * @return OrgProduit[] Returns an array of OrgProduit objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('o')
            ->andWhere('o.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('o.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?OrgProduit
    {
        return $this->createQueryBuilder('o')
            ->andWhere('o.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
