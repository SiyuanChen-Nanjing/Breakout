import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * main game file for Breakout game
 * based on ExampleBounce.java by Robert Duvall
 * @author Siyuan
 *
 */

public class Breakout extends Application {
    private final String TITLE = "Breakout";
    private final int SIZE = 400;
    private final int FRAMES_PER_SECOND = 60;
    private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private final Paint BACKGROUND = Color.WHITE;  
    private final int BOUNCER_SIZE = 4;
    
    private final int BLOCK_SIZE_X = 40;
    private final int BLOCK_SIZE_Y = 20;
    private final int BLOCK_COUNT_X = (SIZE*4/5)/BLOCK_SIZE_X - 1;
    private final int BLOCK_COUNT_Y = (SIZE/2)/BLOCK_SIZE_Y - 1;
    private final String RULES = "1. Control the paddle and the flippers (space) \n\tto prevent the ball from falling.\n"
    		+ "2. Iron blocks could not be broken.\n"
    		+ "3. Red blocks are bombs that makes the game much harder.\n\t Try saving them to the end.\n"
    		+ "4. Powerups:\n"
    		+ "\tRed: makes the paddle longer\n"
    		+ "\tBlue: gives the paddle the ability \n\t\tto warp from one side to the other\n"
    		+ "\tBrown: makes the paddle faster\n"
    		+ "\tGray: makes the paddle iron-breakable\n"
    		+ "\tGreen: awards additional life";
    	    
    private ArrayList<ArrayList<Block>> myBlocks;
    private ArrayList<Powerup> myPowerups = new ArrayList<>();
    private int numBlocks;
    private int numLife = 3;
    private int score = 0;
    private String myName;
    private TreeMap<Integer,String> Leaderboard =  new TreeMap<>(Collections.reverseOrder());
    private boolean isPaddleWarp = false;
    private int paddleSpeed = 15;
    private int myCurrentScene = 1;

    private Label myLifeLabel;
    private Label myScoreLabel;
    private Group myRoot;
    private Stage myStage;
    private Scene myLeaderboard;
    private Scene myStart;
    private Bouncer myBouncer;
    private Rectangle myPaddle;
    private Rectangle myLeftFlipper;
    private Rectangle myRightFlipper;
    
    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
    		setupGameScene(SIZE,SIZE,BACKGROUND,setupLevel1Blocks());
        myStart = startScene();
        stage.setScene(myStart);
        stage.setTitle(TITLE);
        stage.show();
        myStage = stage;
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
    
    // create the startScene of the game;
    private Scene startScene() {
    		Group root = new Group();
    		Scene scene = new Scene(root, SIZE, SIZE, BACKGROUND);
    		TextField playerName = new TextField(); // place to enter player's name to enter leaderboard
    		playerName.setPromptText("Enter your name: ");
    		playerName.setLayoutX(120); playerName.setLayoutY(120);
    		Button start = new Button(); // create start button
    		start.setLayoutX(160); start.setLayoutY(160);
    		start.setText("Breakout");
    		start.setOnAction(value -> {
    			myName = playerName.getText();
    			myStage.setScene(setupGameScene(SIZE,SIZE,BACKGROUND,setupLevel1Blocks()));
    		});
    		root.getChildren().add(start);
    		root.getChildren().add(playerName);
    		Text text = new Text(20, 200, RULES);
    		root.getChildren().add(text);
    		return scene;
    }
    
    // create scene for each level of the game; could decide which level by inputing different block configurations
    private Scene setupGameScene (int width, int height, Paint background, ArrayList<ArrayList<Block>> blocks) {
        Group root = new Group();
        Scene scene = new Scene(root, width, height, background);
        myPaddle = new Rectangle(SIZE/2 - 20, SIZE*29/30 + 0.5, 40, 1); myPaddle.setFill(Color.BLACK);
        myBouncer = new Bouncer(SIZE/2, SIZE*29/30 + 0.5, 0, 0, BOUNCER_SIZE);
        myLifeLabel = new Label("Remaining lives: " + numLife);
        myScoreLabel = new Label("Score: " + score); myScoreLabel.setLayoutX(200);
        myBlocks = blocks;
        setFlippers();
        root.getChildren().add(myBouncer.getMyCircle());
        root.getChildren().add(myPaddle);
        for (ArrayList<Block> column : myBlocks) for (Block b : column) root.getChildren().add(b.getMyBlock());
        root.getChildren().add(myLifeLabel); root.getChildren().add(myScoreLabel);
        root.getChildren().add(myLeftFlipper); root.getChildren().add(myRightFlipper);
        myRoot = root;
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }
    
