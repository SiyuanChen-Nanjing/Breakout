import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChartreusePowerup extends Powerup {

	public ChartreusePowerup(double xpos, double ypos) {
		super(xpos, ypos);
		getMyPowerup().setFill(Color.CHARTREUSE);
	}

	@Override
	public void takeEffect(Rectangle paddle, Bouncer bouncer, int life, int paddle_speed, boolean isWarp) {
		life++;
	}
	
}
