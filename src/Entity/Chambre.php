<?php

namespace App\Entity;

use App\Repository\ChambreRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use JsonSerializable;
use Symfony\Component\Serializer\Annotation\Groups;
use App\Entity\ReservationMaison;

/**
 * @ORM\Entity(repositoryClass=ChambreRepository::class)
 */
class Chambre implements JsonSerializable
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups("chambre")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="nom est requis")
     * @Groups("chambre")
     */
    private $nom;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="type de lit est requis")
     * @Groups("chambre")
     */
    private $typeLit;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank(message="Nombre de personne est requis")
     * @Groups("chambre")
     */
    private $nbr_pers;

    /**
     * @ORM\Column(type="float")
     * @Assert\NotBlank(message="Prix est requis")
     * @Groups("chambre")
     */
    private $prix;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Photo est requis")
     * @Groups("chambre")
     */
    private $photo;

    /**
     * @ORM\ManyToOne(targetEntity=Maison::class, inversedBy="chambres")
     * @Groups("chambre")
     */
    private $maison;

    /**
     * @ORM\OneToMany(targetEntity=ReservationMaison::class, mappedBy="chambre")
     * @Groups("chambre")
     */
    private $reservations;

    /**
     * @ORM\Column(type="string", nullable=true)
     * @Groups("chambre")
     */
    private $user_id;



    public function __construct()
    {
        $this->reservations = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getTypeLit(): ?string
    {
        return $this->typeLit;
    }

    public function setTypeLit(string $typeLit): self
    {
        $this->typeLit = $typeLit;

        return $this;
    }

    public function getNbrPers(): ?int
    {
        return $this->nbr_pers;
    }

    public function setNbrPers(int $nbr_pers): self
    {
        $this->nbr_pers = $nbr_pers;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getPhoto()
    {
        return $this->photo;
    }

    public function setPhoto( $photo)
    {
        $this->photo = $photo;

        return $this;
    }

    public function getMaison(): ?Maison
    {
        return $this->maison;
    }

    public function setMaison(?Maison $maison): self
    {
        $this->maison = $maison;

        return $this;
    }


    /**
     * @return Collection|ReservationMaison[]
     */
    public function getReservations(): Collection
    {
        return $this->reservations;
    }

    public function addReservations(ReservationMaison $reservations): self
    {
        if (!$this->reservations->contains($reservations)) {
            $this->reservations[] = $reservations;
            $reservations->setChambre($this);
        }

        return $this;
    }

    public function removeReservations(ReservationMaison $reservations): self
    {
        if ($this->reservations->removeElement($reservations)) {
            // set the owning side to null (unless already changed)
            if ($reservations->getChambre() === $this) {
                $reservations->setChambre(null);
            }
        }

        return $this;
    }

    public function jsonSerialize()
    {
        return array(
            'id' => $this->id,
            'nom' => $this->nom,
            'typeLit' => $this->typeLit,
            'nbr_pers' => $this->nbr_pers,
            'photo' => $this->photo,
            'prix' => $this->prix,
            'maison_id' => $this->maison,
            'nomHebergeur' => $this->user_id
        );
    }

    public function getUserId(): ?string
    {
        return $this->user_id;
    }

    public function setUserId(?string $user_id): self
    {
        $this->user_id = $user_id;

        return $this;
    }
}