    private void initializePowerup(double xpos, double ypos) {
		Powerup powerup = new Powerup(xpos, ypos, (int)(Math.random()*4)+1);
		myPowerups.add(powerup);
		myRoot.getChildren().add(powerup.getMyPowerup());
    }
    
    private ArrayList<ArrayList<Block>> setupLevel1Blocks() {
		ArrayList<ArrayList<Block>> blocks = new ArrayList<>();
		blocks = new ArrayList<ArrayList<Block>>();
		int count = 0;
		for (int i = 0; i < BLOCK_COUNT_X; i++) {
			ArrayList<Block> curr = new ArrayList<>();
    			for (int j = 0; j < BLOCK_COUNT_Y; j++) {
    				curr.add(new Block(SIZE/10 + i*BLOCK_SIZE_X + BLOCK_SIZE_X/2, SIZE/20 + 
    					j*BLOCK_SIZE_Y + BLOCK_SIZE_Y/2, BLOCK_SIZE_X, BLOCK_SIZE_Y, 1));
    				count++;
    			}
    			blocks.add(curr);
		}
		numBlocks = count;
		return blocks;
    }
    
    private ArrayList<ArrayList<Block>> setupLevel2Blocks() {
    		ArrayList<ArrayList<Block>> blocks = new ArrayList<>();
        blocks = new ArrayList<ArrayList<Block>>();
        int count = 0;
        for (int i = 0; i < BLOCK_COUNT_X; i++) {
        		ArrayList<Block> curr = new ArrayList<>();
        		for (int j = 0; j < BLOCK_COUNT_Y; j++) {
        			if (j != 1 && j != 6) {
        				curr.add(new Block(SIZE/10 + i*BLOCK_SIZE_X + BLOCK_SIZE_X/2, SIZE/20 + 
            					j*BLOCK_SIZE_Y + BLOCK_SIZE_Y/2 + 20, BLOCK_SIZE_X, BLOCK_SIZE_Y, typeRandomGenerator()));
        				count++;
        			} else curr.add(new Block(SIZE/10 + i*BLOCK_SIZE_X + BLOCK_SIZE_X/2, SIZE/20 + 
        					j*BLOCK_SIZE_Y + BLOCK_SIZE_Y/2 + 20, BLOCK_SIZE_X, BLOCK_SIZE_Y, 4));
        		} blocks.add(curr);
        }   
        numBlocks = count;
        return blocks;
    }
    
    private ArrayList<ArrayList<Block>> setupLevel3Blocks() {
    		ArrayList<ArrayList<Block>> blocks = new ArrayList<>();
        blocks = new ArrayList<ArrayList<Block>>();
        int count = 0;
        for (int i = 0; i < BLOCK_COUNT_X; i++) {
        		ArrayList<Block> curr = new ArrayList<>();
        		for (int j = 0; j < BLOCK_COUNT_Y; j++) {
        			if (j==1 || (i==0 && j!=0 && j!=8) || (i==1 && j==7) || (i==5 && j==7) || (i==6 && j!=0 && j!=8)) {
        				curr.add(new Block(SIZE/10 + i*BLOCK_SIZE_X + BLOCK_SIZE_X/2, SIZE/20 + 
            					j*BLOCK_SIZE_Y + BLOCK_SIZE_Y/2 + 40, BLOCK_SIZE_X, BLOCK_SIZE_Y, 4));
        			}
        			else { curr.add(new Block(SIZE/10 + i*BLOCK_SIZE_X + BLOCK_SIZE_X/2, SIZE/20 + 
            					j*BLOCK_SIZE_Y + BLOCK_SIZE_Y/2 + 40, BLOCK_SIZE_X, BLOCK_SIZE_Y, typeRandomGenerator()));
        				   count++;
        			}
        		} blocks.add(curr);
        }   
        numBlocks = count;
        return blocks;
    }
    
