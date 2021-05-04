<?php

namespace App\Entity;

use App\Repository\ProduitRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=ProduitRepository::class)
 */
class Produit
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=25)
     * @Assert\NotBlank(message="nom est requis")
     */
    private $nom;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank(message="quantite est requis")
     */
    private $quantite;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Assert\NotBlank(message="description est requis")
     */
    private $description;

    /**
     * @ORM\Column(type="float")
     * @Assert\NotBlank(message="prix est requis")
     */
    private $prix;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    private $marque;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="image est requis")
     */
    private $image;

    /**
     * @ORM\ManyToMany(targetEntity=Organisation::class, mappedBy="produits")
     */
    private $organisation;

    public function __construct()
    {
        $this->organisation = new ArrayCollection();
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

    public function getQuantite(): ?int
    {
        return $this->quantite;
    }

    public function setQuantite(int $quantite): self
    {
        $this->quantite = $quantite;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): self
    {
        $this->description = $description;

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

    public function getMarque(): ?string
    {
        return $this->marque;
    }

    public function setMarque(?string $marque): self
    {
        $this->marque = $marque;

        return $this;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage( $image)
    {
        $this->image = $image;

        return $this;
    }

    /**
     * @return Collection|Organisation[]
     */
    public function getOrganisation(): Collection
    {
        return $this->organisation;
    }

    public function addOrganisation(Organisation $organisation): self
    {
        if (!$this->organisation->contains($organisation)) {
            $this->organisation[] = $organisation;
            $organisation->addProduit($this);
        }

        return $this;
    }

    public function removeOrganisation(Organisation $organisation): self
    {
        if ($this->organisation->removeElement($organisation)) {
            $organisation->removeProduit($this);
        }

        return $this;
    }

}