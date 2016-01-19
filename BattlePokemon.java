//Joey Chen
//actually battle screen
//all battle happens in the class
//communicates with individual pokemon classes to move pokemon
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

public class BattlePokemon extends JFrame implements ActionListener{
	AudioClip backS;
	javax.swing.Timer myTimer;  
	BattlePanel game; 
	JButton attPoke1;	//
	JButton defPoke1;	//
	JButton attPoke2;	//
	JButton defPoke2;	// every combat button
	JButton attPoke3;	//
	JButton defPoke3;	//	
	JButton attPoke4;	//
	JButton defPoke4;	//
	JButton revive; //
	JButton potion; //item buttons
	JButton escape; 		// escape from battle
	
	Boolean finishItem;

	String []ButtonPoke=new String[]{"","","",""}; //used to see what each pokemon is doing(attack or defence)
	
	Boolean[]pokeAttacking=new Boolean[]{false,false,false,false}; // see whne each pokemon on both team is finish attacking
	Boolean[]pokeEAttacking=new Boolean[]{true,true,true,true};    //
	
	Boolean usePot;
	Boolean useRev;
	
	int []attackingGTeam=new int[]{0,0,0,0};//targets for enemy team, target player team
	
	Boolean enemyTurn;
	
	Trainer p;
	int rev;
	int pot;
	Map myMap;					//
	PokeAthon g;				//
	GamePanel gp;				//get all classes
	BattlePokeBackground tt;	//
	
	Pokemon []goodTeam;		//good team
	Pokemon []enemyTeam;	//enemy team
	
