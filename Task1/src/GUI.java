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
	private JLabel fileNameLabel = new JLabel("������ �� ����� ��� ������� �� ��� ������ ��� ���������");
	private JTextField fileNameField = new JTextField(TEXT_SIZE);
	private JButton readButton = new JButton("��������");
	private JLabel readSuccess = new JLabel();
	private JLabel inputLabel = new JLabel("������ ��� ������ ��� �� ��������:");
	private JTextField inputField = new JTextField(TEXT_SIZE);
	private JLabel resultLabel = new JLabel();
	private JLabel retryLabel = new JLabel("��� �� ���������� ����� ���� ��� ������ ��������");
	private JLabel stateLabel = new JLabel();
	private JButton executeButton  = new JButton("��������");
	private JButton exitButton = new JButton("������");
	
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
		this.setTitle("��������: ������� 1");
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
					readSuccess.setText("��� ����� ������� ����� �������");
				else{
					Boolean read = readInputData.readData(fileInputName);
					if(read){
						readSuccess.setText("�� ������ �� ���� ������� ���������� ��������!");
						startingState = readInputData.getStartingState();
						finalStateList = readInputData.getFinalStateList();
						transition = readInputData.getTransition();
					}
					else
						readSuccess.setText("������ ������ ��������. ��������� �� ��� ������� ���������!");
				}
			}else if(event.getSource().equals(executeButton)){
				if(fileInputName.equals(""))
					resultLabel.setText("��� ����� ������ ������ ������ �� ������� ��� �� ��������");
				else{
					String input = inputField.getText();
					Integer currentState = startingState;
					int previousState = startingState;
					for(char c : input.toCharArray()){
						currentState = transition.getGoalState(currentState, c);
						if(currentState == null){
							stateLabel.setText("��� ������� �������� ��� ��� ��������� "+previousState+ " �� ���� �� �� ������� "+ c);
							break;
						}
						previousState = currentState;
					}
					if(finalStateList.contains(currentState))
						resultLabel.setText("�� �������� ��������� �� ������ ���������.\n");
					else 
						resultLabel.setText("�� �������� ��� ��������� �� ������ ���������.\n");
				}
			}else if(event.getSource().equals(exitButton)){
				System.exit(0);
			}
		}
		
	}
	
}
