import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class Square extends JPanel {
	int i,j;
	boolean obstacle, start, end, border, expanding;

	/**
	 * Create the panel.
	 */
	public Square(int i, int j) {
		this.i = i;
		this.j = j;
		obstacle = start = end = border = expanding = false;
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setToolTipText(String.format("(%d,%d)",i,j));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (start) drawStart(g);
		if (end) drawEnd(g);
		
		if (expanding) setBackground(Color.YELLOW);
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

}
