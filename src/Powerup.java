import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Powerup {
	private Circle myPowerup;
	private final int SPEED_Y = 70;
	private final int RADIUS = 5;
	private int myType;
	
	/**
	 * default constructor for Powerup
	 * @param xpos x coordinate of center
	 * @param ypos y coordinate of center
	 * @param type type number
	 */
	public Powerup(double xpos, double ypos, int type) {
		setMyPowerup(new Circle(xpos, ypos, RADIUS));
		switch (type) {
		case 1:getMyPowerup().setFill(Color.RED);
		break;
		case 2:getMyPowerup().setFill(Color.AQUA);
		break;
		case 3:getMyPowerup().setFill(Color.BROWN);
		break;
		case 4:getMyPowerup().setFill(Color.GAINSBORO);
		break;
		case 5:getMyPowerup().setFill(Color.CHARTREUSE);
		break;
		}
		myType = type;
	}
	
	/**
	 * make the powerup drop
	 * @param elapsedTime time elapsed per frame
	 */
	public void update(double elapsedTime) {
		getMyPowerup().setCenterY(elapsedTime * SPEED_Y + getMyPowerup().getCenterY());
	}
	
	public int getMyType() {
		return myType;
	}

	public void setMyType(int myType) {
		this.myType = myType;
	}

	public Circle getMyPowerup() {
		return myPowerup;
	}

	public void setMyPowerup(Circle myPowerup) {
		this.myPowerup = myPowerup;
	}
}
