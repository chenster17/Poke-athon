//Joey chen
//checks to see if player walks into bushes
//tells programs location of bushes 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.util.*;
import java.awt.geom.*;
public class Bushes {
	private int numPoke; //check number of pokemon
	public int bushW, bushH;
	private Point location; //find location of bushes
	Rectangle2D bushRect; //create box around bushes
    public Bushes(Point pos) {
        numPoke=10;
    	bushW=300; //bush size
    	bushH=100; //
    	this.location=new Point(pos.x,pos.y); //set location of bush based on where world puts it
    	bushRect=new Rectangle2D.Double(location.x,location.y,bushW,bushH);//set locations of the bush
    }
    public Rectangle2D getbush(){
    	return bushRect;
    }
    public void setBLocationY(int newY){ 
    	//set the new location of the bush
    	location.y=newY;
    	bushRect=new Rectangle2D.Double(location.x,location.y,bushW,bushH);
    	
    }
    public Point getBLocation(){
    	return location;
    }
    public int getNumPoke(){
    	return numPoke;
    }


  
}
    
    