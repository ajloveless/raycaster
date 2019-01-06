import java.util.Random;

public class Map
{
	int size;
	String tiles = "";
	 //# is wall
	 //. is empty space

	public Map(int size)
	{
		this.size = size;
		
 		this.tiles += repeat("#",size);
 		for(int i=0;i<(size - 2);i++)
 		{
 			this.tiles += "#" + repeat(".",size -2) + "#";
 		}
 		this.tiles += repeat("#",size);

 			for(int i=0; i< size; i++)
 			{
 				Random random = new Random();
 				int cell = random.nextInt((size * size) -1);
 				if (cell != size/4) 
 				{
 					this.tiles = this.tiles.substring(0, cell)+'#'+tiles.substring(cell + 1);
 				}
 				

 			}

	}


	public static String repeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }

}