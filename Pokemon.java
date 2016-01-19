//Joey chen
//keep track of individual information
//move pokemon towards other pokemon to complete attack
//attacking movements take place in this class 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.util.*;
import java.awt.geom.*;
public class Pokemon{
	private String name,type,resist,weak;
	private int hp,attack,defence,origHp,walkNum,attackNum;
	private double level;
	
	Image selfS;			//
	Image selfH;			//all images for pokemon
	ArrayList<Image> selfW;	//
	ArrayList<Image> selfA;	//
	
	ArrayList<Object>allInfo;
	
	int width;	//
	int height;	//size of image, used to create rect
	Point pos;
	
	Point origPos; //set location seperate of pokemon point, used to return pokemon
	
	Point sizeRect; // dimensions of pokemon
	
	Rectangle2D pokeRect; //the rectange around pokemon
		
	int moveChar;	
	int nextMove;	
	int phaseNum;
	
	int def;//amount of defence

    public Pokemon(String name,int hp,String type,String resist,String weak,double level,int attack,int defence,int walkNum,int attackNum){
		//take all information and seperate from BattlePokeBackground
		
	    this.name=name;
	    this.hp=hp;
	    this.origHp=hp;
	    this.type=type;
	    this.resist=resist;
	    this.weak=weak;
	    this.level=level;
	    this.attack=attack;
	    this.defence=defence;
	    this.walkNum=walkNum;
	    this.attackNum=attackNum;
	      
	    pos=new Point(0,0);
	    origPos=new Point(0,0);
		selfW=new ArrayList<Image>();
		selfA=new ArrayList<Image>();
		
		moveChar=0;
		nextMove=0;
	   
	    phaseNum=0;
	    
	    def=0;
	   	//load all picture of individual pokemon
	    selfS=new ImageIcon("Images/Pokemon sprites/pokemon/"+name+"/"+name+"S.png").getImage();
	    
	    getSizes(name); //get dimensions of pokemon and make a rectange with dimensions
	    pokeRect=new Rectangle2D.Double(pos.x,pos.y,width,height);
	    sizeRect=new Point(height,width);
	    
	    //load all picture of individual pokemon
	    selfH=new ImageIcon("Images/Pokemon sprites/pokemon/"+name+"/"+name+"H.png").getImage();
	    
	    for(int w=0;w<walkNum;w++){
	    	selfW.add(new ImageIcon("Images/Pokemon sprites/pokemon/"+name+"/"+name+"W"+(w+1)+".png").getImage());
	    }
	 
	    for(int a=0;a<attackNum;a++){
	    	selfA.add(new ImageIcon("Images/Pokemon sprites/pokemon/"+name+"/"+name+"A"+(a+1)+".png").getImage());
	    }


	}
	public void getSizes(String name){
		//get the dimension of pokemon
	    try{
		    BufferedImage bimg = ImageIO.read(new File("Images/Pokemon sprites/pokemon/"+name+"/"+name+"S.png"));
		    height=bimg.getHeight();
		    width=bimg.getWidth();
	    } 
		catch(Exception exception){
		    System.out.println("OOPS ... " + exception);
		}
	}
	public void restInfo(){
		//reset all the information 
		moveChar=0;
		nextMove=0;
	    phaseNum=0;
	}
	public String name(){
		return name;
	} 
	public void levelUp(double a){
		//level up pokemon by difference betwwen levels
	
		origHp=(int)Math.round(origHp*a);

		attack =(int)Math.round(attack*a);

		defence=(int)Math.round(defence*a);
		
		level=Math.round(level*a);
		
	}
	public int getOrigHp(){
		return origHp;
	}
	public int getHp(){
		return hp;
	}
	public void restHp(){
		hp=origHp;
	}
	public void heal(){
		//heal the pokemon by set amount based on health
		hp+=(int)Math.round(hp+(origHp*1.25));
		if(hp>origHp){
			hp=origHp;
		}
	}
	public void getDamage(int amount,String tmpType){
		//calculate damage based on weakness and resistance 
		amount-=def;
		if(tmpType.equals(resist)){//if the pokemon attacking's type is what targeted pokemon's resistance, weaken attack
			amount=(int)Math.round(amount*.85);
		}
		if(tmpType.equals(weak)){//if the pokemon attacking's type is what targeted pokemon's weakness, increase attack
			amount=(int)Math.round(amount*1.15);
		}
		hp-=amount;
		def=0;
	}
	public String getType(){
		return type;
	}
	public String getResist(){
		return resist;
	}

