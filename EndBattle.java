//Joey Chen
//screen after completing battle
//transactions such as trading or capturing  occuring in this class
//also brings player back to game
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
public class EndBattle extends JFrame implements ActionListener{
	AudioClip vict;
	JButton pokeButton1;	//
	JButton pokeButton2;	//
	JButton pokeButton3;	//used to select pokemon for trading
	JButton pokeButton4;	//
	JButton pokeButtonC;//capture button
	
	JButton skip; //skip button
		
	TradePanel game;
	BattlePokemon bp;
	Pokemon[]goodP;
	Pokemon enemyP;

	boolean trade;
    public EndBattle(BattlePokemon bp,Pokemon[]goodP,Pokemon enemyP,boolean trade) {
    	super("CONGRATULATION");
    	setSize(410,330);
    	setLayout(null);
    	
    	game=new TradePanel(this,goodP,enemyP,trade);
    	game.setSize(410,330);
    	game.setLocation(0,0);
    	game.setLayout(null);
    	add(game);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    
    	setVisible(true);
    	
    	this.bp=bp;
    	this.goodP=goodP;
    	this.enemyP=enemyP;
  
    	this.trade=trade;
    	vict =Applet.newAudioClip(getClass().getResource("108-victory-vs-wild-pokemon-.wav"));
    	vict.play();
    

    	//all the buttons
		pokeButton1=new JButton(goodP[0].name());
		pokeButton1.setBounds(11,20,80,34);
		pokeButton1.setBackground(Color.RED);
		pokeButton1.addActionListener(this);
		game.add(pokeButton1);
		
		pokeButton2=new JButton(goodP[1].name());
		pokeButton2.setBounds(109,20,80,34);
		pokeButton2.setBackground(Color.RED);
		pokeButton2.addActionListener(this);
		game.add(pokeButton2);

		pokeButton3=new JButton(goodP[2].name());
		pokeButton3.setBounds(211,20,80,34);
		pokeButton3.setBackground(Color.RED);
		pokeButton3.addActionListener(this);
		game.add(pokeButton3);
		
		pokeButton4 =new JButton(goodP[3].name());
		pokeButton4.setBounds(310,20,80,34);
		pokeButton4.setBackground(Color.RED);
		pokeButton4.addActionListener(this);
		game.add(pokeButton4);
		if (trade==true && !bp.gp.catchThemAll.contains(enemyP.name())){ //capture button only appears when the trading is allowed 

			pokeButtonC=new JButton("CAPTURE");
			pokeButtonC.setBounds(310,145,80,34);
			pokeButtonC.setBackground(Color.ORANGE);
			pokeButtonC.addActionListener(this);
			game.add(pokeButtonC);
		}
		

		skip =new JButton("SKIP");
		skip.setBounds(310,195,80,34);
		skip.setBackground(Color.BLUE);
		skip.addActionListener(this);
		game.add(skip);
		

    	
    }
    public void actionPerformed(ActionEvent e){
    	Object source=e.getSource();
    	enemyP.restHp();
    	if(source==skip){ //leaves endBattle to continue game
    		bp.g.setVisible(true);
			bp.myMap.setbattleTime(false);
			bp.gp.return_From_Battle(); //reset information and bring player back to world
			vict.stop();
			dispose();
    	}
    	//All buttons are on when trade is true
    	//pokemon buttons; trade with whatever pokemon you select
    	//capture button takes the enemy pokemon and adds to counter
  		//all buttons return player to world 
    	if(source==pokeButtonC && trade==true && !bp.gp.catchThemAll.contains(enemyP.name())){
    
    		bp.gp.catchThemAll.add(enemyP.name());
    		bp.g.setVisible(true);
			bp.myMap.setbattleTime(false);
			bp.gp.return_From_Battle();
			vict.stop();
			dispose();
    	}
    	if(source==pokeButton1 && trade==true){
    
    		goodP[0]=enemyP;
    		bp.g.setVisible(true);
			bp.myMap.setbattleTime(false);
			bp.gp.return_From_Battle();
			vict.stop();
			dispose();
    		
    	}
    	if(source==pokeButton2 && trade==true){
    		goodP[1]=enemyP;
    		bp.g.setVisible(true);
			bp.myMap.setbattleTime(false);
			bp.gp.return_From_Battle();
			vict.stop();
			dispose();
    	}
    	if(source==pokeButton3 && trade==true){
    		goodP[2]=enemyP;  
    		bp.g.setVisible(true);
			bp.myMap.setbattleTime(false);
			bp.gp.return_From_Battle();
			vict.stop();
			dispose();  		
    	}
    	if(source==pokeButton4 && trade==true){
    		goodP[3]=enemyP;
    		bp.g.setVisible(true);
			bp.myMap.setbattleTime(false);
			bp.gp.return_From_Battle();
			vict.stop();
			dispose();    	
    	}
    }
    public static void main(String[]args){
		Pokemon[]goodTeam=new Pokemon[4];
		Pokemon[]enemyTeam=new Pokemon[4];
		PokeAthon g=new PokeAthon();
		GamePanel gp=new GamePanel(g);
		Trainer p=new Trainer("",0,0);
		Map myMap=new Map(0);
		BattlePokeBackground tt=new BattlePokeBackground(p,g,gp,myMap);
		
		
		BattlePokemon bp=new BattlePokemon(goodTeam,enemyTeam,p,g,gp,myMap,tt);
		Pokemon[]goodP = new Pokemon[4];
		Pokemon[]enemyP = new Pokemon[4];
		int c=0;
		boolean trade = false;
    	JFrame.setDefaultLookAndFeelDecorated(true);
    	EndBattle game= new EndBattle(bp,goodP,enemyP[1],trade);
    	game.setVisible(true);
    }
}
class TradePanel extends JPanel implements ActionListener{
	EndBattle eb;
	TradePanel game;
	BattlePokemon bp;
	Pokemon[]goodP;
	Pokemon enemyP;
	Image backEndGround;
	boolean trade;
	public TradePanel(EndBattle eb,Pokemon[]goodP,Pokemon enemyP,Boolean trade){
	
		super();
		setFocusable(true);
		this.goodP=goodP;
		this.enemyP=enemyP;
		this.trade=trade;
		backEndGround=new ImageIcon("Images/EndBSBG.png").getImage(); 
	
	}
	public void paintComponent(Graphics g){
		g.drawImage(backEndGround,0,0,this);
		g.setColor(Color.BLACK);
		//print all stats of pokemon under button(can't put it in for loop since positions are ll different)
		g.drawString(goodP[0].name(),16,70);
		g.drawString("Hp:"+""+goodP[0].getHp(),16,85);
		g.drawString("ATT:"+""+goodP[0].getAttack(),16,100);
		g.drawString("DEF:"+""+goodP[0].getDefence(),16,115);
		
		g.drawString(goodP[1].name(),114,70);
		g.drawString("Hp:"+""+goodP[1].getHp(),114,85);
		g.drawString("ATT:"+""+goodP[1].getAttack(),114,100);
		g.drawString("DEF:"+""+goodP[1].getDefence(),114,115);
		
		g.drawString(goodP[2].name(),216,70);
		g.drawString("Hp:"+""+goodP[2].getHp(),216,85);
		g.drawString("ATT:"+""+goodP[2].getAttack(),216,100);
		g.drawString("DEF:"+""+goodP[2].getDefence(),216,115);
		
		g.drawString(goodP[3].name(),315,70);
		g.drawString("Hp:"+""+goodP[3].getHp(),315,85);
		g.drawString("ATT:"+""+goodP[3].getAttack(),315,100);
		g.drawString("DEF:"+""+goodP[3].getDefence(),315,115);
		
		if(trade==true){//if trade is true, print information under under enemy pokemon picture 
			g.drawImage(enemyP.selfS,170,145,this);
			g.drawString(enemyP.name(),170,235);
			g.drawString("Hp:"+""+enemyP.getOrigHp(),170,250);
			g.drawString("ATT:"+""+enemyP.getAttack(),170,265);
			g.drawString("DEF:"+""+enemyP.getDefence(),170,280);
			
		}
		

	
		
	}
	
	public void actionPerformed(ActionEvent e){}
}