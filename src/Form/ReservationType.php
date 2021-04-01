<?php

namespace App\Form;

use App\Entity\Reservation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;

use Captcha\Bundle\CaptchaBundle\Form\Type\CaptchaType;
use Captcha\Bundle\CaptchaBundle\Validator\Constraints\ValidCaptcha;


class ReservationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('numreservation')
            ->add('idRandonnee', HiddenType::class)
            ->add('datereservation', DateType::class,['label' => 'Date Reservation:   : ','widget' => 'single_text'])
            ->add('observation')
            ->add('montant')
            ->add('nombrepersonne')
            ->add("captchaCode", CaptchaType::class,[
                'captchaConfig' => 'ExempleCaptchaReservation',
                'constraints' => [
                    new ValidCaptcha([
                        'message' => 'Invalid captcha, please try again'
                    ])
                ]
            ])

        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Reservation::class,
        ]);
    }
}