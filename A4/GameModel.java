//Nevin Wong Syum Ganesan, 8831598
//Nick Broadbent, 8709720

// ADD YOUR IMPORTS HERE
import java.util.Random;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.*;  

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
public class GameModel implements Cloneable, Serializable {
//public class GameModel {
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
	private int numCaptured = 0;
    private int numOfSteps = 0;
    protected Object cloneModel;
    
    private static GameModel saved_model;
	private GenericLinkedStack<GameModel> rs = null;
	private GenericLinkedStack<GameModel> ms = null;
    private static boolean torus , plane , orthogonal , diagonals;
	private static boolean savedGame, isNewGame;
    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param size
     *            the size of the board
     */

	
    public Object clone() throws CloneNotSupportedException{
        GameModel gm = new GameModel(size);

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                gm.dots[i][j] = (DotInfo) dots[i][j].clone();
				gm.dots[i][j].setCaptured(isCaptured(i, j));
            }
        }
        gm.size = size;
        gm.curSelColour = curSelColour;
        gm.numOfSteps = numOfSteps;
        gm.numCaptured = numCaptured;
        //gm.isfinished=(numCaptured==gm.size);

        return (Object) gm;
    }
	
    
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
		//setCurrentSelectedColor(dots[0][0].getColor());
		//dots[0][0].setCaptured(true);
    }        

    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){
        Random rand = new Random();

        dots = new DotInfo[size][size];
		
		int  n = 0;

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                dots[i][j] = new DotInfo(i, j, rand.nextInt(5));
            }
        }

		// Reset the current selected color
		//setCurrentSelectedColor(dots[0][0].getColor());
		//dots[0][0].setCaptured(true);
		
		// Reset steps
        numOfSteps = 0; 
		numCaptured = 0;
		isNewGame = true;
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
		if(isCaptured(i, j))
            return curSelColour;
        else
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
		numCaptured++;
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
		System.out.println(numCaptured);
		return numCaptured >= size*size;	
    }

   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
    	if (numOfSteps==0){
    		return "    Select initial dot  ";
    	}
    	else{

    	
        return "   Number of Steps: "+ getNumberOfSteps();}
    }

    public void Write(GameModel model){
		try{			
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savedGame.ser"));
			out.writeObject(model); 
			out.close();
			savedGame = true;
		}
		catch(IOException i){
			savedGame = false;
			i.printStackTrace();
		}
    }
	
	public void Read() throws IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("savedGame.ser"));
		saved_model = (GameModel) in.readObject();
		in.close();
    }
	
    public boolean isSaved(){
        return savedGame;
    }

    public GameModel getSavedModel(){
        return saved_model;
    }

    public void setNewGame(boolean t){
		isNewGame = t;
	}
	
	public boolean getNewGame(){
		return isNewGame;
	}
	
        //set settings
		
	public void setMS(GenericLinkedStack<GameModel> modelStack){
		ms = modelStack;
	}
	
	public GenericLinkedStack<GameModel> getMS(){
		return ms;
	}
	
	public void setRS(GenericLinkedStack<GameModel> redoStack){
		rs = redoStack;
	}
	
	public GenericLinkedStack<GameModel> getRS(){
		return rs;
	}

    public void setTorus(){
        torus=true; plane=false;
    }
    public void setPlane(){
        plane=true; torus=false;
    }
    public void setOrthogonal(){
        orthogonal=true; diagonals=false;
    }
    public void setDiagonals(){
        diagonals=true; orthogonal=false;
    }
    public boolean getTorus(){
        return torus;
    }
    public boolean getPlane(){
        return plane;
    }
    public boolean getOrthogonal(){
        return orthogonal;
    }
    public boolean getDiagonals(){
        return diagonals;
    }
}