package Test;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 */

/**
 * @author hacheson
 *
 */
public class SerializableTest implements Serializable {

	private String _a;
	private ArrayList<String> _b;
	public SerializableTest(){
		_a = null;
		_b = null;
	}
	
	public void setA(String a){
		_a = a;
	}
	public void setB(ArrayList<String> b){
		_b = b;
	}
	
	public String getA(){
		return _a;
	}
	public ArrayList<String>getB(){
		return _b;
	}
}
