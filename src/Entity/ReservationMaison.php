<?php

namespace App\Entity;

use App\Repository\ReservationMaisonRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=ReservationMaisonRepository::class)
 */
class ReservationMaison
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="date")
     */
    private $dateReservation;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $etats;

    /**
     * @ORM\ManyToOne(targetEntity=Chambre::class, inversedBy="reservationMaisons")
     * @ORM\JoinColumn(nullable=false)
     */
    private $chambre;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="reservationMaisons")
     * @ORM\JoinColumn(nullable=false)
     */
    private $user;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateReservation(): ?\DateTimeInterface
    {
        return $this->dateReservation;
    }

    public function setDateReservation(\DateTimeInterface $dateReservation): self
    {
        $this->dateReservation = $dateReservation;

        return $this;
    }

    public function getEtats(): ?string
    {
        return $this->etats;
    }

    public function setEtats(string $etats): self
    {
        $this->etats = $etats;

        return $this;
    }

    public function getChambre(): ?Chambre
    {
        return $this->chambre;
    }

    public function setChambre(?Chambre $chambre): self
    {
        $this->chambre = $chambre;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }
}
