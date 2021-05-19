<?php

namespace App\Entity;

use App\Repository\CommentaireRepository;
use Doctrine\ORM\Mapping as ORM;
use JsonSerializable;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=CommentaireRepository::class)
 */
class Commentaire implements JsonSerializable
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups("comment")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("comment")
     */
    private $comment;

    /**
     * @ORM\ManyToOne(targetEntity=Maison::class, inversedBy="commentaires")
     * @ORM\JoinColumn(nullable=false)
     * @Groups("comment")
     */
    private $maison;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="commentaires")
     * @ORM\JoinColumn(nullable=false)
     * @Groups("comment")
     */
    private $user;

    /**
     * @ORM\Column(type="date")
     * @Groups("comment")
     */
    private $date;


    /**
     * Commentaire constructor.
     * @param $date
     */

    public function __construct(){

        $this->date = new \DateTime();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getComment(): ?string
    {
        return $this->comment;
    }

    public function setComment(string $comment): self
    {
        $this->comment = $comment;

        return $this;
    }

    public function getMaison(): ?Maison
    {
        return $this->maison;
    }

    public function setMaison(?Maison $maison): self
    {
        $this->maison = $maison;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function jsonSerialize()
    {
        return array(
            'id' => $this->id,
            'comment' => $this->comment,
            'date' => $this->date,

        );
    }
}