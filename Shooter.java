package org.usfirst.frc.team4361.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;

public class Shooter {
	
	private Timer timer;
	private boolean start;

	private CANTalon[] SHT, LIFT, IND;
	
	private DigitalInput[] LTS;
	
	private int shooterPos, goPos;
	private boolean move;

	public Shooter(CANTalon[] SHT, CANTalon[] LIFT, CANTalon[] IND, DigitalInput[] LTS)
	{
		this.SHT = SHT;
		this.LIFT = LIFT;
		this.IND = IND;
		this.LTS = LTS;
		timer = new Timer();
		start = false;
		move = true;
		shooterPos = 0;
		goPos = 0;
		
		for(int i = 0; i < this.LTS.length; i++)
		{
			if(LTS[i].get())
			{
				shooterPos = i;
				goPos = i;
			}
		}
	}

	public void lift(double val, boolean mid) 
	{
		if(val<-.5&&move)
		{
			goPos++;
			if(mid)
				goPos++;
			move = false;
			timer.start();
			if(shooterPos > 2)
			{
				shooterPos = 2;
				move = true;
				timer.stop();
				timer.reset();
			}
			else
			{
				LIFT[0].set(-1);
				LIFT[1].set(1);
			}
		}
		else if(val>.5&&move)
		{
			goPos--;
			if(mid)
				goPos--;
			move = false;
			timer.start();
			if(shooterPos < 0)
			{
				shooterPos = 0;
				move = true;
				timer.stop();
				timer.reset();
			}
			else
			{
				LIFT[0].set(1);
				LIFT[1].set(-1);
			}
		}
		
		if(timer.get()>=10)
		{
			LIFT[0].set(0);
			LIFT[1].set(0);
			move = true;
			timer.stop();
			timer.reset();
		}
		
		if(shooterPos!=goPos&&!move)
		{
			if(shooterPos == 0)
			{
				if(LTS[1].get() && goPos == 1)
				{
					shooterPos = 1;
					LIFT[0].set(0);
					LIFT[1].set(0);
					move = true;
					timer.stop();
					timer.reset();
				}
				else if(LTS[2].get())
				{
					shooterPos = 2;
					LIFT[0].set(0);
					LIFT[1].set(0);
					move = true;
					timer.stop();
					timer.reset();
				}
			}
			if(shooterPos == 1)
			{
				if(LTS[2].get())
				{
					shooterPos = 2;
					LIFT[0].set(0);
					LIFT[1].set(0);
					move = true;
					timer.stop();
					timer.reset();
				}
				else if(LTS[0].get())
				{
					shooterPos = 0;
					LIFT[0].set(0);
					LIFT[1].set(0);
					move = true;
					timer.stop();
					timer.reset();
				}
			}
			if(shooterPos == 2)
			{
				if(LTS[1].get() && goPos == 1)
				{
					shooterPos = 1;
					LIFT[0].set(0);
					LIFT[1].set(0);
					move = true;
					timer.stop();
					timer.reset();
				}
				else if(LTS[0].get())
				{
					shooterPos = 0;
					LIFT[0].set(0);
					LIFT[1].set(0);
					move = true;
					timer.stop();
					timer.reset();
				}
			}
		}
	}
	
	public void liftSim(double val)
	{
		LIFT[0].set(-val);
		LIFT[1].set(val);
	}

	public void shoot(boolean in, boolean out) 
	{
		if (in) 
		{
			SHT[0].set(1);
			SHT[1].set(-1);
		}
		
		// check direction
		else if (out) 
		{
			SHT[0].set(-1);
			SHT[1].set(1);

		}
		
		else 
		{
			for (int i = 0; i < SHT.length; i++) 
			{
				SHT[i].set(0);
			}
		}
	}

	public void index(boolean in, boolean out) 
	{
		if (in)
		{
			IND[0].set(-.4);
		} 
		else if (out) 
		{
			IND[0].set(.4);
		} 
		else 
		{
			IND[0].set(0);
		}
	}
	
	public boolean indexAuto()
	{
		if(start)
		{
			IND[0].set(-.4);
		}
		if(LTS[0].get())
		{
			IND[0].set(.4);
			start = false;
		}
		if(LTS[1].get())
		{
			IND[0].set(0);
			start = true;
			return false;
		}
		return true;
	}

	private void region() {
		/*
		 * public void startIntake(boolean check) { if(check) { CAN[0].set(-.5);
		 * CAN[1].set(.5); } } public void startShooter(boolean check) {
		 * if(check) { CAN[0].set(1); CAN[1].set(-1); } }
		 * 
		 * public void lowerShooter(boolean check) { if(check) { LIFT[0].set(1);
		 * LIFT[1].set(-1); } } public void raiseShooter(boolean check) {
		 * if(check) { LIFT[0].set(-1); LIFT[1].set(1); } }
		 * 
		 * public void stopShooter(boolean check) { if(!check) { for(int i = 0;
		 * i < CAN.length; i++) { CAN[i].set(0); } } } public void
		 * stopLift(boolean check) { if(!check) { for(int i = 0; i <
		 * LIFT.length; i++) { LIFT[i].set(0); } } }
		 */}

}
