<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210304171042 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE favoris ADD maison_id INT DEFAULT NULL, ADD user_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE favoris ADD CONSTRAINT FK_8933C4329D67D8AF FOREIGN KEY (maison_id) REFERENCES maison (id)');
        $this->addSql('ALTER TABLE favoris ADD CONSTRAINT FK_8933C432A76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('CREATE INDEX IDX_8933C4329D67D8AF ON favoris (maison_id)');
        $this->addSql('CREATE INDEX IDX_8933C432A76ED395 ON favoris (user_id)');
        $this->addSql('ALTER TABLE maison DROP FOREIGN KEY FK_F90CB66D51E8871B');
        $this->addSql('DROP INDEX IDX_F90CB66D51E8871B ON maison');
        $this->addSql('ALTER TABLE maison DROP favoris_id');
        $this->addSql('ALTER TABLE user DROP FOREIGN KEY FK_8D93D64951E8871B');
        $this->addSql('DROP INDEX IDX_8D93D64951E8871B ON user');
        $this->addSql('ALTER TABLE user DROP favoris_id');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE favoris DROP FOREIGN KEY FK_8933C4329D67D8AF');
        $this->addSql('ALTER TABLE favoris DROP FOREIGN KEY FK_8933C432A76ED395');
        $this->addSql('DROP INDEX IDX_8933C4329D67D8AF ON favoris');
        $this->addSql('DROP INDEX IDX_8933C432A76ED395 ON favoris');
        $this->addSql('ALTER TABLE favoris DROP maison_id, DROP user_id');
        $this->addSql('ALTER TABLE maison ADD favoris_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE maison ADD CONSTRAINT FK_F90CB66D51E8871B FOREIGN KEY (favoris_id) REFERENCES favoris (id)');
        $this->addSql('CREATE INDEX IDX_F90CB66D51E8871B ON maison (favoris_id)');
        $this->addSql('ALTER TABLE user ADD favoris_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE user ADD CONSTRAINT FK_8D93D64951E8871B FOREIGN KEY (favoris_id) REFERENCES favoris (id)');
        $this->addSql('CREATE INDEX IDX_8D93D64951E8871B ON user (favoris_id)');
    }
}
