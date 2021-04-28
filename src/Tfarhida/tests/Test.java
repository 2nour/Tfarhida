package Tfarhida.tests;
 
import Tfarhida.base.MaConnexion;
import Tfarhida.entities.Maison;
import Tfarhida.services.MaisonService;

public class Test {

  
    public static void main(String[] args) throws ClassNotFoundException {
        // TODO code application logic here
        MaConnexion cm = MaConnexion.getinstance();
        Maison m = new Maison(35,"Rayen", "Nabeul", 5, 54102045, "photo", "Salut");
        MaisonService ps = new MaisonService();
        ps.ajouterMaison(m);
    }
    
}
