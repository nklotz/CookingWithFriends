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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KitchenName other = (KitchenName) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "KitchenName [_id=" + _id + ", _name=" + _name + "]";
	}
}