	public String getWeak(){
		return weak;
		
	}
	public double getLevel(){
		return level;
	}
	public int getAttack(){
		return attack;
	}
	public int getDefence(){
		return defence;
	}
	
	public int getWalkNum(){
		return walkNum;
	}
	public int getAttackNum(){
		return attackNum;
	}
	public Image getImageStand(){
		return selfS;
	}
	public Image getImageHurt(){
		return selfH;
	}
	public ArrayList<Image> getImageWalk(){
		return selfW;
	}
	public ArrayList<Image> getImageAttack(){
		return selfA;
	}
	public Rectangle2D getRect(){
		return pokeRect;
	}
	public Point getPoint(){
		return pos;
	}
	public Point getOPoint(){
		return origPos;
	}
	public void set_Pos_Orig(){
		pos=new Point(origPos.x,origPos.y);
	}
	public Point getRectSize(){
		return sizeRect;	
	}
	public void setPoint(Point p){
		pos.x=p.x;
		pos.y=p.y;
		origPos=p;
	}
	public void movePoint(Point p){
		pos.x+=p.x;
		pos.y+=p.y;
	}
	public void DrawEnemyPokemon(Graphics g, JPanel panel,boolean finishA,Pokemon tar,int pokeNum,BattlePokemon bp){
		//create new rectangles based on location
		pokeRect=new Rectangle2D.Double(pos.x,pos.y,-width,height);
		if (finishA){//if the enemy is not attack, draw him standing
			set_Pos_Orig();
			phaseNum=0; //reset info
			moveChar=0;
			g.drawImage(selfS,pos.x,pos.y,-width,height,panel);
		}
		else{
			if(tar.getHp()>0){//when he is attacking, create all movements
				activateBTBattle(g,panel,tar,bp,pokeNum);
			}
			if(tar.getHp()<0){ //if the target suddenly dies midmovement, bring pokemon back to original position  
				set_Pos_Orig();
				phaseNum=0;
				moveChar=0;
				bp.finishEAttacking(pokeNum);
				g.drawImage(selfS,pos.x,pos.y,-width,height,panel);

		
			}
		}
		
	
		

	
		
	}
	public void DrawGoodPokemon(Graphics g, JPanel panel,String a_or_d,boolean finishA,Pokemon tar,int pokeNum,BattlePokemon bp){
		if(a_or_d=="Defence" || a_or_d=="" || finishA){ //if it's the players turn and he chose to atack or defend
			set_Pos_Orig();
			moveChar=0;//set all position to original
			phaseNum=0;
			if(a_or_d=="Defence"){
				bp.finishAttacking(pokeNum);//considerd his turn over when defence was chosen
			}
			g.drawImage(selfS,pos.x,pos.y,panel); //draw him standing
		}
		
		if(a_or_d=="Attack" && !finishA && tar.getHp()>0){//if pokemon is attacking, draw animation
			activateGTBattle(g,panel,tar,bp,pokeNum);
			
		}
		if(a_or_d=="Attack" && !finishA && tar.getHp()<0){//if the target suddenly dies midmovement, bring pokemon back to original position   
			set_Pos_Orig();
			moveChar=0;
			phaseNum=0;
			bp.finishAttacking(pokeNum);
			g.drawImage(selfS,pos.x,pos.y,panel);
		}
	
		
		
	}
	//activateGTBattle and activateBTBattle do the same thing, only activateBTBattle draws images in reverse
	public void activateGTBattle(Graphics g, JPanel panel,Pokemon tar,BattlePokemon bp, int pokeNum){
		//perform all animations and battle calculation 
		double dist =  Math.pow(Math.pow((double)pos.x-(double)tar.getOPoint().x,2)+Math.pow((double)pos.y-(double)tar.getOPoint().y,2),.5);//calculate distance between player pokemon and enemy pokemon 
		int numOfAttackMoves =Math.round(tar.getRectSize().x/selfA.size()); //number of steps for hitting animation 
		
		boolean isApporoaching =  dist>0 && pos.x-tar.getPoint().x>0 && phaseNum==0;
		nextMove+=1;
		int finishPos=tar.getPoint().x-(selfA.size()*numOfAttackMoves); //if the player pokemon has not come in contact with enmy, walk toward enemy 
		if (isApporoaching){
			delay(5000000);
			MoveCharacter(tar.getPoint());
			g.drawImage(selfW.get(moveChar),pos.x,pos.y,panel);	
			if(nextMove==30){
				moveChar+=1;
				nextMove=0;
			}
			if(moveChar==selfW.size()){
				moveChar=0;
			}
		}
		if(dist==0){
			moveChar=0;
		}

		if(!isApporoaching && finishPos!=pos.x && phaseNum<2){ //if player pokemon collides with enemy pokemon, draw hitting animation 
			phaseNum=1;
			pos.x-=numOfAttackMoves;
			tar.getDamage(Math.round(attack/selfA.size()),type);//calculated damage taken
			g.drawImage(selfA.get(moveChar),pos.x,pos.y,panel);
			delay(900000000);
			

			moveChar+=1;

			if(moveChar==selfA.size()){
				moveChar=0;
			}
		}
		if(finishPos==pos.x ){
			moveChar=0;
		}
		if(finishPos==pos.x || phaseNum==2){ //after hitting return to original positon  
			phaseNum=2;
			delay(5000000);
			MoveCharacter(origPos);
			g.drawImage(selfW.get(moveChar),pos.x,pos.y,panel);	
		
			if(nextMove==30){
				moveChar+=1;
				nextMove=0;
			}
			if(moveChar==selfW.size()){
				moveChar=0;
			}
		}
		if(pos.equals(origPos)){ //when you have return to original positon, consider this pokemonfinished for this round,not battle
			bp.finishAttacking(pokeNum);
		}
			

	}
public void activateBTBattle(Graphics g, JPanel panel,Pokemon tar,BattlePokemon bp, int pokeNum){
		double dist =  Math.pow(Math.pow((double)pos.x-(double)tar.getOPoint().x,2)+Math.pow((double)pos.y-(double)tar.getOPoint().y,2),.5);
		int numOfAttackMoves =Math.round(tar.getRectSize().x/selfA.size());
		nextMove+=1;
		boolean isApporoaching =  dist>0 && pos.x-tar.getPoint().x<0 && phaseNum==0;
		
		int finishPos=tar.getPoint().x+(selfA.size()*numOfAttackMoves); 
			
		if (isApporoaching){
			delay(5000000);
			MoveCharacter(tar.getPoint());
			g.drawImage(selfW.get(moveChar),pos.x,pos.y,-width,height,panel);	
			if(nextMove==30){
				moveChar+=1;
				nextMove=0;
			}
			if(moveChar==selfW.size()){
				moveChar=0;
			}
		}
		if(dist==0){
			moveChar=0;
		}

		if(!isApporoaching && finishPos!=pos.x && phaseNum<2){
			phaseNum=1;
			pos.x+=numOfAttackMoves;
			tar.getDamage(Math.round(attack/selfA.size()),type);
			g.drawImage(selfA.get(moveChar),pos.x,pos.y,-width,height,panel);
			delay(100000000);
			
			moveChar+=1;
			if(moveChar==selfA.size()){
				moveChar=0;
			}
		}
		if(finishPos==pos.x ){
			moveChar=0;
		}
		if(finishPos==pos.x || phaseNum==2){
			phaseNum=2;
			delay(5000000);
			MoveCharacter(origPos);
			g.drawImage(selfW.get(moveChar),pos.x,pos.y,-width,height,panel);	
		
			if(nextMove==30){
				moveChar+=1;
				nextMove=0;
			}
			if(moveChar==selfW.size()){
				moveChar=0;
			}
		}
		if(pos.equals(origPos)){
			phaseNum=0;
			moveChar=0;
			bp.finishEAttacking(pokeNum);
		}
		
	}
	public void MoveCharacter(Point tarPos){
		if(pos.x>tarPos.x){
			pos.x-=1;
		}
		if(pos.x<tarPos.x){
			pos.x+=1;
		}
		if(pos.y>tarPos.y){
			pos.y-=1;
		}
		if(pos.y<tarPos.y){
			pos.y+=1;
		}
	}
	public void delay(long delayFac){
		int tmp=0;
		for(long i =0;i<delayFac;i++){
			tmp++;
		}
		
	}

    


}