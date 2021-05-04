<?php

namespace App\Entity;

use App\Repository\ReservationMaisonRepository;
use Doctrine\ORM\Mapping as ORM;
use JsonSerializable;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=ReservationMaisonRepository::class)
 */
class ReservationMaison implements JsonSerializable
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups("reservation")
     */
    private $id;


    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("reservation")
     */
    private $etats;

    /**
     * @ORM\ManyToOne(targetEntity=Chambre::class, inversedBy="reservationMaisons")
     * @ORM\JoinColumn(nullable=false)
     * @Groups("reservation")
     */
    private $chambre;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="reservationMaisons")
     * @ORM\JoinColumn(nullable=false)
     * @Groups("reservation")
     */
    private $user;

    /**
     * @ORM\Column(type="date")
     * @Groups("reservation")
     */
    private $dateArrivee;

    /**
     * @ORM\Column(type="date")
     * @Groups("reservation")
     */
    private $dateDepart;

    /**
     * @ORM\Column(type="float")
     * @Groups("reservation")
     */
    private $totalPrix;

    public function getId(): ?int
    {
        return $this->id;
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

    public function getDateArrivee(): ?\DateTimeInterface
    {
        return $this->dateArrivee;
    }

    public function setDateArrivee(\DateTimeInterface $dateArrivee): self
    {
        $this->dateArrivee = $dateArrivee;

        return $this;
    }

    public function getDateDepart(): ?\DateTimeInterface
    {
        return $this->dateDepart;
    }

    public function setDateDepart(\DateTimeInterface $dateDepart): self
    {
        $this->dateDepart = $dateDepart;

        return $this;
    }

    public function getTotalPrix(): ?float
    {
        return $this->totalPrix;
    }

    public function setTotalPrix(float $totalPrix): self
    {
        $this->totalPrix = $totalPrix;

        return $this;
    }

    public function jsonSerialize()
    {
        return array(
            'id' => $this->id,
            'date_arrivee' => $this->dateArrivee,
            'etats' => $this->etats,
            'dateDepart' => $this->dateDepart,
            'totalPrix' => $this->totalPrix
        );
    }
}
