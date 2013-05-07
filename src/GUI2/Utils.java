package GUI2;

public class Utils {

    /**
     * Returns true if the password is of appropriate length.
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password){
    	if(password.length()<6){
    		return false;
    	}
    	return true;
    }
    
    public static boolean isValidEmailStructure(String email){
    	String[] splitAt = email.split("@");
    	if(splitAt.length==2){
    		String[] splitDot = email.split("\\.");
    		return (splitDot.length>1);
    	}
    	return false;
    }
}
