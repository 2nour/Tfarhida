/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.entities;
import com.teknikindustries.bulksms.SMS;


public class SmsTutorial {
    
    

    
 public static void main(String[]args)   
 {
     SMS smsTut = new SMS();
     smsTut.SendSMS("", "", "", "", "(\"«EAPI URL»/submission/send_sms/2/2.0\");");
 }
}
