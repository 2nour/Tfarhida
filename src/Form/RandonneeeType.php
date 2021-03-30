<?php

namespace App\Form;

use App\Entity\Randonnee;
use Doctrine\DBAL\Types\DateTimeType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;

class RandonneeeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('villedepart',TextType::class)
            ->add('villearrivee')
            ->add('activite')
            ->add('datedepart', DateType::class,['label' => 'Date depart:   : ','widget' => 'single_text'])
            ->add('dateretour', DateType::class,['label' => 'Date retour:   : ','widget' => 'single_text'])
            ->add('description', TextareaType::class)
            ->add('image',FileType::class)
            ->add('duree')
            ->add('difficulte')
            ->add('budget')

        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Randonnee::class,
        ]);
    }
}
