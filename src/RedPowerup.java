import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RedPowerup extends Powerup {

	public RedPowerup(double xpos, double ypos) {
		super(xpos, ypos);
		getMyPowerup().setFill(Color.RED);
	}

	@Override
	public void takeEffect(Rectangle paddle, Bouncer bouncer, int life, int paddle_speed, boolean isWarp) {
		paddle.setWidth(paddle.getWidth()*1.3);
	}
}
