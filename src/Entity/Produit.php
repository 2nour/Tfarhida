<?php

namespace App\Entity;

use App\Repository\ProduitRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

use JsonSerializable;
use Symfony\Component\Serializer\Annotation\Groups;


/**
 * @ORM\Entity(repositoryClass=ProduitRepository::class)
 */

class Produit implements JsonSerializable

{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups("produit")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=25)
     * @Assert\NotBlank(message="nom est requis")
     * @Groups("produit")

     */
    private $nom;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank(message="quantite est requis")
     * @Groups("produit")

     */
    private $quantite;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Assert\NotBlank(message="description est requis")

     * @Groups("produit")

     */
    private $description;

    /**
     * @ORM\Column(type="float")
     * @Assert\NotBlank(message="prix est requis")
     * @Groups("produit")

     */
    private $prix;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups("produit")

     */
    private $marque;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="image est requis")
     * @Groups("produit")
     */
    private $image;

    /**
     * @ORM\ManyToMany(targetEntity=Organisation::class, mappedBy="produits")
     */
    private $organisation;

    public function __construct()
    {
        $this->organisation = new ArrayCollection();}



    /**
     * @ORM\OneToMany(targetEntity=comment::class,cascade={"persist", "remove"}, mappedBy="produit")
     * @Groups("produit")
     */
    private $comments;

    /**
     * @ORM\OneToMany(targetEntity=Commande::class, mappedBy="produit",cascade={"persist", "remove"}, orphanRemoval=true)
     * @Groups("produit")
     */
    private $commandes;

    /**
     * @ORM\ManyToMany(targetEntity=Categorie::class, inversedBy="produit")
     * @Groups("produit")
     */
    private $categories;

    public function __construct()
    {
        $this->comments = new ArrayCollection();
        $this->commandes = new ArrayCollection();
        $this->categories = new ArrayCollection();

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
    /**
     * @return Collection|comment[]
     */
    public function getComments(): Collection
    {
        return $this->comments;
    }

    public function addComment(comment $comment): self
    {
        if (!$this->comments->contains($comment)) {
            $this->comments[] = $comment;
            $comment->setProduit($this);
        }

        return $this;
    }

    public function removeComment(comment $comment): self
    {
        if ($this->comments->removeElement($comment)) {
            // set the owning side to null (unless already changed)
            if ($comment->getProduit() === $this) {
                $comment->setProduit(null);
            }

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
            $commande->setProduit($this);

        }

        return $this;
    }


    public function removeCommande(Commande $commande): self
    {
        if ($this->commandes->removeElement($commande)) {
            // set the owning side to null (unless already changed)
            if ($commande->getProduit() === $this) {
                $commande->setProduit(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection|Categorie[]
     */
    public function getCategories(): Collection
    {
        return $this->categories;
    }

    public function addCategory(Categorie $category): self
    {
        if (!$this->categories->contains($category)) {
            $this->categories[] = $category;
            $category->addProduit($this);
        }

        return $this;
    }

    public function removeCategory(Categorie $category): self
    {
        if ($this->categories->removeElement($category)) {
            $category->removeProduit($this);
        }

        return $this;
    }


    public function jsonSerialize()
    {
        return array(
            'id' => $this->id,
            'nom' => $this->nom,
            'quantite' => $this->quantite,
            'description' => $this->description,
            'prix' => $this->prix,
            'marque' => $this->marque,
            'image' => $this->image,

        );
    }

}