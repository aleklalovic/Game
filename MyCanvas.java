package one;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import sun.audio.*;

public class MyCanvas extends Canvas implements KeyListener {
	int score = 0;
	boolean startgame=true;
	// global variables - accessible in all methods
	Goodguy link = new Goodguy(10,10,200,200,"files/Donkey.png");
	LinkedList badguys = new LinkedList();
	LinkedList knives = new LinkedList();

	//LinkedList Timer = new LinkedList();
	Background start = new Background(0,0,1450,950, "files/Start.jpg"); 
	Background back = new Background(0,0,1450,950,"files/Background.jpg");
	int crossed = 1;
	
	private boolean gameover = false;
	/**
	 * MyCanvas drawing canvas inherits from java.awt.Canvas
	 * @author alek.lalovic
	 * @since Oct. 9, 2018
	 * @param no parameters, default constructor
	 */
	public 	MyCanvas() {
			this.setSize(1450,950); // set same size as MyScreen
			this.addKeyListener(this); // add the listener to your canvas
			playIt("Files/Gamesong.wav");
			
			Random rand  = new Random();
			int winwidth = this.getWidth();
			int winheight = this.getHeight();
			int rx = rand.nextInt(winwidth);
			for(int i = 0; i<20; i++) {
				Badguy bg = new Badguy(rx + 1350, rand.nextInt(winheight),50,50,"files/Badguy.png");
				Rectangle r = new Rectangle(100,100,30,30);
				if (r.contains(link.getxCoord(), link.getyCoord()))	{ //check to see if badguy spawns on link
					System.out.println("badguy on top of link");
					continue;
				}
				badguys.add(bg);
			}
				
			TimerTask repeatedTask = new TimerTask() {
			        public void run() {
			        	for(int i = 0; i < badguys.size(); i++) {// draw bad guys
			    			Badguy bg = (Badguy) badguys.get(i);
			    			bg.setxCoord(bg.getxCoord() - 4);
			        	}
			            repaint();
			        }
			    };
			    Timer timer = new Timer();
			    long delay  = 0;
			    long period = 50;
			    timer.scheduleAtFixedRate(repeatedTask, delay, period);	
				
			
	}
		
	public void playIt(String filename) {
			
			try {
				InputStream in = new FileInputStream(filename);
				AudioStream as = new AudioStream(in);
				AudioPlayer.player.start(as);
			} catch (IOException e) {
				System.out.println(e);
			}
			
	}
	
		
		/**
		 * paint overload java.awt.Canvas paint method and make it draw an oval
		 * @param graphics context variable called g
		 */
		@Override 
		public void paint(Graphics g) {
			
			if (startgame==true) {
				g.drawImage(start.getImg(), start.getxCoord(),0,  1450, 950, this);
			} else {	
			
				// g.fillOval(100, 100, 10, 10);
				g.drawImage(back.getImg(), back.getxCoord(),0,  1450, 950, this);
				g.drawImage(link.getImg(), link.getxCoord(), link.getyCoord(), link.getWidth(), link.getHeight(), this); // draw good guy
			
			
				Font font = new Font("Helvetica", Font.BOLD, 90);
				g.setFont(font);
				g.setColor(Color.WHITE);
				g.drawString("Score: " + Integer.toString(score), 150, 80);
			
				//gameover screen
				if(gameover==true) {
					g.setColor(Color.black);
					g.fillRect(0, 0, 1450, 950);
				
					g.setColor(Color.orange);
					g.setFont(new Font("Helvetica", Font.BOLD,90));
					g.drawString("Game Over!", 400, 400);
					return;
				}
				if(score==11) {
					g.setColor(Color.green);
					g.fillRect(0, 0, 1450, 950);
					g.setColor(Color.blue);
					g.setFont(new Font("Helvetica", Font.BOLD,200));
					g.drawString("Victory!", 400, 400);
					return;
				}
			
			for(int i = 0; i < badguys.size(); i++) {// draw bad guys
				Badguy bg = (Badguy) badguys.get(i);
				g.drawImage(bg.getImg(), bg.getxCoord(), bg.getyCoord(), bg.getWidth(), bg.getHeight(), this);
				Rectangle r = new Rectangle(bg.getxCoord(),bg.getyCoord(),bg.getWidth(),bg.getHeight());

				for(int j = 0; j < knives.size(); j++) {
					Projectile k = (Projectile) knives.get(j);
					if (k.getxCoord() > this.getWidth()) { knives.remove(k); }
					k.setxCoord(k.getxCoord() + .1);
					g.drawImage(k.getImg(), (int) k.getxCoord(), k.getyCoord(), k.getWidth(), k.getHeight(), this);
		
					Rectangle kr = new Rectangle((int) k.getxCoord(), k.getyCoord(), k.getWidth(), k.getHeight());
					if (kr.intersects(r)) {
					badguys.remove(i);
					knives.remove(j);
					score ++;
					}
					repaint();
				}
			}
		}
	} 
		
		
		
	
		@Override
		public void keyTyped(KeyEvent e) {
			//System.out.println(e);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			
			System.out.println(e);
			if (e.getKeyCode() == 27) {
				startgame = false;
				return;
			}
			
			if (e.getKeyCode() == 32) {
				Projectile knife = new Projectile(link.getxCoord(), link.getyCoord(),30,30,"files/Knife.png");
				knives.add(knife);
				
			}
			
			System.out.println(e);
			
			link.moveIt(e.getKeyCode(),this.getWidth(),this.getHeight()); // move link in response to key press
			for(int i = 0; i < badguys.size(); i++) { // check if badguys hit
				Badguy bg = (Badguy) badguys.get(i); // convert generic
				Rectangle ggr = new Rectangle(link.getxCoord(),link.getyCoord(),link.getWidth(),link.getHeight());
				Rectangle r = new Rectangle(bg.getxCoord(),bg.getyCoord(),bg.getWidth(),bg.getHeight());
				if (ggr.intersects(r)) {
					System.out.println("badguy hit by link");
					badguys.remove(i);
				if(bg.getxCoord()>0) {
					crossed++;
					if(crossed==3) {
						//gameover
						gameover = true;
						
						
					}
				}
				}
			}
			repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}
}





