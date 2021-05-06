<?php

namespace App\Entity;

use Symfony\Component\Validator\Constraints as Assert;
use App\Repository\ReservationRepository;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\DBAL\Types\DateType;

/**
 * @ORM\Entity(repositoryClass=ReservationRepository::class)
 */
class Reservation
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="integer")
     */
    private $numreservation;

    /**
     * @ORM\Column(type="date", type="datetime")
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $datereservation;

    /**
     * @ORM\Column(type="string", length=255)
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $observation;

    /**
     * @ORM\Column(type="float")
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $montant;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $idRandonnee;

    /**
     * @ORM\Column(type="integer")
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $nombrepersonne;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $etat;

    protected $captchaCode;

    public function getId(): ?int
    {
        return $this->id;
    }


    public function getNumreservation(): ?int
    {
        return $this->numreservation;
    }

    public function setNumreservation(int $numreservation): self
    {
        $this->numreservation = $numreservation;

        return $this;
    }

    public function getDatereservation()
    {
        return $this->datereservation;
    }

    public function setDatereservation($datereservation): self
    {
        $this->datereservation = $datereservation;

        return $this;
    }

    public function getObservation(): ?string
    {
        return $this->observation;
    }

    public function setObservation(string $observation): self
    {
        $this->observation = $observation;

        return $this;
    }

    public function getMontant(): ?float
    {
        return $this->montant;
    }

    public function setMontant(float $montant): self
    {
        $this->montant = $montant;

        return $this;
    }

    public function getIdRandonnee(): ?string
    {
        return $this->idRandonnee;
    }

    public function setIdRandonnee(string $idRandonnee): self
    {
        $this->idRandonnee = $idRandonnee;

        return $this;
    }

    public function getNombrepersonne(): ?int
    {
        return $this->nombrepersonne;
    }

    public function setNombrepersonne(int $nombrepersonne): self
    {
        $this->nombrepersonne = $nombrepersonne;

        return $this;
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function setEtat(string $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    public function getCaptchaCode()
    {
        return $this->captchaCode;
    }

    public function setCaptchaCode($captchaCode)
    {
        $this->captchaCode = $captchaCode;
    }

}