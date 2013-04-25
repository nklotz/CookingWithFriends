/**
 * 
 */
package UserInfo;

import java.io.Serializable;
import java.util.HashMap;

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
	public HashMap<String, Boolean> _preferences;
	public Preferences(){
		_preferences = new HashMap<String, Boolean>();
	}
	
	public void addPreference(String p, boolean b){
		_preferences.put(p, b);
	}

	@Override
	public String toString() {
		return "Preferences [_preferences=" + _preferences + "]";
	}
}
