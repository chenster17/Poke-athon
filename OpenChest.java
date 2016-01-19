//Joey Chen
//the window that opens when a chest is opened 
//obtains chest information for chest class
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.util.*;
import java.awt.geom.*;

public class OpenChest extends JFrame implements ActionListener{ // this is the window that opends when a chest is opened
	JPanel items;

	Chest c;
	Trainer p;
	JButton potion;
	JButton revive;
	int numPotion;
	int numRevive;
	
	public OpenChest(Chest c,int reviveC, int potionC,Trainer p){
		super("Open Chest");
		this.c=c;
		this.p=p; //get the trainer information, change information potion counter in trainer 

		setSize(233,100);
		
		setLayout(null);
		
		
		items=new JPanel();
		items.setSize(233,100);
		items.setLocation(0,0);
		add(items);
		numPotion=potionC;
		numRevive=reviveC;
		c.open();

		
		potion =new JButton("POTION: "+""+numPotion);
		revive=new JButton("REVIVE: "+""+numRevive);


		potion.addActionListener(this);
		revive.addActionListener(this);
		items.add(potion);
		items.add(revive);
	
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){  //when you close the chest window, the program will still run 
			public void windowClosing(WindowEvent e){
				JFrame frame=(JFrame)e.getSource();
				closeChest();
				frame.dispose();
			}
			});
		setResizable(false);
		
	

	}
	public void closeChest(){
		c.close();
	}
	public void actionPerformed(ActionEvent e){
		Object source =e.getSource();
		if(source==potion){//when you grab the potion, change player and chest number
			if(numPotion!=0 && p.potionN()<5){ 
				c.setPotion(c.getPotion()-1);
				p.addPotion(1);
				numPotion-=1;
				potion.setText("POTION: "+numPotion);
			}

		}
		if(source==revive){//when you grab the revive, change player and chest number
			if(numRevive!=0 && p.revive()<1){
				c.setRevive(c.getRevive()-1);
				p.addRevive(1);
				numRevive-=1;
				revive.setText("REVIVE: "+""+numRevive);
			}

		}
	}	


}
