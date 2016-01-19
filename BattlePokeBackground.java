//Joey Chen
//load all information for individual pokemon from text file 
//create enemy team from randomly selected pokemon
//sent information to battlePokemon 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.util.*;
import java.awt.geom.*;
public class BattlePokeBackground{
	static String[]txtName=new String[]{"GrassPoke.txt","WaterPoke.txt","SandPoke.txt","FirePoke.txt","SkyPoke.txt"}; //all the different enemy text files
	static Pokemon[][]attributes=new Pokemon[5][5]; // the list the enemys will be stored in
	
	static Pokemon[]goodTeam=new Pokemon[4]; //good player pokemon list
	static Pokemon[]enemyTeam=new Pokemon[4]; //enemy pokemon list

	Trainer p;		// player
	PokeAthon g;	//all classes in involved with battling
	GamePanel gp;	// main program
	Map myMap;		// the map
	

	static Point[]goodTeamPoints=new Point[]{new Point(293,125),new Point(257,90),new Point(257,160),new Point(225,125)};		//the points where pokemon stand
	static Point[]enemyTeamPoints=new Point[]{new Point(67,125),new Point(103,90),new Point(103,160),new Point(135,125),};		//
		
	public BattlePokeBackground(Trainer p,PokeAthon g,GamePanel gp,Map myMap){
		wildLoadAll();
		
		this.p=p;
		this.g=g;
		this.gp=gp;
		this.myMap=myMap;

	}
	
	public static void wildLoadAll(){//laod all the wild pokemon information
		try{
			for (int a=0;a<txtName.length;a++){
				BufferedReader poke=new BufferedReader(new FileReader(txtName[a])); //read all the text files
				for(int b=0;b<5;b++){
					String pokeLine=poke.readLine(); //read each line at a time
					
					String[]unitStats=pokeLine.split(","); //divide line by comas
					
					String name = unitStats[0];
				
					int hp=Integer.parseInt(unitStats[1]);

					String type=unitStats[2];				 
				
					String resist=unitStats[3];				
	
					String weak=unitStats[4];
				
					int tmpLevel=Integer.parseInt(unitStats[5]);
					double level =(double)tmpLevel;					
					
					int attack=Integer.parseInt(unitStats[6]);
					
					int defence=Integer.parseInt(unitStats[7]);
			
					int walkNum=Integer.parseInt(unitStats[8]);
					
					int attackNum=Integer.parseInt(unitStats[9]);
	
					attributes[a][b]=new Pokemon(name,hp,type,resist,weak,level,attack,defence,walkNum,attackNum); //transfer all divided information to indvidual pokemon class
					
						
				}
				poke.close();
			}

    	}
    	catch(Exception exception){
		    System.out.println("OOPS ... " + exception);
			}
		}
	public void fightMode(int w){ // activate the actually battling by calling battle pokemon class
	
	
		for(int i=0;i<4;i++){
			goodTeam[i].setPoint(goodTeamPoints[i]); //set all the points for good pokemon 
		}
		ArrayList<Integer>allInfo=new ArrayList<Integer>();
		for(int j=0;j<4;j++){
			int rand=(int)(Math.random()*5);
			if(allInfo.contains(rand)){ //creates a duplicate of pokemon that is already being used
				Pokemon tmp=new Pokemon(attributes[w][rand].name(),attributes[w][rand].getOrigHp(),attributes[w][rand].getType(),attributes[w][rand].getResist(),attributes[w][rand].getWeak(),attributes[w][rand].getLevel(),attributes[w][rand].getAttack(),attributes[w][rand].getDefence(),attributes[w][rand].getWalkNum(),attributes[w][rand].getAttackNum());
				enemyTeam[j]=tmp;
			}
			else{
				enemyTeam[j]=attributes[w][rand];
			}
			
			allInfo.add(rand); // store all random number information
			enemyTeam[j].setPoint(enemyTeamPoints[j]); //set all enemy points
		}


		BattlePokemon bp =new BattlePokemon(goodTeam,enemyTeam,p,g,gp,myMap,this); // call battle pokemon
		bp.setVisible(true);
	}
	public void teamLoadAll(){ //load all good team pokemon, only once 
		try{
			BufferedReader pokeGood=new BufferedReader(new FileReader("TeamPoke.txt"));
			for(int x=0;x<4;x++){
					//System.out.println(x);
					String pokeLine=pokeGood.readLine();
					
					String[]unitStats=pokeLine.split(",");
					
					String name = unitStats[0];
					//System.out.println(name); 
	
					int hp=Integer.parseInt(unitStats[1]);
					//System.out.println(hp); 
			
					String type=unitStats[2];
					//System.out.println(type); 
				
					String resist=unitStats[3];
					//System.out.println(resist); 
	
					String weak=unitStats[4];
					//System.out.println(weak); 

					int level=Integer.parseInt(unitStats[5]);
					//System.out.println(level); 
					
					int attack=Integer.parseInt(unitStats[6]);
					//System.out.println(attack); 
					
					int defence=Integer.parseInt(unitStats[7]);
					//System.out.println(defence); 

					int walkNum=Integer.parseInt(unitStats[8]);
					//System.out.println(walkNum); 
					
					int attackNum=Integer.parseInt(unitStats[9]);
					//System.out.println(attackNum+"car"); 
	
					goodTeam[x]=new Pokemon(name,hp,type,resist,weak,level,attack,defence,walkNum,attackNum); //still add the to individual pokemon classes
			}
			pokeGood.close();
			
		}
    	catch(Exception exception){
		    System.out.println("OOPS ... " + exception);
			}
	}

	
	public static void main(String[]args){
	}
}