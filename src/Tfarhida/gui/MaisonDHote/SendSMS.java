/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tfarhida.gui.MaisonDHote;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 *
 * @author NJR
 */
public class SendSMS {
    
    public static final String ACCOUNT_SID = "AC3ed91adcc73afbe4b3e5a936f81111d4";
  public static final String AUTH_TOKEN = "0710eadfdd15de5c635c70b209a69ebb";

  public static void main(String[] args) throws Exception  {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Message message = Message.creator(new PhoneNumber("+17343897714"), //To
        new PhoneNumber("+13309131519"), //from
        "Salut nourhene").create();

    System.out.println(message.getSid());
  }
    
}
