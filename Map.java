//Joey Chen
//create the world the player moves in
//creates all elements in the world(chest and bushes)
//keeps track of player in world
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.util.*;
import java.awt.geom.*;

public class Map {

	//size of map
	private int map_x,map_y;
	static int secs;
	static int interval;
	static int min;
	
	//defines walkable area
	private int path_x1,path_x2,path_y1,path_y2; //create dimension of path 
	private int YBpos,YBposPrev, extraY;  //map positions
	private int mapHeight;
	private ArrayList<Chest> chests; //create all chest
	private ArrayList<Bushes> bushes; //create all bushes
	private int step; 
	int mapMiddle;
	Rectangle2D doorRect; //the door rectang;e
	Point oBox;
	Chest cClassTmp; //temp chest variable
	Stopwatch time;
   	JPanel timerDisplay;
    public Map(int mapHeight){
    	map_x=500; //Size of the world
    	map_y=1000;//
    	path_x1=100; //
    	path_x2=380; //
    	path_y1=149; //world path dimensions
    	path_y2=671; //
	    secs = 61;
	    min =10;
    	YBpos=0;			//position of the world
    	YBposPrev =0;		//
    	extraY=250;//final position of world
		step=2;
		mapMiddle=mapHeight/2; //middle of the world
		oBox = new Point(0,0); 
    	this.mapHeight=mapHeight;
    	chests=new ArrayList<Chest>(); //create list for bushes and chest
    	bushes=new ArrayList<Bushes>();//
    	doorRect=new Rectangle2D.Double(239,121,20,30); //create the door
    	time =new Stopwatch();
    	createObjects(); //create all objects
    
    }
	public void drawTimer(Graphics g,GamePanel pg){
		g.setColor(Color.WHITE);
		int tmpM=Integer.parseInt(time.getMin());
		int tmpS=Integer.parseInt(time.getSec());
		String minutes=time.getMin();
		String seconds=time.getSec();
		
		if(tmpM<10){
			minutes="0"+minutes;
		}
		if(tmpS<10){
			seconds="0"+seconds;
		}
		g.drawString(minutes+" : "+seconds,233,55); 
		if(tmpM==0 && tmpS==0){ //shut game down when time is 00:00
			pg.shutDown();
			
		}
		
	}


	
    
    public Point movePlayer(Point playerXY, Point moveXY){
    	//move the palyer
     	Point newPlayerXY = new Point(playerXY.x + moveXY.x, playerXY.y + moveXY.y); //temp move
     	
     	
     	newPlayerXY.x = movePlayerX(newPlayerXY.x); //check if player can move X
     	newPlayerXY.y = movePlayerY(playerXY.y,moveXY.y); //check if player can move Y
     	
     	if(YBposPrev!=YBpos){
			for(Chest a: chests){
    			a.setLocationY(a.getLocation().y+(YBpos-YBposPrev));//change location of chest
			}
			for(Bushes b: bushes){
    			b.setBLocationY(b.getBLocation().y+(YBpos-YBposPrev));//change location of bushes
			}
			
		}
     	return newPlayerXY; //if the player can move, return new location
     }
    public void setChestC(Chest a){ 
    	//find which chest player collides with
    	
    	cClassTmp=a;
    	
    	
    }
    public void setbattleTime(boolean of){
    	time.cFreeze(of);
    }
    public Chest getChestC(){
    	//give the used class to PokeAthon
    	
    	return cClassTmp;//
    }
    public void openChestW(Chest c,Trainer player){
    	//open the chest
    	time.cFreeze(true);
    	c.activateChest(c,player,time);
    }
	public void leaveWorld(Trainer player){
		//reset world when entering new world
		player.setX(239);
		player.setY(600);
		YBpos=0;
    	chests=new ArrayList<Chest>(); //erase everything and restart
    	bushes=new ArrayList<Bushes>();//
		createObjects();
	}
    
    public void openChest(Point pos){
    	//find postion of collided chest
    	oBox= pos; 
    	
    }
    public Point openChest(){
    	//return position of collided chest to PokeAthon
    	return oBox;
    } 
    public int movePlayerX(int x){//check if user can move X
    	if(x<=path_x1){
			return path_x1+1;	
		}
		if(x>=path_x2){
			return path_x2-1;	
		}
		return x;
    	
    }
    public int movePlayerY(int y,int dy){ //Check is player can move Y
    	
    	YBposPrev=YBpos;
    	
    	if(y+dy<=path_y1){
			return path_y1+1;	
		}
		if(y+dy>=path_y2){
			return path_y2-1;	
		}
		
		if(dy<0){
			
			if(mapMiddle==y && YBpos == 0){
				YBpos+=step;

			}
			if(YBpos>0 && YBpos<extraY){
				YBpos+=step;
				
			}
			if(YBpos==extraY || YBpos==0){
				return y+dy; 
			}
			
		}
		if(dy>0){
			
			if(mapMiddle==y && YBpos == extraY){
				YBpos-=step;
				
			}
			if(YBpos>0 && YBpos<extraY){
				YBpos-=step;
					
			}
			if(YBpos==extraY || YBpos==0){
				return y+dy; 
			}
			
		}

		return y;
    	
    }
    public int getYBpos(){
    	return YBpos;
    }
    public void setYBpos(int amount){
    	for(Chest a: chests){
    		a.setLocationY(a.getLocation().y+(amount-YBpos));
		}
	    for(Bushes b: bushes){
    		b.setBLocationY(b.getBLocation().y+(amount-YBpos));
		}
		
    	YBpos=amount;
    	
    }
    public void createObjects(){
    	for(int cy=100;cy<600;cy+=220){
    		Point chestLocation = new Point(105,cy); 
    		chests.add(new Chest(chestLocation));
    		
    	}
    	for(int by=-1;by<620;by+=220){
    		Point bushLocation = new Point(100,by); 
    		bushes.add(new Bushes(bushLocation));
    		
    	}
    	
    }

    
    public int isColliding(Rectangle2D p,Trainer player){
    	for(Chest a: chests){
    		if(p.intersects(a.getchest())){
    			openChest(a.getLocation());
    			setChestC(a);
    			return 1;
    		}	
    		
    	}
    	for(Bushes b:bushes){
     		if(b.getbush().contains((double)player.getX(),(double)player.getY()+20,20,10)){
    			return 2;
    		}	
    	}

    	if(p.intersects(doorRect)){
    		leaveWorld(player);
    		return 3;
    	}
    	
    
    	return 0;	
    	
    } 
    
 
}