<?php

namespace App\Entity;
//use App\Entity\Useer;
use DateTime;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Constraints\Image;
use App\Repository\RandonneeRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=RandonneeRepository::class)
 */
class Randonnee
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Length(
     * min = 3,
     * max = 255,
     * minMessage = "Erreur il doit comporter au moins {{ limit }} caractères",
     * maxMessage = "Erreur il  doit comporter au plus {{ limit }} caractères"
     * )
     */
    private $villedepart;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Length(
     * min = 3,
     * max = 255,
     * minMessage = "Erreur il doit comporter au moins {{ limit }} caractères",
     * maxMessage = "Erreur il  doit comporter au plus {{ limit }} caractères"
     * )
     */
    private $villearrivee;

    /**
     * @ORM\Column(type="datetime",nullable=true)
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $datedepart;

    /**
     * @ORM\Column(type="datetime",nullable=true)
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $dateretour;

    /**
     * @ORM\Column(type="string", length=255)
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $activite;

    /**
     * @ORM\Column(type="string", length=255)
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $description;

    /**
     * @ORM\Column(type="string", length=255)
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $image;

    /**
     * @ORM\Column(type="integer")
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $duree;

    /**
     * @ORM\Column(type="string", length=255)
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $difficulte;

    /**
     * @ORM\Column(type="integer")
     * * @Assert\NotBlank(message="nom est requis")
     */
    private $budget;

    /**
     * @ORM\OneToMany(targetEntity=PostLike::class, mappedBy="randonnee")
     */
    private $likes;

    public function __construct()
    {
        $this->likes = new ArrayCollection();
    }


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getVilledepart(): ?string
    {
        return $this->villedepart;
    }

    public function setVilledepart(string $villedepart): self
    {
        $this->villedepart = $villedepart;

        return $this;
    }

    public function getVillearrivee(): ?string
    {
        return $this->villearrivee;
    }

    public function setVillearrivee(string $villearrivee): self
    {
        $this->villearrivee = $villearrivee;

        return $this;
    }

    /**
     * @return mixed
     */
    public function getDatedepart()
    {
        return $this->datedepart;
    }

    /**
     * @param mixed $datedepart
     */
    public function setDatedepart($datedepart): void
    {
        $this->datedepart = $datedepart;
    }

    /**
     * @return mixed
     */
    public function getDateretour()
    {
        return $this->dateretour;
    }

    /**
     * @param mixed $dateretour
     */
    public function setDateretour($dateretour): void
    {
        $this->dateretour = $dateretour;
    }


    public function getActivite(): ?string
    {
        return $this->activite;
    }

    public function setActivite(string $activite): self
    {
        $this->activite = $activite;

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

    public function getImage()
    {
        return $this->image;
    }

    public function setImage( $image)
    {
        $this->image = $image;

        return $this;
    }

    public function getDuree(): ?int
    {
        return $this->duree;
    }

    public function setDuree(int $duree): self
    {
        $this->duree = $duree;

        return $this;
    }

    public function getDifficulte(): ?string
    {
        return $this->difficulte;
    }

    public function setDifficulte(string $difficulte): self
    {
        $this->difficulte = $difficulte;

        return $this;
    }

    public function getBudget(): ?int
    {
        return $this->budget;
    }

    public function setBudget(int $budget): self
    {
        $this->budget = $budget;

        return $this;
    }

    /**
     * @return Collection|PostLike[]
     */
    public function getLikes(): Collection
    {
        return $this->likes;
    }

    public function addLike(PostLike $like): self
    {
        if (!$this->likes->contains($like)) {
            $this->likes[] = $like;
            $like->setPost($this);
        }

        return $this;
    }

     public function removeLike(PostLike $like): self
    {
     if ($this->likes->removeElement($like)) {
            // set the owning side to null (unless already changed)
    if ($like->getPost() === $this) {
      $like->setPost(null);
        }
     }

      return $this;
      }

    /**
     * permet de savoir si cette randonnée est liké par un utilisateur
     * @param User $user
     * @return bool
     */
    public function isLikedByUser(User $user) : bool {
    foreach ($this->likes as $like){
        if($like->getUser() == $user) return true;
    }
    return false;

    }


}
