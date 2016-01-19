//joey Chen
//keep track of all player information; positon in world, item number 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.util.*;
import java.awt.geom.*;

public class Trainer extends JPanel{
		private String name;
		private int potionN, reviveN, timewait; //number of items 
		int x, y, score;
		Rectangle2D playerRect;// players rectangle
		Image player;//the image of the player
		Point charS; 
		Point prevDirect;
		ArrayList<Image>forward=new ArrayList<Image>();	// 
		ArrayList<Image>backward=new ArrayList<Image>();//the image of player walk in all direction
		ArrayList<Image>left=new ArrayList<Image>(); 	//
		ArrayList<Image>right=new ArrayList<Image>();	//
		 
		Image standF;//
		Image standB;//
		Image standL;//image of player standing in all direction
		Image standR;//
		private int walktimer;
		private int cStep;
		
		int imageX;
		int imageY;
		public Trainer(String name, int potionN, int reviveN){
			this.name=name;
			this.potionN=potionN;//number of individual items
			this.reviveN=reviveN;//
			walktimer = 0;
			charS=new Point(20,30);
			x=239;//starting position of image
			y=600;//
			cStep=0;
			imageX=20;//size of image
			imageY=30;//
			Point prevMove;// keep track of prevous movements
			prevDirect= new Point(0,-2); //starting direction 
			playerRect=new Rectangle2D.Double(x,y,charS.x,charS.y);
			for(int i=2;i<4;i++){//load all images
				forward.add(new ImageIcon("Images/TrainersF"+i+".png").getImage());
				backward.add(new ImageIcon("Images/TrainersB"+i+".png").getImage());
				left.add(new ImageIcon("Images/TrainersL"+i+".png").getImage());
				right.add(new ImageIcon("Images/TrainersR"+i+".png").getImage());
			
			}//load standing images
			standF = new ImageIcon("Images/TrainersF1.png").getImage();
			standB= new ImageIcon("Images/TrainersB1.png").getImage();
			standL = new ImageIcon("Images/TrainersL1.png").getImage();
			standR = new ImageIcon("Images/TrainersR1.png").getImage();
			
		}
	
		public void draw(Point direct,Graphics g, JPanel panel){
		//draw the image in the appropriate direction when walking or standing

			if(cStep==2){
				cStep=0;
			}
			if(direct.x==0 && direct.y==0){
		
				if(prevDirect.x==2){
					
					g.drawImage(standR,x,y,imageX,imageY,this);
	
				}
				if(prevDirect.x==-2){
					g.drawImage(standL,x,y,imageX,imageY,this);
		
		
				}
				if(prevDirect.y==2){
					g.drawImage(standB,x,y,imageX,imageY,this);
			
				}
				if(prevDirect.y==-2){
					g.drawImage(standF,x,y,imageX,imageY,this);
		
				}
				
			}
			if(direct.x==2){
				g.drawImage(right.get(cStep),x,y,imageX,imageY,this);
				walktimer++;
			}
			if(direct.x==-2){
				g.drawImage(left.get(cStep),x,y,imageX,imageY,this);
				walktimer++;
	
			}
			if(direct.y==2){
				g.drawImage(backward.get(cStep),x,y,imageX,imageY,this);
				walktimer++;
			}
			if(direct.y==-2){
				g.drawImage(forward.get(cStep),x,y,imageX,imageY,this);
				walktimer++;
	
			}
				if (walktimer == 7){ //every 7 steps, change image to next frame
					walktimer = 0;
					newFrame();
				}
			if(direct.x!=0 || direct.y!=0){
				prevDirect=direct;
			}

		
		}
		public void newFrame(){ 
			//change next frame
			cStep ++;

		}
		public Rectangle2D getRect(){
			return playerRect;
		}
		public void move(int dx,int dy){
			//move the player
			x=dx;
			y=dy;

			playerRect = new Rectangle2D.Double(x,y,imageX,imageY);
 
		}
		public Point move(Point xy){
			//move player, incase point is given
			move(xy.x,xy.y);
			return new Point(x,y);
		}

		public int getX(){
			//get individual x 
			return x;
		}
		public int getY(){
			return y;
		}
		
		public Point getPosition(){
			//get the player position in the world
			return new Point (x,y);
		}
		
		public void setX(int newX){
			//set new x and y, change player position(used when entering new world)
			x = newX;
		}	
		public void setY(int newY){
			
			y = newY;
		}
		
		public String name(){
			return name;
		}
		public int potionN(){
			return potionN;
		}
		public void addPotion(int amount){
			potionN+=amount; 
		}
		public int revive(){
			return reviveN;
		}
		public void addRevive(int amount){
			reviveN+=amount; 
		}
    
    
}