<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210301125151 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE chambre (id INT AUTO_INCREMENT NOT NULL, maison_id INT DEFAULT NULL, nom VARCHAR(255) NOT NULL, type_lit VARCHAR(255) NOT NULL, nbr_pers INT NOT NULL, prix DOUBLE PRECISION NOT NULL, photo VARCHAR(255) NOT NULL, INDEX IDX_C509E4FF9D67D8AF (maison_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE maison (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, adresse VARCHAR(255) NOT NULL, nbr_chambre INT NOT NULL, photo VARCHAR(255) NOT NULL, description VARCHAR(1000) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE produit (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(25) NOT NULL, quantite INT NOT NULL, description VARCHAR(255) DEFAULT NULL, prix DOUBLE PRECISION NOT NULL, marque VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE chambre ADD CONSTRAINT FK_C509E4FF9D67D8AF FOREIGN KEY (maison_id) REFERENCES maison (id)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE chambre DROP FOREIGN KEY FK_C509E4FF9D67D8AF');
        $this->addSql('DROP TABLE chambre');
        $this->addSql('DROP TABLE maison');
        $this->addSql('DROP TABLE produit');
    }
}
