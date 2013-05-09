package Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.twilio.sdk.TwilioRestException;

import sms.SmsSender;

import UserInfo.Ingredient;

public class SMSControllerTest {

	@Test
	public void test() throws TwilioRestException {
		List<Ingredient> ings = new ArrayList<>();
		ings.add(new Ingredient("TESTING!"));
		ings.add(new Ingredient("Beer"));
		ings.add(new Ingredient("Cumin"));
		ings.add(new Ingredient("OTHERTHINGS?"));
		ings.add(new Ingredient("<3 CS032 xoxo"));
		ings.add(new Ingredient("HI MIRANDA!"));
		
		SmsSender.send("(503) 367-3550", ings); //Eddie
		SmsSender.send("(301) 641-5583", ings); //Jonathan
		SmsSender.send("(215) 518-5076", ings); //Natalie
		SmsSender.send("(207) 402-7045", ings); //Hannah
	}

}
