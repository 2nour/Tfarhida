<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210314112723 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE favoris DROP FOREIGN KEY FK_8933C432A76ED395');
        $this->addSql('CREATE TABLE commande (id INT AUTO_INCREMENT NOT NULL, produit_id INT NOT NULL, panier_id INT NOT NULL, quantite_produit INT DEFAULT NULL, prixcommande DOUBLE PRECISION DEFAULT NULL, INDEX IDX_6EEAA67DF347EFB (produit_id), INDEX IDX_6EEAA67DF77D927C (panier_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE commentaire (id INT AUTO_INCREMENT NOT NULL, produit_id INT DEFAULT NULL, contenue VARCHAR(255) NOT NULL, datedecommentaire DATE NOT NULL, INDEX IDX_67F068BCF347EFB (produit_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE panier (id INT AUTO_INCREMENT NOT NULL, nbproduit INT NOT NULL, somme DOUBLE PRECISION NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE produit (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(25) NOT NULL, quantite INT NOT NULL, description VARCHAR(255) DEFAULT NULL, prix DOUBLE PRECISION NOT NULL, marque VARCHAR(255) DEFAULT NULL, image VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE commande ADD CONSTRAINT FK_6EEAA67DF347EFB FOREIGN KEY (produit_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE commande ADD CONSTRAINT FK_6EEAA67DF77D927C FOREIGN KEY (panier_id) REFERENCES panier (id)');
        $this->addSql('ALTER TABLE commentaire ADD CONSTRAINT FK_67F068BCF347EFB FOREIGN KEY (produit_id) REFERENCES produit (id)');
        $this->addSql('DROP TABLE favoris');
        $this->addSql('DROP TABLE user');
        $this->addSql('ALTER TABLE maison DROP tel');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE commande DROP FOREIGN KEY FK_6EEAA67DF77D927C');
        $this->addSql('ALTER TABLE commande DROP FOREIGN KEY FK_6EEAA67DF347EFB');
        $this->addSql('ALTER TABLE commentaire DROP FOREIGN KEY FK_67F068BCF347EFB');
        $this->addSql('CREATE TABLE favoris (id INT AUTO_INCREMENT NOT NULL, maison_id INT DEFAULT NULL, user_id INT DEFAULT NULL, INDEX IDX_8933C4329D67D8AF (maison_id), INDEX IDX_8933C432A76ED395 (user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('ALTER TABLE favoris ADD CONSTRAINT FK_8933C4329D67D8AF FOREIGN KEY (maison_id) REFERENCES maison (id) ON UPDATE NO ACTION ON DELETE NO ACTION');
        $this->addSql('ALTER TABLE favoris ADD CONSTRAINT FK_8933C432A76ED395 FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE NO ACTION ON DELETE NO ACTION');
        $this->addSql('DROP TABLE commande');
        $this->addSql('DROP TABLE commentaire');
        $this->addSql('DROP TABLE panier');
        $this->addSql('DROP TABLE produit');
        $this->addSql('ALTER TABLE maison ADD tel INT NOT NULL');
    }
}
