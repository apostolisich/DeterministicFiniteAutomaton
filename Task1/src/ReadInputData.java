import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadInputData {
	
	private Integer startingState;
	private ArrayList<Integer> finalStateList = new ArrayList<Integer>();
	private Transition transition = new Transition();

    public boolean readData(String fileName){
		try{
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			bufferedReader.readLine();
			bufferedReader.readLine();

			
			String line3 = bufferedReader.readLine();
			startingState = Integer.parseInt(line3);
			
			String line4 = bufferedReader.readLine();
			char[] lineToCharArray = line4.toCharArray();
			for(char ch: lineToCharArray)
				if(ch != ' ')
					finalStateList.add(Integer.parseInt(String.valueOf(ch)));
			
			String linesAfter4;
			while((linesAfter4=bufferedReader.readLine()) != null){
				int startState, goalState;
				Character s;
				char[] characters = linesAfter4.toCharArray();
				startState = Integer.parseInt(String.valueOf(characters[0]));
				s = characters[2];
				goalState = Integer.parseInt(String.valueOf(characters[4]));
				transition.setTransition(startState, goalState, s);
			}
			bufferedReader.close();
			return true;
		}catch(FileNotFoundException ex){
			return false;
		}catch(IOException e){
			return false;
		}
	}

	public int getStartingState() {
		return startingState;
	}

	public ArrayList<Integer> getFinalStateList() {
		return finalStateList;
	}

	public Transition getTransition() {
		return transition;
	}
    
    
    
}
