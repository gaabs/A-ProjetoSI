import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.PriorityQueue;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingWorker;


public class GUI {

	private JFrame frame;
	private JPanel panel;
	private JPanel menu;
	private JPanel gridPanel;
	private JPanel referencePanel;
	private JRadioButton startButton;
	private JRadioButton endButton;
	private JRadioButton obstaclesButton;
	ButtonGroup buttonGroup;
	JButton searchButton;
	JButton resetButton;
	JButton clearPathButton;
	private JLabel wValueLabel;
	private JFormattedTextField wValueTextField;
	private JLabel gridSizeLabel;
	private JFormattedTextField gridSizeTextField;
	private JButton updateGridSizeButton;
	private JLabel sleepTimeLabel;
	private int sleepTime;
	private JFormattedTextField sleepTimeTextField;
	private Square startSquare, endSquare;
	private Square[][] grid;
	SwingWorker<Void, Void> worker;

	int mouseButton;

	boolean mouseDown = false;
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
		frame.setResizable(false);
		frame.setBounds(100, 100, 896, 600);
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
		startButton.setBounds(284, 5, 134, 23);
		endButton = new JRadioButton("Set end location");
		endButton.setActionCommand("end");
		endButton.setBounds(420, 5, 135, 23);
		obstaclesButton = new JRadioButton("Set obstacles");
		obstaclesButton.setActionCommand("obstacle");
		obstaclesButton.setBounds(557, 5, 116, 23);
		menu.setLayout(null);
		menu.add(startButton);

		gridSizeLabel = new JLabel("Grid size:");
		gridSizeLabel.setLocation(10, 6);
		gridSizeLabel.setSize(100, 20);
		menu.add(gridSizeLabel);
		gridSizeTextField = new JFormattedTextField();
		gridSizeTextField.setText("10");
		gridSizeTextField.setSize(30, 20);
		gridSizeTextField.setLocation(66, 6);
		menu.add(gridSizeTextField);
		updateGridSizeButton = new JButton("Update Grid Size");
		updateGridSizeButton.setLocation(109, 6);
		updateGridSizeButton.setSize(130, 20);
		updateGridSizeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					n = Integer.parseInt(gridSizeTextField.getText());

