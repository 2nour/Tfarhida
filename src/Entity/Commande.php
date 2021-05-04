<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use Doctrine\ORM\Mapping as ORM;
use JsonSerializable;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=CommandeRepository::class)
 */
class Commande implements JsonSerializable
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups("commande")
     */
    private $id;

    /**
     * @ORM\ManyToOne(targetEntity=Produit::class, inversedBy="commandes")
     * @ORM\JoinColumn(nullable=false)
     * @Groups("commande")
     */
    private $produit;

    /**
     * @ORM\ManyToOne(targetEntity=Panier::class, inversedBy="commandes")
     * @ORM\JoinColumn(nullable=false)
     * @Groups("commande")
     */
    private $panier;

    /**
     * @ORM\Column(type="integer", nullable=true)
     * @Groups("commande")
     */
    private $quantiteProduit;

    /**
     * @ORM\Column(type="float", nullable=true)
     * @Groups("commande")
     */
    private $prixcommande;

    public function getId(): ?int
    {
        return $this->id;
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

    public function getPanier(): ?Panier
    {
        return $this->panier;
    }

    public function setPanier(?Panier $panier): self
    {
        $this->panier = $panier;

        return $this;
    }

    public function getQuantiteProduit(): ?int
    {
        return $this->quantiteProduit;
    }

    public function setQuantiteProduit(?int $quantiteProduit): self
    {
        $this->quantiteProduit = $quantiteProduit;

        return $this;
    }

    public function getPrixcommande(): ?float
    {
        return $this->prixcommande;
    }

    public function setPrixcommande(?float $prixcommande): self
    {
        $this->prixcommande = $prixcommande;

        return $this;
    }

    public function jsonSerialize()
    {
        return array(
            'id' => $this->id,
            'quantiteProduit' => $this->quantiteProduit,
            'prixcommande' => $this->prixcommande
        );
    }
}