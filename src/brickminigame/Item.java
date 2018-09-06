package brickminigame;

import java.util.Random;

public class Item {
	private int posX;
	private int posY;
	private int veloY;
	private int type;

	public Item(int x, int y, int vY) {
		setPosX(x);
		setPosY(y);
		setVeloY(vY);
		Random r = new Random();
		setType(r.nextInt(7));
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getVeloY() {
		return veloY;
	}

	public void setVeloY(int veloY) {
		this.veloY = veloY;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void updatePosition() {
		posY += veloY;
	}
}
