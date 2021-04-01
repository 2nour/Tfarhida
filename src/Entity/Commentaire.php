<?php

namespace App\Entity;

use App\Repository\CommentaireRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=CommentaireRepository::class)
 */
class Commentaire
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $contenue;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $datedecommentaire;

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\Randonnee")
     * @ORM\JoinColumn(name="randonne_id", referencedColumnName="id")
     * */
    private $randonne;

    /**
     * @return mixed
     */
    public function getRandonne()
    {
        return $this->randonne;
    }

    /**
     * @param mixed $randonne
     */
    public function setRandonne($randonne): void
    {
        $this->randonne = $randonne;
    }

    /**
     * @return mixed
     */
    public function getUser()
    {
        return $this->user;
    }

    /**
     * @param mixed $user
     */
    public function setUser($user): void
    {
        $this->user = $user;
    }

    /**
     * @ORM\ManyToOne(targetEntity="App\Entity\User")
     * @ORM\JoinColumn(name="user_id", referencedColumnName="id")
     * */
    private $user;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getContenue(): ?string
    {
        return $this->contenue;
    }

    public function setContenue(string $contenue): self
    {
        $this->contenue = $contenue;

        return $this;
    }

    public function getDatedecommentaire(): ?string
    {
        return $this->datedecommentaire;
    }

    public function setDatedecommentaire(string $datedecommentaire): self
    {
        $this->datedecommentaire = $datedecommentaire;

        return $this;
    }


}
