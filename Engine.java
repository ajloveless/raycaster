import java.awt.Canvas; //Canvas to draw on
import java.awt.Color; //Color definitions
import java.awt.Cursor; //Read and write cursors
import java.awt.event.KeyEvent; //Get key input
import java.awt.event.MouseEvent; //Get mouse input
import java.awt.Graphics; //Engineing library
import java.awt.GraphicsConfiguration; //Read graphics configurations
import java.awt.GraphicsDevice; //Get graphics devices
import java.awt.GraphicsEnvironment; //More graphics stuff
import java.awt.image.BufferStrategy; //Buffering object
import java.awt.image.MemoryImageSource; //Create image from cache
import java.awt.Image; //Generate images
import java.awt.Point; //Point for cursor generation
import java.awt.Robot; //Lock the mouse in frame
import java.awt.Toolkit; //Import java toolkit
import java.lang.Runnable; //Create a runnable
import java.lang.Thread; // import threads
import javax.swing.JFrame; //Display a JFrame


//The Engine class needs to have JFrame methods and the Runnable run() method
public class Engine extends JFrame implements Runnable
{

	int tickSpeed = 60; //Desired frames per second
	float fov = 3.1415F / 4F; //Field of view for the player
	float depth = 100; //How far the player can see
	int sensitivity; //Mouse sensitivity
	int frames; //How many frames have been rendered
	int screenWidth, screenHeight; //Size of the jframe (for GraphicsDevice)
	int counter; //Counts frames for a timer that runs once a second
	int runTime; //How long the game has been running in seconds
	boolean debug; //Debug mode
	public static int width = 800; //Width of the window	
	public static int height = 800; //Height of the window
	public static int alpha = 0xFFFF00DC; //Defined alpha color

	//Create a canvas to draw pixels on
	public Canvas canvas = new Canvas();

	//Define handler objects
	public RenderHandler renderer;
	private MouseHandler mouse = new MouseHandler(this);
	private KeyboardHandler keyboard = new KeyboardHandler(this);
	private GuiHandler gui = new GuiHandler(this);

	//Create objects
	public Player player = new Player(0,0,0);
	public GraphicsDevice defaultScreen;
	public Map map;

	//Method to find graphics devices, used to lock the mouse to the center of the default screen
	public void getGraphicsDevices() 
	{		
   		// //Find the default screen
    	defaultScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    	// //Determine the screen's center
    	screenWidth = defaultScreen.getDisplayMode().getWidth();
    	screenHeight = defaultScreen.getDisplayMode().getHeight();
	}
 	
	//Default engine constructor
	public Engine(int s,int m)
	{
		sensitivity = s; //Get sensitivity setting from config launcher
		debug = false; //Start with debug mode off
		map = new Map(m); //Initialize the map with config map size
		counter = 0; //Start the counter and run time at 0
		runTime = 0;
		long startTime = System.nanoTime(); //Nanosecond time on when the game was launched
		this.width = width; //Set the window width and height
		this.height = height;
		frames = tickSpeed; //Initialize the frames variable with a placeholder, not sure why I used tickSpeed but I'm afraid to change it

		//Spawn the player in the middle of the room
		player = new Player(map.size/2,map.size/2,0);

		//Get display information
		getGraphicsDevices();
		//Set the window size to variables initialized above
		//Make the application terminate when it's closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set temporary position and size of the frame
		setBounds(0,0,width,height);
		//Move the frame to the center of the screen
		setLocationRelativeTo(null);
		//Add the canvas to the frame
		add(canvas);
		//Make the frame visible
		setVisible(true);
		//Create buffer strategy - cache and display frames without stutters or flashing
		canvas.createBufferStrategy(2);
		//Initialize the render handler at the size of the window
		renderer = new RenderHandler(getWidth(),getHeight());
		//Add listeners to recieve input
		canvas.addKeyListener(keyboard); //Keyboard input
		canvas.addFocusListener(keyboard); //Check if window is in focus
		canvas.addMouseListener(mouse); //Mouse button input
		canvas.addMouseMotionListener(mouse); //Mouse movement

	}

