package eu.ase.jrobot;
import robocode.*;

public class MyFourthRobot extends Robot
{
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
		}
	}
}
