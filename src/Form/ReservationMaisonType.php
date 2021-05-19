<?php

namespace App\Form;

use App\Entity\ReservationMaison;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateType;

class ReservationMaisonType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {

        $builder
            ->add('date_arrivee', DateType::class, [
                'label' => 'Date Arrivée:   : ','widget' => 'single_text'])

            ->add('date_depart', DateType::class, [
                'label' => 'Date Depart:   : ','widget' => 'single_text']);


    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => ReservationMaison::class,
        ]);
    }
}
