import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


//Reminder that each method can handle mouse clicks differently
//(the left and right mouse buttons do different things)



//Implement all mouse methods to test for
public class MouseHandler implements MouseListener, MouseMotionListener
{
	//Declare engine object
	private Engine engine;
	public MouseHandler(Engine engine)
	{	
		this.engine = engine;	
	}

	//When the mouse is pressed and released
	public void mouseClicked(MouseEvent event) 
	{  

    }  
    //When the mouse is pressed and dragged
    public void mouseDragged(MouseEvent event) 
    {  
			engine.leftClick(event.getX(), event.getY());
    }  
    //When the mouse enters the window
    public void mouseEntered(MouseEvent event) 
    {  

    }  
    //When the mouse exits the window
    public void mouseExited(MouseEvent event) 
    {  

    }  
    //When the mouse moves
    public void mouseMoved(MouseEvent event) 
    {  
    	engine.mouseMoved(event.getX(), event.getY());
    }  
    //When the mouse is pressed down
    public void mousePressed(MouseEvent event) 
    {  
    		//Run left click method in engine class
    	 	if(event.getButton() == MouseEvent.BUTTON1)
    	 	{
    	 					engine.leftClick(event.getX(), event.getY());

    	 	}
			else if (event.getButton() == MouseEvent.BUTTON3)
			{
							engine.rightClick(event.getX(), event.getY());

			}
	}
	//When the mouse is released
    public void mouseReleased(MouseEvent event) 
    {  

    }  
}