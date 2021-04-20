package Tfarhida.tests;
 
import Tfarhida.base.MaConnexion;
import Tfarhida.entities.CommentaireR;
import Tfarhida.entities.Post_like;
import Tfarhida.entities.Randonnee;
import Tfarhida.entities.Reservation;
import Tfarhida.entities.User;
import Tfarhida.services.CommentaireService;
import Tfarhida.services.PostLikeService;
import Tfarhida.services.RandonneService;
import Tfarhida.services.ReservationService;
import java.util.Date;

public class Test {

  
    public static void main(String[] args) throws ClassNotFoundException {
        // TODO code application logic here
        RandonneService randonneService=new RandonneService();
        ReservationService reservationService=new ReservationService();
        CommentaireService commentaireService=new CommentaireService();
        PostLikeService postLikeService=new PostLikeService();
        Randonnee r=new Randonnee(38,"kelibia", "bizertte", "13-04-2021","20-20-2021","aaa", "mmm","rrr", 2,"faible", 3);
        Randonnee r11=new Randonnee(40,"kelibia", "tunis", "13-04-2021","20-20-2021","aaa", "mmm","rrr", 2,"faible", 3);
Randonnee r2=new Randonnee(42,"haouaria", "test", "14-04-2021","15-04-2021","test", "test","test", 2,"test", 3);
        Post_like p=new Post_like(3,r,new User(1,"hana@gmail.com","hana1","hana1234","hana1234","testtest","ROLE_USER","aaaaaaaaaaaaa"));
         Post_like p1=new Post_like(3,r,new User(1,"hana@gmail.com","hana2","hana123456","hana123456","test","ROLE_USER","aaaaaaaaaaaaa"));
          Post_like p2=new Post_like(4,r,new User(1,"hana123@gmail.com","NOUU","hana1234","hana1234","root","ROLE_USER","aaaaaaaaaaaaa"));
        CommentaireR commentaireR=new CommentaireR(2,"ah hana ahhhh!","16-04-2021", r, new User(1,"hana@gmail.com","hana1","hana1234","hana1234","aaaa","ROLE_USER","aaaaaaaaaaaaa"));
        CommentaireR commentaireR1=new CommentaireR(3,"prix svp!","17-04-2022", r, new User(1,"hana@gmail.com","hana1","hana1234","hana1234","aaaa","ROLE_USER","aaaaaaaaaaaaa"));
        CommentaireR commentaireR2=new CommentaireR(3,"ah hana!","16-04-2021", r, new User(1,"hana@gmail.com","hana1","hana1234","hana1234","aaaa","ROLE_USER","aaaaaaaaaaaaa"));
        
        Reservation r1=new Reservation(52, 123, "22-04-2021", "Salut", 50, r, 0,"cc");
         Reservation r4=new Reservation(55, 126, "26-04-2021", "test", 30, r, 0,"cc");
          Randonnee r3=new Randonnee(43,"nabeul", "tunnis", "14-04-2021","20-20-2021","test", "mmm","rrr", 2,"moyenne", 3);
            Reservation r5=new Reservation(57, 123, "16-04-2021", "test", 50, r, 0,"test");
         reservationService.delete(57);
         
    //randonneService.delete(43);
      
       //randonneService.delete(40);
        //reservationService.insert(r1);
        //randonneService.insert(r);
       //commentaireService.insert(commentaireR);
     
       // postLikeService.delete(12);
        //randonneService.update(r);
        //reservationService.update(r1);
        //commentaireService.update(commentaireR);
      
     // randonneService.delete(r.getId());
      //randonneService.insert(r11);
      //randonneService.delete(14);
     
     //reservationService.delete(0)
      //reservationService.insert(r2);
 //reservationService.delete(53);
      //commentaireService.delete(12);
     // postLikeService.delete(0);
        System.out.println("bien ajoutée avec succés");
        
        
        
        
    }
    
}