	public BattlePokemon(Pokemon[] goodTeam,Pokemon[] enemyTeam,Trainer p,PokeAthon g,GamePanel gp,Map myMap,BattlePokeBackground tt){
		super("FIGHT !!!!");
		setSize(360,580);
		setLayout(null);
		this.tt=tt;
		game=new BattlePanel(goodTeam,enemyTeam,tt,gp,this);
		game.setSize(360,580);
		game.setLocation(0,0);
		game.setLayout(null);
		add(game);
		myMap.setbattleTime(true);
		

		
		this.myMap=myMap;
		this.g=g;
		this.p=p;
		this.gp=gp;
		
		this.goodTeam=goodTeam;
		this.enemyTeam=enemyTeam;
		
		usePot=false; //when using items
		useRev=false; //
		
		pot=p.potionN();
		rev=p.revive();
			
		enemyTurn=false; //see who's turn is it
		
		finishItem=false; //see when you are doning using one iteam at a time
		
		backS = Applet.newAudioClip(getClass().getResource("107-battle-vs-wild-pokemon-.wav")); //play song once
		backS.play();
		//every button in the window
		escape=new JButton();
		escape.setBounds(294,10,46,25);
		escape.setBackground(Color.GREEN);
		escape.addActionListener(this);
		game.add(escape);
		
		revive=new JButton("REVIVE: "+rev);
		revive.setBounds(11,461,163,79);
		revive.setBackground(new Color(213,117,3));
		revive.addActionListener(this);
		game.add(revive);

		potion=new JButton("POTION: "+pot);
		potion.setBounds(176,461,164,79);
		potion.setBackground(new Color(213,117,3));
		potion.addActionListener(this);
		game.add(potion);
		
		attPoke1 =new JButton("");
		attPoke1.setBounds(92,322,30,15);
		attPoke1.setBackground(Color.RED);
		attPoke1.addActionListener(this);
		game.add(attPoke1);
		
		defPoke1 = new JButton("");
		defPoke1.setBounds(131,322,30,15);
		defPoke1.setBackground(new Color(127,00,255));
		defPoke1.addActionListener(this);
		game.add(defPoke1);
		
		attPoke2 =new JButton("");
		attPoke2.setBounds(92,424,30,15);
		attPoke2.setBackground(Color.RED);
		attPoke2.addActionListener(this);
		game.add(attPoke2);
		
		defPoke2 = new JButton("");
		defPoke2.setBounds(131,424,30,15);
		defPoke2.setBackground(new Color(127,00,255));
		defPoke2.addActionListener(this);
		game.add(defPoke2);

		attPoke3 =new JButton("");
		attPoke3.setBounds(258,322,30,15);
		attPoke3.setBackground(Color.RED);
		attPoke3.addActionListener(this);
		game.add(attPoke3);
		
		defPoke3 = new JButton("");
		defPoke3.setBounds(297,322,30,15);
		defPoke3.setBackground(new Color(127,00,255));
		defPoke3.addActionListener(this);
		game.add(defPoke3);
		
		attPoke4 =new JButton("");
		attPoke4.setBounds(258,424,30,15);
		attPoke4.setBackground(Color.RED);
		attPoke4.addActionListener(this);
		game.add(attPoke4);
		
		defPoke4 = new JButton("");
		defPoke4.setBounds(297,424,30,15);
		defPoke4.setBackground(new Color(127,00,255));
		defPoke4.addActionListener(this);
		game.add(defPoke4);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		myTimer=new javax.swing.Timer(30,this); 
		myTimer.start();			
	}
	public void actionPerformed(ActionEvent e){
		Object source=e.getSource();
		if(source==escape){ //if player choices to run, reset all info, do not level up good team pokemon
			g.setVisible(true);
			backS.stop();
			myMap.setbattleTime(false);
			gp.return_From_Battle();
			for(int i=0;i<4;i++){
				goodTeam[i].restHp();
				enemyTeam[i].restHp();
				goodTeam[i].restInfo();
				enemyTeam[i].restInfo();
			}
			dispose();
			
			
		}
		if(source==potion && ButtonPoke[0]=="" && ButtonPoke[1]=="" && ButtonPoke[2]=="" && ButtonPoke[3]=="" && useRev == false && usePot==false){ //when using potion, everything else must be off

			if(pot>0){//only can use potion when there are potions in player
				usePot=true;

				}
			}
		
		if(source==revive && ButtonPoke[0]=="" && ButtonPoke[1]=="" && ButtonPoke[2]=="" && ButtonPoke[3]=="" && usePot == false){// when using revive, everything must be off
			if(rev>0){
				useRev=true;
				}
			}
		//nothing can be used when iteams are on
		if(usePot==false && useRev==false){
			if(goodTeam[0].getHp()>0){
	
				if(source==attPoke1 && ButtonPoke[0]==""){
		
					 ButtonPoke[0]="Attack"; //set pokemon 1 as attacking
			
				}
				if(source==defPoke1 && ButtonPoke[0]==""){
					 ButtonPoke[0]="Defence";//set pokemon 1 as defending
					 goodTeam[0].def=goodTeam[0].getDefence();
			
				}
			}
			if(goodTeam[1].getHp()>0){
				if(source==attPoke2 && ButtonPoke[1]==""){
			
					 ButtonPoke[1]="Attack";
			
				}
				if(source==defPoke2 && ButtonPoke[1]==""){
					 ButtonPoke[1]="Defence";
					 goodTeam[1].def=goodTeam[1].getDefence();
			
				}
			}
			if(goodTeam[2].getHp()>0){
				if(source==attPoke3 && ButtonPoke[2]==""){
		
					ButtonPoke[2]="Attack";
			
				}
				if(source==defPoke3 && ButtonPoke[2]==""){
					 ButtonPoke[2]="Defence";
					 goodTeam[2].def=goodTeam[2].getDefence();
			
				}
			}
			if(goodTeam[3].getHp()>0){
				if(source==attPoke4 && ButtonPoke[3]==""){
		
					ButtonPoke[3]="Attack";
			
				}
				if(source==defPoke4 && ButtonPoke[3]==""){
					ButtonPoke[3]="Defence";
					goodTeam[3].def=goodTeam[3].getDefence();
		
				}
			}
		}
		if(	pokeAttacking[0] && pokeAttacking[1]&& pokeAttacking[2]&& pokeAttacking[3] && enemyTurn==false){ //enemy's turn, when lpayer is done moving
			enemyTurn=true;

			for(int newC=0;newC<enemyTeam.length;newC++){
				//enemy auto target, randomly chose good team pokemon to traget, however health must be alive
				while(true){
					int tmp=(int)(Math.random()*goodTeam.length);
					if(goodTeam[tmp].getHp()>0){
						attackingGTeam[newC]=tmp;
						break;
					}
				}

			
			}
			for(int i=0;i<4;i++){
				//allow enemy to attack
				pokeEAttacking[i]=false;
			}

		}
		if(	pokeEAttacking[0] && pokeEAttacking[1]&& pokeEAttacking[2]&& pokeEAttacking[3] && enemyTurn){
			enemyTurn=false;
			for(int i=0;i<4;i++){//allow player to attack
				goodTeam[i].def=0;
				ButtonPoke[i]="";
				pokeAttacking[i]=false;
			}
		}
		
	}

