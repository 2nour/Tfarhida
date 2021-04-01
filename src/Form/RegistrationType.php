<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;

use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;

use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class RegistrationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder

            ->add('email',EmailType::class, [
        'attr'=> [
            'placeholder' => 'Entrer votre adresse email',
            'class' => 'form-control'
        ]
    ])

            ->add('username', TextType::class, [
        'attr'=> [
            'placeholder' => 'Entrer votre nom',
            'class' => 'form-control'
        ]
    ])

            ->add('password', PasswordType::class, [
                'attr'=> [
                    'placeholder' => 'Entrer votre mot de passe',
                    'class' => 'form-control'
                ]
            ])
            ->add('confirm_password', PasswordType::class, [
                'attr'=> [
                    'placeholder' => 'Confirmer votre mot de passe',
                    'class' => 'form-control'
                ]
            ])
            ->add('roles', ChoiceType::class,[
                'choices' => [
                    'Utilisateur' => 'ROLE_USER',
                    'Hébérgeur' => 'ROLE_HEBERGEUR'
                ],
                'mapped' => false,
//                'expanded' => true,
//                'multiple' => true,
                'label' => 'Roles'
            ])

            ->add("submit",SubmitType::class)

            ->add('email')
            ->add('username')
            ->add('password', PasswordType::class)
            ->add('confirm_password', PasswordType::class)




//            ->add('roles', ChoiceType::class,[
//                'choices' => [
//                    'Utilisateur' => 'ROLE_USER',
//                    'Hébérgeur' => 'ROLE_HEBERGEUR',
//                    'Administrateur' => 'ROLE_ADMIN'
//                ],
//'mapped' => false,
////                'expanded' => true,
////                'multiple' => true,
//                'label' => 'Roles'
//            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
