import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;


public class Teste {

	private JFrame frame;
	private JPanel panel;
	private JPanel menu;
	private JPanel grid;
	private JRadioButton startButton;
	private JRadioButton goalButton;
	private JRadioButton obstaclesButton;
	private boolean[][] moveable;
	private JLabel wValueLabel;
	private JTextField wValueTextField;
	private Square start, goal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Teste window = new Teste();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Teste() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		menu = new JPanel();
		menu.setLocation(0, 0);
		panel.add(menu);
		menu.setSize(784,77);
		
		startButton = new JRadioButton("Set start location");
		startButton.setActionCommand("start");
		startButton.setBounds(184, 5, 134, 23);
		goalButton = new JRadioButton("Set goal location");
		goalButton.setActionCommand("goal");
		goalButton.setBounds(320, 5, 135, 23);
		obstaclesButton = new JRadioButton("Set obstacles");
		obstaclesButton.setActionCommand("obstacle");
		obstaclesButton.setBounds(457, 5, 116, 23);
		menu.setLayout(null);
		menu.add(startButton);
		
		wValueLabel = new JLabel("w value:");
		wValueLabel.setBounds(236, 52, 46, 14);
		menu.add(wValueLabel);
		menu.add(goalButton);
		menu.add(obstaclesButton);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(startButton);
		buttonGroup.add(goalButton);
		buttonGroup.add(obstaclesButton);
		
		wValueTextField = new JTextField();
		wValueTextField.setText("1.0");
		wValueTextField.setBounds(292, 48, 54, 23);
		menu.add(wValueTextField);
		wValueTextField.setColumns(10);
		
		JButton SearchButton = new JButton("Search");
		SearchButton.setBounds(411, 48, 89, 23);
		menu.add(SearchButton);
		
		//frame.getContentPane().add(menu, BorderLayout.NORTH);
		
		int n = 10;
		Scanner scan = new Scanner(System.in);
		//n = scan.nextInt();
		grid = new JPanel();
		grid.setBounds(0, 78, 784, 483);
		panel.add(grid);
		grid.setLayout(new GridLayout(n,n));
		
		
		moveable = new boolean[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				Square square = new Square(i,j);
				square.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						moveable[square.i][square.j] = !moveable[square.i][square.j];
						
						if (moveable[square.i][square.j]) square.setBackground(Color.WHITE);
						else square.setBackground(Color.GRAY);
						
						System.out.println(buttonGroup.getSelection().getActionCommand());
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				
				grid.add(square);
				moveable[i][j] = true;
			}
		}
		//frame.pack();
		scan.close();
	}
}
