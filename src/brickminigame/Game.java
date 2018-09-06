package brickminigame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener {

	private final int HORIZONTAL = 1000;
	private final int VERTICAL = 900;
	private Platform pf;
	private Ball ball;
	private Brick brick[][];
	private int row;
	private int col;
	private int totalBrick;
	private int score;
	private int rate;
	private Timer timer;
	private int life;
	private boolean lose;
	private boolean win;
	private List<Item> itemArray;
	private Random r;

	public Game() {
		lose = false;
		win = false;
		life = 3;
		rate = 5;
		// Set score
		score = 0;
		// Initial platform
		pf = new Platform(310, 200);
		// Initial ball
		ball = new Ball(HORIZONTAL / 2, VERTICAL / 2, -3, -4);
		itemArray = new ArrayList<Item>();
		// Initial number of brick's column and row
		r = new Random();
		row = r.nextInt(4) + 3;
		col = r.nextInt(4) + 8;
		// Generate bricks
		brick = new Brick[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				brick[i][j] = new Brick();
				if (brick[i][j].getDestroyed() == false)
					totalBrick++;
			}
		}
		// Add key action listener
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(8, this);
		timer.start();
	}

	public void paint(Graphics gr) {
		Graphics2D g = (Graphics2D) gr;
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, HORIZONTAL, VERTICAL);
		g.setColor(Color.BLUE);
		g.fillRect(pf.getPosition(), VERTICAL - 100, pf.getLength(), 5);
		GradientPaint c;
		// Draw bricks
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (brick[i][j].getDestroyed() == false) {
					c = new GradientPaint(j * (HORIZONTAL - 60) / col + 50, i
							* 150 / row + 10, brick[i][j].getColor(0), j
							* (HORIZONTAL - 60) / col + 100,
							i * 150 / row + 20, brick[i][j].getColor(1));
					g.setPaint(c);
					g.fillRect(j * (HORIZONTAL - 60) / col + 50, i * 150 / row
							+ 10, 50, 20);
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.BLACK);
				}
			}
		}
		// Draw items
		for (Item i : itemArray) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 14));
			g.setColor(Color.red);
			switch (i.getType()) {
			case 0:
				g.drawString("UP", i.getPosX(), i.getPosY());
				break;
			case 1:
				g.drawString("FAST", i.getPosX(), i.getPosY());
				break;
			case 2:
				g.drawString("DOUBLE", i.getPosX(), i.getPosY());
				break;
			case 3:
				g.drawString("SLOW", i.getPosX(), i.getPosY());
				break;
			case 4:
				g.drawString("NORMAL", i.getPosX(), i.getPosY());
				break;
			case 5:
				g.drawString("LONG", i.getPosX(), i.getPosY());
				break;
			case 6:
				g.drawString("SHORT", i.getPosX(), i.getPosY());
				break;
			default:
				break;
			}
		}
		// Draw ball
		c = new GradientPaint(ball.getX(), ball.getY(), Color.RED,
				ball.getX() + 20, ball.getY() + 20, Color.WHITE);
		g.setPaint(c);
		g.fillOval(ball.getX(), ball.getY(), 20, 20);
		// Draw information
		g.setFont(new Font("TimesRoman", Font.PLAIN, 24));
		g.setColor(Color.black);
		g.drawString("LIFE: " + life + "              " + "SCORE: " + score,
				HORIZONTAL / 3, VERTICAL - 50);
		if (lose) {
			if (life == -1) {
				g.setFont(new Font("TimesRoman", Font.PLAIN, 34));
				g.setColor(Color.red);
				g.drawString("GAME OVER", HORIZONTAL / 2 - 90, VERTICAL / 2);
				timer.stop();
			} else {
				lose = false;
				g.setFont(new Font("TimesRoman", Font.PLAIN, 34));
				g.setColor(Color.red);
				g.drawString("HIT SPACEBAR", HORIZONTAL / 2 - 90, VERTICAL / 2);
				timer.stop();
			}
		}
		if (win) {
			g.setFont(new Font("TimesRoman", Font.PLAIN, 34));
			g.setColor(Color.red);
			g.drawString("YOU WIN", HORIZONTAL / 2 - 95, VERTICAL / 2);
			timer.stop();
		}
		g.dispose();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			if (pf.getPosition() + pf.getLength() < HORIZONTAL - 20)
				pf.setPosition(pf.getPosition() + 20);
			break;
		case KeyEvent.VK_LEFT:
			if (pf.getPosition() > 0)
				pf.setPosition(pf.getPosition() - 20);
			break;
		case KeyEvent.VK_SPACE:
			if (!timer.isRunning() && life != -1)
				timer.start();
			break;
		default:
			break;
		}
	}

	public void actionPerformed(ActionEvent e) {
		// Ball reflection if hit border
		if (ball.getX() <= 0 || ball.getX() >= HORIZONTAL - 35) {
			ball.setVeloX(-1 * ball.getVeloX());
		}
		if (ball.getY() <= 0) {
			ball.setVeloY(-1 * ball.getVeloY());
		}
		// You lose
		if (ball.getY() >= VERTICAL) {
			lose = true;
			life -= 1;
			ball.setPosX(VERTICAL / 2);
			ball.setPosY(HORIZONTAL / 2);
			ball.setVeloY(-4);
			itemArray.clear();
		}
		// Set new ball's position
		ball.updatePosition();
		// Check collision of ball and platform
		Rectangle r1 = new Rectangle(ball.getX(), ball.getY(), 20, 20);
		Rectangle r2 = new Rectangle(pf.getPosition(), VERTICAL - 100,
				pf.getLength(), 5);
		if (r1.intersects(r2)) {
			ball.setVeloY(-1 * ball.getVeloY());
		}
		// Check collision of ball and each brick
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Rectangle r3 = new Rectangle(j * (HORIZONTAL - 60) / col + 50,
						i * 150 / row + 10, 50, 20);
				if (r1.intersects(r3) && brick[i][j].getDestroyed() == false) {
					brick[i][j].setDestroyed(true);
					ball.setVeloY(-1 * ball.getVeloY());
					if (r.nextBoolean() == false)
						ball.setVeloX(-1 * ball.getVeloX());
					totalBrick--;
					score += rate;
					if (r.nextInt(100) < 35) {
						Item itemp = new Item(ball.getX(), ball.getY(), 2);
						itemArray.add(itemp);
					}
				}
			}
		}
		List<Item> toRemove = new ArrayList<Item>(); // avoid
														// ConcurrentModificationException
		for (Item i : itemArray) {
			i.updatePosition();
			// Item intersection
			if (i.getPosY() > VERTICAL - 120) {
				Rectangle r3 = new Rectangle(i.getPosX(), i.getPosY(), 50, 10);
				boolean a = r2.intersects(r3);
				System.out.println(a);
				if (r2.intersects(r3)) {
					switch (i.getType()) {
					case 0:
						life++;
						break;
					case 1:
						if (Math.abs(ball.getVeloX()) < 4) {
							if (ball.getVeloX() < 0)
								ball.setVeloX(ball.getVeloX() - 1);
							else
								ball.setVeloX(ball.getVeloX() + 1);
							if (ball.getVeloY() < 0)
								ball.setVeloY(ball.getVeloY() - 1);
							else
								ball.setVeloY(ball.getVeloY() - 1);
						}
						break;
					case 2:
						rate = 10;
						break;
					case 3:
						if (Math.abs(ball.getVeloX()) > 2) {
							if (ball.getVeloX() < 0)
								ball.setVeloX(ball.getVeloX() + 1);
							else
								ball.setVeloX(ball.getVeloX() - 1);
							if (ball.getVeloY() < 0)
								ball.setVeloY(ball.getVeloY() + 1);
							else
								ball.setVeloY(ball.getVeloY() - 1);
						}
						break;
					case 4:
						rate = 5;
						break;
					case 5:
						if (pf.getLength() < 400)
							pf.setLength(pf.getLength() * 2);
						break;
					case 6:
						if (pf.getLength() > 100)
							pf.setLength(pf.getLength() / 2);
						break;
					default:
						break;
					}
					toRemove.add(i);
				}
			}
			// Destroy items
			if (i.getPosY() > VERTICAL) {
				toRemove.add(i);
			}
		}
		itemArray.removeAll(toRemove);
		// You win
		if (totalBrick == 0) {
			win = true;
		}
		repaint();
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
