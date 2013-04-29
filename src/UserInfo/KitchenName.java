/**
 * 
 */
package UserInfo;

/**
 * @author hacheson
 *
 */
public class KitchenName implements Nameable{

	private String _id;
	private String _name;
	
	public KitchenName(String name, String id){
		_name = name;
		_id = id;
	}
	
	public void setName(String name){
		_name = name;
	}
	
	public void setId(String id){
		_id = id;
	}
	
	@Override
	public String getName(){
		return _name;
	}
	
	@Override
	public String getID(){
		return _id;
	}
}
