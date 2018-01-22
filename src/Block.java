import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Block {
	
	private Rectangle myBlock;
	private int remainingHits;
	private int myType;
	private final Paint COLOR_GLASS = Color.ALICEBLUE;
	private final Paint COLOR_WOOD = Color.BLANCHEDALMOND;
	private final Paint COLOR_STONE = Color.DARKORANGE;
	private final Paint COLOR_IRON = Color.DIMGRAY;
	private final Paint COLOR_BOMB = Color.RED;
	private boolean isPowerup = false;
	
	public Block(double X_Position, double Y_Position, double a, double b, int type) {
		myBlock = new Rectangle(X_Position, Y_Position, a, b);
		myBlock.setArcHeight(10);
		myBlock.setArcWidth(5);
		myType = type;
		if (type==4) {setRemainingHits(Integer.MAX_VALUE); getMyBlock().setFill(COLOR_IRON);}
		else if (type==5) {setRemainingHits(1); getMyBlock().setFill(COLOR_BOMB);}
		else {setRemainingHits(type);
			if (type==1) {
				getMyBlock().setFill(COLOR_GLASS);
				if (Math.random()<0.05) setPowerup(true);
			}
			else if (type==2) {
				getMyBlock().setFill(COLOR_WOOD);
				if (Math.random()<0.07) setPowerup(true);
			}
			else if (type==3) {
				getMyBlock().setFill(COLOR_STONE);
				if (Math.random()<0.1) setPowerup(true);
			}
		}
		getMyBlock().setStroke(Color.BLACK);
	}

	public Rectangle getMyBlock() {
		return myBlock;
	}

	public void setMyBlock(Rectangle myBlock) {
		this.myBlock = myBlock;
	}

	public int getMyType() {
		return myType;
	}

	public void setMyType(int myType) {
		this.myType = myType;
	}

	public int getRemainingHits() {
		return remainingHits;
	}

	public void setRemainingHits(int remainingHits) {
		this.remainingHits = remainingHits;
	}

	public boolean isPowerup() {
		return isPowerup;
	}

	public void setPowerup(boolean isPowerup) {
		this.isPowerup = isPowerup;
	}

	
}
