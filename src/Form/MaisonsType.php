<?php

namespace App\Form;

use App\Entity\Maison;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Beelab\Recaptcha2Bundle\Form\Type\RecaptchaType;
use Beelab\Recaptcha2Bundle\Validator\Constraints\Recaptcha2;

class MaisonsType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom', TextType::class, [
                'attr'=> [
                    'placeholder' => 'Entrer Le nom de Maison',
                    'class' => 'form-control'
                ]
            ])
            ->add('adresse', TextType::class, [
                'attr'=> [
                    'placeholder' => 'Entrer LAdresse de la Maison',
                    'class' => 'form-control'
                ]
            ])
            ->add('tel', IntegerType::class, [
                'attr'=> [
                    'placeholder' => 'Saisir Le numéro de téléhone',
                    'class' => 'form-control'
                ]
            ])
            ->add('nbr_chambre', IntegerType::class, [
                'attr'=> [
                    'placeholder' => 'Saisir le nombre de chambre',
                    'class' => 'form-control'
                ]
            ])
            ->add('photo', FileType::class , [
                'data_class' => null,
                'attr'=> [
                    'placeholder' => 'Mettre vos photos',
                    'class' => 'form-control',
                    'label' => false,
                    'multiple' => true,
                    'mapped'=> false,
                    'required'=>false,
                ]
            ])
            ->add('description', TextareaType::class, [
                'attr'=> [
                    'placeholder' => 'Saisir votre description',
                    'class' => 'form-control'
                ]
            ])

            // For Invisible Recaptcha
            /*
            ->add('captcha', RecaptchaSubmitType::class, [
                'label' => 'Save'
            ])
            */
            ->add('Envoyer', SubmitType::class, [
                'attr'=> [
                    'class' => 'btn btn-info',
                ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Maison::class,
        ]);
    }
}
