public class Column 
{
	//Declare variables
	public int x,y,w,h;
	private int[] pixels;

	//if arguments are given, make the column the specified size
	Column(int x, int y, int w, int h) 
	{
		this.x = x;
		this.y = y;
		this.w = 1;
		this.h = h;
	}

	//For no arguments, make the column size 0
	Column() 
	{
		this(0,0,0,0);
	}

	//Render an object to the screen with a color argument
	public void generateGraphics(int color) 
	{
		pixels = new int[w*h];
		for(int y = 0; y < h; y++)
			for(int x = 0; x < w; x++)
				pixels[x + y * w] = color;
	}


	//Get pixel data from the screen
	public int[] getPixels() 
	{
		if(pixels != null)
			return pixels;
		else
			System.out.println("Attempted to retrive pixels from a Column without generated graphics.");

		return null;
	}
}