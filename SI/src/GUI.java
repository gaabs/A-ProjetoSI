import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.xml.bind.Marshaller.Listener;

import java.awt.ComponentOrientation;


public class GUI {

	private JFrame frame;
	private JPanel panel;
	private JPanel menu;
	private JPanel gridPanel;
	private JRadioButton startButton;
	private JRadioButton endButton;
	private JRadioButton obstaclesButton;
	ButtonGroup buttonGroup;
	JButton searchButton;
	JButton resetButton;
	private JLabel wValueLabel;
	private JFormattedTextField wValueTextField;
	private JLabel gridSizeLabel;
	private JFormattedTextField gridSizeTextField;
	private JButton updateGridSizeButton;
	private Square startSquare, endSquare;
	private Square[][] grid;
	int n = 10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
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
		endButton = new JRadioButton("Set end location");
		endButton.setActionCommand("end");
		endButton.setBounds(320, 5, 135, 23);
		obstaclesButton = new JRadioButton("Set obstacles");
		obstaclesButton.setActionCommand("obstacle");
		obstaclesButton.setBounds(457, 5, 116, 23);
		menu.setLayout(null);
		menu.add(startButton);

		gridSizeLabel = new JLabel("Grid size:");
		gridSizeLabel.setLocation(10, 6);
		gridSizeLabel.setSize(100, 20);
		menu.add(gridSizeLabel);
		gridSizeTextField = new JFormattedTextField();
		gridSizeTextField.setText("10");
		gridSizeTextField.setSize(30, 20);
		gridSizeTextField.setLocation(104, 6);
		menu.add(gridSizeTextField);
		updateGridSizeButton = new JButton("Update Grid Size");
		updateGridSizeButton.setLocation(10, 35);
		updateGridSizeButton.setSize(130, 20);
		updateGridSizeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				n = Integer.parseInt(gridSizeTextField.getText());
				buildGrid();
			}
		});

		menu.add(updateGridSizeButton);

		wValueLabel = new JLabel("w value:");
		wValueLabel.setBounds(236, 52, 46, 14);
		menu.add(wValueLabel);
		menu.add(endButton);
		menu.add(obstaclesButton);
		buttonGroup = new ButtonGroup();
		buttonGroup.add(startButton);
		buttonGroup.add(endButton);
		buttonGroup.add(obstaclesButton);
		obstaclesButton.setSelected(true);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
		decimalFormat.setGroupingUsed(false);
		wValueTextField = new JFormattedTextField(decimalFormat);
		wValueTextField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		wValueTextField.setValue(new Double(1.0));
		wValueTextField.setColumns(5);
		//wValueTextField.setText("1.0");
		wValueTextField.setBounds(292, 48, 54, 23);
		wValueTextField.revalidate();
		menu.add(wValueTextField);

		wValueTextField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
				String s = wValueTextField.getText();
				System.out.println(s);
				if(!s.matches("[0-9]*[,.]?[0-9]*")){
					wValueTextField.setText("");
				}

			}

			public void keyReleased(KeyEvent arg0) {
				String s = wValueTextField.getText();
				if(!s.matches("[0-9]*[,.]?[0-9]*")){
					wValueTextField.setText("");
				}

			}

			public void keyPressed(KeyEvent arg0) {
				String s = wValueTextField.getText();
				if(!s.matches("[0-9]*[,.]?[0-9]*")){
					wValueTextField.setText("");
				}				
			}
		});


		searchButton = new JButton("Search");
		searchButton.setBounds(411, 48, 89, 23);
		menu.add(searchButton);
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (startSquare != null && endSquare != null){

					SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

						@Override
						protected Void doInBackground()
								throws Exception {
							frame.revalidate();
							frame.repaint();



							double w = Double.parseDouble(wValueTextField.getText());
							Square.w = w;

							PriorityQueue<Square> pq = new PriorityQueue<Square>();

							//ArrayList<Square> pq = new ArrayList<Square>();



							for(int i=0;i<n;i++){
								for(int j=0;j<n;j++){
									grid[i][j].reset();
									grid[i][j].h = Math.sqrt(Math.pow((i - endSquare.i),2) + Math.pow((j - endSquare.j),2));
									grid[i][j].repaint();

								}

							}

							//				startSquare.function = h;
							startSquare.real=0;
							pq.add(startSquare);

							// U, D, L, R, UL, UR, DL, DR 
							int di[] = {-1,1,0,0,-1,-1,1,1};
							int dj[] = {0,0,-1,1,-1,1,-1,1};
							double cost[] = {1,1,1,1,1.4,1.4,1.4,1.4};
							double custo;
							int ti, tj;
							Square current = startSquare;
							String s="";
							while(!pq.isEmpty()){
								s+=pq + "\n";

								current = pq.peek();
								//current = pq.get(0);
								if(current==endSquare){
									pq.clear();
								}else{
									pq.remove();
									//pq.remove(0);
									current.expanding=true;
									current.repaint();

									try {
										publish();
										Thread.sleep(0);
										current.expanding = false;
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}


									for(int i=0;i<8;i++){
										ti = current.i + di[i];
										tj = current.j + dj[i];
										custo = current.real + cost[i];
										if(ti<n && ti>=0 && tj<n && tj>=0 && !grid[ti][tj].obstacle){
											if(grid[ti][tj].real > custo){									
												grid[ti][tj].real = custo;
												if(grid[ti][tj].parent==null){
													pq.add(grid[ti][tj]);
													grid[ti][tj].border=true;
													grid[ti][tj].revalidate();
													grid[ti][tj].repaint();
												}
												grid[ti][tj].parent = current;

											}
										}

									}
									current.border=false;
									current.expanded=true;
									current.repaint();
								}



							}

							//current = endSquare;
							//System.out.println("saiii");
							if(current==endSquare){
								while(current!=null){
									current.path = true;
									current.repaint();
									System.out.println(current.parent);
									current = current.parent;
								}
							}
							System.out.println(s);




							return null;
						}

						private void publish() {
							frame.revalidate();
							frame.repaint();
						}

					};
					worker.execute();
				} else{
					JOptionPane.showMessageDialog(null,"Erro! Selecione um começo e um fim!");
				}
			}
		});

		resetButton = new JButton("Reset");
		resetButton.setBounds(549, 48, 89, 23);
		menu.add(resetButton);
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < n; i++){
					for (int j = 0; j < n; j++){
						grid[i][j].resetAll();
						grid[i][j].repaint();
					}
				}
				startSquare = null;
				endSquare = null;
			}
		});





		//		gridPanel = new JPanel();
		//		gridPanel.setLayout(new GridLayout(n,n));
		//		gridPanel.setBounds(0, 78, 784, 483);
		//		panel.add(gridPanel);

		buildGrid();


		//frame.pack();
	}

	void buildGrid(){
		if (gridPanel != null){ 
			gridPanel.removeAll();
			panel.remove(gridPanel);
		}

		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(n,n));
		gridPanel.setBounds(0, 78, 784, 483);
		panel.add(gridPanel);

		grid = new Square[n][n];
		startSquare = endSquare = null;

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
						//moveable[square.i][square.j] = !moveable[square.i][square.j];

						System.out.println(buttonGroup.getSelection().getActionCommand());
						String choice = buttonGroup.getSelection().getActionCommand();
						if (choice == "start" && !square.obstacle){
							if (startSquare != null){
								startSquare.start = false;
								startSquare.repaint();
							} 
							startSquare = square;
							square.start = true;
							square.repaint();
						}

						if (choice == "end" && !square.obstacle){
							if (endSquare != null){
								endSquare.end = false;
								endSquare.repaint();
							} 
							endSquare = square;
							square.end = true;
							square.repaint();
						}

						if (choice == "obstacle"){
							if (!square.start && !square.end){
								square.obstacle = !square.obstacle;
								square.repaint();
							}
						}
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

				gridPanel.add(square);
				grid[i][j] = square;
			}
		}
		gridPanel.revalidate();
		gridPanel.repaint();
	}
}
