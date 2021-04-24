
package Tfarhida.tests;
import  Tfarhida.tools.Connexion;

import Tfarhida.entities.Organisation;
import Tfarhida.services.OrganisationService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Test {


    public static void main(String[] args) throws ParseException {
Connexion  cm=Connexion.getinstance();

String date="2021/2/2";
Date da=new SimpleDateFormat("dd/MM/yyyy").parse(date);


//Organisation o=new Organisation (1,2,"3",da,"camping","fflmfmd","bizerte","");
//OrganisationService os=new OrganisationService();

//os.ajouterOrganisation(o);
Organisation k=new Organisation (21,20,"3",da,"camp","djjdjdjd","tunis","");
OrganisationService os=new OrganisationService();
os.modifierOrganisation(k);

int p=35;
os.supprimerOrganisation(p);

    }
    
}
