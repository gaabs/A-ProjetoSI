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
//	static Color START_COLOR = new Color(arg0, arg1, arg2);
//	static Color END_COLOR = new Color(arg0, arg1, arg2);
//	static Color OBSTACLE_COLOR = new Color(arg0, arg1, arg2);
//	static Color BORDER_COLOR = new Color(arg0, arg1, arg2);
//	static Color EXPANDING_COLOR = new Color(arg0, arg1, arg2);
//	static Color EXPANDED_COLOR = new Color(arg0, arg1, arg2);
//	static Color PATH_COLOR = new Color(arg0, arg1, arg2);

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
		
		//if (real > p.real) return 1;
		//if (real < p.real) return -1;
		
		return 0;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (start) drawStart(g);
		if (end) drawEnd(g);
		
		if (start) setBackground(Color.RED);
		else if (end) setBackground(Color.GREEN);
		else if (obstacle) setBackground(Color.GRAY);
		else if (path) setBackground(Color.BLUE);
		else if (expanding) setBackground(Color.YELLOW);
		else if (border) setBackground(Color.ORANGE);	
		else if (expanded) setBackground(Color.CYAN);
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
