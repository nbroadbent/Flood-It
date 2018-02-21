//Nevin Wong Syum Ganesan, 8831598
//Nick Broadbent, 8709720


// ADD YOUR IMPORTS 
import javax.swing.*;

/**
 * In the application <b>FloodIt</b>, a <b>DotButton</b> is a specialized color of
 * <b>JButton</b> that represents a dot in the game. It can have one of six colors
 * 
 * The icon images are stored in a subdirectory ``data''. We have 3 sizes, ``normal'',
 * ``medium'' and ``small'', respectively in directory ``N'', ``M'' and ``S''.
 *
 * The images are 
 * ball-0.png => grey icon
 * ball-1.png => orange icon
 * ball-2.png => blue icon
 * ball-3.png => green icon
 * ball-4.png => purple icon
 * ball-5.png => red icon
 *
 *  <a href=
 * "http://developer.apple.com/library/safari/#samplecode/Puzzler/Introduction/Intro.html%23//apple_ref/doc/uid/DTS10004409"
 * >Based on Puzzler by Apple</a>.
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class DotButton extends JButton {


// ADD YOUR INSTANCE VARIABLES HERE
	private JLabel label;
	private ImageIcon image;
	private String[] t = {"ball-0.png", "ball-1.png", "ball-2.png", "ball-3.png", "ball-4.png", "ball-5.png"};
	private String s;
	private int row = 0;
	private int column = 0;
	private int color = 0;
	private int size = 0;

    /**
     * Constructor used for initializing a cell of a specified color.
     * 
     * @param row
     *            the row of this Cell
     * @param column
     *            the column of this Cell
     * @param color
     *            specifies the color of this cell
     * @param iconSize
     *            specifies the size to use, one of SMALL_SIZE, MEDIUM_SIZE or MEDIUM_SIZE
     */

    public DotButton(int row, int column, int color, int iconSize) {
		super(Integer.toString(color));
		this.row = row;
		this.column = column;
		this.color = color;
		size = iconSize;
		
		s = "M";
		
		// Create button
		String[] colors = {"Grey", "Yellow", "Blue", "Green", "Purple", "Red"};
		
		//JButton dot = new JButton(colors[color]);
		
		// Small
		if (iconSize == 1)
			s = "S";
		else if(iconSize == 2)
			s = "M";
		else
			s = "N";
		
		System.out.println(s);
		
		image = new ImageIcon("data/"+s+"/"+t[color]);
		label = new JLabel(image, JLabel.CENTER);
		//label.setForeground (Color.red);
		add(label);
	}

 /**
     * Other constructor used for initializing a cell of a specified color.
     * no row or column info available. Uses "-1, -1" for row and column instead
     * 
     * @param color
     *            specifies the color of this cell
     * @param iconSize
     *            specifies the size to use, one of SMALL_SIZE, MEDIUM_SIZE or MEDIUM_SIZE
     */   
    public DotButton(int color, int iconSize) {
		this.color = color;
		size = iconSize;
    }

    /**
     * Changes the cell color of this cell. The image is updated accordingly.
     * 
     * @param color
     *            the color to set
     */
    public void setColor(int color) {
		this.color = color;
		image = new ImageIcon("data/"+s+"/"+t[color]);
		label.setIcon(image);
	}

    /**
     * Getter for color
     *
     * @return color
     */
    public int getColor(){
		return color;
    }
 
    /**
     * Getter method for the attribute row.
     * 
     * @return the value of the attribute row
     */

    public int getRow() {
		return row;
    }

    /**
     * Getter method for the attribute column.
     * 
     * @return the value of the attribute column
     */

    public int getColumn() {
		return column;
    }


// ADD YOUR PRIVATE METHODS HERE (IF USING ANY)
}