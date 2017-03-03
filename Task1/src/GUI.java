import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame {
	
	private final int TEXT_SIZE = 10;
	
	private JPanel panel;
	private JLabel fileNameLabel = new JLabel("Γράψτε το όνομα του αρχείου με τον ορισμό του αυτομάτου");
	private JTextField fileNameField = new JTextField(TEXT_SIZE);
	private JButton readButton = new JButton("Ανάγνωση");
	private JLabel readSuccess = new JLabel();
	private JLabel inputLabel = new JLabel("Γράψτε μία είσοδο για το αυτόματο:");
	private JTextField inputField = new JTextField(TEXT_SIZE);
	private JLabel resultLabel = new JLabel();
	private JLabel retryLabel = new JLabel("Για να συνεχίσετε δώστε άλλη μία είσοδο παραπάνω");
	private JLabel stateLabel = new JLabel();
	private JButton executeButton  = new JButton("Εκτέλεση");
	private JButton exitButton = new JButton("Έξοδος");
	
	public GUI(){
	
		panel = new JPanel();
		panel.add(fileNameLabel);
		panel.add(fileNameField);
		panel.add(readButton);
		panel.add(readSuccess);
		panel.add(inputLabel);
		panel.add(inputField);
		panel.add(stateLabel);
		panel.add(resultLabel);
		panel.add(retryLabel);
		panel.add(executeButton);
		panel.add(exitButton);
		
		this.setContentPane(panel);
		
		ButtonListener listener = new ButtonListener();
		
		readButton.addActionListener(listener);
		executeButton.addActionListener(listener);
		exitButton.addActionListener(listener);
		
		this.setSize(405, 250);
		this.setVisible(true);
		this.setTitle("Αυτόματα: Εργασία 1");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	class ButtonListener implements ActionListener{
		ReadInputData readInputData = new ReadInputData();
		int startingState = 0;
		ArrayList<Integer> finalStateList = new ArrayList<Integer>();
		Transition transition = new Transition();
		String fileInputName = "";
		
		public void actionPerformed(ActionEvent event) {
			if(event.getSource().equals(readButton)){
				fileInputName = fileNameField.getText();
				if(fileInputName.equals(""))
					readSuccess.setText("Δεν έχετε εισάγει όνομα αρχείου");
				else{
					Boolean read = readInputData.readData(fileInputName);
					if(read){
						readSuccess.setText("Το αρχείο με τους κανόνες διαβάστηκε επιτυχώς!");
						startingState = readInputData.getStartingState();
						finalStateList = readInputData.getFinalStateList();
						transition = readInputData.getTransition();
					}
					else
						readSuccess.setText("Υπήρξε κάποιο πρόβλημα. Δοκιμάστε με την απόλυτη διεύθυνση!");
				}
			}else if(event.getSource().equals(executeButton)){
				if(fileInputName.equals(""))
					resultLabel.setText("Δεν έχετε ορίσει κάποιο αρχείο με κανόνες για το αυτόματο");
				else{
					String input = inputField.getText();
					Integer currentState = startingState;
					int previousState = startingState;
					for(char c : input.toCharArray()){
						currentState = transition.getGoalState(currentState, c);
						if(currentState == null){
							stateLabel.setText("Δεν υπάρχει μετάβαση από την κατάσταση "+previousState+ " σε άλλη με το σύμβολο "+ c);
							break;
						}
						previousState = currentState;
					}
					if(finalStateList.contains(currentState))
						resultLabel.setText("Το αυτόματο τερμάτησε σε τελική κατάσταση.\n");
					else 
						resultLabel.setText("Το αυτόματο δεν τερμάτησε σε τελική κατάσταση.\n");
				}
			}else if(event.getSource().equals(exitButton)){
				System.exit(0);
			}
		}
		
	}
	
}