	public void finishAttacking(int a){
		//after pokemon from player is done attacking, set him to true
		pokeAttacking[a]=true; 
		if(enemyTeam[game.choosenOne].getHp()<0){
			for(int i=0;i<enemyTeam.length;i++){
				//if the target was killed, auto chose differnet target
				if(enemyTeam[i].getHp()>0){
					game.choosenOne=i;
				}
			}
		} 
		
	}
	public void finishEAttacking(int a){ //after enemy pokemon is finish attacking, set him to true
		pokeEAttacking[a]=true;  
		
	}
	public static void main(String[]args){
		Pokemon[]goodTeam=new Pokemon[4];
		Pokemon[]enemyTeam=new Pokemon[4];
		PokeAthon g=new PokeAthon();
		GamePanel gp=new GamePanel(g);
		Trainer p=new Trainer("",0,0);
		Map myMap=new Map(0);
		BattlePokeBackground tt=new BattlePokeBackground(p,g,gp,myMap);
		JFrame.setDefaultLookAndFeelDecorated(true);
		BattlePokemon game=new BattlePokemon(goodTeam,enemyTeam,p,g,gp,myMap,tt); 
		game.setVisible(true);

	}
}

class BattlePanel extends JPanel implements ActionListener,MouseMotionListener,MouseListener{

	Image backG;

	Image healthBar;
				
	Point pokePPos1;
	Point pokePPos2;
	Point pokePPos3;
	Point pokePPos4;
	


	Image[]pokemonP=new Image[4];
	Image[]fightBG=new Image[5];

	Image tmp;

	Pokemon[] goodP;
	Pokemon[] enemyP;
	BattlePokeBackground tt;
	BattlePokemon bp;
	GamePanel gp;
	
	ArrayList<Rectangle2D> healSquare;
	
	int choosenOne;
	
