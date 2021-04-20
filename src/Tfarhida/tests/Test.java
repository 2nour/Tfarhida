package Tfarhida.tests;
 
import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Produit;
import Tfarhida.services.ProduitService;

public class Test {

  
    public static void main(String[] args) throws ClassNotFoundException {
        // TODO code application logic here
        MaConnexion cm = MaConnexion.getinstance();
        Produit p = new Produit("aa",10,"aa",13.5,"aa","aa");
        ProduitService ps= new ProduitService();
       // ps.ajouterProduit(p);
    }
    
}
