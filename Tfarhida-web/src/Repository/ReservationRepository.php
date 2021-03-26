<?php

namespace App\Repository;

use App\Entity\Reservation;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Reservation|null find($id, $lockMode = null, $lockVersion = null)
 * @method Reservation|null findOneBy(array $criteria, array $orderBy = null)
 * @method Reservation[]    findAll()
 * @method Reservation[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ReservationRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Reservation::class);
    }

    // /**
    //  * @return Reservation[] Returns an array of Reservation objects
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
    public function findOneBySomeField($value): ?Reservation
    {
        return $this->createQueryBuilder('r')
            ->andWhere('r.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
    public function AffecterReservation()
    {


        $conn = $this->getEntityManager()->getConnection();
        $id=$_GET['idAffected'];
        $sqlu = "UPDATE Reservation SET etat='Affected' WHERE `id`= $id ";


        $stmt = $conn->prepare($sqlu);

        $stmt->execute();

    }
    public function RefuserReservation()
    {


        $conn = $this->getEntityManager()->getConnection();
        $id=$_GET['idRefuser'];
        $sqlu = "UPDATE Reservation SET etat='Refuser' WHERE `id`= $id ";


        $stmt = $conn->prepare($sqlu);

        $stmt->execute();

    }

}
