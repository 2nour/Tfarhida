<?php

namespace App\Form;

use App\Entity\Chambre;
use Doctrine\DBAL\Types\FloatType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class ChambreType extends AbstractType
{
    protected static $subjects = array('Subject A', 'Subject B', 'Subject C');
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom', TextType::class, [
                'attr'=> [
                    'placeholder' => 'Saisir le nom de la chambre',
                    'class' => 'form-control'
                ]
            ])
            ->add('typeLit', choiceType::class,[
                'choices' => [
                    'Double' => 'Double',
                    'Simple' => 'Simple',
                ],
                'attr'=> [
                    'placeholder' => 'Saisir votr',
                    'class' => 'form-control'
                ]
            ])
            ->add('nbr_pers', IntegerType::class, [
                'attr'=> [
                    'placeholder' => 'Combien de personne pour la chambre ?',
                    'class' => 'form-control'
                ]
            ])
            ->add('prix', IntegerType::class, [
                'attr'=> [
                    'placeholder' => 'Saisir le prix',
                    'class' => 'form-control'
                ]
            ])
            ->add('photo', FileType::class, [
                'data_class' => null,
                'attr'=> [
                    'placeholder' => 'Mettre vos photos',
                    'class' => 'form-control',
                    'label' => false,
                    'multiple' => true,
                    'mapped'=> false,
                    'required'=>false
                ]
            ])
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
            'data_class' => Chambre::class,
        ]);
    }
}
