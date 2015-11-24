import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class Square extends JPanel implements Comparable<Square>{
	int i,j;
	double function;
	double real;
	double h;
	boolean obstacle, start, end, border, expanding, path, expanded;
	Square parent;
	
	static double w = 1.0;
	static Color START_COLOR = new Color(204, 0, 0);
	static Color END_COLOR = new Color(0, 153, 76);
	static Color OBSTACLE_COLOR = Color.GRAY;
	static Color BORDER_COLOR = new Color(255, 255, 153);
	static Color EXPANDING_COLOR = Color.YELLOW;
	static Color EXPANDED_COLOR = new Color(204, 255, 255);
	static Color PATH_COLOR = new Color(55, 153, 253);

	/**
	 * Create the panel.
	 */
	public Square(int i, int j) {
		this.i = i;
		this.j = j;
		
		obstacle = start = end = border = expanding = path = expanded = false;
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setToolTipText(String.format("(%d,%d)",i,j));
	}
	
	void reset(){
		real = Double.MAX_VALUE;
		parent = null;
		border = expanding = path = expanded = false;
	}
	
	void resetAll(){
		real = Double.MAX_VALUE;
		parent = null;
		obstacle = start = end = border = expanding = path = expanded = false;
	}
	
	public int compareTo(Square p) {
		function = real + w*h;
		p.function =p.real + w*p.h;
		
		if (function > p.function) return 1;
		if (function < p.function) return -1;
		
		return 0;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (start) drawStart(g);
		if (end) drawEnd(g);
		
		if (start) setBackground(START_COLOR);
		else if (end) setBackground(END_COLOR);
		else if (obstacle) setBackground(OBSTACLE_COLOR);
		else if (path) setBackground(PATH_COLOR);
		else if (expanding) setBackground(EXPANDING_COLOR);
		else if (border) setBackground(BORDER_COLOR);	
		else if (expanded) setBackground(EXPANDED_COLOR);
		else setBackground(Color.WHITE);
	}
	
	public void drawStart(Graphics g){
		g.setColor(Color.WHITE);
		g.drawString("START", 20, 20);
			
	}
	
	public void drawEnd(Graphics g){
		g.setColor(Color.WHITE);
		g.drawString("END", 20, 20);
	}

	public String toString(){
	return "" + (real + h);
	//return "function: " + (real + h) + "real: "+real+" h: "+h;
		//return String.format("(%d,%d)%b", i,j, obstacle);
	}
	
}
