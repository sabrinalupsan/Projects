package eu.ase.jrobot;
import robocode.*;
import java.lang.String;

public class MySixthRobot extends Robot
{
	public void run() 
	{
		int c=90;
		for(int i=0;;i++)
		{
			if(i%2==0) 
			{
				out.println("Travelling anticlockwise");
				c=-c;
			}
			else
			{
				out.println("Travelling clockwise");
				c=90;
			}
			ahead(150);
			turnRight(c);
			ahead(150);
			turnRight(c);
			ahead(150);
			turnRight(c);
			ahead(150);
			turnRight(2*c);
		}
	}
	

}
