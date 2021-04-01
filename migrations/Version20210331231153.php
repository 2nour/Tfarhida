<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210331231153 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE commentaire DROP FOREIGN KEY FK_67F068BCC8C97C3C');
        $this->addSql('DROP INDEX IDX_67F068BCC8C97C3C ON commentaire');
        $this->addSql('ALTER TABLE commentaire ADD user_id INT DEFAULT NULL, DROP body, DROP created, CHANGE randonnee_id randonne_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE commentaire ADD CONSTRAINT FK_67F068BCCF1641FE FOREIGN KEY (randonne_id) REFERENCES randonnee (id)');
        $this->addSql('ALTER TABLE commentaire ADD CONSTRAINT FK_67F068BCA76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('CREATE INDEX IDX_67F068BCCF1641FE ON commentaire (randonne_id)');
        $this->addSql('CREATE INDEX IDX_67F068BCA76ED395 ON commentaire (user_id)');
        $this->addSql('ALTER TABLE randonnee DROP FOREIGN KEY FK_CB71A99FBA9CD190');
        $this->addSql('DROP INDEX IDX_CB71A99FBA9CD190 ON randonnee');
        $this->addSql('ALTER TABLE randonnee DROP commentaire_id');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE commentaire DROP FOREIGN KEY FK_67F068BCCF1641FE');
        $this->addSql('ALTER TABLE commentaire DROP FOREIGN KEY FK_67F068BCA76ED395');
        $this->addSql('DROP INDEX IDX_67F068BCCF1641FE ON commentaire');
        $this->addSql('DROP INDEX IDX_67F068BCA76ED395 ON commentaire');
        $this->addSql('ALTER TABLE commentaire ADD randonnee_id INT DEFAULT NULL, ADD body LONGTEXT CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`, ADD created DATETIME DEFAULT NULL, DROP randonne_id, DROP user_id');
        $this->addSql('ALTER TABLE commentaire ADD CONSTRAINT FK_67F068BCC8C97C3C FOREIGN KEY (randonnee_id) REFERENCES randonnee (id) ON UPDATE NO ACTION ON DELETE NO ACTION');
        $this->addSql('CREATE INDEX IDX_67F068BCC8C97C3C ON commentaire (randonnee_id)');
        $this->addSql('ALTER TABLE randonnee ADD commentaire_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE randonnee ADD CONSTRAINT FK_CB71A99FBA9CD190 FOREIGN KEY (commentaire_id) REFERENCES commentaire (id) ON UPDATE NO ACTION ON DELETE NO ACTION');
        $this->addSql('CREATE INDEX IDX_CB71A99FBA9CD190 ON randonnee (commentaire_id)');
    }
}
