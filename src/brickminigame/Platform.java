package brickminigame;

public class Platform {
	private int position;
	private int length;

	public Platform(int position, int length) {
		this.position = position;
		this.length = length;
	}

	public void setPosition(int p) {
		position = p;
	}

	public int getPosition() {
		return position;
	}

	public void setLength(int l) {
		length = l;
	}

	public int getLength() {
		return length;
	}
}
