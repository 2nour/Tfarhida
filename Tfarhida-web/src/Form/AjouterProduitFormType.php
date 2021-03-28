<?php

namespace App\Form;

use App\Entity\Produit;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use App\Entity\Categorie;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\Image;

class AjouterProduitFormType extends AbstractType
{
    /**
     * AjouterProduitFormType constructor.
     */

    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom',TextType::class,[
                'attr'=> [
                    'placeholder' => 'Saisir le nom du produit',
                    'class' => 'form-control',
                    'trim' => true
                ]])
            ->add('quantite',NumberType::class,
                [
                    'attr'=> [
                        'placeholder' => 'Saisir la quantite',
                        'class' => 'form-control',
                        'trim' => true
                    ]])
            ->add('description',TextType::class,
                [
                    'attr'=> [
                        'placeholder' => 'donner une description',
                        'class' => 'form-control',
                        'trim' => true
                    ]])
            ->add('prix',NumberType::class,
                [
                    'attr'=> [
                        'placeholder' => 'donner le prix ',
                        'class' => 'form-control',
                        'trim' => true
                    ]])
            ->add('categories', EntityType::class, [

                'required' => true,
                'class' => Categorie::class,
                'expanded' => true,
                'multiple' => true
            ])
            ->add('marque',TextType::class,
                [
                    'attr'=> [
                        'placeholder' => 'Saisir la marque',
                        'class' => 'form-control',
                        'trim' => true
                    ]])
            ->add("image",FileType::class,[
                'data_class' => null,
                'attr'=> [
                    'placeholder' => 'Saisir l image',
                    'class' => 'form-control',

                ]])

            ->add('submit',SubmitType::class)
        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Produit::class,
        ]);
    }



}
