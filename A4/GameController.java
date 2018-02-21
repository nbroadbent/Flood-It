//Nevin Wong Syum Ganesan, 8831598
//Nick Broadbent, 8709720


import java.awt.event.*;
import javax.swing.*;
import java.io.*;  
/**
 * The class <b>GameController</b> is the controller of the game. It has a method
 * <b>selectColor</b> which is called by the view when the player selects the next
 * color. It then computesthe next step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {

 // ADD YOUR INSTANCE VARIABLES HERE
    private int size;
    private boolean inDot = true;
    private boolean torus , plane , orthogonal , diagonals;
    private GameModel model;
    
	private GameView view;

	private GameModel undoModel,redoModel; 
	private GenericLinkedStack<GameModel> modelStack;
	private GenericLinkedStack<GameModel> redoStack;
	private GenericLinkedStack<DotInfo> gameStack;
	private int num = 0;
	
    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param size
     *            the size of the board on which the game will be played
     */
    public GameController(int size) { 
		GameModel newModel = null;
		
		// Size must be the size of the entire board
		
        model = new GameModel(size);
		
		gameStack = new GenericLinkedStack<DotInfo>();
		modelStack = new GenericLinkedStack<GameModel>();
		redoStack = new GenericLinkedStack<GameModel>();

		// Save new game model
		try{
			newModel = (GameModel)model.clone();
		}catch(CloneNotSupportedException ex){
			System.err.print("CloneNotSupportedException");
		}
		
		// Check and read saved game data
		System.out.println("check: " + checkFile());
		if (checkFile())
			readFile();
		else{
			model.setPlane();
			model.setOrthogonal();
			setPlane();
			setOrthogonal();
		}
		
		// Check if game is at beginning
		inDot = isStart();
		//inDot = model.getNewGame();
		
		if (newModel != null && newModel.getSize() != model.getSize()){
			model = newModel;
			inDot = true;
		}
		
		//previous setting
		plane = model.getPlane();
		orthogonal = model.getOrthogonal();
		torus = model.getTorus();
		diagonals = model.getDiagonals();
		
		this.size = model.getSize();
		
		selectColor(model.getCurrentSelectedColor());
		view = new GameView(model, this);
		//no saved file default settings

		checkSettings();
	}
	


	private void undo(){
		if (!modelStack.isEmpty()){
			// Update model
			redoStack.push(model);
			model = modelStack.pop();
			view.setModel(model);
			
			// Update game state
			selectColor(model.getCurrentSelectedColor());
			view.update();
		}
		else{
			System.out.println("Empty STACK");
			inDot = true;
		}
		
	}

	private void storeMove(){
		try{
    		//push one previous copy into the undostack
    		modelStack.push((GameModel)model.clone());
			//num++;
    	}catch(CloneNotSupportedException ex){System.err.print("CloneNotSupportedException");}
	}
	
	private void redo(){
		if (!redoStack.isEmpty()){
			modelStack.push(model);
			model = redoStack.pop();
			view.setModel(model);
			
			// Update game state
			selectColor(model.getCurrentSelectedColor());
			view.update();
		}
	}
	
    /**
     * resets the game
     */
    public void reset(){
		// Reset the model
		model.reset();
		
		// Empty the stacks
        while(!gameStack.isEmpty()){
        	gameStack.pop();
        }
		while(!modelStack.isEmpty()){
			modelStack.pop();
		}

        // Capture dots around initial dot
		selectColor(model.getCurrentSelectedColor());
		
		// Update the view
        view.update();

        inDot = true;
    }

	private void readFile(){
		File file = new File("savedGame.ser");
		
    	if (file.exists()){
			try{
				//reads previous game
				model.Read();
				//obtain previous saved game
				model = model.getSavedModel();
				//inDot = model.getNewGame();
				
				GenericLinkedStack<GameModel> m = model.getMS();
				GenericLinkedStack<GameModel> r = model.getRS();
				if (m != null)
					modelStack = m;
				if (r != null)
					redoStack = r;
				
				inDot = false;
				System.out.println("ind False");
			}
			catch(IOException i) {
				i.printStackTrace();
			}
			catch(ClassNotFoundException c) {
				System.out.println("GameModel class not found");
				c.printStackTrace();
			}
		}
	}

 	private boolean checkFile(){
 		return new File("savedGame.ser").exists();
 	}
	
	private boolean isStart(){
		selectColor (model.getCurrentSelectedColor());
		// Returns true if no dots are captured
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				if (model.isCaptured(i, j))
					return false;
			}
		}
		System.out.println("no dots");
		return true;
	}

    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
    	//clone model before performing action
    	   	
    	//check if there is savedGame.ser file in directory

		// A dot is clicked

		if (e.getSource() instanceof DotButton) {
			int colour = ((DotButton)(e.getSource())).getColor();
			
			if (inDot){
				int x = ((DotButton)(e.getSource())).getRow();
				int y = ((DotButton)(e.getSource())).getColumn();

				System.out.println( x + "::"+ y + "::"+ colour);

				model.capture(x, y);
				model.setCurrentSelectedColor(colour);
				inDot = false;
				model.setNewGame(false);
			}
			System.out.println(((DotButton)(e.getSource())).getColor());
			
			// Store state in stack
			if (model.getCurrentSelectedColor() != colour){
				storeMove();
				model.setMS(modelStack);
				model.setRS(redoStack);
			}
			// Perform move
            selectColor(colour);
		} 
		else if (e.getSource() instanceof JButton) {
			JButton clicked = (JButton)(e.getSource());

			if (clicked.getText().equals("quit")) {
				checkSettings();
				model.Write(model);
				System.out.println(model.isSaved());
				System.exit(0);
			} 
			else if (clicked.getText().equals("reset")){
				reset();
			}
			else if(clicked.getText().equals("Undo")){
				undo();
			}
			else if(clicked.getText().equals("Redo")){
				redo();
			}
			else if(clicked.getText().equals("Settings")){
				settings();
			}
			else if(clicked.getText().equals("ok")){
				view.settingsFrame.dispose();

			}
		}
    }

    /**
     * <b>selectColor</b> is the method called when the user selects a new color.
     * If that color is not the currently selected one, then it applies the logic
     * of the game to capture possible locations. It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives two options: start a new game, or exit
     * @param color
     *            the newly selected color
     */
    public void selectColor(int color){		
		
		// Check if colour is not currently captured
		if (!model.isFinished() && model.getCurrentSelectedColor() != color){
			System.out.println("test select color");
			// Set the selected in the model
			model.setCurrentSelectedColor(color);
			
			// Capture dots
			flood();
			
			//Update the view
			view.update();
			
			// Check if the game is finished
			if (model.isFinished()){
				// Congratulate player and show number of moves
				System.out.println("Congratulations, you won in " + model.getNumberOfSteps()+" steps!");
				
				// Give options    
				view.finish();		
			}	
		}
	}
	
	//plane mode
	private void flood(){
		DotInfo dot = null;
		int color = model.getCurrentSelectedColor();
		int x = 0;
		int y = 0;
		
		// Push captured to the stack
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
                if(model.isCaptured(i, j)) 
                    gameStack.push(model.get(i, j));
			}
        }
		
		// Check for dots to capture
		//for (int i = 0; i < size*size; i++){
		while(!gameStack.isEmpty()){
			// Get dot
			dot = gameStack.pop();
			
			// Get X and Y of current dot
			x = dot.getX();
			y = dot.getY();
						
			// Check Above
			if (y < size-1){
				if (shouldBeCaptured(x, y+1)){
					// Capture dot
					model.capture(x, y+1);
					gameStack.push(model.get(x, y+1));
				}
			}
			// Check Left
			if (x > 0){
				if (shouldBeCaptured(x-1, y)){
					// Capture dot
					model.capture(x-1, y);
					gameStack.push(model.get(x-1, y));
				}
			}
			// Check Right
			if (x < size-1){
				if (shouldBeCaptured(x+1, y)){
					// Capture dot
					model.capture(x+1, y);
					gameStack.push(model.get(x+1, y));
				}
			}
			// Check Below
			if (y > 0){
				if (shouldBeCaptured(x, y-1)){
					// Capture dot in model
					model.capture(x, y-1);
					gameStack.push(model.get(x, y-1));
				}
			}

			// Check if x, y are at the corners

				// Then check respective corners
			if (torus){
				// check top left corner
				if (x == 0){
					if (shouldBeCaptured(size-1, y)){
						model.capture(size-1, y);
						gameStack.push(model.get(size-1, y));
					}
				}
				else if (x == size-1){
					if (shouldBeCaptured(0, y)){
						model.capture(0, y);
						gameStack.push(model.get(0, y));
					}
				}
				if (y == 0){
					if (shouldBeCaptured(x, size-1)){
						model.capture(x, size-1);
						gameStack.push(model.get(x, size-1));
					}
				}
				else if (y == size-1){
					if (shouldBeCaptured(x, 0)){
						model.capture(y, 0);
						gameStack.push(model.get(x, 0));
					}
				}
			}
			if (diagonals){
				// Check Above-left
				if (y < size-1 && x > 0){
					if (shouldBeCaptured(x-1, y+1)){
						// Capture dot
						model.capture(x-1, y+1);
						gameStack.push(model.get(x-1, y+1));
					}
				}
				// Check Above-Right
				if (y < size-1 && x < size-1){
					if (shouldBeCaptured(x+1, y+1)){
						// Capture dot
						model.capture(x+1, y+1);
						gameStack.push(model.get(x+1, y+1));
					}
				}
				// Check Below-Left
				if (y > 0 && x > 0){
					if (shouldBeCaptured(x-1, y-1)){
						// Capture dot
						model.capture(x-1, y-1);
						gameStack.push(model.get(x-1, y-1));
					}
				}
				// Check Below-Right
				if (y > 0 && x < size-1){
					if (shouldBeCaptured(x+1, y-1)){
						// Capture dot in model
						model.capture(x+1, y-1);
						gameStack.push(model.get(x+1, y-1));
					}
				}
			}
		}	
		// Increment step
		model.step();
	}
	
	//private void 

	private boolean shouldBeCaptured(int i, int j) {
		// Check if a dot should be captured
        if(!model.isCaptured(i, j) &&
           (model.getColor(i,j) == model.getCurrentSelectedColor()))
            return true;
        else
            return false;
    }

    public void settings(){

    	JFrame message = new JFrame();
   		String[] options1=  new String[2];
   		String option1 = new String ("Play on plane or torus?");
   		options1[0] = new String ("Plane");          options1[1] = new String ("Torus");


   		String[] options2=  new String[2];
   		String option2 = new String ("Diagonal moves?");
   		options2[0] = new String ("Orthogonal");    options2[1] = new String ("Diagonals");

   		int choice= JOptionPane.showOptionDialog(message.getContentPane(),option1,"Settings", 0,JOptionPane.INFORMATION_MESSAGE,null,options1,null);
   		int choice2= JOptionPane.showOptionDialog(message.getContentPane(),option2,"Settings", 0,JOptionPane.INFORMATION_MESSAGE,null,options2,null);

   		if(choice == 0){
   			setPlane();
   			System.out.println("Plane Mode Activated");
   		}
   		if(choice == 1){
   			setTorus();
   			System.out.println("Torus Mode Activated");
   		}
   		if(choice2 == 0){
   			setOrthogonal();
   			System.out.println("Orthogonal Mode Activated");
   		}
   		if(choice2 == 1){
   			setDiagonals();
   			System.out.println("Diagonals Mode Activated");
   		}
   	}

  
    public void setTorus(){
    	model.setTorus();
    	torus=true; plane=false;
    }
    public void setPlane(){
    	model.setPlane();
    	plane=true; torus=false;
    }
    public void setOrthogonal(){
    	model.setOrthogonal();
    	orthogonal=true; diagonals=false;
    }
    public void setDiagonals(){
    	model.setDiagonals();
    	diagonals=true; orthogonal=false;
    }
    public void checkSettings(){

    	if (model.getDiagonals()){
    		System.out.println("Diagonals Mode saved");
    	}
    	if (orthogonal){
    		System.out.println("Orthogonal Mode saved");
    	
  		}
  		if (torus){
  			System.out.println("Torus Mode saved");
  		}
  		if(plane){
  			System.out.println("Plane Mode saved");
  		}
  	}
	
	public GenericLinkedStack<GameModel> getMS(){
		return modelStack;
	}
	
	public GenericLinkedStack<GameModel> getRS(){
		return redoStack;
	}

	/*
    public void settings_DO_NOT_USE(){
    	JFrame frame=new JFrame();
    	Object[] options = {"Plane","Torus","Orthogonal","Diagonals"};
		int n = JOptionPane.showOptionDialog(frame,"Play on plane or torus?","Settings",
		JOptionPane.YES_NO_OPTION,
    	JOptionPane.QUESTION_MESSAGE,
    	null,     //do not use a custom Icon
    	options,  //the titles of buttons
    	options[0]); //default button title


    	int n1 = JOptionPane.showOptionDialog(frame,"Diagonal moves?","Settings",
		JOptionPane.YES_NO_OPTION,
    	JOptionPane.QUESTION_MESSAGE,
    	null,     //do not use a custom Icon
    	options,  //the titles of buttons
    	options[3]); //default button title
	}
	*/
}