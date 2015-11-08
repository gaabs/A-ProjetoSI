import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class Square extends JPanel {
	int i,j;

	/**
	 * Create the panel.
	 */
	public Square(int i, int j) {
		this.i = i;
		this.j = j;
		
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setToolTipText(String.format("(%d,%d)",i,j));
	}

}
