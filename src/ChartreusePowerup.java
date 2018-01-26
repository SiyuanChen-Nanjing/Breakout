import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A type of powerup that gives an additional life. This class is one of the five subclasses of Powerup that
 * represent a single type of Powerups. It has its specific takeEffect() implementation so that calling 
 * takeEffect() on it would generate the anticipated effect.
 * @author Siyuan Chen
 *
 */

public class ChartreusePowerup extends Powerup {

	/**
	 * constructor for ChartreusePowerup that specifies the color of this type of Powerup
	 * @param xpos x-coordinate for center of the powerup
	 * @param ypos y-coordinate for center of the powerup
	 */
	public ChartreusePowerup(double xpos, double ypos) {
		super(xpos, ypos);
		getMyPowerup().setFill(Color.CHARTREUSE);
	}

	@Override
	public void takeEffect(Rectangle paddle, Bouncer bouncer, int life, int paddle_speed, boolean isWarp) {
		life++;
	}
	
}