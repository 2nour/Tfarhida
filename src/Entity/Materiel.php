<?php

namespace App\Entity;

use App\Repository\MaterielRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=MaterielRepository::class)
 */
class Materiel
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
    private $idMat;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $nomMat;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $photo;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdMat(): ?string
    {
        return $this->idMat;
    }

    public function setIdMat(string $idMat): self
    {
        $this->idMat = $idMat;

        return $this;
    }

    public function getNomMat(): ?string
    {
        return $this->nomMat;
    }

    public function setNomMat(string $nomMat): self
    {
        $this->nomMat = $nomMat;

        return $this;
    }

    public function getPhoto(): ?string
    {
        return $this->photo;
    }

    public function setPhoto(string $photo): self
    {
        $this->photo = $photo;

        return $this;
    }
}
