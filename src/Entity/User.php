<?php

namespace App\Entity;

use App\Repository\UserRepository;
use App\Entity\comment;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Validator\Constraints\Json;

use Doctrine\Common\Collections\ArrayCollection;
/**
 * @ORM\Entity(repositoryClass=UserRepository::class)
 * @UniqueEntity(
 *     fields={"email","username"},
 *     message= "l'email que vous avez indiqué est déja utilisé ! "
 *     )
 */
class User implements UserInterface
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Email()
     */
    private $email;

    /**
     * @ORM\Column(type="string", length=255)
     *
     */
    private $username;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Length(min="8", minMessage="votre mot de passe doit faire mininum 8 carractéres")
     */
    private $password;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\EqualTo(propertyPath="password",message="vous n'avez pas tapé le meme mot de passe" )
     */
    private $confirm_password;

    /**
     * @ORM\Column(type="string", length=50, nullable=true)
     */
    private $Activation_token;

    /**
     * @ORM\Column(type="json")
     */
    private $roles = [];

    /**
     * @ORM\Column(type="string", length=50, nullable=true)
     */
    private $reset_token;

    /**
     * @ORM\OneToMany(targetEntity=Favoris::class, mappedBy="user")
     */
    private $favoris;

    /**
     * @ORM\OneToMany(targetEntity=Commentaire::class, mappedBy="user")
     */
    private $commentaires;

    /**
     * @ORM\OneToOne(targetEntity=Panier::class, mappedBy="user", cascade={"persist", "remove"})
     */
    private $panier;

    /**
     * @ORM\OneToMany(targetEntity=comment::class, mappedBy="User")
     */
    private $comments;

    /**
     * @ORM\OneToMany(targetEntity=ReservationMaison::class, mappedBy="user", orphanRemoval=true)
     */
    private $reservationMaisons;

    public function __construct()
    {
        $this->favoris = new ArrayCollection();
        $this->commentaires = new ArrayCollection();
        $this->setRoles(["ROLE_USER"]);
        $this->comments = new ArrayCollection();
        $this->reservationMaisons = new ArrayCollection();
    }



    public function getId(): ?int
    {
        return $this->id;
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

    public function getUsername(): ?string
    {
        return $this->username;
    }

    public function setUsername(string $username): self
    {
        $this->username = $username;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    public function getConfirmPassword(): ?string
    {
        return $this->confirm_password;
    }

    public function setConfirmPassword(string $confirm_password): self
    {
        $this->confirm_password = $confirm_password;

        return $this;
    }
    public function eraseCredentials()
    {
        // TODO: Implement eraseCredentials() method.
    }
    public function getSalt()
    {
        // TODO: Implement getSalt() method.
    }

    public function getActivationToken(): ?string
    {
        return $this->Activation_token;
    }

    public function setActivationToken(?string $Activation_token): self
    {
        $this->Activation_token = $Activation_token;

        return $this;
    }

    public function getRoles(): ?array
    {
        $roles = $this->roles;
        $roles[] = 'ROLE_USER';
        return array_unique($roles);
    }

    public function setRoles(array $roles): self
    {
        $this->roles = $roles;

        return $this;
    }


    public function getResetToken(): ?string
    {
        return $this->reset_token;
    }

    public function setResetToken(?string $reset_token): self
    {
        $this->reset_token = $reset_token;

        return $this;
    }

    /**
     * @return mixed
     */
    public function getFavoris()
    {
        return $this->favoris;
    }

    /**
     * @param mixed $favoris
     */
    public function setFavoris($favoris): void
    {
        $this->favoris = $favoris;
    }

    /**
     * @return mixed
     */
    public function getCommentaires()
    {
        return $this->commentaires;
    }

    /**
     * @param mixed $commentaires
     */
    public function setCommentaires($commentaires): void
    {
        $this->commentaires = $commentaires;
    }
    public function removeFavori(Favoris $favori): self
    {
        if ($this->favoris->removeElement($favori)) {
            // set the owning side to null (unless already changed)
            if ($favori->getUser() === $this) {
                $favori->setUser(null);
            }
        }

        return $this;
    }

    public function contientFavori(Favoris $fav):bool
    {
        return $this->favoris->exists(function($key, $value) use ($fav) {
            return $value ->getMaison()-> getId() === ($fav ->getMaison()-> getId());
        });

    }


    public function removeCommentaire(comment $commentaire): self
    {
        if ($this->commentaires->removeElement($commentaire)) {
            // set the owning side to null (unless already changed)
            if ($commentaire->getUser() === $this) {
                $commentaire->setUser(null);
            }
        }

        return $this;
    }

    public function getPanier(): ?Panier
    {
        return $this->panier;
    }

    public function setPanier(Panier $panier): self
    {
        // set the owning side of the relation if necessary
        if ($panier->getUser() !== $this) {
            $panier->setUser($this);
        }

        $this->panier = $panier;

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
            $comment->setUser($this);
        }

        return $this;
    }

    public function removeComment(comment $comment): self
    {
        if ($this->comments->removeElement($comment)) {
            // set the owning side to null (unless already changed)
            if ($comment->getUser() === $this) {
                $comment->setUser(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection|ReservationMaison[]
     */
    public function getReservationMaisons(): Collection
    {
        return $this->reservationMaisons;
    }

    public function addReservationMaison(ReservationMaison $reservationMaison): self
    {
        if (!$this->reservationMaisons->contains($reservationMaison)) {
            $this->reservationMaisons[] = $reservationMaison;
            $reservationMaison->setUser($this);
        }

        return $this;
    }

    public function removeReservationMaison(ReservationMaison $reservationMaison): self
    {
        if ($this->reservationMaisons->removeElement($reservationMaison)) {
            // set the owning side to null (unless already changed)
            if ($reservationMaison->getUser() === $this) {
                $reservationMaison->setUser(null);
            }
        }

        return $this;
    }


}