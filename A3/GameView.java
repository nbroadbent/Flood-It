//Nevin Wong Syum Ganesan, 8831598
//Nick Broadbent, 8709720


// ADD YOUR IMPORTS HERE
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;


import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JComponent;


/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out the actual game and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {

// ADD YOUR INSTANCE VARIABLES HERE
	private DotButton[][] dots;
	private GameModel model;
	private GameController controller;
	private int size = 0;
	private int dotSize = 0;
	private ImageIcon[] images;
	private JLabel numberofsteps;
	private JButton quit,reset;
	
    /**
     * Constructor used for initializing the Frame
     * 
     * @param model
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel model, GameController controller) {
		super("Puzzler");
		System.out.println("view");
		
    	this.model = model;
    	this.controller = controller;
		
		size = model.getSize();
		
		// 1 is small icons and 2 is medium
		if (size > 25)
			dotSize = 1;
		else
			dotSize = 2;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);


		// Updates and Shows The Number of Steps 
		numberofsteps= new JLabel("    Number of steps: " + 0);//model.getNumberOfSteps());

		// Reset Button
		JButton reset = new JButton("reset");
		reset.setFocusPainted(false);
		reset.addActionListener(controller);

		JButton quit= new JButton("quit");
		quit.setFocusPainted(false);
		quit.addActionListener(controller);

		// Create Dot Panel
		JPanel dotPanel = new JPanel();
		dotPanel.setBackground(Color.WHITE);
		dotPanel.setLayout(new GridLayout(size+1, size));
		dotPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
		
		// Create Dot Selection Panel
		JPanel selectPanel = new JPanel();
		selectPanel.setBackground(Color.WHITE);
		selectPanel.setLayout(new GridLayout(1, 3));
		
		// Create Dot options Panel
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBackground(Color.WHITE);
		optionsPanel.setLayout(new GridLayout(1, 3));
		
		// Create Selection Dots
		DotButton d0 = new DotButton(0, 0, 0, 3);
		DotButton d1 = new DotButton(0, 1, 1, 3);
		DotButton d2 = new DotButton(0, 2, 2, 3);
		DotButton d3 = new DotButton(0, 3, 3, 3);
		DotButton d4 = new DotButton(0, 4, 4, 3);
		DotButton d5 = new DotButton(0, 5, 5, 3);
		
		// Add action listeners
		d0.addActionListener(controller);
		d1.addActionListener(controller);
		d2.addActionListener(controller);
		d3.addActionListener(controller);
		d4.addActionListener(controller);
		d5.addActionListener(controller);
		
		// Add selection buttons to panel
		selectPanel.add(d0);
		selectPanel.add(d1);
		selectPanel.add(d2);
		selectPanel.add(d3);
		selectPanel.add(d4);
		selectPanel.add(d5);
		
		// Add options buttons
		optionsPanel.add(numberofsteps);
		optionsPanel.add(reset);
		optionsPanel.add(quit);
		
		
		// Add buttons
		dots = new DotButton[size][size];
		addDots(dotPanel);
		
		// Add panels to frame
		add(dotPanel);

		//adding two south panels to frame 
		JPanel southPanel=new JPanel();
		southPanel.setLayout(new GridLayout(2, 6));
		southPanel.add(selectPanel,BorderLayout.NORTH);
		southPanel.add(optionsPanel,BorderLayout.SOUTH);
		add(southPanel,BorderLayout.SOUTH);

		pack();
		setResizable(false);
		setVisible(true);	
    }

    /**
     * update the status of the board's DotButton instances based on the current game model
     */

    public void update(){
    	System.out.println(model.toString());
		numberofsteps.setText(model.toString());
		int selectedColor = model.getCurrentSelectedColor();
		
		// Update dots
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				// Assign captured dots to correct colour
				if (model.isCaptured(i, j))
					dots[i][j].setColor(selectedColor);
			}
		}
		
    }

    /**
     * adds the dots to the JPanel
     */
	private void addDots(JPanel panel){
		DotInfo dot;
		
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				// Read dots from model
				dot = model.get(i, j);
				System.out.println(dot.getX() +" "+ dot.getY() +" "+ dot.getColor());
				
				// Create dot button
				dots[i][j] = new DotButton(i, j, model.getColor(i, j), dotSize);
				// Add action listener
				dots[i][j].addActionListener(controller);
				// Add dot to frame
				panel.add(dots[i][j]);
			}
		}
	}
	
	 /**
     * sets the color of the dot at i, j position
     */
	public void setColor(int i, int j, int color){
		dots[i][j].setColor(color);
	}
		
	 /**
     * resets the color of the dot at i, j position
     */
	public void reset(){
		//int selectedColor = dots.getColor();

		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){

					dots[i][j].setColor(model.getColor(i,j));
			}
		}
	}


	/**
     * a window that pops out after finishing the game to prompt players to play again or quit
     */
	
   	public void finish(){
   		String congrats="Congratulations, you won in " + model.getNumberOfSteps()+" steps! ";
   		JFrame frame = new JFrame();
		String[] options = new String[2];
		options[0] = new String("Play again");
		options[1] = new String("Quit");
		int result= JOptionPane.showOptionDialog(frame.getContentPane(),congrats,"Won! ", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
		if (result == JOptionPane.NO_OPTION) {
					System.exit(0);
				}
				if (result == JOptionPane.YES_OPTION) {
					controller.reset(); }
   	}
  }

//    	   public void finish2(){
//     	String congrats="Congratulations, you won in " + model.getNumberOfSteps()+" steps! ";
//     	int result = JOptionPane.showConfirmDialog(null,"Congratulations, you won in " + model.getNumberOfSteps()+" steps! "+ " Do you want to play again?",
// 					"Won", JOptionPane.YES_NO_OPTION);
// 				if (result == JOptionPane.NO_OPTION) {
// 					System.exit(0);
// 				}
// 				if (result == JOptionPane.YES_OPTION) {
// 					controller.reset(); }
//    	}
// }