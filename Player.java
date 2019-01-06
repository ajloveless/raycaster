public class Player
{
	//Declare player variables
	float x,y,direction;
	float speed = 0.1f;	
	public Player(float x,float y, float direction)
	{
		//Initialize variables in a way as to not have to come up with more arbitrary names
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public void update(Engine engine)
	{
		KeyboardHandler keyboard = engine.getKeyHandler();

		//If the player presses the up key
		if(keyboard.up())
		{
			//Move the player in the appropriate direction
			engine.player.x += (float) Math.sin(engine.player.direction) * speed;
			engine.player.y += (float) Math.cos(engine.player.direction) * speed;
			//Check for collisions
			if (engine.map.tiles.charAt((int)engine.player.y * engine.map.size + (int)engine.player.x) == '#')
			{
				engine.player.x -= (float) Math.sin(engine.player.direction) * speed;
				engine.player.y -= (float) Math.cos(engine.player.direction) * speed;
			}		
		}
		if(keyboard.down())
		{
			engine.player.x -= (float) Math.sin(engine.player.direction) * speed;
			engine.player.y -= (float) Math.cos(engine.player.direction) * speed;
			if (engine.map.tiles.charAt((int)engine.player.y * engine.map.size + (int)engine.player.x) == '#')
			{
				engine.player.x += (float) Math.sin(engine.player.direction) * speed;
				engine.player.y += (float) Math.cos(engine.player.direction) * speed;
			}		
		} 
		if(keyboard.left())
		{
			engine.player.x -= (float) Math.cos(engine.player.direction) * speed;
			engine.player.y += (float) Math.sin(engine.player.direction) * speed;
			if (engine.map.tiles.charAt((int)engine.player.y * engine.map.size + (int)engine.player.x) == '#')
			{
				engine.player.x += (float) Math.cos(engine.player.direction) * speed;
				engine.player.y -= (float) Math.sin(engine.player.direction) * speed;
			}	
		}
		if(keyboard.right())
		{
			engine.player.x += (float) Math.cos(engine.player.direction) * speed;
			engine.player.y -= (float) Math.sin(engine.player.direction) * speed;
			if (engine.map.tiles.charAt((int)engine.player.y * engine.map.size + (int)engine.player.x) == '#')
			{
				engine.player.x -= (float) Math.cos(engine.player.direction) * speed;
				engine.player.y += (float) Math.sin(engine.player.direction) * speed;
			}	
		}

		//Close program on escape key press
		if(keyboard.escape())
		{
			System.exit(0);
		}
	}
}