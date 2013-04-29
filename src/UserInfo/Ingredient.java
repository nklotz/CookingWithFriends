/**
 * 
 */
package UserInfo;

/**
 * @author hacheson
 * Ingredient class about 
 */
public class Ingredient implements Nameable{
	private String _name;
	private String _unit;
	private double _quantity;
	
	public Ingredient(String name, String unit, double quantity){
		_name = name;
		_unit = unit;
		_quantity = quantity;
	}
	
	public Ingredient(String name){
		_name = name;
		_unit = "";
		_quantity = 0;
	}
	public void setName(String name){
		_name = name;
	}
	
	public void setUnit(String unit){
		_unit = unit;
	}
	
	public void setQuantityNumber(double quantity){
		_quantity = quantity;
	}
	
	public double getQuantity(){
		return _quantity;
	}
	
	@Override
	public String getName(){
		return _name;
	}
	
	public String getUnit(){
		return _unit;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
}
