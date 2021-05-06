<?php

namespace App\Entity;

use App\Repository\HebergeurRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=HebergeurRepository::class)
 */
class Hebergeur
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $cin;

    /**
     * @ORM\Column(type="integer")
     */
    private $numeroDeTelephone;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getCin(): ?string
    {
        return $this->cin;
    }

    public function setCin(string $cin): self
    {
        $this->cin = $cin;

        return $this;
    }

    public function getNumeroDeTelephone(): ?int
    {
        return $this->numeroDeTelephone;
    }

    public function setNumeroDeTelephone(int $numeroDeTelephone): self
    {
        $this->numeroDeTelephone = $numeroDeTelephone;

        return $this;
    }
}
