<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210401190208 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE post_like (id INT AUTO_INCREMENT NOT NULL, randonnee_id INT DEFAULT NULL, user_id INT DEFAULT NULL, INDEX IDX_653627B8C8C97C3C (randonnee_id), INDEX IDX_653627B8A76ED395 (user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE randonnee (id INT AUTO_INCREMENT NOT NULL, villedepart VARCHAR(255) NOT NULL, villearrivee VARCHAR(255) NOT NULL, datedepart DATETIME DEFAULT NULL, dateretour DATETIME DEFAULT NULL, activite VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, image VARCHAR(255) NOT NULL, duree INT NOT NULL, difficulte VARCHAR(255) NOT NULL, budget INT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE reservation (id INT AUTO_INCREMENT NOT NULL, numreservation INT NOT NULL, datereservation DATETIME NOT NULL, observation VARCHAR(255) NOT NULL, montant DOUBLE PRECISION NOT NULL, id_randonnee VARCHAR(255) NOT NULL, nombrepersonne INT NOT NULL, etat VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE post_like ADD CONSTRAINT FK_653627B8C8C97C3C FOREIGN KEY (randonnee_id) REFERENCES randonnee (id)');
        $this->addSql('ALTER TABLE post_like ADD CONSTRAINT FK_653627B8A76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE post_like DROP FOREIGN KEY FK_653627B8C8C97C3C');
        $this->addSql('DROP TABLE post_like');
        $this->addSql('DROP TABLE randonnee');
        $this->addSql('DROP TABLE reservation');
    }
}
