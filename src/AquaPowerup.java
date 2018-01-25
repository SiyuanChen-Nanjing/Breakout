import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AquaPowerup extends Powerup {

	public AquaPowerup(double xpos, double ypos) {
		super(xpos, ypos);
		getMyPowerup().setFill(Color.AQUA);
	}

	@Override
	public void takeEffect(Rectangle paddle, Bouncer bouncer, int life, int paddle_speed, boolean isWarp) {
		isWarp = true;
	}

}
