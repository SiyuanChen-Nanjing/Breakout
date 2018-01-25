import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BrownPowerup extends Powerup {

	public BrownPowerup(double xpos, double ypos) {
		super(xpos, ypos);
		getMyPowerup().setFill(Color.BROWN);
	}

	@Override
	public void takeEffect(Rectangle paddle, Bouncer bouncer, int life, int paddle_speed, boolean isWarp) {
		paddle_speed += 7;
	}

}
