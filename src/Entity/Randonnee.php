<?php

namespace App\Entity;
use App\Entity\Useer;
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
     */
    private $datedepart;

    /**
     * @ORM\Column(type="datetime",nullable=true)
     */
    private $dateretour;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $activite;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $description;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $image;

    /**
     * @ORM\Column(type="integer")
     */
    private $duree;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $difficulte;

    /**
     * @ORM\Column(type="integer")
     */
    private $budget;

    /**
     * @ORM\OneToMany(targetEntity=Organisation::class, mappedBy="activite")
     */
    private $organisations;

    public function __construct()
    {
        $this->organisations = new ArrayCollection();
    }

    /**
     * @ORM\OneToMany(targetEntity=PostLiike::class, mappedBy="post")

    private $likes;

    public function __construct()
    {
        $this->likes = new ArrayCollection();
    }*/

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
     * @return Collection|PostLiike[]

    public function getLikes(): Collection
    {
        return $this->likes;
    }

    public function addLike(PostLiike $like): self
    {
        if (!$this->likes->contains($like)) {
            $this->likes[] = $like;
            $like->setPost($this);
        }

        return $this;
    }

    public function removeLike(PostLiike $like): self
    {
        if ($this->likes->removeElement($like)) {
            // set the owning side to null (unless already changed)
            if ($like->getPost() === $this) {
                $like->setPost(null);
            }
        }

        return $this;
    }*/

    /**
     * permet de savoir si cette randonnée est liké par un utilisateur
     * @param Useer $useer
     * @return bool

    public function isLikedByUser(Useer $useer) : bool {
    foreach ($this->likes as $like){
        if($like->getUseer() == $useer) return true;
    }
    return false;

    } */

    /**
     * @return Collection|Organisation[]
     */
    public function getOrganisations(): Collection
    {
        return $this->organisations;
    }

    public function addOrganisation(Organisation $organisation): self
    {
        if (!$this->organisations->contains($organisation)) {
            $this->organisations[] = $organisation;
            $organisation->setActivite($this);
        }

        return $this;
    }

    public function removeOrganisation(Organisation $organisation): self
    {
        if ($this->organisations->removeElement($organisation)) {
            // set the owning side to null (unless already changed)
            if ($organisation->getActivite() === $this) {
                $organisation->setActivite(null);
            }
        }

        return $this;
    }
}