	public BattlePanel(Pokemon[] goodP, Pokemon[] enemyP,BattlePokeBackground tt,GamePanel gp,BattlePokemon bp){
		super();
		setFocusable(true);
		addMouseListener(this); //implement mouse and action listener
		addMouseMotionListener(this);
		this.goodP=goodP;
		this.enemyP=enemyP;
		this.tt=tt;
		this.gp=gp;
		this.bp=bp;
		backG=new ImageIcon("Images/BattleBG.png").getImage(); //background
		for(int p=0;p<4;p++){
			pokemonP[p]=new ImageIcon("Images/Pokemon sprites/portrayes/"+goodP[p].name()+".png").getImage(); //get all the icons for the pokemon on the player team
		}
		for(int g=0;g<5;g++){
			fightBG[g]=new ImageIcon("Images/FightBG"+g+".png").getImage(); //get all battle backgrounds 
		}

	
		
		healthBar= new ImageIcon("Images/healthBar.png").getImage();//image of the health bar
		
		pokePPos1=new Point(11,251);	//
		pokePPos2=new Point(11,353);	//all the points of the icons
		pokePPos3=new Point(177,251);	//
		pokePPos4=new Point(177,353);	//
		
		healSquare = new ArrayList<Rectangle2D>();
		healSquare.add(new Rectangle2D.Double(15,258,70,85));	//
		healSquare.add(new Rectangle2D.Double(15,360,70,85));	//the square you click to heal pokemon
		healSquare.add(new Rectangle2D.Double(181,258,70,85));	//
		healSquare.add(new Rectangle2D.Double(181,360,70,85));	//

		choosenOne=0; //the pokemon that player targets
		

	}
	
	
	public void paintComponent(Graphics g){
	

		if(enemyP[0].getHp()<0 && enemyP[1].getHp()<0 && enemyP[2].getHp()<0 && enemyP[3].getHp()<0){ //if all pokemon die, ronud over
			Pokemon tmpPoke = new Pokemon("",0,"","","",0,0,0,0,0);//make a tmp pokemon that is used to give to user
			boolean trade=false; 
			int randChoice =(int)(Math.random()*7); //chose a number from 0 to 7, only pokemon 0 to 3 are used(meaning trade), any other number will be ignored(meaning no trade) 
			if(randChoice<=3){
				tmpPoke= new Pokemon(enemyP[randChoice].name(),enemyP[randChoice].getOrigHp(),enemyP[randChoice].getType(),enemyP[randChoice].getResist(),enemyP[randChoice].getWeak(),enemyP[randChoice].getLevel()-5,enemyP[randChoice].getAttack(),enemyP[randChoice].getDefence(),enemyP[randChoice].getWalkNum(),enemyP[randChoice].getAttackNum());
				trade=true;//player can trade
			}

			for(int i=0;i<4;i++){
				double tmpLevel=1+((((enemyP[i].getLevel()*10)-goodP[i].getLevel())/1000)/2); //level up all the pokemon
				if(tmpLevel==1){
					tmpLevel=1.01;
				}
				goodP[i].levelUp(tmpLevel);	//level up
				goodP[i].restHp();			//
				enemyP[i].restHp();			//resst all info
				goodP[i].restInfo();		//
				enemyP[i].restInfo();		//
			}
			
			bp.backS.stop();
			bp.dispose();
			EndBattle eb=new EndBattle(bp,goodP,tmpPoke,trade); //turn off all information, transfer everything to EndGame class


	
			

		}
		if(goodP[0].getHp()<0 && goodP[1].getHp()<0 && goodP[2].getHp()<0 && goodP[3].getHp()<0){ //if player dies, reset info, do not level up

			bp.g.setVisible(true);
			bp.backS.stop();
			bp.myMap.setbattleTime(false);
			bp.gp.return_From_Battle();
			for(int i=0;i<4;i++){
				goodP[i].restHp();
				enemyP[i].restHp();
				goodP[i].restInfo();
				enemyP[i].restInfo();
			}
			bp.dispose();
		}
		

		g.drawImage(fightBG[gp.worldCount()],11,10,this);
		if(bp.enemyTurn==false){// if it is player's turn, player draws ontop of enemy
			for(int enemyNum=0;enemyNum<enemyP.length;enemyNum++){ //draw everything from pokemon class
				if(enemyP[enemyNum].getHp()>0){// oly draw the pokemon if they are alive 
					enemyP[enemyNum].DrawEnemyPokemon(g,this,bp.pokeEAttacking[enemyNum],goodP[bp.attackingGTeam[enemyNum]],enemyNum,bp);
				}
				else{
					bp.finishEAttacking(enemyNum);
				}
				
				
			}
			for(int goodNum=0;goodNum<goodP.length;goodNum++){
				if(goodP[goodNum].getHp()>0){
					goodP[goodNum].DrawGoodPokemon(g,this,bp.ButtonPoke[goodNum],bp.pokeAttacking[goodNum],enemyP[choosenOne],goodNum,bp);
				}
				else{
					bp.finishAttacking(goodNum);
				}
				
				
			}
		}
		else{//enemy draws ontop of player
			for(int goodNum=0;goodNum<goodP.length;goodNum++){
				if(goodP[goodNum].getHp()>0){
					goodP[goodNum].DrawGoodPokemon(g,this,bp.ButtonPoke[goodNum],bp.pokeAttacking[goodNum],enemyP[choosenOne],goodNum,bp);
				}
				else{
					bp.finishAttacking(goodNum);
				}
				
				
			}
			for(int enemyNum=0;enemyNum<enemyP.length;enemyNum++){
				if(enemyP[enemyNum].getHp()>0){
					enemyP[enemyNum].DrawEnemyPokemon(g,this,bp.pokeEAttacking[enemyNum],goodP[bp.attackingGTeam[enemyNum]],enemyNum,bp);
				}
				else{
					bp.finishEAttacking(enemyNum);
				}
				
				
			}
		}



		g.drawImage(backG,0,0,this);
		
		if((int)Math.round(309*((double)enemyP[choosenOne].getHp()/(double)enemyP[choosenOne].getOrigHp()))>=0){
			//draw health bar based on how much health enemy has 
			g.drawImage(healthBar,22,236,(int)Math.round(309*((double)enemyP[choosenOne].getHp()/(double)enemyP[choosenOne].getOrigHp())),7,this); 
		}
		g.drawImage(pokemonP[0],pokePPos1.x,pokePPos1.y,this);
		g.drawImage(pokemonP[1],pokePPos2.x,pokePPos2.y,this);
		g.drawImage(pokemonP[2],pokePPos3.x,pokePPos3.y,this);
		g.drawImage(pokemonP[3],pokePPos4.x,pokePPos4.y,this);
		

	 	g.setColor(Color.WHITE);
	 	
	 	g.drawString(enemyP[choosenOne].name(),80,229);
	 	
	 	//write the health of player pokemon
	 	if(goodP[0].getHp()>=0){
	 		g.drawString(""+goodP[0].getHp(),95,315);
	 	}
	 	else{
	 		g.drawString("DEAD",95,315); //if health goes below zero, consider him dead
	 	}
	 	g.drawString("/"+""+goodP[0].getOrigHp(),130,315);
	 
	 	if(goodP[1].getHp()>=0){
	 		g.drawString(""+goodP[1].getHp(),95,417);
	 	}
	 	else{
	 		g.drawString("DEAD",95,417);	
	 	}
	 	g.drawString("/"+""+goodP[1].getOrigHp(),130,417);
	 	
	 	if(goodP[2].getHp()>=0){
	 		g.drawString(""+goodP[2].getHp(),261,315);
	 	}
	 	else{
	 		g.drawString("DEAD",261,315);	
	 	}
	 	
	 	g.drawString("/"+""+goodP[2].getOrigHp(),296,315);
	 	
	 	if(goodP[3].getHp()>=0){
	 		g.drawString(""+goodP[3].getHp(),261,417);
	 	}
	 	else{
	 		g.drawString("DEAD",261,417);
	 	}
	 	
	 	g.drawString("/"+""+goodP[3].getOrigHp(),296,417);
	 	
	 	
		repaint();
	}

