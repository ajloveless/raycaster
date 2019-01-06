public class GuiHandler
{

	Player player;
	Engine engine;
	String output;
	String text;

	public GuiHandler(Engine engine)
	{
		this.engine = engine;
		player = engine.getPlayer();
		this.output = output;

	}

	public String updateGUI()
	{
			int dir = (int) ((engine.player.direction / 3.14) * 180) % 360;
			if (dir <= 0) dir += 360;
			output = ""+
			"POS: (" + (int) engine.player.x + "," +(int) engine.player.y +   ")   " +
			"DIRECTION: " + dir + "   " +
			"FPS: " + (engine.frames + 1) / (engine.runTime + 1) + "   " +
			"RUN TIME: " + engine.runTime + " SECONDS";
			return output;

	}
}