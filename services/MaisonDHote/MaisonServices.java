/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.MaisonDHote;

import Tfarhida.entities.Maison;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import utils.Statics;

/**
 *
 * @author NJR
 */
public class MaisonServices {
    
    //var
    boolean resultOK;
    ConnectionRequest req;
    static MaisonServices instance;
    ArrayList<Maison> maisons = new ArrayList<>();
    
     //constructor
    private MaisonServices() {
        req = new ConnectionRequest();
    }
    
    //SINGLETON
    public static MaisonServices getInstance(){
        
        if (instance == null) {
            instance = new MaisonServices();
        }
        
        return instance;
    }
    
    //ADD : insert
    public boolean addMaisonAction(Maison m){
        String url = Statics.BASE_URl + "/ajoutMaisonJSON/"+ m.getNom()+"/"+ m.getAdresse()+"/"+ m.getNbr_chambre()+"/"+ m.getDescription()+"/"+ m.getPhoto()+"/"+ m.getTel();
       // ConnectionRequest request = new ConnectionRequest(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener((evt) -> {
            resultOK = req.getResponseCode()==200;
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    //PARSING:[text/JSON] => Java Objects
    public ArrayList<Maison> parseJSONAction(String textJson){
        
        JSONParser j = new JSONParser();
        
        try {
            
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(textJson.toCharArray()));
            ArrayList<Map<String,Object>> tasksList = (ArrayList<Map<String,Object>>) tasksListJson.get("root");
            
            for (Map<String, Object> obj : tasksList) {
                
                Maison m = new Maison();
                float id = Float.parseFloat(obj.get("id").toString());
                m.setId((int)id);
                m.setNom(obj.get("nom").toString());
                m.setAdresse(obj.get("adresse").toString());
                m.setNbr_chambre((int) Float.parseFloat(obj.get("nbr_chambre").toString()));
                m.setDescription(obj.get("description").toString());
                m.setPhoto(obj.get("photo").toString());
                m.setTel((int) Float.parseFloat(obj.get("tel").toString()));
             
                
                maisons.add(m);

            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return maisons;  
    }

    
    
    //SHOW : select[text/JSON]
    public ArrayList<Maison> getMaisons(){
        
         String url = Statics.BASE_URl + "/afficheMJson/";
         ConnectionRequest request = new ConnectionRequest(url);
         request.setPost(false);
         request.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 
             maisons = parseJSONAction(new String(request.getResponseData()));
             request.removeResponseListener((ActionListener<NetworkEvent>) this);
             }
         });
         
        
        
        NetworkManager.getInstance().addToQueueAndWait(request);
        return maisons;
    }
    
    public boolean deleteMaison(int id) {
        String url = Statics.BASE_URl + "/deleteMaisonJSON/" +id;
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
