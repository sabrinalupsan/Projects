package eu.ase.jrobot;
import robocode.*;

public class MySecondRobot extends Robot
{
	public void run() 
	{
		for(int i=0; i<10; i++)
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
