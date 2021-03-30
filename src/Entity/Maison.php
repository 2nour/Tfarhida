<?php

namespace App\Entity;

use App\Repository\MaisonRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use JsonSerializable;

/**
 * @ORM\Entity(repositoryClass=MaisonRepository::class)
 */
class Maison implements JsonSerializable
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Nom est requis")
     */
    private $nom;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Adresse est requis")
     */
    private $adresse;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank(message="Nombre chambre est requis")
     */
    private $nbr_chambre;

    /**
     * @ORM\Column(type="string", length=1000)
     */
    private $description;

    /**
     * @ORM\OneToMany(targetEntity=Chambre::class, mappedBy="maison", cascade={"all"})
     */
    private $chambres;


    /**
     * @ORM\Column(type="string", length=255)
     */
    private $photo;

    /**
     * @ORM\Column(type="integer")
     * @Assert\NotBlank(message="Tel est requis")
     */
    private $tel;

    /**
     * @ORM\OneToMany(targetEntity=Favoris::class, mappedBy="maison")
     */
    private $favoris;

    protected $captchaCode;

    /**
     * @ORM\OneToMany(targetEntity=Commentaire::class, mappedBy="maison")
     */
    private $commentaires;

    /**
     * @ORM\Column(type="integer", nullable=true, options={"default" = 0})
     */
    private $nbrComment;

    public function __construct()
    {
        $this->chambres = new ArrayCollection();
        $this->favoris = new ArrayCollection();
        $this->commentaires = new ArrayCollection();
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

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getNbrChambre(): ?int
    {
        return $this->nbr_chambre;
    }

    public function setNbrChambre(int $nbr_chambre): self
    {
        $this->nbr_chambre = $nbr_chambre;

        return $this;
    }



    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    /**
     * @return Collection|Chambre[]
     */
    public function getChambres(): Collection
    {
        return $this->chambres;
    }

    public function addChambre(Chambre $chambre): self
    {
        if (!$this->chambres->contains($chambre)) {
            $this->chambres[] = $chambre;
            $chambre->setMaison($this);
        }

        return $this;
    }

    public function removeChambre(Chambre $chambre): self
    {
        if ($this->chambres->removeElement($chambre)) {
            // set the owning side to null (unless already changed)
            if ($chambre->getMaison() === $this) {
                $chambre->setMaison(null);
            }
        }

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

    public function getTel(): ?int
    {
        return $this->tel;
    }

    public function setTel(int $tel): self
    {
        $this->tel = $tel;

        return $this;
    }

    /**
     * @return Collection|Favoris[]
     */
    public function getFavoris(): Collection
    {
        return $this->favoris;
    }

    public function addFavori(Favoris $favori): self
    {
        if (!$this->favoris->contains($favori)) {
            $this->favoris[] = $favori;
            $favori->setMaison($this);
        }

        return $this;
    }

    public function removeFavori(Favoris $favori): self
    {
        if ($this->favoris->removeElement($favori)) {
            // set the owning side to null (unless already changed)
            if ($favori->getMaison() === $this) {
                $favori->setMaison(null);
            }
        }

        return $this;
    }


    public function contientFavori(int $idUser):bool
    {
        return $this->favoris->exists(function($key, $value) use ($idUser) {
            return $value ->getUser()-> getId() === ($idUser);
        });

    }

    public function getFavoriWithIdUser(int $idUser): Favoris
    {
        return $this->favoris->filter(function($value) use ($idUser) {
            return $value ->getUser()-> getId() === ($idUser);
        })->first();

    }

    public function jsonSerialize()
    {
        return array(
            'id' => $this->id,
            'nom' => $this->nom,
            'photo' => $this->photo,
            'description' => $this->description,
            'nbr_chambre' => $this->nbr_chambre,
            'adresse' => $this->adresse
        );
    }

    /**
     * @return Collection|comment[]
     */
    public function getCommentaires(): Collection
    {
        return $this->commentaires;
    }

    public function addCommentaire(comment $commentaire): self
    {
        if (!$this->commentaires->contains($commentaire)) {
            $this->commentaires[] = $commentaire;
            $commentaire->setMaison($this);
        }

        return $this;
    }

    public function removeCommentaire(comment $commentaire): self
    {
        if ($this->commentaires->removeElement($commentaire)) {
            // set the owning side to null (unless already changed)
            if ($commentaire->getMaison() === $this) {
                $commentaire->setMaison(null);
            }
        }

        return $this;
    }

    public function getNbrComment(): ?int
    {
        return $this->nbrComment;
    }

    public function setNbrComment(?int $nbrComment): self
    {
        $this->nbrComment = $nbrComment;

        return $this;
    }
}