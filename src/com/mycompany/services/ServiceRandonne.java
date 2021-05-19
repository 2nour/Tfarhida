/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entites.Randonne;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public class ServiceRandonne {

    public ArrayList<Randonne> randonnee;
    public static ServiceRandonne instance;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceRandonne() {
        req = new ConnectionRequest();
    }

    public static ServiceRandonne getInstance() {
        if (instance == null) {
            instance = new ServiceRandonne();
        }
        return instance;
    }

    public boolean AddRandonne(Randonne r) {
        String url = Statics.BASE_URL + "/randonnee/Add?vd=" + r.getVilledepart() + "&va=" + r.getVillearrivee() + "&dd=" + r.getDatedepart() + "&dr=" + r.getDateretour() + "&act=" + r.getActivite() + "&d=" + r.getDescription() + "&img=" + r.getImage() + "&dur=" + r.getDuree() + "&diff" + r.getDifficulte() + "&b=" + r.getBudget();
        ConnectionRequest req = new ConnectionRequest(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;

    }

    public boolean deleteRandonne(int id) {
        String url = Statics.BASE_URL + "/rand/Supp/" + id;
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

    public ArrayList<Randonne> parseRandonne(String jsonText) {
        try {
            randonnee = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Randonne r = new Randonne();
                float id = Float.parseFloat(obj.get("id").toString());
                r.setId((int) id);
                r.setVilledepart(obj.get("villedepart").toString());
                r.setVillearrivee(obj.get("villearrivee").toString());
                r.setDatedepart(obj.get("datedepart").toString());
                r.setDateretour(obj.get("dateretour").toString());
                r.setActivite(obj.get("activite").toString());
                r.setDescription(obj.get("description").toString());
                r.setImage(obj.get("image").toString());
                float durre = Float.parseFloat(obj.get("duree").toString());
                float budget = Float.parseFloat(obj.get("budget").toString());

                r.setDuree((int) durre);
                r.setDifficulte(obj.get("difficulte").toString());
                r.setBudget((int) budget);

                randonnee.add(r);
            }
        } catch (IOException ex) {

        }
        return randonnee;
    }

    public ArrayList<Randonne> getAllrandonnee() {
        ArrayList<Randonne> result = new ArrayList<>();
        String url = Statics.BASE_URL + "/randonnee/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String, Object> mapReclamation = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listofMaps = (List<Map<String, Object>>) mapReclamation.get("root");

                    for (Map<String, Object> obj : listofMaps) {
                        Randonne re = new Randonne();

                        int idRandonne = (int) Float.parseFloat(obj.get("id").toString());
                        String villedepart = obj.get("villedepart").toString();
                        String villearrivee = obj.get("villearrivee").toString();
                        String datedepart = obj.get("datedepart").toString();
                        String dateretour = obj.get("dateretour").toString();
                        String activite = obj.get("activite").toString();
                        String description = obj.get("description").toString();
                        String image = obj.get("image").toString();
                        int duree = (int) Float.parseFloat(obj.get("duree").toString());
                        String difficulte = obj.get("difficulte").toString();
                        int budget = (int) Float.parseFloat(obj.get("budget").toString());

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                        String dateString = formatter.format(datedepart);
                        re.setDatedepart(dateString);

                        SimpleDateFormat formatterr = new SimpleDateFormat("yyyy-mm-dd");
                        String dateStringg = formatterr.format(dateretour);
                        re.setDateretour(dateStringg);

                        re.setId(idRandonne);
                        re.setVilledepart(villedepart);
                        re.setVillearrivee(villearrivee);
                        re.setDatedepart(datedepart);
                        re.setDateretour(dateretour);
                        re.setActivite(activite);
                        re.setDescription(description);
                        re.setImage(image);
                        re.setDuree(duree);
                        re.setDifficulte(difficulte);
                        re.setBudget(budget);

                        //add date int arraylist result
                        result.add(re);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(e -> {
            String str = new String(req.getResponseData());//reponse jason 
            System.out.println("data ==> " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

    public ArrayList<Randonne> findrandonnee(String name) {
        
 ArrayList<Randonne> resultt = new ArrayList<>();
        String url = Statics.BASE_URL + "/randonnee/find/" + name;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();
                try {
                    Map<String, Object> mapReclamation = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listofMaps = (List<Map<String, Object>>) mapReclamation.get("root");

                    for (Map<String, Object> obj : listofMaps) {
                        Randonne re = new Randonne();

                        int idRandonne = (int) Float.parseFloat(obj.get("id").toString());
                        String villedepart = obj.get("villedepart").toString();
                        String villearrivee = obj.get("villearrivee").toString();
                        String datedepart = obj.get("datedepart").toString();
                        String dateretour = obj.get("dateretour").toString();
                        String activite = obj.get("activite").toString();
                        String description = obj.get("description").toString();
                        String image = obj.get("image").toString();
                        int duree = (int) Float.parseFloat(obj.get("duree").toString());
                        String difficulte = obj.get("difficulte").toString();
                        int budget = (int) Float.parseFloat(obj.get("budget").toString());

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                        String dateString = formatter.format(datedepart);
                        re.setDatedepart(dateString);

                        SimpleDateFormat formatterr = new SimpleDateFormat("yyyy-mm-dd");
                        String dateStringg = formatterr.format(dateretour);
                        re.setDateretour(dateStringg);

                        re.setId(idRandonne);
                        re.setVilledepart(villedepart);
                        re.setVillearrivee(villearrivee);
                        re.setDatedepart(datedepart);
                        re.setDateretour(dateretour);
                        re.setActivite(activite);
                        re.setDescription(description);
                        re.setImage(image);
                        re.setDuree(duree);
                        re.setDifficulte(difficulte);
                        re.setBudget(budget);

                        //add date int arraylist result
                        resultt.add(re);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(e -> {
            String str = new String(req.getResponseData());//reponse jason 
            System.out.println("data ==> " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultt;

    }

}
