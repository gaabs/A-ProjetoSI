import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class Square extends JPanel implements Comparable<Square>{
	int i,j;
	double function;
	double real;
	double h;
	boolean obstacle, start, end, border, expanding, path;
	Square parent;

	/**
	 * Create the panel.
	 */
	public Square(int i, int j) {
		this.i = i;
		this.j = j;
		
		obstacle = start = end = border = expanding = path = false;
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setToolTipText(String.format("(%d,%d)",i,j));
	}
	
	public int compareTo(Square p) {
		function = real + h;
		p.function =p.real+p.h;
		
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
		
		if (path) setBackground(Color.BLUE);
		else if (expanding) setBackground(Color.YELLOW);
		else if (border) setBackground(Color.ORANGE);	
		else if (!obstacle) setBackground(Color.WHITE);
		else setBackground(Color.GRAY);
	}
	
	public void drawStart(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(0, 0, 10, 10);
	}
	
	public void drawEnd(Graphics g){
		g.setColor(Color.GREEN);
		g.fillOval(this.getWidth()-10, 0, 10, 10);
	}

	public String toString(){
		return String.format("(%d,%d, %b)", i,j, obstacle);
	}
	
}
