import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GainsboroPowerup extends Powerup {

	public GainsboroPowerup(double xpos, double ypos) {
		super(xpos, ypos);
		getMyPowerup().setFill(Color.GAINSBORO);
	}

	@Override
	public void takeEffect(Rectangle paddle, Bouncer bouncer, int life, int paddle_speed, boolean isWarp) {
		bouncer.setIronDestroyer(true);
	}

}
