package org.usfirst.frc.team4361.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;

public class Autonomous{
	
	double diameter;
	double circumference;
	double distanceNeeded;
	
	boolean isEnc, hasRun;
	int runNum, lEncNum, rEncNum, large;
	Timer timer, timerSpeed;
	Digit digit;
	
	Drive left, right;
	Shooter shoot;
	
	Encoder lEnc, rEnc;
	
	public Autonomous(Drive left, Drive right, Shooter shoot)
	{
		diameter = 8.4;
		circumference = diameter * Math.PI;
		
		isEnc = false;
		lEncNum = 0;
		rEncNum = 0;
		large = 0;
		hasRun = false;
		runNum = 0;
		timer = new Timer();
		timerSpeed = new Timer();
		digit = new Digit();
		
		this.left = left;
		this.right = right;
		this.shoot = shoot;
	}
	public Autonomous(Drive left, Drive right, Shooter shoot, Encoder lEnc, Encoder rEnc)
	{
		this(left, right, shoot);
		this.lEnc = lEnc;
		this.rEnc = rEnc;
	}
	
	public void defaultGoToObstacle()
	{
		if(runNum == 0)
			goDistance(88, .9);
	}
	
	public void portcullis()
	{
		if(runNum == 0)
			goDistance(88, .9);
	}
	
	public void chevelDeFrise()
	{
		if(runNum == 0)
			goDistance(88, .9);
	}
	
	public void moat()
	{
		if(runNum == 0)
			goDistance(88, .9);
		if(runNum == 1)
			goDistance(42, .8);
	}
	
	public void ramparts()
	{
		if(runNum == 0)
			goDistance(88, .9);
		if(runNum == 1)
			goDistance(40, .4);
	}
	
	public void drawbridge()
	{
		if(runNum == 0)
			goDistance(88, .9);
	}
	
	public void sallyPort()
	{
		if(runNum == 0)
			goDistance(88, .9);
	}
	
	public void rockWall()
	{
		if(runNum == 0)
			goDistance(88, .9);
		if(runNum == 1)
			goDistance(38, .8);
	}
	
	public void roughTerrain()
	{
		if(runNum == 0)
			goDistance(88, .9);
		if(runNum == 1)
			goDistance(40, .4);
	}
	private void goDistance(double dist, double speed)
	{
		if(!hasRun)
		{
			right.drive(-speed);
			left.drive(speed);
		}
		
		if(isEnc)
		{
			if(!hasRun)
			{
				timer.start();
				lEnc.reset();
				rEnc.reset();
				hasRun = true;
			}
			if(lEncNum!=lEnc.getRaw()||rEncNum!=rEnc.getRaw())
			{
				timer.reset();
			}
			if(timer.get()==1)
			{
				isEnc = false;
			}
			
			large = Math.max(lEnc.getRaw(), rEnc.getRaw());
			if(large*circumference>dist)
			{
				right.drive(0);
				left.drive(0);
				
				hasRun = false;
				runNum++;
			}
		}
		
		//For when the encoders break
		if(!isEnc)
		{
			double timeNeeded = (dist/circumference) / ((speed * 5310) / (60*12.75)) + 1;
			if(!hasRun)
			{
				timer.start();
				hasRun = true;
			}
			
			if(timer.get()>timeNeeded)
			{
				right.drive(0);
				left.drive(0);
				
				hasRun = false;
				runNum++;
				timeNeeded = 0;
				
				timer.stop();
				timer.reset();
			}
		}
	}
}
