//Joey chen 
//Main program that branches information to all other classes
//create character movement
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.util.*;
import java.awt.geom.*;
import javax.sound.sampled.AudioSystem; //audo imports
import java.applet.*;


public class PokeAthon extends JFrame implements ActionListener{
	javax.swing.Timer myTimer;  //activate a timer
	GamePanel game; //define game as game panel

	
	public PokeAthon(){
		super("PokeAthon");
		setSize(508,800);
		setLayout(null);
		
		game=new GamePanel(this);
		game.setSize(508,800);
		game.setLocation(0,0);
		add(game);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		myTimer=new javax.swing.Timer(30,this); //  start a timer
		myTimer.start();						//--^
	}
	public void actionPerformed(ActionEvent e){
		
		game.move();
	}	

	public static void main(String[]args){
		JFrame.setDefaultLookAndFeelDecorated(true);
		PokeAthon game=new PokeAthon(); 
		game.setVisible(true);				
	}
}

class GamePanel extends JPanel implements KeyListener{
	private boolean[] keys;//create a list of key board keys
	Image character; //create character
	Image route;  //create route that user uses
	Image patch;  //create the small patches of grass
	Image stats;  //create top bar
	Image box;    //create open treasure chest
	boolean tap;
	boolean freeze;
	private ArrayList<Image>backG;  //create a list of background(different worlds)
	private ArrayList<AudioClip>backGSong;
	
	ArrayList<String>catchThemAll;
	int totalAmount;

	String name;
	Trainer player; //define player as Trainer
	Map myMap;  //define myMap as map
	Point moveMe; //position of player
	boolean grass; // chek if player is in the grass
	int numRev;
	int numPot;
	int YBpos=0; //position of the world
	int world=0; //check which world the trainer is in
	boolean openBattle=false;
	PokeAthon g;
	BattlePokeBackground bpg;
	public GamePanel(PokeAthon g){
		super();
		setFocusable(true);
		keys=new boolean[2000];
		addKeyListener(this);
		grass=false;
		this.g=g;
		freeze=false;
		numRev=1;
		numPot=5;
		tap=false;
		box=new ImageIcon("Images/BrownChest.png").getImage(); 
		stats=new ImageIcon("Images/stats.png").getImage();    
		patch=new ImageIcon("Images/Grass.png").getImage();    
		route = new ImageIcon("Images/path.png").getImage();   
		name="";
		player =new Trainer(name,numPot,numRev); //define player
		moveMe = new Point(0,0); 				 //define the postion of player
		myMap=new Map(800);						 //height of screen
		
		backG=new ArrayList<Image>();
		backG.add(new ImageIcon("Images/GrassforTowns.png").getImage()); //
		backG.add(new ImageIcon("Images/WaterforTowns.png").getImage()); //
		backG.add(new ImageIcon("Images/SandforTowns.png").getImage());  //All the world the player can travel
		backG.add(new ImageIcon("Images/LavaforTowns.png").getImage());  //
		backG.add(new ImageIcon("Images/CloudforTowns.png").getImage()); //
		
		backGSong=new ArrayList<AudioClip>();
		backGSong.add(Applet.newAudioClip(getClass().getResource("102-palette-town-theme.wav")));
		backGSong.add(Applet.newAudioClip(getClass().getResource("138-ocean.wav")));
		backGSong.add(Applet.newAudioClip(getClass().getResource("123-vermillion-city-s-theme.wav")));	
		backGSong.add(Applet.newAudioClip(getClass().getResource("131-lavender-town-s-theme.wav")));	
		backGSong.add(Applet.newAudioClip(getClass().getResource("133-celadon-city.wav")));	
		backGSong.get(0).loop();
		
		bpg=new BattlePokeBackground(player,g,this,myMap); 
		bpg.teamLoadAll();
		
		catchThemAll =new ArrayList<String>();
		totalAmount=0;
	}
	public void shutDown(){
		backGSong.get(world).stop();
		g.dispose();
	}
	public void nextWorld(){
		//move player through worlds
		backGSong.get(world).stop();
		if(world<4){
			world+=1;
			totalAmount+=catchThemAll.size();
			catchThemAll.clear();
			backGSong.get(world).loop();
		}
		else{
			EndGame eg = new EndGame(totalAmount); //if you leave the fifth world, Game over
			backGSong.get(world).stop(); 
			freeze=true; //stop all background movement
			g.dispose(); //get rid of the frame
			
		}
	}
	
