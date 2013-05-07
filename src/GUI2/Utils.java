package GUI2;

public class Utils {
	 public static final int MAX_FIELD_LEN = 100;
    /**
     * Returns true if the password is of appropriate length.
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password){
    	if(password.length()<6 || password.length()>MAX_FIELD_LEN){
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
    
    //From: http://javapassgen.blogspot.com/
    public static String generateRandomPassword() {
    	int len=8;
    	char[] pwd = new char[len];
    	int c = 'A';
    	int rand = 0;
    	for (int i=0; i < 8; i++){
	    	rand = (int)(Math.random() * 3);
	    	switch(rand) {
	    	case 0: c = '0' + (int)(Math.random() * 10); break;
	    	case 1: c = 'a' + (int)(Math.random() * 26); break;
	    	case 2: c = 'A' + (int)(Math.random() * 26); break;
	    	}
	    	pwd[i] = (char)c;
    	}
    	return new String(pwd);
    }
}
