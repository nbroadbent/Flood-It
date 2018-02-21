//Nevin Wong Syum Ganesan, 8831598
//Nick Broadbent, 8709720


import java.awt.event.*;
import javax.swing.JOptionPane;
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
    private GameModel model;
    private GameStack gameStack;
	private GameView view;
	
    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param size
     *            the size of the board on which the game will be played
     */
    public GameController(int size) { 
		// Size must be the size of the entire board
		this.size = size;
        model = new GameModel(size);
		view = new GameView(model, this);
		gameStack = new GameStack(size*size);
    }

    /**
     * resets the game
     */
    public void reset(){
		// Reset the model
		model.reset();
		
		// Empty the stack
        while(!gameStack.isEmpty()){
        	gameStack.pop();
        }
        view.reset();
    }
 

    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
		// A dot is clicked
        if(e.getActionCommand().equals("0")) {
			System.out.println("Grey");
			selectColor(0);
		} 
        else if(e.getActionCommand().equals("1")) {
			selectColor(1); 
		}
        else if(e.getActionCommand().equals("2")) {
			selectColor(2); 
		}
        else if(e.getActionCommand().equals("3")) {
			selectColor(3); 
		}
        else if(e.getActionCommand().equals("4")) {
			selectColor(4); 
		}
		else if(e.getActionCommand().equals("5")) {
			selectColor(5); 
		}
		
		// Reset button is clicked
		if(e.getActionCommand().equals("reset")) {
			reset();
			view.reset();
		} 
		// Quit is clicked
		if (e.getActionCommand().equals("quit")) {
			System.exit(0);
		}
		if (e.getActionCommand().equals("Play again")) {
			reset();
			view.reset();
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
			// Capture first dot
			if (gameStack.isEmpty()){
				System.out.println("empty stack :'(");
				gameStack.push(model.get(0, 0));
				model.capture(0, 0);
			}
			
			System.out.println("selectColor");	
			
			// Set the selected in the model
			model.setCurrentSelectedColor(color);
			
			// Get stack
			DotInfo[] stack = gameStack.getStack();
			int top = gameStack.getTop();
			int x = 0;
			int y = 0;
			
			// Check for dots to capture
			for (int i = 0; i < top; i++){
				//Print stack
				System.out.println(stack[i].getColor());
				
				// Get X and Y of current dot
				x = stack[i].getX();
				y = stack[i].getY();
				
				// Check Above
				if (y < size-1){
					if (!model.isCaptured(x, y+1) 
						&& model.getColor(x, y+1) == color){
						// Capture dot in model
						model.capture(x, y+1);
						// Capture dot in view
						view.setColor(x, y+1, color);
						gameStack.push(model.get(x, y+1));
					}
				}
				// Check Left
				if (x > 0){
					if (!model.isCaptured(x-1, y)
						&& model.getColor(x-1, y) == color){
						// Capture dot in model
						model.capture(x-1, y);
						// Capture dot in view
						view.setColor(x-1, y, color);
						
						gameStack.push(model.get(x-1, y));
					}
				}
				// Check Right
				if (x < size-1){
					if (!model.isCaptured(x+1, y)
						&& model.getColor(x+1, y) == color){
						// Capture dot in model
						model.capture(x+1, y);
						// Capture dot in view
						view.setColor(x+1, y, color);
						
						gameStack.push(model.get(x+1, y));
					}
				}
				// Check Below
				if (y > 0){
					if (!model.isCaptured(x, y-1)
						&& model.getColor(x, y-1) == color){
						// Capture dot in model
						model.capture(x, y-1);
						// Capture dot in view
						view.setColor(x, y-1, color);
						
						gameStack.push(model.get(x, y-1));
					}
				}
				// Update our stack data
				stack = gameStack.getStack();
				top = gameStack.getTop();
			}
			// Increment step
			model.step();
			//Update the view
			view.update();
			
			// Check if the game is finished
			if (top == size*size){
				// Congratulate player and show number of moves
				System.out.println("Congratulations, you won in " + model.getNumberOfSteps()+" steps!");
				
				// Give options    
				view.finish();
			}		
		}	
	}
}