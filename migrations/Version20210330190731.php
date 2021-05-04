<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210330190731 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE organisation DROP date_depart, CHANGE date date DATETIME NOT NULL');
        $this->addSql('ALTER TABLE produit ADD image VARCHAR(255) NOT NULL, CHANGE marque marque VARCHAR(255) DEFAULT NULL');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE organisation ADD date_depart INT NOT NULL, CHANGE date date DATE NOT NULL');
        $this->addSql('ALTER TABLE produit DROP image, CHANGE marque marque VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`');
    }
}
