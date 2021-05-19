<?php

namespace App\Form;

use App\Entity\Organisation;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;


class OrganisationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nbrpersonne', TextType::class, [
                'attr'=> [
                    'placeholder' => 'Saisir le nombre de personnes',
                    'class' => 'form-control'
                ]
            ])
            ->add('date',  DateType::class, [
                'attr'=> [
                    'placeholder' => 'Choisir la date de départ',
                    'class' => 'form-control'
                ]
            ])


            ->add('nbrjours', TextType::class, [
                'attr'=> [
                    'placeholder' => 'Saisir le nombre de jours ',
                    'class' => 'form-control'
                ]
            ])


            ->add('activite', ChoiceType::class, [
                'choices' => [
                    'Camping' => 'Camping',
                    'TourVelo' => 'Tour à velo',
                    'CircuitSahara'=>'Circuit Sahara'

                ],
                'attr'=> [
                    'placeholder' => 'Saisir l activité',
                    'class' => 'form-control'
                ]

            ])
            ->add('Lieu', ChoiceType::class, [
                'choices' => [
                    'Bizerte' => 'Bizerte',
                    'Tunis' => 'Tunis',
                    'Tozeur'=>'Tozeur',
                    'kef'=>'kef',
                    'Sousse'=>'Sousse',


                ],
                'attr'=> [
                    'placeholder' => 'Saisir le lieu',
                    'class' => 'form-control'
                ]

            ])


            ->add('commentaire', TextType::class, [
                'attr'=> [
                    'placeholder' => 'Saisir un commentaire',
                    'class' => 'form-control'
                ]
            ])

        ;
    }


    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Organisation::class,
        ]);
    }
}
