import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Create a bouncer to be used in the Breakout game. The bouncer could collide with the walls, blocks,
 * paddle and flippers.
 * @author Siyuan Chen
 *
 */

public class Bouncer {
	
	private Circle myCircle;
	private int bouncerXSpeed;
	private int bouncerYSpeed;
	private boolean isIronDestroyer = false;
	private final int SIZE = 400;
	
	/**
	 * default constructor for Bouncer
	 * @param x x coordinate of center
	 * @param y y coordinate of center
	 * @param speed_x x-direction speed
	 * @param speed_y y-direction speed
	 * @param radius radius of bouncer
	 */
	public Bouncer(double x, double y, int speed_x, int speed_y, double radius) {
		setBouncerXSpeed(speed_x);
		setBouncerYSpeed(speed_y);
		
		myCircle = new Circle(x,y, radius);
	}
	
	/**
	 * update positions after checking if a bouncer will hit the wall
	 * @param elapsedTime time elapsed per frame
	 */
	public void update(double elapsedTime) {
	    double updatedXPosition = getMyCircle().getCenterX() + getMyCircle().getRadius() + getBouncerXSpeed() * elapsedTime;
		double updatedYPosition = getMyCircle().getCenterY() + getMyCircle().getRadius() + getBouncerYSpeed() * elapsedTime;
		if (updatedXPosition > SIZE | updatedXPosition < 0) setBouncerXSpeed(getBouncerXSpeed() * -1);
		if (updatedYPosition > SIZE | updatedYPosition < 0) setBouncerYSpeed(getBouncerYSpeed() * -1);
		
		getMyCircle().setCenterX(getMyCircle().getCenterX() + getBouncerXSpeed() * elapsedTime); 
    		getMyCircle().setCenterY(getMyCircle().getCenterY() + getBouncerYSpeed() * elapsedTime);
	}
	
	/**
	 * detect collision between bouncer and block; change direction accordingly
	 * @param elapsedTime time elapsed per frame
	 * @param b the Block to detect
	 * @return whether the Block is hit
	 */
	public boolean hitBlock(double elapsedTime, Block b) {
		double xspeed = getBouncerXSpeed();
		double yspeed = getBouncerYSpeed();
		if (getMyCircle().getBoundsInParent().intersects(b.getMyBlock().getBoundsInParent())) {
			if (getMyCircle().getBoundsInParent().intersects(new Rectangle(b.getMyBlock().getX(),b.getMyBlock().getY(),2,b.getMyBlock().getHeight()).getBoundsInParent())) setBouncerXSpeed((int) (-1 * Math.abs(xspeed)));
			else if (getMyCircle().getBoundsInParent().intersects(new Rectangle(b.getMyBlock().getX()+b.getMyBlock().getWidth(),b.getMyBlock().getY(),2,b.getMyBlock().getHeight()).getBoundsInParent())) setBouncerXSpeed((int) (Math.abs(xspeed)));
			else if (getMyCircle().getBoundsInParent().intersects(new Rectangle(b.getMyBlock().getX(),b.getMyBlock().getY()-1,b.getMyBlock().getWidth()+1,2).getBoundsInParent())) setBouncerYSpeed((int) (-1 * Math.abs(yspeed)));
			else setBouncerYSpeed((int) (Math.abs(yspeed)));
			getMyCircle().setCenterX(getMyCircle().getCenterX() - (elapsedTime* 1.03) * xspeed);
			getMyCircle().setCenterY(getMyCircle().getCenterY() - (elapsedTime* 1.03) * yspeed);
			return true;
		}
		return false;
	}
	
	/**
	 * check if bouncer hits a paddle
	 * @param paddle the target paddle to check
	 * @return whether the bouncer hits the paddle
	 */
	public boolean hitPaddle(Rectangle paddle) {
		return myCircle.getBoundsInParent().intersects(paddle.getBoundsInParent());
	}
	
	/**
	 * count for the movement of the bouncer with the paddle before it leaves the paddle
	 * @param paddle the target paddle that the bouncer resides on
	 */
	public void moveWithPaddle(Rectangle paddle) {
		myCircle.setCenterX(paddle.getX()+paddle.getWidth()/2);
		myCircle.setCenterY(paddle.getY() - 6);
	}
	
	/**
	 * check for intersection between the bouncer and two flippers, update bouncer's y-direction speed if
	 * collision is detected
	 * @param left left-side flipper in the game
	 * @param right right-side flipper in the game
	 */
	public void intersectFlipper(Rectangle left, Rectangle right) {
		if (myCircle.getBoundsInParent().intersects(left.getBoundsInParent()) ||
        		myCircle.getBoundsInParent().intersects(right.getBoundsInParent())) {
    			bouncerYSpeed = Math.abs(bouncerYSpeed)  * -1;
    		}
	}
	
	/**
	 * initialize the bouncer to make it move
	 * @param speed_x x-direction speed
	 * @param speed_y y-direction speed
	 */
	public void initialize(int speed_x, int speed_y) {
		setBouncerXSpeed(speed_x);
		setBouncerYSpeed(speed_y);
	}
	
	/**
	 * getter for myCircle
	 * @return myCircle
	 */
	public Circle getMyCircle() {
		return myCircle;
	}
	
	/**
	 * getter for bouncerXSpeed
	 * @return bouncerXSpeed
	 */
	public int getBouncerXSpeed() {
		return bouncerXSpeed;
	}
	
	/**
	 * setter for bouncerXSpeed
	 * @param bouncerXSpeed input speed
	 */
	public void setBouncerXSpeed(int bouncerXSpeed) {
		this.bouncerXSpeed = bouncerXSpeed;
	}

	/**
	 * getter for bouncerYSpeed
	 * @return bouncerYSpeed
	 */
	public int getBouncerYSpeed() {
		return bouncerYSpeed;
	}
	
	/**
	 * setter for bouncerYSpeed
	 * @param bouncerYSpeed input speed
	 */
	public void setBouncerYSpeed(int bouncerYSpeed) {
		this.bouncerYSpeed = bouncerYSpeed;
	}

	/**
	 * getter for isIronDestroyer 
	 * @return isIronDestroyer (boolean variable for whether the bouncer could destroy iron blocks)
	 */
	public boolean isIronDestroyer() {
		return isIronDestroyer;
	}
	
	/**
	 * setter for isIronDestroyer
	 * @param isIronDestroyer input boolean
	 */
	public void setIronDestroyer(boolean isIronDestroyer) {
		this.isIronDestroyer = isIronDestroyer;
	}
	
}
