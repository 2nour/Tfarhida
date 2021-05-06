<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210506035459 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE commentaire_r (id INT AUTO_INCREMENT NOT NULL, randonne_id INT DEFAULT NULL, user_id INT DEFAULT NULL, contenue VARCHAR(255) NOT NULL, datedecommentaire VARCHAR(255) NOT NULL, INDEX IDX_C9E82C5ACF1641FE (randonne_id), INDEX IDX_C9E82C5AA76ED395 (user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE commentaire_r ADD CONSTRAINT FK_C9E82C5ACF1641FE FOREIGN KEY (randonne_id) REFERENCES randonnee (id)');
        $this->addSql('ALTER TABLE commentaire_r ADD CONSTRAINT FK_C9E82C5AA76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE randonnee CHANGE datedepart datedepart VARCHAR(255) NOT NULL, CHANGE dateretour dateretour VARCHAR(255) NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE commentaire_r');
        $this->addSql('ALTER TABLE randonnee CHANGE datedepart datedepart VARCHAR(255) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE dateretour dateretour VARCHAR(255) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`');
    }
}
