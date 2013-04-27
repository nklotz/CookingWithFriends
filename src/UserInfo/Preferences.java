/**
 * 
 */
package UserInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author hacheson
 * The class containing the preferences for the gui.
 */
public class Preferences implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Maps a list of preferences to whether the user user's preference (boolean).
	private HashSet<String> restrictions_;
	private HashSet<String> allergies_;
	private HashSet<String> dislikes_;
	
	public Preferences(){
		restrictions_ = new HashSet<String>();
		allergies_ = new HashSet<String>();
		dislikes_ = new HashSet<String>();
	}
	
	public Preferences(HashSet<String> r, HashSet<String> a, HashSet<String> d){
		restrictions_ = r;
		allergies_ = a;
		dislikes_ = d;
	}
	
	public void addRestriction(String r){
		restrictions_.add(r);
	}
	
	public void addDislike(String d){
		dislikes_.add(d);
	}
	
	public void addAllergy(String a){
		allergies_.add(a);
	}

	@Override
	public String toString() {
		return "Preferences [restrictions_=" + restrictions_ + ", allergies_="
				+ allergies_ + ", dislikes_=" + dislikes_ + "]";
	}

	
}
