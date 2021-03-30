<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class RegistrationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
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