    // tool to determine which type of blocks to generate based on the probability of each type's appearance
    private int typeRandomGenerator() {
    		int type = 0;
		double random = Math.random();
		if (random < 0.5) type = 1;
		else if (random < 0.8) type = 2;
		else if (random < 0.97) type = 3;
		else type = 5;
		return type;
    }
    
    private void resetPaddle() {
    		myRoot.getChildren().remove(myPaddle);
    		myPaddle = new Rectangle(SIZE/2 - 20, SIZE*29/30 + 0.5, 40, 1);
        myPaddle.setFill(Color.BLACK);
        myRoot.getChildren().add(myPaddle);
        isPaddleWarp = false;
        
        myBouncer = new Bouncer(SIZE/2, SIZE*29/30 + 0.5, 0, 0, BOUNCER_SIZE);
		myRoot.getChildren().add(myBouncer.getMyCircle());
    }
    
    // create scene for winning the game
    private Scene setupEndScene() {
    		Group root = new Group();
		Scene scene = new Scene(root, SIZE, SIZE, BACKGROUND);
		
		Label label = new Label("You are out. But what lies beyond...");
		label.setLayoutX(100);
		label.setLayoutY(150);
		root.getChildren().add(label);
		
		root.getChildren().add(restart());
		root.getChildren().add(leaderboard());
		root.getChildren().add(exit(300));
		return scene;
    }
    
    // create scene for losing the game
    private Scene setupFailureScene() {
    		Group root = new Group();
    		Scene scene = new Scene(root, SIZE, SIZE, BACKGROUND);
    		
    		Label label = new Label("You have lost.");
    		label.setLayoutX(150);
    		label.setLayoutY(150);
    		root.getChildren().add(label);
    		
    		root.getChildren().add(restart());
    		return scene;
    }
    
    private void setFlippers() {
    		myLeftFlipper = new Rectangle(0,360,40,1);
        myRightFlipper = new Rectangle(360,360,40,1);
        myLeftFlipper.getTransforms().add(new Rotate(45,myLeftFlipper.getX(),myLeftFlipper.getY()));
        myRightFlipper.getTransforms().add(new Rotate(-45,myRightFlipper.getX()+40,myRightFlipper.getY()+1));
    }
    
    // flip the two flippers once key is pressed
    private void flip() {
    		myLeftFlipper.getTransforms().add(new Rotate(-45,myLeftFlipper.getX(),myLeftFlipper.getY()));
		myRightFlipper.getTransforms().add(new Rotate(45,myRightFlipper.getX()+40,myRightFlipper.getY()+1));
		PauseTransition pause = new PauseTransition();
		pause.setDuration(Duration.seconds(0.5));
		pause.setOnFinished(value -> {
			myLeftFlipper.getTransforms().add(new Rotate(45,myLeftFlipper.getX(),myLeftFlipper.getY()));
			myRightFlipper.getTransforms().add(new Rotate(-45,myRightFlipper.getX()+40,myRightFlipper.getY()+1));
		});
		pause.play();
    }
    
    // setup the leaderboard scene of the game
    private void setupLeaderboard() {
		Leaderboard.put(score, myName);
    		Group root = new Group();
    		Scene scene = new Scene(root,SIZE,SIZE,BACKGROUND);
    		String text = "Rank\tName\tScore\n";
    		int count = 0;
    		for (Integer i : Leaderboard.keySet()) {
    			count++;
    			text += count + "\t\t" + Leaderboard.get(i) + "\t\t" + i + "\n";
    			if (count > 10) break;
    		}
    		
    		Text leaderboard = new Text(100, 50, text);
    		
    		root.getChildren().add(leaderboard);
    		Button restart = restart();
    		restart.setLayoutY(330);
    		root.getChildren().add(restart);
    		root.getChildren().add(exit(370));
    		myLeaderboard = scene;
    }
    
    // create button to reach the leaderboard scene from the winning scene
    private Button leaderboard() {
    		Button lb = new Button("Leaderboard");
    		lb.setLayoutX(160);
    		lb.setLayoutY(240);
    		
    		lb.setOnAction(value -> {
    			setupLeaderboard();
    			myStage.setScene(myLeaderboard);
    		});
    		return lb;
    }
    
