package UserInfo;

public class Invitation {

	private String _fromID, _fromName, _toID, _kitchenID;
	
	public Invitation(String fromID, String fromName, String toID, String kitchenID){
		_fromID = fromID;
		_fromName = fromName;
		_toID = toID;
		_kitchenID = kitchenID;
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
	
	public String getKitchenID(){
		return _kitchenID;
	}
		
}
