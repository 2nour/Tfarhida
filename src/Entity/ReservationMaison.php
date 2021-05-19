<?php

namespace App\Entity;

use App\Repository\ReservationMaisonRepository;
use Doctrine\ORM\Mapping as ORM;
use JsonSerializable;
use Symfony\Component\Serializer\Annotation\Groups;
use App\Entity\Chambre;

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
     * @ORM\ManyToOne(targetEntity=Chambre::class, inversedBy="user")
     * @Groups("reservation")
     */
    private $chambre;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="reservationMaisons")
     * @Groups("reservation")
     */
    private $user;

    /**
     * @ORM\Column(type="date")
     * @Groups("reservation")
     */
    private $date_arrivee;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups("reservation")
     */
    private $etats;

    /**
     * @ORM\Column(type="date")
     * @Groups("reservation")
     */
    private $date_depart;

    /**
     * @ORM\Column(type="float", nullable=true)
     * @Groups("reservation")
     */
    private $total_prix;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups("reservation")
     */
    private $nomChambre;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups("reservation")
     */
    private $nomUser;

    /**
     * @ORM\Column(type="string", nullable=true)
     * @Groups("reservation")
     */
    private $hebergeur_id;

    public function getId(): ?int
    {
        return $this->id;
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
        return $this->date_arrivee;
    }

    public function setDateArrivee(\DateTimeInterface $date_arrivee): self
    {
        $this->date_arrivee = $date_arrivee;

        return $this;
    }

    public function getEtats(): ?string
    {
        return $this->etats;
    }

    public function setEtats(?string $etats): self
    {
        $this->etats = $etats;

        return $this;
    }

    public function getDateDepart(): ?\DateTimeInterface
    {
        return $this->date_depart;
    }

    public function setDateDepart(\DateTimeInterface $date_depart): self
    {
        $this->date_depart = $date_depart;

        return $this;
    }

    public function getTotalPrix(): ?float
    {
        return $this->total_prix;
    }

    public function setTotalPrix(?float $total_prix): self
    {
        $this->total_prix = $total_prix;

        return $this;
    }

    public function jsonSerialize()
    {
        return array(
            'id' => $this->id,
            'chambre' => $this->chambre,
            'user' => $this->user,
            'date_arrivee' => $this->date_arrivee,
            'etats' => $this->etats,
            'date_depart' => $this->date_depart,
            'total_prix' => $this->total_prix,
            'nom_chambre' => $this->nomChambre,
            'nom_user' => $this->nomUser,
            'hebergeur_id' => $this->hebergeur_id
        );
    }

    public function getNomChambre(): ?string
    {
        return $this->nomChambre;
    }

    public function setNomChambre(?string $nomChambre): self
    {
        $this->nomChambre = $nomChambre;

        return $this;
    }

    public function getNomUser(): ?string
    {
        return $this->nomUser;
    }

    public function setNomUser(?string $nomUser): self
    {
        $this->nomUser = $nomUser;

        return $this;
    }

    public function getHebergeurId(): ?string
    {
        return $this->hebergeur_id;
    }

    public function setHebergeurId(string $hebergeur_id): self
    {
        $this->hebergeur_id = $hebergeur_id;

        return $this;
    }

}
