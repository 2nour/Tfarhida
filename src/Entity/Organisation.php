<?php

namespace App\Entity;

use App\Repository\OrganisationRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use PhpParser\Node\Scalar\String_;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;
use JsonSerializable;

/**
 * @ORM\Entity(repositoryClass=OrganisationRepository::class)
 */
class Organisation implements JsonSerializable
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups ("organisation")
     *

     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Nombre de personnes obligatoire")
     * @Groups ("organisation")
     */
    private $nbrpersonne;

    /**
     * @ORM\Column(type="integer")
     *  @Assert\NotBlank(message="Nombre de jours obligatoire")
     * @Groups ("organisation")
     */
    private $nbrjours;



    /**
     * @ORM\Column(type="text", length=255)
     * @Groups ("organisation")
     *
     */
    private $etat;

    /**
     * @ORM\Column(type="text", length=255)
     * @Groups ("organisation")
     *
     */

    private $commentaire;

    /**
     * @ORM\Column(type="date" , type="datetime")
     * @Assert\NotBlank(message="Date obligatoire")
     * @Groups ("organisation")
     */
    private $date;

    /**
     *@ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="activite obligatoire")
     * @Groups ("organisation")
     */
    private $activite;
    /**
     *@ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="lieu obligatoire ")
     * @Groups ("organisation")
     */
    private $Lieu;

    /**
     * @ORM\ManyToMany(targetEntity=Produit::class, inversedBy="organisation")
     *@Groups ("organisation")
     */
    private $produits;

    public function __construct()
    {
        $this->produits = new ArrayCollection();
    }

    public function jsonSerialize()
    {
        return array(
            'id' => $this->id,
            'nbrpersonne' => $this->nbrpersonne,
            'nbrjours' => $this->nbrjours,
            'etat' => $this->etat,
            'commentaire' => $this->commentaire,
            'date' => $this->date,
            'activite' => $this->activite,
            'Lieu' => $this->Lieu
        );
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNbrpersonne(): ?string
    {
        return $this->nbrpersonne;
    }

    public function setNbrpersonne(string $nbrpersonne): self
    {
        $this->nbrpersonne = $nbrpersonne;

        return $this;
    }
    public function getnbrjours(): ?string
    {
        return $this->nbrjours;
    }

    public function setnbrjours(string $nbrjours): self
    {
        $this->nbrjours = $nbrjours;

        return $this;
    }


    public function getDate()
    {
        return $this->date;
    }

    public function setDate($date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getNomPrenom(): ?string
    {
        return $this->NomPrenom;
    }

    public function setNomPrenom(string $NomPrenom): self
    {
        $this->NomPrenom = $NomPrenom;

        return $this;
    }

    public function getNumero(): ?int
    {
        return $this->Numero;
    }

    public function setNumero(int $Numero): self
    {
        $this->Numero = $Numero;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getCommentaire(): ?string
    {
        return $this->commentaire;
    }

    public function setCommentaire(?string $commentaire): self
    {
        $this->commentaire = $commentaire;

        return $this;
    }
    public function getetat(): ?string
    {
        return $this->etat;
    }

    public function setetat(?string $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    public function getActivite(): ?string
    {
        return $this->activite;
    }

    public function setActivite(?string $activite): self
    {
        $this->activite = $activite;

        return $this;
    }

    public function getLieu(): ?string
    {
        return $this->Lieu;
    }

    public function setLieu(?string $Lieu): self
    {
        $this->Lieu = $Lieu;

        return $this;
    }

    /**
     * @return Collection|produit[]
     */
    public function getProduits(): Collection
    {
        return $this->produits;
    }

    public function addProduit(produit $produit): self
    {
        if (!$this->produits->contains($produit)) {
            $this->produits[] = $produit;
        }

        return $this;
    }

    public function removeProduit(produit $produit): self
    {
        $this->produits->removeElement($produit);

        return $this;
    }
}
