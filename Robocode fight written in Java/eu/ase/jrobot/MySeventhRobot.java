package eu.ase.jrobot;
import robocode.*;

public class MySeventhRobot extends Robot
{
	public int x = 0;
	public void run() 
	{
		moveInSquare(150);
	}
	
	public void moveInSquare(int lengthOfSide)
	{
		while(true)
		{
			ahead(lengthOfSide);
			turnRight(90);
			ahead(lengthOfSide);
			turnRight(90);
			ahead(lengthOfSide);
			turnRight(90);
			ahead(lengthOfSide);
			turnRight(90);
			this.x=x+1;
		}
	}
	public void onRoundEnded(RoundEndedEvent evnt)
	{
		out.println(this.x);
	}
}
