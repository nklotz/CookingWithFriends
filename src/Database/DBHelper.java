/**
 * 
 */
package Database;

import java.io.IOException;

/**
 * @author hacheson
 *
 */
public class DBHelper {

	public DBHelper(){
		String s = "mongod --port 27017 -dbpath /home/hacheson/course/cs032/MongoData/";
		String[] args = s.split(" ");
		Process p = null;
		try{
			p = Runtime.getRuntime().exec(s);
		} catch(IOException e){
			e.printStackTrace();
		}
		
		//exec(args);
	}
}
