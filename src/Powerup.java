import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Create Powerup object to be used in Breakout game. Powerups have 5 types and each type has distinct
 * effects.
 * @author Siyuan Chen
 *
 */

public abstract class Powerup {
	private Circle myPowerup;
	private final int SPEED_Y = 70;
	private final int RADIUS = 5;
	
	/**
	 * default constructor for Powerup
	 * @param xpos x coordinate of center
	 * @param ypos y coordinate of center
	 * @param type type number
	 */
	public Powerup(double xpos, double ypos) {
		myPowerup = new Circle(xpos, ypos, RADIUS);
	}
	
	/**
	 * make the powerup drop
	 * @param elapsedTime time elapsed per frame
	 */
	public void update(double elapsedTime) {
		getMyPowerup().setCenterY(elapsedTime * SPEED_Y + getMyPowerup().getCenterY());
	}

	/**
	 * getter for myPowerup
	 * @return myPowerup
	 */
	public Circle getMyPowerup() {
		return myPowerup;
	}
	
	/**
	 * allow the specific powerup to take effect
	 * @param paddle current functioning paddle in the game
	 * @param bouncer current functioning bouncer in the game
	 * @param life current number of lives left for the player
	 * @param paddle_speed current speed of paddle
	 * @param isWarp whether the current paddle could warp from one edge to the other
	 */
	public abstract void takeEffect(Rectangle paddle, Bouncer bouncer, int life, int paddle_speed, boolean isWarp);
}
