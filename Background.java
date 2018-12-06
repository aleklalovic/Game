package one;

import java.awt.Image;
import java.awt.Toolkit;

public class Background {

	private int xCoord = 0;
	private int yCoord = 0;
	private int width = 10; 
	private int height = 10;
	private Image img; 
	
	/**
	 * Badguy default constructor
	 */
	public Background() {
		setxCoord(10);
		setyCoord(10);
		setWidth(30);
		setHeight(30); 
		setImg("files/Background.jpg");
	}
	
	/**
	 * Goodguy overloaded constructor
	 * @param x initial x location
	 * @param y initial y location
	 * @param w initial width
	 * @param h initial height
	 */
	public Background(int x, int y, int w, int h, String imgpath)  {
		setxCoord(x);
		setyCoord(y);
		setWidth(w);
		setHeight(h); 
		setImg(imgpath) ;
	}
	
	public void setImg(Image img) {
		this.img = img;
	} 
	public void setImg(String imgpath) {
		this.img = Toolkit.getDefaultToolkit().getImage(imgpath);
	}

	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImg() {
		return img;
	}

	
	
}
