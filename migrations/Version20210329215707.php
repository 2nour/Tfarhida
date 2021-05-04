<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210329215707 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE org_produit (id INT AUTO_INCREMENT NOT NULL, id_produit_id INT NOT NULL, id_organisation_id INT NOT NULL, INDEX IDX_5ADAB8E9AABEFE2C (id_produit_id), INDEX IDX_5ADAB8E97735D60D (id_organisation_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE org_produit ADD CONSTRAINT FK_5ADAB8E9AABEFE2C FOREIGN KEY (id_produit_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE org_produit ADD CONSTRAINT FK_5ADAB8E97735D60D FOREIGN KEY (id_organisation_id) REFERENCES organisation (id)');
        $this->addSql('DROP TABLE materiel');
        $this->addSql('ALTER TABLE organisation ADD date DATE NOT NULL');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE materiel (id INT AUTO_INCREMENT NOT NULL, nom_mat VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`, photo VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('DROP TABLE org_produit');
        $this->addSql('ALTER TABLE organisation DROP date');
    }
}
