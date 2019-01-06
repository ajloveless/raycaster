import java.awt.Canvas; //Canvas to draw on
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy; //Buffering object
import java.awt.image.DataBufferInt;

public class RenderHandler
{

	//Declare objects 
	private BufferedImage view;
	private Engine engine;
	//private Rectangle camera;
	private int[] pixels;
	public RenderHandler(int width, int height)
	{
		//Create a buffered image that represents the view
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//camera = new Rectangle(0, 0, width, height);

		//Create an array for the pixels
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

	}

	public void render(Graphics graphics)
	{
		//Render the handled view to grahpics
		graphics.drawImage(view,0,0,view.getWidth(),view.getHeight(),null);

	}


	public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPosition, int yPosition) 
	{
		//Render an array of pixels to the screen
		for(int y = 0; y < renderHeight; y++)
			for(int x = 0; x < renderWidth; x++)
						setPixel(renderPixels[x + y * renderWidth], (x + xPosition), (y + yPosition));
	}


	public void setPixel(int pixel, int x, int y) 
	{
		//Set a pixel a color 

			int pixelIndex = (x) + (y) * view.getWidth();
			if(pixels.length > pixelIndex && pixel != Engine.alpha)
				pixels[pixelIndex] = pixel;
			}

	public void clear()
	{
		for(int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
	}

	public void renderColumn(Column column)
	{
		//Render a column of pixels 
		int[] columnPixels = column.getPixels();
		if(columnPixels != null)
			renderArray(columnPixels, column.w, column.h, column.x, column.y);	
	}




}