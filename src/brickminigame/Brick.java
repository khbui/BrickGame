package brickminigame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Brick {
	private boolean isDestroyed;
	private ArrayList<Color> c;
	private Random rand;

	public Brick() {
		//Randomly generate bricks (90%)
		rand = new Random();
		if (rand.nextInt(10) != 0)
			isDestroyed = false;
		else
			isDestroyed = true;
		//Set color for bricks
		c = new ArrayList<Color>();
		c.add(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
		c.add(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
	}
	//Return bricks' status
	public boolean getDestroyed() {
		return isDestroyed;
	}
	//Update destroyed bricks
	public void setDestroyed(boolean d) {
		isDestroyed = d;
	}
	//Return bricks' colors
	public Color getColor(int index) {
		return c.get(index);
	}
}

