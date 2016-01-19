//joey Chen 
//background timer
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch{

	static int interval;
	static int min;
	static Timer timer;
	static boolean freeze=false;
	public Stopwatch(){
		runTimer();
	}
	
	public static void runTimer() {
	
	    
	    min =2; //minutes
	    int delay = 1000;
	    int period = 1000;
	    timer = new Timer();
	    interval = 59; //seconds
	    
	    timer.scheduleAtFixedRate(new TimerTask() {
	
	        public void run() {
	        	if(!freeze){//only count down when freeze(from pokeAthon, freeze pauses game) is false
	        		setInterval();
	        	}

	        }
	    }, delay, period);
	}
	public void cFreeze(boolean f){
		freeze=f;
	}
	public String getMin(){
		return ""+min;
	}
	public String getSec(){
		return ""+interval;
	}
	
	private static final int setInterval() {
	    if (interval == 0){
		    min-=1;
			interval=60;
	    }
	
	    return --interval;
	}
}