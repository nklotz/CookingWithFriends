package autocorrect;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	/**
	 * Reads in a file, strips of punctuation, then returns the file
	 * 
	 * @param fileName
	 */
	public static List<String> readFile(String fileName) throws IOException{
		String line;
		BufferedReader bufferStream = null;
		List<String> ret = new ArrayList<String>();
		try{
			try{
				bufferStream = new BufferedReader(new FileReader(fileName));
			}
			catch (FileNotFoundException e){
				System.err.println("ERROR: Please enter a valid file name and complete the command again.");
				System.exit(0);
			}
			//If you don't read the position line, you read the action line;
			while ((line = bufferStream.readLine()) != null) {
				
				line = line.replaceAll("\\p{Punct}|\\d", " ").toLowerCase(); //Remove all punctuation.
				line = line.replaceAll("\\s+", " ");	//Replace all double spaces to one space.
				String[] lines = line.split("\\s+");
				for(int i = 0; i < lines.length; i++){
					
					if(!lines[i].equals(" ")&&!lines[i].equals("\n")&&!lines[i].equals("")){
							ret.add(lines[i]);
					}
				}
			}
		} 
		finally{
			if (bufferStream != null) {
				bufferStream.close();
			}
		}
		return ret;
	}
	
	
	/**
	 * Main method parses the command line, then calls run to run the command line interface.
	 * @param args arguments array
	 */
	public static void main(String[] args) throws IOException {
		//Parse for flags and arguments. Have separate booleans instead of 
		//an array to avoid possible bugs of indexing into the array poorly.
		boolean isSmart = false;
		boolean isPrefix = false;
		boolean isLED = false;
		boolean isWhitespace = false;
		boolean isGUI = false;
		int edits = 0;	//Default edits is 0.
		List<String> fileNames = new ArrayList<String>();
		String invalidFormat = "You must enter the format as follows:" +
				"flags: --led int --smart --prefix --whitespace fileNames";
		//Parses the arguments array.
		for(int i = 0; i<args.length; i++){
			//Parses flags
			if(args[i].equals("--smart"))
				isSmart = true;
			else if(args[i].equals("--prefix"))
				isPrefix = true;
			else if(args[i].equals("--whitespace"))
				isWhitespace = true;
			else if(args[i].equals("--gui"))
				isGUI = true;
			else if(args[i].equals("--led")){
				isLED = true;
			}
			else{
				if(i!=0){
					if(args[i-1].equals("--led")){
						try{
							edits = Integer.parseInt(args[i]);
						}
						catch (NumberFormatException e){
							System.err.println("ERROR: " + invalidFormat);
							System.exit(0);
						}
					}
					//Parse all other strings as file names.
					else
						fileNames.add(args[i]);
				}
				//Parse all other strings as file names.
				else
					fileNames.add(args[i]);
			}
		}
		
		//Reads in all words from the file input, then adds the array list
		//of words to the file name. A little verbose to be passing in a 2D
		//array list, but made this decision over combining all lists into the
		//same list to avoid the edge case of the last word of the first file
		//and the first word of the second file being considered as a bigram.
		List<List<String>> words = new ArrayList<List<String>>();
		for(String name: fileNames){
			words.add(readFile(name));
		}
		
		//Now create the engine which will suggest for us.
		Engine engine = new SuggestionEngine(words, isLED, isPrefix, isWhitespace, isSmart, edits);
		run(engine);

	}
	
	/**
	 * Runs the command line interface.
	 * @param engine The engine with which to generate suggestions.
	 * @throws IOException 
	 */
	public static void run(Engine engine) throws IOException{
		InputStreamReader inputStream = new InputStreamReader(System.in);
		BufferedReader stream = new BufferedReader(inputStream);
		String line;
		System.out.println("Ready");
		while ((line = stream.readLine()) != null) {
			line = line.replaceAll("\\p{Punct}|\\d", " ").toLowerCase();
			line = line.replaceAll(("\\s+"), " ");

			if(line.trim().equals("")){
				System.exit(0);
			}
			//If there isn't whitespace at the end of the line, continue to suggest.
			else if(line.charAt(line.length()-1) != ' '){
				engine.setText(line);
				List<String> suggestions = engine.suggest();
				for(int i = 0; i<5; i++){
					if(i<suggestions.size())
						System.out.println(suggestions.get(i));
				}
			}
			System.out.println("");
		}
	}
}
