<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210505225350 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE org_produit (id INT AUTO_INCREMENT NOT NULL, id_produit_id INT NOT NULL, id_organisation_id INT NOT NULL, INDEX IDX_5ADAB8E9AABEFE2C (id_produit_id), INDEX IDX_5ADAB8E97735D60D (id_organisation_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE organisation (id INT AUTO_INCREMENT NOT NULL, nbrpersonne VARCHAR(255) NOT NULL, nbrjours INT NOT NULL, etat TINYTEXT NOT NULL, commentaire TINYTEXT NOT NULL, date DATETIME NOT NULL, activite VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE organisation_produit (organisation_id INT NOT NULL, produit_id INT NOT NULL, INDEX IDX_4A219B249E6B1585 (organisation_id), INDEX IDX_4A219B24F347EFB (produit_id), PRIMARY KEY(organisation_id, produit_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE paiement (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE org_produit ADD CONSTRAINT FK_5ADAB8E9AABEFE2C FOREIGN KEY (id_produit_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE org_produit ADD CONSTRAINT FK_5ADAB8E97735D60D FOREIGN KEY (id_organisation_id) REFERENCES organisation (id)');
        $this->addSql('ALTER TABLE organisation_produit ADD CONSTRAINT FK_4A219B249E6B1585 FOREIGN KEY (organisation_id) REFERENCES organisation (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE organisation_produit ADD CONSTRAINT FK_4A219B24F347EFB FOREIGN KEY (produit_id) REFERENCES produit (id) ON DELETE CASCADE');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE org_produit DROP FOREIGN KEY FK_5ADAB8E97735D60D');
        $this->addSql('ALTER TABLE organisation_produit DROP FOREIGN KEY FK_4A219B249E6B1585');
        $this->addSql('DROP TABLE org_produit');
        $this->addSql('DROP TABLE organisation');
        $this->addSql('DROP TABLE organisation_produit');
        $this->addSql('DROP TABLE paiement');
    }
}
