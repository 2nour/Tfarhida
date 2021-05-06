<?php

namespace App\Entity;

use App\Repository\PanierRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use JsonSerializable;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=PanierRepository::class)
 */
class Panier implements JsonSerializable
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups("panier")
     */
    private $id;

    /**
     * @ORM\Column(type="integer")
     * @Groups("panier")
     */
    private $nbproduit;

    /**
     * @ORM\Column(type="float")
     * @Groups("panier")
     */
    private $somme;

    /**
     * @ORM\OneToMany(targetEntity=Commande::class, mappedBy="panier", orphanRemoval=true)
     * @Groups("panier")
     */
    private $commandes;

    /**
     * @ORM\OneToOne(targetEntity=User::class, inversedBy="panier", cascade={"persist", "remove"})
     * @ORM\JoinColumn(nullable=false)
     * @Groups("panier")
     */
    private $user;

    public function __construct()
    {
        $this->commandes = new ArrayCollection();
    }






    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNbproduit(): ?int
    {
        return $this->nbproduit;
    }

    public function setNbproduit(int $nbproduit): self
    {
        $this->nbproduit = $nbproduit;

        return $this;
    }

    public function getSomme(): ?float
    {
        return $this->somme;
    }

    public function setSomme(float $somme): self
    {
        $this->somme = $somme;

        return $this;
    }

    /**
     * @return Collection|Commande[]
     */
    public function getCommandes(): Collection
    {
        return $this->commandes;
    }

    public function addCommande(Commande $commande): self
    {
        if (!$this->commandes->contains($commande)) {
            $this->commandes[] = $commande;
            $commande->setPanier($this);
        }

        return $this;
    }

    public function removeCommande(Commande $commande): self
    {
        if ($this->commandes->removeElement($commande)) {
            // set the owning side to null (unless already changed)
            if ($commande->getPanier() === $this) {
                $commande->setPanier(null);
            }
        }

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(User $user): self
    {
        $this->user = $user;

        return $this;
    }


    public function jsonSerialize()
    {
        return array(
            'id' => $this->id,
            'nbproduit' => $this->nbproduit,
            'somme' => $this->somme

        );
    }
}