					if (n > 0){
						buildGrid();
					} else{
						JOptionPane.showMessageDialog(null, "Input must be positive!");
					}

				} catch (Exception ex){
					JOptionPane.showMessageDialog(null, "Input must be a natural number!");
				}
			}
		});

		menu.add(updateGridSizeButton);

		sleepTimeLabel = new JLabel("Wait on expand time (ms):");
		sleepTimeLabel.setLocation(50, 52);
		sleepTimeLabel.setSize(150, 20);
		menu.add(sleepTimeLabel);

		sleepTimeTextField = new JFormattedTextField();
		sleepTimeTextField.setText("100");
		sleepTimeTextField.setSize(50, 20);
		sleepTimeTextField.setLocation(209, 52);
		menu.add(sleepTimeTextField);

		wValueLabel = new JLabel("w value:");
		wValueLabel.setBounds(284, 55, 46, 14);
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
		wValueTextField.setBounds(340, 51, 54, 23);
		wValueTextField.revalidate();
		menu.add(wValueTextField);

		wValueTextField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent arg0) {
				String s = wValueTextField.getText();
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
		searchButton.setBounds(420, 51, 89, 23);
		menu.add(searchButton);
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					sleepTime = Integer.parseInt(sleepTimeTextField.getText());


					if (startSquare != null && endSquare != null){
						if (worker != null && !worker.isDone()){
							worker.cancel(true);
							worker = null;
						}

						worker = new SwingWorker<Void, Void>() {

							@Override
							protected Void doInBackground()
									throws Exception {
								frame.revalidate();
								frame.repaint();



								double w = Double.parseDouble(wValueTextField.getText());
								Square.w = w;

								PriorityQueue<Square> pq = new PriorityQueue<Square>();

								clearPath();

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
									if (isCancelled()) return null;
									s+=pq + "\n";

									current = pq.peek();
									if(current==endSquare){
										pq.clear();
									}else{
										pq.remove();
										current.expanding=true;
										current.repaint();

										try {
											publish();
											Thread.sleep(sleepTime);
											current.expanding = false;
										} catch (InterruptedException e1) {
											Thread.currentThread().interrupt();
											return null;
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

								if(current==endSquare){
									while(current!=null){
										current.path = true;
										current.repaint();
										current = current.parent;
									}
								}

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

				} catch (Exception ex){
					JOptionPane.showMessageDialog(null,"Sleep time must be an Integer");
				}
			}
		});

		resetButton = new JButton("Reset");
		resetButton.setBounds(519, 51, 89, 23);
		menu.add(resetButton);
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (worker != null && !worker.isDone()){
					worker.cancel(true);
					worker = null;
				}

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

		clearPathButton = new JButton("Clear Path");
		clearPathButton.setBounds(619, 51, 110, 23);
		menu.add(clearPathButton);
		clearPathButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (worker != null && !worker.isDone()){
					worker.cancel(true);
					worker = null;
				}
				clearPath();
			}
		});

		buildGrid();

		referencePanel = new JPanel();
		referencePanel.setBounds(720, 400, 193, -345);
		panel.add(referencePanel);

		JLabel pathLabel = new JLabel("Path");
		pathLabel.setBounds(738, 95, 97, 14);
		panel.add(pathLabel);

		JButton pathColor = new JButton("");
		pathColor.setBackground(Square.PATH_COLOR);
		pathColor.setBounds(810, 91, 25, 23);
		panel.add(pathColor);

		JLabel expandingLabel = new JLabel("Expanding");
		expandingLabel.setBounds(738, 134, 97, 14);
		panel.add(expandingLabel);

		JButton expandingColor = new JButton("");
		expandingColor.setBackground(Square.EXPANDING_COLOR);
		expandingColor.setBounds(810, 129, 25, 23);
		panel.add(expandingColor);

		JLabel expandedLabel = new JLabel("Expanded");
		expandedLabel.setBounds(738, 171, 97, 14);
		panel.add(expandedLabel);

		JButton expandedColor = new JButton("");
		expandedColor.setBackground(Square.EXPANDED_COLOR);
		expandedColor.setBounds(810, 165, 25, 23);
		panel.add(expandedColor);

		JLabel borderLabel = new JLabel("Border");
		borderLabel.setBounds(738, 205, 97, 14);
		panel.add(borderLabel);

		JButton borderColor = new JButton("");
		borderColor.setBackground(Square.BORDER_COLOR);
		borderColor.setBounds(810, 200, 25, 23);
		panel.add(borderColor);

		//frame.pack();
	}

	void buildGrid(){
		if (gridPanel != null){ 
			gridPanel.removeAll();
			panel.remove(gridPanel);
		}

		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(n,n));
		gridPanel.setBounds(0, 78, 728, 483);

		panel.add(gridPanel);

		grid = new Square[n][n];
		startSquare = endSquare = null;

		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				Square square = new Square(i,j);
				square.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						mouseDown = false;

					}

					@Override
					public void mousePressed(MouseEvent e) {
						mouseButton = e.getButton();
						mouseDown = true;
						updateSquare(square);

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub


					}

					@Override
					public void mouseEntered(MouseEvent e) {
						if (mouseDown) updateSquare(square);

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

	void clearPath(){
		if (endSquare != null){
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					grid[i][j].reset();
					grid[i][j].h = Math.sqrt(Math.pow((i - endSquare.i),2) + Math.pow((j - endSquare.j),2));
					grid[i][j].repaint();

				}
			}
		}
	}

	void updateSquare(Square square){
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
				if (mouseButton == MouseEvent.BUTTON1) square.obstacle = true;
				else if (mouseButton == MouseEvent.BUTTON3) square.obstacle = false;
				square.repaint();
			}
		}
	}
}
