<?php

namespace App\Entity;

use App\Repository\OrgProduitRepository;
use Doctrine\ORM\Mapping as ORM;

use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=OrgProduitRepository::class)
 */
class OrgProduit
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups ("organisation")
     */
    private $id;

    /**
     * @ORM\ManyToOne(targetEntity=Produit::class)
     * @ORM\JoinColumn(nullable=false)
     * @Groups ("organisation")
     */
    private $idProduit;

    /**
     * @ORM\ManyToOne   (targetEntity=Organisation::class)
     * @ORM\JoinColumn(nullable=false)
     * @Groups ("organisation")
     */
    private $idOrganisation;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdProduit(): ?Produit
    {
        return $this->idProduit;
    }

    public function setIdProduit(?Produit $idProduit): self
    {
        $this->idProduit = $idProduit;

        return $this;
    }

    public function getIdOrganisation(): ?Organisation
    {
        return $this->idOrganisation;
    }

    public function setIdOrganisation(?Organisation $idOrganisation): self
    {
        $this->idOrganisation = $idOrganisation;

        return $this;
    }
}
