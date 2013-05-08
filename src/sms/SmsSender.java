package sms;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
 
import UserInfo.Ingredient;

import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;
 
public class SmsSender {
 
    public static final String ACCOUNT_SID = "AC375770bc9f7f60092fb0a61c49b0393d";
    public static final String AUTH_TOKEN = "b5f3529b5346c96127745b3ad06f6688";
 
    public static void send(String toNumber, List<Ingredient> _shoppingList) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
 
        Account account = client.getAccount();
 
        SmsFactory smsFactory = account.getSmsFactory();
        Map<String, String> smsParams = new HashMap<String, String>();
        smsParams.put("To", toNumber); 
        smsParams.put("From", "+12403983670");
        
        String body = new String();
        for (Ingredient ingredient : _shoppingList) {
        	body += ingredient.getName();
        	body += '\n';
        }
        smsParams.put("Body", body);
        Sms sms = smsFactory.create(smsParams);
    }
}