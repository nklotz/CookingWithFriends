/**
 * 
 */
package UserInfo;

import java.io.Serializable;

/**
 * @author hacheson
 *
 */
public class KitchenName implements Nameable, Serializable{

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
	
	@Override
    public int hashCode() {
		return (_name.hashCode() + _id.hashCode());
    }
}
