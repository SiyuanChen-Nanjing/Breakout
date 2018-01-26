import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A type of powerup that gives the bouncer the ability to warp from one edge to the other. This class is 
 * one of the five subclasses of Powerup that represent a single type of Powerups. It has its specific 
 * takeEffect() implementation so that calling takeEffect() on it would generate the anticipated effect.
 * @author Siyuan Chen
 *
 */

public class AquaPowerup extends Powerup {
	
	/**
	 * constructor for AquaPowerup that specifies the color of this type of Powerup
	 * @param xpos x-coordinate for center of the powerup
	 * @param ypos y-coordinate for center of the powerup
	 */
	public AquaPowerup(double xpos, double ypos) {
		super(xpos, ypos);
		getMyPowerup().setFill(Color.AQUA);
	}

	@Override
	public void takeEffect(Rectangle paddle, Bouncer bouncer, int life, int paddle_speed, boolean isWarp) {
		isWarp = true;
	}

}