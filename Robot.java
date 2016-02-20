
package org.usfirst.frc.team4361.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	private final String defaultAuto = "Drive to Obstacle";
    private final String customAuto = "My Auto";
    private String autoSelected;
    private SendableChooser chooser;
	
    //Roxbotix Variables
    private Autonomous auto;
    private VisionTracking vis;
    
    private Joystick[] stick;
    
    private double stick0X, stick0Y, stick1X, stick1Y;
    
    private boolean startIndex, notPress;
    
    private CANTalon[] CAN;
    
    private Drive left, right, portLift, chevelLift;
    
    private Shooter shooter;
    
    private int visTemp;
    
    private Timer timer;
    
    private Digit digit;
    
    private DigitalInput[] limitSwitch;
    
    //GRIP 
    private NetworkTable table;
    double[] centerXs;
    double[] centerYs;
    double[] areas;
    private double corCenterX;
    private double corCenterY;
    private double corArea;

    final double TOLERANCE = .85;
    
    //private Encoder lEnc, rEnc;
    
    
    //private AnalogInput hallSensor;
    
    private int testPos;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        //Roxbotix Variables
        vis = new VisionTracking();
        digit = new Digit();
        
        stick = new Joystick[3];
        for(int i = 0; i < stick.length; i++)
        {
        	stick[i] = new Joystick(i);
        }
        
        CAN = new CANTalon[12];
        for(int i = 0; i < CAN.length; i++)
        {
        	CAN[i] = new CANTalon(i);
        }
        
        limitSwitch = new DigitalInput[3];
    	for(int i = 0; i < limitSwitch.length; i++)
    	{
    		limitSwitch[i] = new DigitalInput(i);
    	}
        
        CANTalon[] leftDrive = {CAN[0], CAN[1]};
    	left = new Drive(leftDrive);
    	
    	CANTalon[] rightDrive = {CAN[2], CAN[3]};
    	right = new Drive(rightDrive);
    	
    	CANTalon[] shoot = { CAN[4], CAN[5]};
    	CANTalon[] shooterLift = {CAN[6], CAN[7]};
    	CANTalon[] index = {CAN[8]};
    	shooter = new Shooter(shoot, shooterLift, index, limitSwitch);
    	
    	CANTalon[] PortLift = {CAN[9], CAN[10]};
    	portLift = new Drive(PortLift);
    	
    	CANTalon[] ChevelLift = {CAN[11]};
    	chevelLift = new Drive(ChevelLift);
    	
    	//lEnc = new Encoder(0, 1, true, EncodingType.k1X); rEnc = new Encoder(2, 3, true, EncodingType.k1X);
    	
    	auto = new Autonomous(left, right, shooter);
    	//auto = new Autonomous(left, right, shoot, lEnc, rEnc);
    	
    	stick0X = stick[0].getAxis(Joystick.AxisType.kX);
    	stick0Y = stick[0].getAxis(Joystick.AxisType.kY);
    	stick1X = stick[1].getAxis(Joystick.AxisType.kX);
    	stick1Y = stick[1].getAxis(Joystick.AxisType.kY);
    	
    	startIndex = false;
    	notPress = true;
    	
    	timer = new Timer();
    	
    	digit.print("4361");
    	
    	//hallSensor = new AnalogInput(0);
    	
    	testPos = 0;//testing
    	
    	//GRIP
    	table = NetworkTable.getTable("GRIP/myContoursReport");
    	
    	//Default Variables
    	chooser = new SendableChooser();
        chooser.addDefault("Drive to Obstacle", "default");
        chooser.addObject("Portcullis", "portcullis");
        chooser.addObject("Chevel De Frise", "chevelDeFrise");
        chooser.addObject("Moat", "moat");
        chooser.addObject("Ramparts", "ramparts");
        chooser.addObject("Drawbridge", "drawbridge");
        chooser.addObject("Sally Port", "sallyPort");
        chooser.addObject("Rock Wall", "rockWall");
        chooser.addObject("Rough Terrain", "roughTerrain");
        
        SmartDashboard.putData("Auto choices", chooser);

        
        //GRIP VALUES
        //corArea = areas[0];
        //corCenterX = centerXs[0];
        //corCenterY = centerYs[0];
        

    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case "portcullis":
    	auto.portcullis();
            break;
    	case "chevelDeFrise":
    	auto.chevelDeFrise();
            break;
    	case "moat":
    	auto.moat();
            break;
    	case "ramparts":
    	auto.ramparts();
            break;
    	case "drawbridge":
    	auto.drawbridge();
            break;
    	case "sallyPort":
    	auto.sallyPort();
            break;
    	case "rockWall":
    	auto.rockWall();
            break;
    	case "roughTerrain":
    	auto.roughTerrain();
            break;
    	
    	case defaultAuto:
    	default:
    	auto.defaultGoToObstacle();
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
    	stick0X = stick[0].getAxis(Joystick.AxisType.kX);
    	stick0Y = stick[0].getAxis(Joystick.AxisType.kY);
    	stick1X = stick[1].getAxis(Joystick.AxisType.kX);
    	stick1Y = stick[1].getAxis(Joystick.AxisType.kY);
    	
    	//Drives Chassis
    	if(stick0Y<-.01 || stick0Y>.01)
    		right.drive(stick0Y*1);
    	if(stick1Y<-.01 || stick1Y>.01)
    		left.drive(-stick1Y*1);
    	
    	//SmartDashboard.putNumber("Throttle", stick[0].getThrottle());
    	
    	//Drives shooter
    	//shooter.lift(stick[2].getAxis(Joystick.AxisType.kY), stick[2].getRawButton(1));
    	shooter.liftSim(stick[2].getAxis(Joystick.AxisType.kY));
    	shooter.shoot(stick[2].getRawButton(5), stick[2].getRawButton(3));
    	shooter.index(stick[1].getRawButton(1), stick[0].getRawButton(1));
    	
    	//currently dead code until limit switches are in
    	if(stick[0].getRawButton(1)&&!notPress)
    	{
    		startIndex = true;
    		notPress = false;
    	}
    	
    	if(!stick[0].getRawButton(1))
    		notPress = true;
    	
    	if(startIndex)
    		startIndex = shooter.indexAuto();
    	
    	/*//Drive Portcullis Lift
    	if(stick[0].getPOV()==0)
    	{
    		portLift.drive(.5);
    	}
    	if(stick[0].getPOV()==180)
    	{
    		portLift.drive(-.5);
    	}
    	if(stick[0].getPOV()==-1)
    	{
    		portLift.drive(0);
    	}*/
    	
    	if(stick[0].getPOV()==0)
    	{
    		CAN[10].set(-.4);
    		CAN[9].set(.4);
    	}
    	if(stick[0].getPOV()==180)
    	{
    		CAN[10].set(.4);
    		CAN[9].set(-.4);
    	}
    	if(stick[0].getPOV()==-1)
    	{
    		CAN[10].set(0);
    		CAN[9].set(0);
    	}
    	
    	//Drives Cheveal Lift
    	if(stick[1].getPOV()==0)
    	{
    		chevelLift.drive(.5);
    	}
    	if(stick[1].getPOV()==180)
    	{
    		chevelLift.drive(-.5);
    	}
    	if(stick[1].getPOV()==-1)
    	{
    		chevelLift.drive(0);
    	}
    	
    	
    		
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() 
    {
    	/*
    	//GRIP
    	//expected center is 165x120 ish
    	if (stick[1].getRawButton(2)) 
    	{
    		timer.reset();
    		table = NetworkTable.getTable("GRIP/myContoursReport");
    		double[] defaultValue = new double[0];
    		double testVal=0;
    		
    		double[] areas = table.getNumberArray("area", defaultValue);
    		
    		double[] centerXs = table.getNumberArray("centerX", defaultValue);
    		corCenterX = centerXs[0];
    		double[] centerYs = table.getNumberArray("centerY", defaultValue);
    		corCenterY = centerYs[0];
    		System.out.print("areas: ");
    		for(double area: areas)
    		{
    			System.out.println(Math.abs(area-corArea)/corArea+ "");
    			if(Math.abs(area-corArea)/corArea == .01)
    			{
    				corArea=area;
    				System.out.println(Math.abs(area-corArea)/corArea+ "");
    			}
    		}
    		for(double centerX: centerXs)
    		{
    			if(Math.abs(centerX-corArea)/corArea == .2)
    			{
    				corCenterX=centerX;
    			}
    		}
    		for(double centerY: centerYs)
    		{
    			if(Math.abs(centerY-corArea)/corArea == .2)
    			{
    				corCenterY=centerY;
    			}
    		}
    		System.out.print(corArea + " " + corCenterX +" " + corCenterY + " ");
    		System.out.println();
    	}
    	/*if(testPos == 0)
    	{
	    	for(int i = 0; i < CAN.length; i++)
	    	{
	    		CAN[i].set(1);
	    		Timer.delay(.5);
	    		CAN[i].set(-1);
	    		Timer.delay(.5);
	    		CAN[i].set(0);
	    	}
    	}
    	if(testPos == 1)
    	{
    		digit.testLoading(true);
    	}*/
    	robotInit();
    	autonomousInit();
    	
    }
    
    private void tracking()
    {
    	visTemp = vis.tryTracking();
		
		//Shoot
		if(visTemp == 0)
		{
			digit.print(" SH ");
		}
		
		//Turning left and right for correction
		else if(visTemp == 1)//right
		{
			digit.print(" RT ");
			//left.drive(true, .5, timer, .25);
			//right.drive(true, .5, timer, .25);
		}
		else if(visTemp == 2)//left
		{
			digit.print(" LT ");
			//left.drive(true, -.5, timer, .25);
			//right.drive(true, -.5, timer, .25);
		}
		
		//Moving back and forward for correction
		else if(visTemp == 3)//back
		{
			digit.print(" BK ");
			//left.drive(true, -.5, timer, .25);
			//right.drive(true, .5, timer, .25);
		}
		else if(visTemp == 4)//forward
		{
			digit.print(" FD ");
			//left.drive(true, .5, timer, .25);
			//right.drive(true, -.5, timer, .25);
		}
		
		//Will return -1 in case nothing is found
		else if(visTemp == -1)
		{
			digit.print("NONE");
		}
		if(visTemp == 5)
			digit.print(" ER ");
    }
}
