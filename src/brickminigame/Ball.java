package brickminigame;

public class Ball {
	private int posX;
	private int posY;
	private int veloX;
	private int veloY;

	public Ball(int x, int y, int vx, int vy) {
		posX = x;
		posY = y;
		veloX = vx;
		veloY = vy;
	}

	public void setPosX(int pX) {
		posX = pX;
	}

	public int getX() {
		return posX;
	}

	public void setPosY(int pY) {
		posY = pY;
	}

	public int getY() {
		return posY;
	}

	public void setVeloX(int vX) {
		veloX = vX;
	}

	public int getVeloX() {
		return veloX;
	}

	public void setVeloY(int vY) {
		veloY = vY;
	}

	public int getVeloY() {
		return veloY;
	}

	public void updatePosition() {
		setPosX(getX() + getVeloX());
		setPosY(getY() + getVeloY());
	}

}
