<?php

namespace App\Form;

use App\Entity\ReservationMaison;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class ReservationMaisonType2Type extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('etats', choiceType::class,[
                'choices' => [
                    'Accepter' => 'Accepter',
                    'Refuser' => 'Refuser',
                ],
                'attr'=> [
                    'class' => 'form-control'
                ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => ReservationMaison::class,
        ]);
    }
}
