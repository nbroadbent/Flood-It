//Nevin Wong Syum Ganesan, 8831598
//Nick Broadbent, 8709720
import java.io.Serializable;

/**
 * The class <b>DotInfo</b> is a simple helper class to store the initial color and state
 * (captured or not) at the dot position (x,y)
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class DotInfo implements Cloneable, Serializable {
	// ADD YOUR INSTANCE VARIABLES HERE
    protected int x;
    protected int y;
    protected int color;
    protected boolean captured;

    /**
     * Constructor 
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     * @param color
     *            the initial color
     */
    public DotInfo(int x, int y, int color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Getter method for the attribute x.
     * 
     * @return the value of the attribute x
     */
    public int getX(){
        return x;
    }
    
    /**
     * Getter method for the attribute y.
     * 
     * @return the value of the attribute y
     */
    public int getY(){
        return y;
    }
 
    /**
     * Setter for captured
     * @param captured
     *            the new value for captured
     */
    public void setCaptured(boolean captured){
        this.captured = captured;
    }

    /**
     * Get for captured
     *
     * @return captured
     */
    public boolean isCaptured(){
        return captured;
    }

    /**
     * Get for color
     *
     * @return color
     */
    public int getColor() {
        return color;
    }

    public Object clone() throws CloneNotSupportedException{
        DotInfo a = new DotInfo(this.x, this.y, this.color);
        return (Object)(a);
    }
    public String toString(){
        return x+ "::"+ y+ "::"+color;
    }
    public static void main(String [] args) throws CloneNotSupportedException{
        DotInfo a = new DotInfo(1,2,1);
        DotInfo b= (DotInfo) a.clone();
        System.out.println(b);
    }
}