	public void actionPerformed(ActionEvent e){}
    // ------------ MouseListener ------------------------------------------
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}    
    public void mouseClicked(MouseEvent e) {
    	Point tmpPoint=new  Point(e.getX(),e.getY());
    	if(bp.usePot==false && bp.useRev==false){
	    	for(int test=0;test<enemyP.length;test++){
	    		//set target for player, player click on enemy pokemon image as set target
	    		Rectangle2D tmpRect=new Rectangle2D.Double(enemyP[test].getPoint().x-enemyP[test].getRectSize().x,enemyP[test].getPoint().y,enemyP[test].getRectSize().x,enemyP[test].getRectSize().y);
	    		if(tmpRect.contains(tmpPoint)&& enemyP[test].getHp()>0){
	    			choosenOne=test;
	    		}
	
	    	}
    	}
    	if(bp.usePot){//use potion to heal
    		
    		for(int test=0;test<enemyP.length;test++){
    			Rectangle2D tmpRect=healSquare.get(test);
    			if(tmpRect.contains(tmpPoint)&& goodP[test].getHp()>0 && goodP[test].getHp()<goodP[test].getOrigHp()){
    				goodP[test].heal();
    				bp.p.addPotion(-1);
					bp.pot-=1;
					bp.potion.setText("POTION: "+bp.pot);
	    		}
    		}
    		bp.usePot=false;
    	}
    	if (bp.useRev){//use revive 
    
       		for(int test=0;test<enemyP.length;test++){
    			Rectangle2D tmpRect=healSquare.get(test);
    			if(tmpRect.contains(tmpPoint)&& goodP[test].getHp()<0){
    				goodP[test].restHp();
    				bp.finishItem=true;
    				bp.p.addRevive(-1);
					bp.rev-=1;
					bp.revive.setText("REVIVE: "+bp.rev);
	    		}
    		}
    		bp.useRev=false;
    		}
    	
    }
    public void mousePressed(MouseEvent e){}
     
    // ---------- MouseMotionListener ------------------------------------------
    public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e) {}	
	
}
	