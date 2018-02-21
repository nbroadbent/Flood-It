//Nevin Wong Syum Ganesan, 8831598
//Nick Broadbent, 8709720

// ADD YOUR IMPORTS HERE
import java.util.Random;
import java.util.ArrayList;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the followiung information:
 * - the state of all the ``dots'' on the board (color, captured or not)
 * - the size of the board
 * - the number of steps since the last reset
 * - the current color of selection
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameModel {
    /**
     * predefined values to capture the color of a DotInfo
     */
    public static final int COLOR_0           = 0;
    public static final int COLOR_1           = 1;
    public static final int COLOR_2           = 2;
    public static final int COLOR_3           = 3;
    public static final int COLOR_4           = 4;
    public static final int COLOR_5           = 5;
    public static final int NUMBER_OF_COLORS  = 6;


// ADD YOUR INSTANCE VARIABLES HERE
    DotInfo[][] dots;
	
	private int size;
	private int curSelColour;
    private int numOfSteps = 0;
    private boolean finished = false;

    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param size
     *            the size of the board
     */
    public GameModel(int size) {
        this.size = size;
		
        // forms a 2D array list of dots in [x][y] position
        dots = new DotInfo [size][size];  

		Random rand = new Random();
		
		// Create size*size dots with random colours
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				dots[i][j] = new DotInfo(i, j, rand.nextInt(5));
			}
		}
		
		// Set the selected colour to top-left dot
		setCurrentSelectedColor(dots[0][size-1].getColor());
    }        

    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){
		Random rand = new Random();
        int[] list = {COLOR_0, COLOR_1, COLOR_2, COLOR_3, COLOR_4, COLOR_5};
		int  n = 0;
		
		// Set the colours of the dots
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
				// Choose random colour
				dots[i][j].color = list[rand.nextInt(5)];
				// Set to not captured
				dots[i][j].setCaptured(false);
			}
        }
		// Reset the current selected color
		setCurrentSelectedColor(dots[0][size-1].getColor());
		
		// Reset steps
        numOfSteps = 0; 
    }
	
    /**
     * Getter method for the size of the game
     * 
     * @return the value of the attribute sizeOfGame
     */   
    public int getSize(){
        return size;
    }
	
    /**
     * returns the current color  of a given dot in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public int getColor(int i, int j){      
		return dots[i][j].getColor();
    }

    /**
     * returns true is the dot is captured, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCaptured(int i, int j){
		/*
        if (dots[i][j].getX() == i && dots[i][j].getY() == j){
            return true;
        else
            return false;
		*/
		return dots[i][j].isCaptured();
    }
	
    /**
     * Sets the status of the dot at coordinate (i,j) to captured
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void capture(int i, int j){
        dots[i][j].captured = true;
	}


    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        return numOfSteps;
    }

    /**
     * Setter method for currentSelectedColor
     * 
     * @param val
     *            the new value for currentSelectedColor
    */   
    public void setCurrentSelectedColor(int val) {
        curSelColour = val;
    }

    /**
     * Getter method for currentSelectedColor
     * 
     * @return currentSelectedColor
     */   
    public int getCurrentSelectedColor() {
		return curSelColour;
    }

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        return dots[i][j];
    }

   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new color.
     */
     public void step(){
		numOfSteps++;
    }
 
   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the dats are captured.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        return finished;
    }

   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        return "    Number of Steps: "+ getNumberOfSteps();
    }
}