<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210519192637 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE notifiable_notification DROP FOREIGN KEY FK_ADCFE0FAC3A0A4F8');
        $this->addSql('ALTER TABLE notifiable_notification DROP FOREIGN KEY FK_ADCFE0FAEF1A9D84');
        $this->addSql('CREATE TABLE commentaire_r (id INT AUTO_INCREMENT NOT NULL, randonne_id INT DEFAULT NULL, user_id INT DEFAULT NULL, contenue VARCHAR(255) NOT NULL, datedecommentaire VARCHAR(255) NOT NULL, INDEX IDX_C9E82C5ACF1641FE (randonne_id), INDEX IDX_C9E82C5AA76ED395 (user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE org_produit (id INT AUTO_INCREMENT NOT NULL, id_produit_id INT NOT NULL, id_organisation_id INT NOT NULL, INDEX IDX_5ADAB8E9AABEFE2C (id_produit_id), INDEX IDX_5ADAB8E97735D60D (id_organisation_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE organisation (id INT AUTO_INCREMENT NOT NULL, nbrpersonne VARCHAR(255) NOT NULL, nbrjours INT NOT NULL, etat TINYTEXT NOT NULL, commentaire TINYTEXT NOT NULL, date DATETIME NOT NULL, activite VARCHAR(255) NOT NULL, lieu VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE organisation_produit (organisation_id INT NOT NULL, produit_id INT NOT NULL, INDEX IDX_4A219B249E6B1585 (organisation_id), INDEX IDX_4A219B24F347EFB (produit_id), PRIMARY KEY(organisation_id, produit_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE paiement (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE post_like (id INT AUTO_INCREMENT NOT NULL, randonnee_id INT DEFAULT NULL, user_id INT DEFAULT NULL, INDEX IDX_653627B8C8C97C3C (randonnee_id), INDEX IDX_653627B8A76ED395 (user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE randonnee (id INT AUTO_INCREMENT NOT NULL, villedepart VARCHAR(255) NOT NULL, villearrivee VARCHAR(255) NOT NULL, datedepart DATE NOT NULL, dateretour DATE NOT NULL, activite VARCHAR(255) NOT NULL, description VARCHAR(255) NOT NULL, image VARCHAR(255) NOT NULL, duree INT NOT NULL, difficulte VARCHAR(255) NOT NULL, budget INT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE reservation (id INT AUTO_INCREMENT NOT NULL, numreservation INT NOT NULL, datereservation DATE NOT NULL, observation VARCHAR(255) NOT NULL, montant DOUBLE PRECISION NOT NULL, id_randonnee INT NOT NULL, nombrepersonne INT NOT NULL, etat VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE commentaire_r ADD CONSTRAINT FK_C9E82C5ACF1641FE FOREIGN KEY (randonne_id) REFERENCES randonnee (id)');
        $this->addSql('ALTER TABLE commentaire_r ADD CONSTRAINT FK_C9E82C5AA76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE org_produit ADD CONSTRAINT FK_5ADAB8E9AABEFE2C FOREIGN KEY (id_produit_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE org_produit ADD CONSTRAINT FK_5ADAB8E97735D60D FOREIGN KEY (id_organisation_id) REFERENCES organisation (id)');
        $this->addSql('ALTER TABLE organisation_produit ADD CONSTRAINT FK_4A219B249E6B1585 FOREIGN KEY (organisation_id) REFERENCES organisation (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE organisation_produit ADD CONSTRAINT FK_4A219B24F347EFB FOREIGN KEY (produit_id) REFERENCES produit (id) ON DELETE CASCADE');
        $this->addSql('ALTER TABLE post_like ADD CONSTRAINT FK_653627B8C8C97C3C FOREIGN KEY (randonnee_id) REFERENCES randonnee (id)');
        $this->addSql('ALTER TABLE post_like ADD CONSTRAINT FK_653627B8A76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('DROP TABLE notifiable_entity');
        $this->addSql('DROP TABLE notifiable_notification');
        $this->addSql('DROP TABLE notification');
        $this->addSql('ALTER TABLE chambre ADD user_id VARCHAR(255) DEFAULT NULL');
        $this->addSql('ALTER TABLE comment DROP sentiment');
        $this->addSql('ALTER TABLE maison ADD user_id VARCHAR(255) DEFAULT NULL');
        $this->addSql('ALTER TABLE reservation_maison ADD date_depart DATE NOT NULL, ADD total_prix DOUBLE PRECISION DEFAULT NULL, ADD nom_chambre VARCHAR(255) DEFAULT NULL, ADD nom_user VARCHAR(255) DEFAULT NULL, ADD hebergeur_id VARCHAR(255) DEFAULT NULL, CHANGE chambre_id chambre_id INT DEFAULT NULL, CHANGE user_id user_id INT DEFAULT NULL, CHANGE etats etats VARCHAR(255) DEFAULT NULL, CHANGE date_reservation date_arrivee DATE NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE org_produit DROP FOREIGN KEY FK_5ADAB8E97735D60D');
        $this->addSql('ALTER TABLE organisation_produit DROP FOREIGN KEY FK_4A219B249E6B1585');
        $this->addSql('ALTER TABLE commentaire_r DROP FOREIGN KEY FK_C9E82C5ACF1641FE');
        $this->addSql('ALTER TABLE post_like DROP FOREIGN KEY FK_653627B8C8C97C3C');
        $this->addSql('CREATE TABLE notifiable_entity (id INT AUTO_INCREMENT NOT NULL, identifier VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`, class VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE notifiable_notification (id INT AUTO_INCREMENT NOT NULL, notification_id INT DEFAULT NULL, notifiable_entity_id INT DEFAULT NULL, seen TINYINT(1) NOT NULL, INDEX IDX_ADCFE0FAC3A0A4F8 (notifiable_entity_id), INDEX IDX_ADCFE0FAEF1A9D84 (notification_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE notification (id INT AUTO_INCREMENT NOT NULL, date DATETIME NOT NULL, subject VARCHAR(4000) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`, message VARCHAR(4000) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, link VARCHAR(4000) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8 COLLATE `utf8_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('ALTER TABLE notifiable_notification ADD CONSTRAINT FK_ADCFE0FAC3A0A4F8 FOREIGN KEY (notifiable_entity_id) REFERENCES notifiable_entity (id) ON UPDATE NO ACTION ON DELETE NO ACTION');
        $this->addSql('ALTER TABLE notifiable_notification ADD CONSTRAINT FK_ADCFE0FAEF1A9D84 FOREIGN KEY (notification_id) REFERENCES notification (id) ON UPDATE NO ACTION ON DELETE NO ACTION');
        $this->addSql('DROP TABLE commentaire_r');
        $this->addSql('DROP TABLE org_produit');
        $this->addSql('DROP TABLE organisation');
        $this->addSql('DROP TABLE organisation_produit');
        $this->addSql('DROP TABLE paiement');
        $this->addSql('DROP TABLE post_like');
        $this->addSql('DROP TABLE randonnee');
        $this->addSql('DROP TABLE reservation');
        $this->addSql('ALTER TABLE chambre DROP user_id');
        $this->addSql('ALTER TABLE comment ADD sentiment VARCHAR(255) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE maison DROP user_id');
        $this->addSql('ALTER TABLE reservation_maison ADD date_reservation DATE NOT NULL, DROP date_arrivee, DROP date_depart, DROP total_prix, DROP nom_chambre, DROP nom_user, DROP hebergeur_id, CHANGE chambre_id chambre_id INT NOT NULL, CHANGE user_id user_id INT NOT NULL, CHANGE etats etats VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`');
    }
}
