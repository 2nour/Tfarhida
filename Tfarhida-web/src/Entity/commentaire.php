<?php

namespace App\Entity;

use App\Repository\CommentaireRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=CommentaireRepository::class)
 */
class commentaire
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255, nullable=false)
     */
    private $contenue;

    /**
     * @ORM\Column(type="date",nullable=false)
     */
    private $datedecommentaire;

    /**
     * @ORM\ManyToOne(targetEntity=Produit::class,cascade={"persist","remove"}, inversedBy="comments")
     */
    private $produit;



    public function getId(): ?int
    {
        return $this->id;
    }

    public function getContenue(): ?string
    {
        return $this->contenue;
    }

    public function setContenue(?string $contenue): self
    {
        $this->contenue = $contenue;

        return $this;
    }

    public function getDatedecommentaire(): ?\DateTimeInterface
    {
        return $this->datedecommentaire;
    }

    public function setDatedecommentaire(\DateTimeInterface $datedecommentaire): self
    {
        $this->datedecommentaire = $datedecommentaire;

        return $this;
    }

    public function getProduit(): ?Produit
    {
        return $this->produit;
    }

    public function setProduit(?Produit $produit): self
    {
        $this->produit = $produit;

        return $this;
    }

}
