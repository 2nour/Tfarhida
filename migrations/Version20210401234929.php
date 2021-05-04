<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210401234929 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE organisation_produit (organisation_id INT NOT NULL, produit_id INT NOT NULL, INDEX IDX_4A219B249E6B1585 (organisation_id), INDEX IDX_4A219B24F347EFB (produit_id), PRIMARY KEY(organisation_id, produit_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE organisation_produit ADD CONSTRAINT FK_4A219B249E6B1585 FOREIGN KEY (organisation_id) REFERENCES organisation (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE organisation_produit ADD CONSTRAINT FK_4A219B24F347EFB FOREIGN KEY (produit_id) REFERENCES produit (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE organisation DROP materiels');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE organisation_produit');
        $this->addSql('ALTER TABLE organisation ADD materiels VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`');
    }
}
