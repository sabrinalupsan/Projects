package eu.ase.jrobot;
import robocode.*;
import java.lang.String;

public class MyFifthRobot extends Robot
{
	public int x=0;
	public void run() 
	{
		while(true)
		{
			ahead(100);
			turnRight(90);
			ahead(100);
			turnRight(90);
			ahead(100);
			turnRight(90);
			ahead(100);
			turnRight(90);
			this.x++;
		}
	}
	public void onRoundEnded(RoundEndedEvent evnt)
	{
		out.println(this.x);
	}
}
