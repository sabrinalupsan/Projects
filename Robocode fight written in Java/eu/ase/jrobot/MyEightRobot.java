package eu.ase.jrobot;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class MyEightRobot extends Robot
{
	private boolean aggressive;
	private boolean scanForRobots;
	private double noDegreesRadar;
	private double noDegreesGun;
	private int lengthOfSide;
	public void run() 
	{
		this.aggressive=true;
		this.scanForRobots=false;
		this.lengthOfSide=139;
		moveInSquare(lengthOfSide, aggressive, scanForRobots);
		this.noDegreesGun = 100;
		this.noDegreesRadar=70;
		turnGunLeft(noDegreesGun);
		turnRadarLeft(noDegreesRadar);
	}
	
	public void moveInSquare(int lengthOfSide, boolean aggressive, boolean scanForRobots)
	{
		if(aggressive==true)
		{
				if(scanForRobots==true)
						for(int i=0; i<10; i++)
						{
							ahead(lengthOfSide);
							fire(2);
							scan();
							turnRight(90);
							ahead(lengthOfSide);
							fire(2);
							scan();
							turnRight(90);
							ahead(lengthOfSide);
							fire(2);
							scan();
							turnRight(90);
							ahead(lengthOfSide);
							fire(2);
							scan();
							turnRight(90);
						}
				else
						for(int i=0; i<10; i++)
						{
							ahead(lengthOfSide);
							fire(2);
							turnRight(90);
							ahead(lengthOfSide);
							fire(2);
							turnRight(90);
							ahead(lengthOfSide);
							fire(2);
							turnRight(90);
							ahead(lengthOfSide);
							fire(2);
							turnRight(90);
						}
				}
			else
				if(scanForRobots==true)
							for(int i=0; i<10; i++)
							{
								ahead(lengthOfSide);
								scan();
								turnRight(90);
								ahead(lengthOfSide);
								scan();
								turnRight(90);
								ahead(lengthOfSide);
								scan();
								turnRight(90);
								ahead(lengthOfSide);
								scan();
								turnRight(90);
							}
				else
						for(int i=0; i<10; i++)
							{
								ahead(lengthOfSide);
								turnRight(90);
								ahead(lengthOfSide);
								turnRight(90);
								ahead(lengthOfSide);
								turnRight(90);
								ahead(lengthOfSide);
								turnRight(90);
							}
	}
	public void onScannedRobot(ScannedRobotEvent e)
	{
		scan();
		out.println("The name is: "+e.getName()+", the energy is: "
			+e.getEnergy()+" and the distance is: "+e.getDistance());
		fire(1);
	}
}