	public int worldCount(){
		return world; //get what world user is in
	}
	public void paintComponent(Graphics gg){	
		numRev=player.revive();
		numPot=player.potionN();


		gg.drawImage(backG.get(world),0,myMap.getYBpos()-200,this); //draw world
		gg.drawImage(route,0,myMap.getYBpos()-200,this);      //draw the path he takes
	 	if(tap==true && keys[KeyEvent.VK_SPACE]){ //draw open chest 
	 	
	 		gg.drawImage(box,myMap.openChest().x-4,myMap.openChest().y-7,this);
	 		drawChest(gg);
	 		if(myMap.getChestC()!=null &&!myMap.getChestC().isOpen()){
	 			keys[KeyEvent.VK_SPACE]=false;
	 		}
	 	}
	 	unFreeze();
	 	player.draw(moveMe,gg,this); //draw the player 
	 	if(grass==true){
	 		gg.drawImage(patch,player.getX(),player.getY()+15,20,20,this); // if the player is in the grass, draw grass infornt of him	
	 	}

		//draw all the text
	 	gg.drawImage(stats,0,0,this);
	 	gg.setColor(Color.WHITE);
	 	gg.drawString(""+catchThemAll.size()+"/5",367,57);
	 	gg.drawString(""+numRev,85,35);
	 	gg.drawString(""+numPot,85,75);
	 	gg.drawString(""+(world+1),142,64);

	 	myMap.drawTimer(gg,this);	 	
	}

	public void unFreeze(){
		if(myMap.getChestC()!=null &&!myMap.getChestC().isOpen()){
			
			freeze=false;
		}
		
	}
	public void return_From_Battle(){
		freeze=false;
		openBattle=false;
		backGSong.get(world).loop();
		
	}
	public void drawChest(Graphics g){
		//open the treasure chest
		if(!freeze){
			freeze=true;
			
			myMap.openChestW(myMap.getChestC(),player);
		}
		
			
	}
	
	public void move(){
		
	
		int temp= myMap.getYBpos(); //get the previous map location
		Point tmp = player.getPosition(); //get previous character location
		if(!freeze){	
			//move the character
			
		    moveMe = new Point(0,0);//set direction that the character must travel 
			if(!keys[KeyEvent.VK_UP] && !keys[KeyEvent.VK_DOWN]){
				
				if (keys[KeyEvent.VK_LEFT]){
					tap=false ;	
					moveMe.x =-2; //move left
				
				}
				if (keys[KeyEvent.VK_RIGHT]){	
					tap=false ;
					moveMe.x =2;//move right
				
				}
			}
			
			if(keys[KeyEvent.VK_UP]){
				tap=false ;
				moveMe.y=-2; //move up
				}
			if(keys[KeyEvent.VK_DOWN]){
				tap=false ;
				moveMe.y=2;	//move down
					
				}
				
	
			player.move(myMap.movePlayer(player.getPosition(),moveMe));	//move the character based on direction	
			if(checkCollision()==1){ //if the player collides with chest
			
				tap=true;
				player.move(tmp); //stop player from moving
				myMap.setYBpos(temp); //stop world from moving
				
			}
			if(checkCollision()==2){ //if player collides with grass 
				grass=true;
				temp = myMap.getYBpos(); //
				tmp = player.getPosition();//allow character move normally, however, grass is drawn onto ofcharacter
				if(foundPoke()){
					if (moveMe.x!=0 || moveMe.y!=0){
						backGSong.get(world).stop();
						openBattleWindow();
					}
			
				}
				
			}
			else{ //character moves normally
				openBattle=false;
				grass=false;
				temp = myMap.getYBpos();
				tmp = player.getPosition();
			}
			if(checkCollision()==3){ //if player collides with exit door, move to the next world
				nextWorld();
			}
			repaint();
		}
		else{
			keys[KeyEvent.VK_LEFT]=false;
			keys[KeyEvent.VK_RIGHT]=false;
			keys[KeyEvent.VK_UP]=false;
			keys[KeyEvent.VK_DOWN]=false;
		}
	}
	public void openBattleWindow(){

		if(openBattle==false){
			openBattle=true;

		    bpg.fightMode(world);
			freeze=true;
			g.setVisible(false); 
			}
	}
	public boolean foundPoke(){
		int bb=(int)(Math.random()*60+1);
		if(bb==1){
			return true;
		}
		return false;
	}
	public int checkCollision(){// check what player collides with
		
		return myMap.isColliding(player.getRect(),player);
	} 
	public void keyTyped(KeyEvent e){
	}
	public void keyPressed(KeyEvent e){

		int i = e.getKeyCode();
		keys[i] = true;
	}
	public void keyReleased(KeyEvent e){

		int i = e.getKeyCode();
		keys[i] = false;
	}
} 