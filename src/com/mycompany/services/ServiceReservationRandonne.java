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
import com.mycompany.entites.ReservationRandonne;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yassin
 */
public class ServiceReservationRandonne {

    public static ServiceReservationRandonne instance = null;
    public ArrayList<ReservationRandonne> reservation;

    private ConnectionRequest req;
    public static boolean resultOk = true;

    public static ServiceReservationRandonne getInstance() {
        if (instance == null) {
            instance = new ServiceReservationRandonne();
        }
        return instance;
    }

    public ServiceReservationRandonne() {
        req = new ConnectionRequest();
    }

    //Ajout
    public void AddReservation(ReservationRandonne re) throws Exception {

        String url = Statics.BASE_URL + "/reservation/Add/" + re.getNumreservation() + "/" + re.getDatereservation() + "/" + re.getObservation() + "/" + re.getMontant() + "/" + re.getNombrepersonne() + "/" + re.getIdr();
        //Na9esa el DateTraitement,NomPrenomCoach,imgRec
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(e -> {
            String str = new String(req.getResponseData());//reponse jason 
            System.out.println("data ==> " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);//execution te3 request
    }

    public ArrayList<ReservationRandonne> parseReservationRandonne(String jsonText) {
        reservation = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String, Object> reservationListJson;
        try {
            reservationListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) reservationListJson.get("root");
            for (Map<String, Object> obj : list) {
                ReservationRandonne r = new ReservationRandonne();
                //   float id = Float.parseFloat(obj.get("id").toString());
                //  r.setId((int)id);
                // r.setNumreservation(Integer.parseInt(obj.get("numreservation").toString()));
                //  r.setDatereservation(obj.get("datereservation").toString());
                r.setObservation(obj.get("observation").toString());
                //  r.setMontant(Float.parseFloat(obj.get("montant").toString()));
                // r.setNombrepersonne(Integer.parseInt(obj.get("nombrepersonne").toString()));
                r.setEtat(obj.get("etat").toString());

                reservation.add(r);
            }
        } catch (IOException ex) {

        }
        return reservation;
    }

    public ArrayList<ReservationRandonne> getAllreservationrandonne() {
        ArrayList<ReservationRandonne> result = new ArrayList<>();
        String url = Statics.BASE_URL + "/index/";
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
                        ReservationRandonne re = new ReservationRandonne();

                        int idreservation = (int) Float.parseFloat(obj.get("id").toString());
                        int numreservation = (int) Float.parseFloat(obj.get("numreservation").toString());
                        //  String datereservation = obj.get("datereservation").toString();
                        String observation = obj.get("observation").toString();
                        float montant = (float) Float.parseFloat(obj.get("montant").toString());
                        int nombrepersonne = (int) Float.parseFloat(obj.get("nombrepersonne").toString());
                        String etat = obj.get("etat").toString();

                        String date = obj.get("datereservation").toString();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                        String dateString = formatter.format(date);
                        re.setDatereservation(dateString);

                        re.setId(idreservation);
                        re.setNumreservation(numreservation);
                        re.setObservation(observation);
                        re.setMontant(montant);
                        re.setNombrepersonne(nombrepersonne);
                        re.setEtat(etat);

                        //add date int arraylist result
                        result.add(re);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

    public boolean deleteReseration(int id) {
        String url = Statics.BASE_URL + "/reservation/delete?id=" + id;
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

//        //Modifier Reclamation
    public boolean ModifierReservation(ReservationRandonne rec) {
        String url = Statics.BASE_URL + "/ModifierReservation?id=" + rec.getId() + "&observation=" + rec.getObservation() + "&montant=" + rec.getMontant() + "&nombrepersonne=" + rec.getNombrepersonne();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200; //code success  http 200 ok
                req.removeResponseListener(this);
            }
        });
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(e -> {
            String str = new String(req.getResponseData());//reponse jason 
            System.out.println("data ==> " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);//execution te3 request
        System.out.println("data ==> " + req);
        return resultOk;

    }
}
