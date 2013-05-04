package UserInfo;

import java.io.Serializable;

public class Invitation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _fromID, _fromName, _toID;
	private KitchenName _kitchenName;
	
	public Invitation(String fromID, String fromName, String toID, KitchenName kitchenName){
		_fromID = fromID;
		_fromName = fromName;
		_toID = toID;
		_kitchenName = kitchenName;
	}
	
	public String getFromID(){
		return _fromID;
	}
	
	public String getFromName(){
		return _fromName;
	}

	public String getToID(){
		return _toID;
	}
	
	public KitchenName getKitchenID(){
		return _kitchenName;
	}
	
	public String getMessage(){
		try{
			return "Hello! " + _fromName + " (" + _fromID + " ) would like you to join kitchen \"" + _kitchenName.getName() + "\"!!!!"; 
		}catch(NullPointerException e){
			System.out.println("i fucked up");
			return null;
		}
		}
		
}