    private Button restart() {
    		Button restart = new Button("Restart");
    		restart.setLayoutX(160);
    		restart.setLayoutY(200);
    		
    		restart.setOnAction(value -> {
    			numLife = 3;
    			numBlocks = 1;
    			score = 0;
    			myCurrentScene = 1;
    			myStage.setScene(myStart);
    		});
    		return restart;
    }
    
    private Button exit(double ypos) {
    		Button exit = new Button("Exit game");
    		exit.setLayoutX(160);
    		exit.setLayoutY(ypos);
    		
    		exit.setOnAction(value -> {
    			Platform.exit();
    		});
    		return exit;
    }
    
    // update blocks, check for collisions between blocks and bouncer
    private void breakBlocks(double elapsedTime) {
        for (ArrayList<Block> column : myBlocks) {
        		for (int n = 0; n < column.size(); n++) {
        			if (myBouncer.hitBlock(elapsedTime, column.get(n))) {
        				score += 10;
        				if (column.get(n).getMyType() != 4) {
        					updateBlock(column, n);
        				}
        				else if (myBouncer.isIronDestroyer()) {
        					myRoot.getChildren().remove(column.get(n).getMyBlock());
        					column.remove(n);
        					score += 90;
        				}
        			}
        		}
        }
    }
    
    // update blocks that are hit according to its type
    private void updateBlock(ArrayList<Block> column, int n) {
    		breakBombs(column.get(n));
		column.get(n).setRemainingHits(column.get(n).getRemainingHits() - 1);
		if (column.get(n).getRemainingHits()==0) {
			if (column.get(n).isPowerup()) initializePowerup(column.get(n).getMyBlock().getX()+BLOCK_SIZE_X/2,
					250);
			myRoot.getChildren().remove(column.get(n).getMyBlock());
			column.remove(n);
			numBlocks--;
		}
		else {
			boolean powerup = column.get(n).isPowerup();
			myRoot.getChildren().remove(column.get(n).getMyBlock());
			column.set(n, new Block(column.get(n).getMyBlock().getX(),column.get(n).getMyBlock().getY(),
				column.get(n).getMyBlock().getWidth(),column.get(n).getMyBlock().getHeight(),column.get(n).getRemainingHits()));
			column.get(n).setPowerup(powerup);
			myRoot.getChildren().add(column.get(n).getMyBlock());
		}
    }
    
    // initiate bomb effect when a bomb block is hit
    private void breakBombs(Block b) {
    		if (b.getMyType() == 5) {
			if (Math.random()<0.5) myPaddle.setWidth(myPaddle.getWidth()*0.8);
			else myBouncer.setBouncerXSpeed(myBouncer.getBouncerXSpeed() * 2);
		}
    }
    
    // check for movement and action of powerups
    private void checkForPowerups(double elapsedTime) {
        for (int m = 0; m < myPowerups.size(); m++) {
    			myPowerups.get(m).update(elapsedTime);
    			if (myPowerups.get(m).getMyPowerup().getBoundsInParent().intersects(myPaddle.getBoundsInParent())) {
    				if (myPowerups.get(m).getMyType()==1)
    					myPaddle.setWidth(myPaddle.getWidth()*1.3);
    				else if (myPowerups.get(m).getMyType()==2)
    					isPaddleWarp = true;
    				else if (myPowerups.get(m).getMyType()==3)
    					paddleSpeed += 5;
    				else if (myPowerups.get(m).getMyType()==4)
    					myBouncer.setIronDestroyer(true);
    				else if (myPowerups.get(m).getMyType()==5)
    					numLife++;
    				myRoot.getChildren().remove(myPowerups.get(m).getMyPowerup());
    				myPowerups.remove(m);
    			}
    			else if (myPowerups.get(m).getMyPowerup().getCenterY() > SIZE - 3) {
    				myRoot.getChildren().remove(myPowerups.get(m).getMyPowerup());
    				myPowerups.remove(m);
    			}
        }
    }
    
    // check if a level has been completed
    private void changeLevel() {
        if (numBlocks<=0) {
    			if (myCurrentScene==1) {
    				myStage.setScene(setupGameScene(SIZE, SIZE, BACKGROUND, setupLevel2Blocks()));
    				myCurrentScene=2;
    			}
    			else if (myCurrentScene==2) {
    				myStage.setScene(setupGameScene(SIZE, SIZE, BACKGROUND, setupLevel3Blocks()));
    				myCurrentScene=3;
    			}
    			else if (myCurrentScene==3) {
    				score += 1000 * numLife;
    				myCurrentScene=0;
    				myStage.setScene(setupEndScene());
    			}
        }
    }
    
