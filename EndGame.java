//Joey Chen
//end screen when player completes game
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
public class EndGame extends JFrame implements ActionListener{
	AudioClip intro;
	int count;
	EndPanel game;
    public EndGame(int count) {
    	super("THANK YOU FOR PLAYING");
    	setSize(710,533);
    	setLayout(null);
    	
    	this.count=count;
    	
    	game=new EndPanel(count);
    	game.setSize(710,533);
    	game.setLocation(0,0);
    	game.setLayout(null);
    	add(game);
     	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    
    	setVisible(true);
     	
    	intro =Applet.newAudioClip(getClass().getResource("[Music_Pok_mon_Advanced_-Advance_Adventure_Instrum.wav"));
    	intro.loop();
    	
    }
    public void actionPerformed(ActionEvent e){}
    public static void main(String[]args){
		int count=1;
    	JFrame.setDefaultLookAndFeelDecorated(true);
    	EndGame game= new EndGame(count);
    	game.setVisible(true);
    } 
}
class EndPanel extends JPanel implements ActionListener{
	Image backGround;
	ArrayList<Image>numbers;		//
	String[]tmp;					//
	String[]word = new String[2];	//all used to conver one number into two seperate numbers
	int [] fScore;					//
	int score;						//
	String count;					//
	public EndPanel(int score){
		super();
		setFocusable(true);
		this.score=score;
		count=""+score;
		if(score<10){ //if number is less then zero, add a zero infront of it
			count="0"+count;
		}
		
		tmp=count.split("");
		word[0]=tmp[1];	//seperate number
		word[1]=tmp[2];	//
		fScore=new int[2];
		for(int k=0;k<2;k++){
		
			fScore[k]=Integer.parseInt(word[k]);
		}
		
		numbers=new ArrayList<Image>();
		
		for(int i=0;i<10;i++){

			numbers.add(new ImageIcon("Images/Numbers"+i+".png").getImage()); //load unmber images from 0 to 9
			
		}


		backGround=new ImageIcon("Images/EndScreen.png").getImage(); 
	
	}
	public void paintComponent(Graphics g){
		g.drawImage(backGround,0,0,this);
		for(int j=0;j<2;j++){
			g.drawImage(numbers.get(fScore[j]),305+(j*35),280,this); //draw the numbers side by side	
		}
		
		
	}
	
	public void actionPerformed(ActionEvent e){}
}

 

    	

    
