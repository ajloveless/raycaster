import java.awt.Canvas; //Canvas to engine on
import java.awt.Color; //Color definitions
import java.awt.image.BufferStrategy; //Buffering object
import java.awt.Graphics; //Engineing library
import java.lang.Runnable; //Create a runnable
import java.lang.Thread; // import threads
import javax.swing.JFrame; //Display a JFrame
import java.awt.event.MouseEvent;

//The Engine class needs to have JFrame methods and the Runnable run() method
public class Engine extends JFrame implements Runnable
{

	int fps = 60;
	public static int width = 800;
	public static int height = 800; 
	public static int alpha = 0xFFFF00DC;

	//Create a canvas to engine on
	private Canvas canvas = new Canvas();

	private RenderHandler renderer;
	private MouseHandler mouse = new MouseHandler(this);

	public Engine()
	{
		
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

		renderer = new RenderHandler(getWidth(),getHeight());
		
		//Add listeners
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);

	}
	//Update logic
	public void update()
	{			

	}

	

	//Render visuals
	public void render()
	{
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics graphics = bufferStrategy.getEngineGraphics();
			super.paint(graphics);

			renderer.render(graphics);
			bufferStrategy.show();
	}

public void leftClick(int x, int y)
	{
		Rectangle pix = new Rectangle(x,y,50,50);
		pix.generateGraphics(0xFFFFFF);
		renderer.renderRectangle(pix,1,1);
	}
	//Run method for threads
	public void run() 
	{
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;

		long lastTime = System.nanoTime();
		double nanoSecondConversion = 1000000000.0 / fps; //One second converted to nanoseconds 
		double deltaTime = 0;
		while(true)
		{
			long now = System.nanoTime();

			deltaTime += (now - lastTime) / nanoSecondConversion;

			while(deltaTime >= 1)
			{
				update();
				deltaTime = 0;
			}

			render();
			lastTime = now;
		}
	}
	


 	public static void main(String[] args)
  	{
  		//Call the engine class
    	Engine engine = new Engine();
    	Thread gameThread = new Thread(engine);
    	gameThread.start();
  	}

  	public MouseHandler getMouseHandler()
  	{
  		return mouse;
  	}

}