	//Update any game logic logic
	public void update()
	{			
		//Update the player's location and angle
		player.update(this);
		
		//Add 1 to runTime every second
		this.counter++;
		if (this.counter == tickSpeed)
		{
			this.runTime += 1;
			this.counter = 0;
		}

		//Check column-by-column the distance of walls
		for(int x=0; x < width; x++)
		{	
			//Set a ray to go out at an incremented angle
			float rayAngle =(player.direction - fov / 2) + ((float)x / width) * fov;

			//Initialize default distance from the ray to wall as 0
			float distanceToWall = 0;
			//Initialize flag variable for a ray for hitting a wall as false
			boolean hitWall = false;

			//Direction the player is looking in
			float eyeX = (float) Math.sin(rayAngle);
			float eyeY = (float) Math.cos(rayAngle);

			//Incrementally check if the ray has hit a wall
			while(!hitWall && distanceToWall < depth)
			{
				//Increase the distance that is being checked, a lower number gives smoother lines
				distanceToWall += 0.01f; 

				//Initialize the point we are testing for as a variable
				int testX = (int)(player.x + eyeX * (distanceToWall/ (Math.cos(rayAngle - player.direction))));
				int testY = (int)(player.y + eyeY * (distanceToWall/ (Math.cos(rayAngle - player.direction))));
				
				//If the distance is less than 0 or if it exceeds the map size
				if (testX < 0 || testX >= map.size || testY < 0 || testY >= map.size)
				{
					//Go ahead and draw a wall at the depth defined earlier
					hitWall = true;
					distanceToWall = depth;
				}
				else
				{
					//Check the map string to see if the player is looking at a wall
					if (map.tiles.charAt(testY * map.size + testX) == '#')
					{
						//If so, flag hitWall as true
						hitWall = true;
					}
				}
			}

			//Set everything above the wall as the ceiling
			int ceiling = (int) ((height/2) - height / (float)distanceToWall);
			//and everything below the wall as the floor
			int floor = height - ceiling;
			//Initialize shading variables for lighting and shadows
			int shade, colorConstant;
			//Loop through each y level to shade and draw the pixels
			for(int y=0; y<height; y++)
			{
				//If current y is defined as ceiling
				if(y <= ceiling)
				{
					//Determine its distance from the player (how close it is to the middle of the screen)
					float b = 1.0f - (((float)y + height/-2.0f) / ((float)height / 2.0f));
					//Shade it appropriately, darker colors towards the middle
					colorConstant = (int) (255 * Math.abs(1.05 - b));
					//Clamp shading color as to not go over rgb limit
					if (colorConstant > 255) colorConstant = 255;
					if (colorConstant < 0) colorConstant = 0;
					//Get integer value of the shaded color
					shade = new Color(colorConstant, colorConstant, colorConstant).getRGB();
					//Draw that color to the screen
					renderer.setPixel(shade,x,y);
					}
					//If the y value is in between the ceiling and the floor (it's a wall)			
					else if(y > ceiling && y <= floor)
					{
					//Shade the pixel based on how far it is from the player
					colorConstant = (int) (255 - (distanceToWall * distanceToWall * 2.05));
					//Clamp shading color as to not go over rgb limit
					if (colorConstant > 255) colorConstant = 255;
					if (colorConstant < 0) colorConstant = 0;
					//Get integer value of the shaded color
					shade = new Color(colorConstant, colorConstant, colorConstant).getRGB();
					//Draw that color to the screen
					renderer.setPixel(shade,x,y);
					}
					else 
					{				
					//Determine it's distance from the player (how close it is to the middle of the screen)
					float b = 1.0f - (((float)y + height/-2.0f) / ((float)height / 2.0f));
					//Shade it appropriately, darker colors towards the middle
					colorConstant = (int) (255 * (0.95 - b));
					//Clamp shading color as to not go over rgb limit
					if (colorConstant > 255) colorConstant = 255;
					if (colorConstant < 0) colorConstant = 0;
					//Get integer value of the shaded color
					shade = new Color(colorConstant, colorConstant, colorConstant).getRGB();
					//Draw that color to the screen
					renderer.setPixel(shade,x,y);
				}

			}

		}
	
	}