    private void intersectPaddle() {
    		if (myBouncer.getBouncerXSpeed() == 0 && myBouncer.getBouncerYSpeed() == 0) {
			myBouncer.getMyCircle().setCenterX(myPaddle.getX()+myPaddle.getWidth()/2);
			myBouncer.getMyCircle().setCenterY(myPaddle.getY() - 6);
		}
        if (myBouncer.getMyCircle().getBoundsInParent().intersects(myPaddle.getBoundsInParent())) {
    			myBouncer.setBouncerYSpeed(Math.abs(myBouncer.getBouncerYSpeed()) * -1);
        }
        else if (myBouncer.getMyCircle().getCenterY() + myBouncer.getMyCircle().getRadius() > SIZE - 3) {
    			myRoot.getChildren().remove(myBouncer.getMyCircle());
    			if (numLife>0) {
    				resetPaddle();
    				numLife--;
    			}
    			else {
    				myStage.setScene(setupFailureScene());
    			}
        }
    }
    
    private void intersectFlipper() {
    		if (myBouncer.getMyCircle().getBoundsInParent().intersects(myLeftFlipper.getBoundsInParent()) ||
        		myBouncer.getMyCircle().getBoundsInParent().intersects(myRightFlipper.getBoundsInParent())) {
    			myBouncer.setBouncerYSpeed(Math.abs(myBouncer.getBouncerYSpeed())  * -1);
    		}
    }
    
    private void step (double elapsedTime) {
        intersectPaddle();
        intersectFlipper();
        checkForPowerups(elapsedTime);
        changeLevel();
        breakBlocks(elapsedTime);
        myBouncer.update(elapsedTime);
        myLifeLabel.setText("Remaining lives: " + numLife);
        myScoreLabel.setText("Score: " + score);
    }
    
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
        	 	if ((myPaddle.getX() + myPaddle.getWidth() + paddleSpeed) <= SIZE) {
        	 		myPaddle.setX(myPaddle.getX() + paddleSpeed);
        	 	}
        	 	else if (isPaddleWarp) myPaddle.setX(0);
        }
        else if (code == KeyCode.LEFT) {
        		if ((myPaddle.getX() - paddleSpeed) >= 0)
        			myPaddle.setX(myPaddle.getX() - paddleSpeed);
        		else if (isPaddleWarp) myPaddle.setX(SIZE - myPaddle.getWidth());
        }
        else if (code == KeyCode.SPACE) {
        		if (myBouncer.getBouncerXSpeed() == 0 && myBouncer.getBouncerYSpeed() == 0)
        			myBouncer.initialize(80, -100);
        		else {
        			flip();
        		}
        }
        else if (code == KeyCode.I) myBouncer.setIronDestroyer(true);
        else if (code == KeyCode.DIGIT1) {
        		myCurrentScene = 1;
        		myStage.setScene(setupGameScene(SIZE,SIZE,BACKGROUND,setupLevel1Blocks()));
        }
        else if (code == KeyCode.DIGIT2) {
        		myCurrentScene = 2;
        		myStage.setScene(setupGameScene(SIZE,SIZE,BACKGROUND,setupLevel2Blocks()));
        }
        else if (code == KeyCode.DIGIT3) {
        		myCurrentScene = 3;
        		myStage.setScene(setupGameScene(SIZE,SIZE,BACKGROUND,setupLevel3Blocks()));
        }
        else if (code == KeyCode.W) myStage.setScene(setupEndScene());
        else if (code == KeyCode.F) {
        		myBouncer.setBouncerXSpeed((int)(myBouncer.getBouncerXSpeed() * 1.8));
        		myBouncer.setBouncerYSpeed((int)(myBouncer.getBouncerYSpeed() * 1.8));
        }
        else if (code == KeyCode.L) numLife++;
        else if (code == KeyCode.R) {
        		myRoot.getChildren().remove(myBouncer.getMyCircle());
        		resetPaddle();
        }
    }
    
    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
	
}
