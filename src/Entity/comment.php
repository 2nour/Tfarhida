<?php

namespace App\Entity;

use App\Repository\CommentRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Vangrg\ProfanityBundle\Validator\Constraints as ProfanityAssert;


/**
 * @ORM\Entity(repositoryClass=CommentRepository::class)
 */
class comment
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255, nullable=false)
     * @ProfanityAssert\ProfanityCheck
     */
    private $contenue;

    /**
     * @ORM\Column(type="date",nullable=false)
     */
    private $datedecommentaire;

    /**
     * @ORM\ManyToOne(targetEntity=Produit::class, inversedBy="comments")
     */
    private $produit;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="comments")
     */
    private $User;

    /**
     * comment constructor.
     * @param $datedecommentaire
     */
    public function __construct()
    {
        $this->datedecommentaire = new \DateTime();
    }


    public function getId(): ?int
    {
        return $this->id;
    }

    public function getContenue(): ?string
    {
        return $this->contenue;
    }

    public function setContenue(?string $contenue): self
    {
        $this->contenue = $contenue;

        return $this;
    }

    public function getDatedecommentaire(): ?\DateTimeInterface
    {
        return $this->datedecommentaire;
    }

    public function setDatedecommentaire(\DateTimeInterface $datedecommentaire): self
    {
        $this->datedecommentaire = $datedecommentaire;

        return $this;
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

    public function getUser(): ?User
    {
        return $this->User;
    }

    public function setUser(?User $User): self
    {
        $this->User = $User;

        return $this;
    }

}