	//Render visuals
	public void render()
	{		
			//Declare and initialize buffer strategy
			this.frames += 1; //Keep track of how many frames have been rendered
			//Get the buffer strategy of the canvas
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			//Create a graphics object
			Graphics graphics = bufferStrategy.getDrawGraphics();
			//Paint it to the canvas
			super.paint(graphics);
			//Render the graphics
			renderer.render(graphics);
			//One-liner to toggler debug info
			if (this.debug) graphics.drawString(gui.updateGUI(),0,10);
			//Show the buffers
			bufferStrategy.show();
	}


	public void leftClick(int x, int y) 
	{
		//Nothing needed here yet, but maybe some day I can use it
	}

	public void rightClick(int x, int y)
	{
		//If right click is pressed do whatever here
	}


	public void keyTyped(int keyCode)
	{
		//Tilde key
		if (keyCode == 192)
		{
			//Toggle debug
			if (this.debug) this.debug = false;
			else this.debug = true;
		}
		//R key
		if (keyCode == 82)
		{
			//Regen the map
			map = new Map(map.size);
		}
	}

	public void mouseMoved(int x, int y)
	{
		//Initialize mouse acceleration as 0
		double acceleration = 0.0;
		//Calculate how fast the acceleration should be
		acceleration = ((x - width/2)/10)*(sensitivity/5);
		//Rotate the player by their acceleration
		player.direction += (0.01f * acceleration);

		try
    	{
    		//Initialize robot object (controls input)
    		Robot robot = new Robot(defaultScreen);
    		//Lock mouse back to the middle of the screen
    		robot.mouseMove(screenWidth/2,screenHeight/2);
    	}
    	//If an error is cause for some reason
    	catch (Exception e)
    	{	
    		//Print error to console
    		e.printStackTrace();
    	}


	}

	public void drawColumn(int x, int y)
	{
		//Determine how long the column would be, assuming it's centered
		int length = Math.abs((height/2) - y) * 2;
		//Determine where to place the column, used in case given y value is below center screen
		int cHeight = Math.abs((height - length)/2);
		//Make a column object
		Column column = new Column(x,cHeight,1,length);
		//Set it's color to white
		column.generateGraphics(0xFFFFFFF);
		//Render the column to the screen
		renderer.renderColumn(column);
	}

	//Implement the run function for Runnable, used with threads
	public void run() 
	{
		//Get and store the buffer strategy
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		//Store the current time as a variable
		long lastTime = System.nanoTime();
		//One second converted to nanoseconds divided across desired fps
		double nanoSecondConversion = 1000000000.0 / tickSpeed;
		//Clear deltaTime, which adjusts discrepency between engine logic speed and fps 
		double deltaTime = 0;
		//Infinite loop the following for accurate-ish engine logic speed
		while(true)
		{
			//Get a new current time
			long now = System.nanoTime();

			//Compare both time variables to see if fps is at a suitable rate in relation to the engine logic speed
			deltaTime += (now - lastTime) / nanoSecondConversion;
			//Adjust engine logic speed according to fps
			while(deltaTime >= 1)
			{
				//Step through engine logic
				update();
				//Reset delta time
				deltaTime = 0;
			}

			//Render the next frame and push the current time variable to the last time variable (for continuous looping)
			render();
			//Push current time to last time variable for looping
			lastTime = now;
		}
	}
	


 	public static void main(String[] args)
  	{
  		//In case this class is run instead of the config class, just start config anyways
  		config config = new config();
  	}


	//Startup called by config with sensitivity and map size arguments
  	public static void startup(int s,int m)
  	{
  		//Initialize the engine class
    	Engine engine = new Engine(s,m);
    	//Create a pixel array for a blank mouse cursor
    	int[] pixels = new int[16 * 16];
    	//Create an image based on the pixel array
		Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, pixels, 0, 16));
		//Create a cursor object with the created image as the icon
		Cursor blankCursor =Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "blankCursor");
        //Use the blank cursor as the cursor in window
        engine.getContentPane().setCursor(blankCursor);
    	//Assign the engine class to a new thread
    	Thread engineThread = new Thread(engine);
    	//Start the thread
    	engineThread.start();
  	}

  	//Used by other classes to get the keyboard object 
	public KeyboardHandler getKeyHandler() 
	{
		return keyboard;
	}

  	//Used by other classes to get the mouse object 
  	public MouseHandler getMouseHandler()
  	{
  		return mouse;
  	}

  	//Used by other classes to get the mouse object 
  	public Player getPlayer()
  	{
  		return player;
  	}

}