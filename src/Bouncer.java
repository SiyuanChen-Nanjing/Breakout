import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Bouncer {
	
	private Circle myCircle;
	private int bouncerXSpeed;
	private int bouncerYSpeed;
	private boolean isIronDestroyer = false;
	private final int SIZE = 400;
	
	public Bouncer(double x, double y, int speed_x, int speed_y, double radius) {
		setBouncerXSpeed(speed_x);
		setBouncerYSpeed(speed_y);
		
		setMyCircle(new Circle(x,y, radius));
	}
	
	// update positions after checking if a bouncer will hit the wall
	public void update(double elapsedTime) {
	    double updatedXPosition = getMyCircle().getCenterX() + getMyCircle().getRadius() + getBouncerXSpeed() * elapsedTime;
		double updatedYPosition = getMyCircle().getCenterY() + getMyCircle().getRadius() + getBouncerYSpeed() * elapsedTime;
		if (updatedXPosition > SIZE | updatedXPosition < 0) setBouncerXSpeed(getBouncerXSpeed() * -1);
		if (updatedYPosition > SIZE | updatedYPosition < 0) setBouncerYSpeed(getBouncerYSpeed() * -1);
		
		getMyCircle().setCenterX(getMyCircle().getCenterX() + getBouncerXSpeed() * elapsedTime); 
    		getMyCircle().setCenterY(getMyCircle().getCenterY() + getBouncerYSpeed() * elapsedTime);
	}
	
	// detect collision between bouncer and block; change direction accordingly
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
	
	public void initialize(int speed_x, int speed_y) {
		setBouncerXSpeed(speed_x);
		setBouncerYSpeed(speed_y);
	}

	public Circle getMyCircle() {
		return myCircle;
	}

	public void setMyCircle(Circle myCircle) {
		this.myCircle = myCircle;
	}

	public int getBouncerXSpeed() {
		return bouncerXSpeed;
	}

	public void setBouncerXSpeed(int bouncerXSpeed) {
		this.bouncerXSpeed = bouncerXSpeed;
	}

	public int getBouncerYSpeed() {
		return bouncerYSpeed;
	}

	public void setBouncerYSpeed(int bouncerYSpeed) {
		this.bouncerYSpeed = bouncerYSpeed;
	}

	public boolean isIronDestroyer() {
		return isIronDestroyer;
	}

	public void setIronDestroyer(boolean isIronDestroyer) {
		this.isIronDestroyer = isIronDestroyer;
	}
	
}
