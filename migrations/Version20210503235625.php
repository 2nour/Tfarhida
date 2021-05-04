<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210503235625 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE reservation_maison ADD date_arrivee DATE NOT NULL, ADD date_depart DATE NOT NULL, DROP dateArrivee, DROP dateDepart, CHANGE totalprix total_prix DOUBLE PRECISION NOT NULL');
        $this->addSql('ALTER TABLE user DROP Code, DROP Auth');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE reservation_maison ADD dateArrivee DATE NOT NULL, ADD dateDepart DATE NOT NULL, DROP date_arrivee, DROP date_depart, CHANGE total_prix totalPrix DOUBLE PRECISION NOT NULL');
        $this->addSql('ALTER TABLE user ADD Code INT DEFAULT NULL, ADD Auth INT DEFAULT NULL');
    }
}
