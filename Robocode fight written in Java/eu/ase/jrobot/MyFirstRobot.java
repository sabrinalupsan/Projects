package eu.ase.jrobot;
import robocode.*;

public class MyFirstRobot extends Robot
{
	public void run() 
	{
		boolean ok=true;
		while(ok) 
		{
			ahead(150);
			turnRight(90);
			ahead(150);
			turnRight(90);
			ahead(150);
			turnRight(90);
			ahead(150);
			ok=false;

		}
	}
	
}
