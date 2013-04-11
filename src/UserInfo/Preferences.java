/**
 * 
 */
package UserInfo;

import java.util.HashMap;

/**
 * @author hacheson
 * The class containing the preferences for the gui.
 */
public class Preferences {

	//Maps a list of preferences to whether the user user's preference (boolean).
	public HashMap<String, Boolean> _preferences;
	public Preferences(){
		_preferences = new HashMap<String, Boolean>();
	}
	
	public void addPreference(String p, boolean b){
		_preferences.put(p, b);
	}
}
