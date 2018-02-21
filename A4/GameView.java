//Nevin Wong Syum Ganesan, 8831598
//Nick Broadbent, 8709720


// ADD YOUR IMPORTS HERE
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;


import java.awt.Color;
import java.awt.GridLayout;
import java.io.Serializable;  
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.ButtonGroup;
import javax.swing.*;


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
	private JButton undo,redo,settings;

	private ButtonGroup settings_bg; 
	private String radioText1,radioText2;
	public JFrame settingsFrame;
	
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
		//numberofsteps= new JLabel("    Number of steps: " + 0);//model.getNumberOfSteps());
		numberofsteps=new JLabel("    Select initial dot ");
		// Reset Button
		JButton reset = new JButton("reset");
		reset.setFocusPainted(false);
		reset.addActionListener(controller);

		JButton quit= new JButton("quit");
		quit.setFocusPainted(false);
		quit.addActionListener(controller);

		//For Assignment 4: undo,redo, settings Button
			//undo button
			JButton undo=new JButton("Undo");
			undo.setFocusPainted(false);
			undo.addActionListener(controller);
			//redo button
			JButton redo=new JButton("Redo");
			redo.setFocusPainted(false);
			redo.addActionListener(controller);
			//settings button
			//JButton settings=new JButton("Settings");
			JButton settings = new JButton("Settings");
			settings.setFocusPainted(false);
			settings.addActionListener(controller);
			//settingsButton();

			//create new Top Panel for undo,redo, settings
			JPanel topPanel=new JPanel();
			topPanel.setBackground(Color.WHITE);
			topPanel.setLayout(new GridLayout(1, 3));

			//add the undo,redo,settings button into the top panel & Set it to NORTH
			topPanel.add(undo); topPanel.add(redo); topPanel.add(settings);
			add(topPanel,BorderLayout.NORTH);

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
		setResizable(true);
		setVisible(true);	
    }

    /**
     * update the status of the board's DotButton instances based on the current game model
     */

    public void update(){
		System.out.println(model);
    	System.out.println(model.toString() + "test view update");
		numberofsteps.setText(model.toString());
		int selectedColor = model.getCurrentSelectedColor();
		
		// Update dots
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				dots[i][j].setColor(model.getColor(i, j));
			}
		}
		repaint();
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
     * resets the color of the dot at i, j position
     */

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
			controller.reset(); 
		}
   	}
	
	public void setModel(GameModel model){
		this.model = model;
	}

	public void settingsButton(){
		
		JFrame settingsFrame=new JFrame("Settings");

		String[] option = {"Plane","Torus","Orthogonal","Diagonals"};
		
		JRadioButton jRadioButton1=new JRadioButton(option[0]);
		JRadioButton jRadioButton2=new JRadioButton(option[1]);
		JRadioButton jRadioButton3=new JRadioButton(option[2]);
		JRadioButton jRadioButton4=new JRadioButton(option[3]);

		JButton ok = new JButton("ok");
		ok.setFocusPainted(false);
		ok.setBackground(Color.BLUE);
		ok.setLayout(new GridLayout(1, 3));
		ok.addActionListener(controller);

		JLabel question1 = new JLabel("Play on plane or torus?");
		question1.setText("Play on plane or torus?");
		JLabel question2=new JLabel("Diagonal moves?");
		question2.setText("Diagonal moves?");

		question1.setBounds(75,50,150,50);
		jRadioButton1.setBounds(75,100,100,30);
		jRadioButton2.setBounds(75,150,100,30);
		question2.setBounds(75,200,150,50);
		jRadioButton3.setBounds(75,250,150,50);
		jRadioButton4.setBounds(75,300,150,50);
		ok.setBounds(75,350,150,50);

		//String radioText="";
		ButtonGroup settings_bg1 = new ButtonGroup();

		settings_bg1.add(jRadioButton1);
		settings_bg1.add(jRadioButton2);

		ButtonGroup settings_bg2 = new ButtonGroup();
		settings_bg2.add(jRadioButton3);
		settings_bg2.add(jRadioButton4);

		settingsFrame.add(question1);
		settingsFrame.add(jRadioButton1);
		settingsFrame.add(jRadioButton2);

		settingsFrame.add(question2);
		settingsFrame.add(jRadioButton3);
		settingsFrame.add(jRadioButton4);

		settingsFrame.add(ok);

		settingsFrame.setSize(300,500);
		settingsFrame.setLayout(null);
		settingsFrame.setVisible(true);

		pack();

		if(jRadioButton1.isSelected()){
			radioText1= jRadioButton1.getText();
		}
		if(jRadioButton2.isSelected()){
			radioText1= jRadioButton2.getText();
		}
		if(jRadioButton3.isSelected()){
			radioText1= jRadioButton3.getText();
		}
		if(jRadioButton4.isSelected()){
			radioText1= jRadioButton3.getText();
		}
		else if(ok.isSelected()){
			//settingsFrame.dispose();
		}

		
		//javax.swing.JOptionPane.showMessageDialog(this, radioText );
	}
	public String getradioText1(){
		return radioText1;
	}
	public String getradioText2(){
		return radioText2;
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