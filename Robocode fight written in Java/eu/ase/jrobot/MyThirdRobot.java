package eu.ase.jrobot;
import robocode.*;

public class MyThirdRobot extends Robot
{
	public void run() 
	{
		int ok=0;
		while(ok<3) 
		{
			turnRight(90);
			ahead(150);
			ahead(150);
			turnRight(90);
			ahead(150);
			turnLeft(90);
			ahead(150);
			turnRight(90);
			ahead(150);
			ok++;

		}
	}
	
}
