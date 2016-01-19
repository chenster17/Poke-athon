//Joey chen
//individual chest
//keeps track of position in the world
//holds a specific number of potions and revives
// keeps track of potion and revive number 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.util.*;
import java.awt.geom.*;
public class Chest{
	
	boolean boxE;
	boolean isOpen;
	private int potionC, reviveC;
	private Point location; 
	public int chestW,chestH; //the size of the chest
	Rectangle2D chestRect; 
	Stopwatch t;
    public Chest(Point pos){
    	restore();
    	chestW=20; // the size of the chest
    	chestH=20; //
    	potionC=5; //number of potion
    	reviveC=1;  //number of revives
		boxE=false; //test
		isOpen=false;
    	this.location=new Point(pos.x,pos.y); //set location of chest
    	chestRect=new Rectangle2D.Double(location.x,location.y,chestW,chestH);  //create a rectangle at location
    }
    public void activateChest(Chest c,Trainer player,Stopwatch t){
    	//open chest window
    	this.t=t;
	    OpenChest oc=new OpenChest(c,reviveC,potionC,player); 
	    oc.setVisible(true);
    	
    }
    public boolean isOpen(){
    	
    	return isOpen; 
    }
    public void open(){
    	isOpen=true;
    }
    public void close(){
    	t.cFreeze(false);
    	isOpen=false;
    }
   	
   
    public void setBoxE(boolean a){ //test
    	boxE=a;
    }

    public Rectangle2D getchest(){
    	return chestRect;
    }

    public void setLocationY(int newY){
    	//set the new location of the chest,only need to change y coordinate
    	location.y=newY;
    	chestRect=new Rectangle2D.Double(location.x,location.y,chestW,chestH);
    	
    }
    public Point getLocation(){ 
    	return location;
    }
    public int getPotion(){
    	return potionC;
    }
    public void setPotion(int amount){
    	potionC=amount;
    	
    }
    public int getRevive(){
    	return reviveC;
    }
    public void setRevive(int amount){
    	reviveC=amount;
    }
    public void restore(){
    	potionC=5;
    	reviveC=1;
    }
  
}