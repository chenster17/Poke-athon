//Joey chen 
//Intro and control screen
//bring player to game
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.util.*;
import java.awt.geom.*;
import javax.sound.sampled.AudioSystem;
import java.applet.*;
public class StartGame extends JFrame implements ActionListener{
	AudioClip intro;
	JButton startGame;
	JButton control;
	
	JButton returnToScreen;
	IntroPanel game;
	HelpPanel help; 
	boolean sScreen;
    public StartGame() {
 
    	super("FUN STARTS HERE!!!");
    	setSize(710,533);
    	setLayout(null);
    	sScreen=true;
    	
    	//create two different class for intro and controls
    	game=new IntroPanel();
    	game.setSize(710,533);
    	game.setLocation(0,0);
    	game.setLayout(null);
    	add(game);
    	game.setVisible(true);
    	
    	help=new HelpPanel();
    	help.setSize(710,533);
    	help.setLocation(0,0);
    	help.setLayout(null);
    	add(help);
    	help.setVisible(false);
    
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    
    	setVisible(true);
    	
    
    	//startGame and control appear only in intro screen
 		startGame=new JButton("PLAY");
		startGame.setBounds(500,50,150,50);
		startGame.setBackground(Color.CYAN);
		startGame.addActionListener(this);
		game.add(startGame);
		
		control=new JButton("CONTROL");
		control.setBounds(500,125,150,50);
		control.setBackground(Color.CYAN);
		control.addActionListener(this);
		game.add(control);
   	
   		//returnToScreen only appears in control screen
 		returnToScreen=new JButton("RETURN");
		returnToScreen.setBounds(50,24,100,51);
		returnToScreen.setBackground(Color.CYAN);
		returnToScreen.addActionListener(this);
		help.add(returnToScreen);
    		
    	
    	//intro =Applet.newAudioClip(getClass().getResource("[Music_Pok_mon_Advanced_-Advance_Adventure_Instrum.wav"));
    	//intro.loop();
    
	
    }
    public void actionPerformed(ActionEvent e){
    	Object source=e.getSource();
		
    	if(source==startGame){//start the game
    		PokeAthon start =new PokeAthon();
    		
    		start.setVisible(true);
    		game.setVisible(false);
    		//intro.stop();
    		
    		dispose();

    	}
    	//bring player to controls and back to intro
    	if(source == control){
    		game.setVisible(false);
    		help.setVisible(true);	
    	}
    	if(source== returnToScreen){
    		game.setVisible(true);
    		help.setVisible(false);	
    	}
    }
    public static void main(String[]args){

    	JFrame.setDefaultLookAndFeelDecorated(true);
    	StartGame game= new StartGame();
    	game.setVisible(true);
    }
}
class IntroPanel extends JPanel implements ActionListener{
	Image backGround;
	public IntroPanel(){
	
		super();
		setFocusable(true);

		backGround=new ImageIcon("Images/IntroBG.png").getImage(); //draw background for Intro screen
	
	}
	public void paintComponent(Graphics g){
		g.drawImage(backGround,0,0,this);
		
		
	}
	
	public void actionPerformed(ActionEvent e){}
}
class HelpPanel extends JPanel implements ActionListener{
	Image helpBackGround;
	public HelpPanel(){
	
		super();
		setFocusable(true);

		helpBackGround=new ImageIcon("Images/helpScreen.png").getImage(); //draw control screen 
	
	}
	public void paintComponent(Graphics g){
		g.drawImage(helpBackGround,0,0,this);
		
		
	}
	
	public void actionPerformed(ActionEvent e){}
}