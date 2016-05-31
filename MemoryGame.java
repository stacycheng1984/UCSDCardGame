/**
 * This is a easy Memory Game featured by it's sound effect and special
 * pictures. It also helps us keep memory.
 */
package MemoryGame;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;

/**
 * @author Yihang Cheng, Zhuojun Chen
 * @PID A92039418(Cheng), A92108974(Chen)
 * about: the program is just for fun, a little picture memory game
 *
 * compile: javac –cp “../lib/*:.” MemoryGame.java
 * run: java -cp "../lib/*:." MemoryGame
 */
public class MemoryGame extends Application implements EventHandler {
  //set up some final parameters
  public final static int START_WIDTH = 488;//width of new game button
  public final static int START_HEIGHT = 79;//height of new game button
  public final static int INSTR_WIDTH = 328;//width of instruction button
  public final static int INSTR_HEIGHT = 79;//height of instruction button
  public final static int EXIT_WIDTH = 110; //same as above
  public final static int EXIT_HEIGHT = 79;

  public final static int MAIN_POSX = 465;  //the x-coordinate of start button
  public final static int START_POSY = 210; //the y-coordinate of start button
  public final static int INSTR_POSY = 342; //same as about
  public final static int EXIT_POSY = 480;
  public final static int RIGHY_POSX = 447;

	private final String NAME_1 = "HeadStar1_1.png";//the picture files
	private final String NAME_2 = "HeadStar1_2.png";//of the featured card heads
	private final String NAME_3 = "HeadStar1_3.png";
	private final String NAME_4 = "HeadStar2_1.png";
	private final String NAME_5 = "HeadStar2_2.png";
	private final String NAME_6 = "HeadStar3.png";
	private final String BACKGROUND_NAME =          //The main interface's bkgr
					"./mainBackground.png";
	private final String BACKGROUND_MUSIC = "Canon.mp3";//Background music
	private final String CORRECT_MUSIC = "correct.mp3"; //file name: correct sound
	private final String WRONG_MUSIC = "wrong.mp3";     //wrong sound
	private final String FLIP_MUSIC = "flip.mp3";       //flip card sound
  private final int CARD_COL_R1 = 155;//the width of card columns
  private final int CARD_COL_R2 = 135;//for GUI design
  private final int CARD_COL_R3 = 63;
  private final int CARD_COL_R4 = 100;
  private final int CARD_COL_R5 = 135;
  private final int CARD_COL_R6 = 64;
  private final int CARD_COL_R7 = 135;
  private final int CARD_ROW_R1 = 52; //the width of card rows
  private final int CARD_ROW_R2 = 210;//for GUI design
  private final int CARD_ROW_R3 = 29;
  private final int CARD_ROW_R4 = 210;
  private final int CARD_ROW_R5 = 29;
  private final int CARD_ROW_R6 = 210;
  private final int MENU_G_PREFSIZE_W = 677;//the width of score and leftmove
  private final int MENU_G_PREFSIZE_H = 800;//menu
  private final int MENU_G_COL_R1 = 90;     //the width of menu columns
  private final int MENU_G_COL_R2 = 142;    //for GUI design
  private final int MENU_G_COL_R3 = 435;
  private final int MENU_G_ROW_R1 = 120;    //the width of menu rows
  private final int MENU_G_ROW_R2 = 70;     //for GUI design
  private final int MENU_G_ROW_R3 = 250;
  private final int MENU_G_ROW_R4 = 100;
  protected final Double STAGE_HEIGHT = 800.0;//height of the stage
  protected final Double STAGE_WIDTH = 1400.0;//width of the stage
  private final int ROTATE_TIME = 300;        //duration time of the flipping
  private final int ROTATE_ANGLE_START = 0;   //rotation parameter
  private final int ROTATE_ANGLE_END = 360;   //rotation parameter
  private final int ROTATE_CYCLE = 1;         //rotation circle number
  protected final int INITIAL_MOVE = 5;       //initial moves every game
  protected final int INITIAL_SCORE = 0;      //initial score every game
  private final int NUM_HEAD = 6;             //the number of diff heads
  private final int NUM_PAIR = 2;             //num of cards with same head
  private final int PREVIEW_TIME = 3;         //the seconds of preview
	private boolean allFound = false;           //if the cards are all found
	private boolean gameOver = false;           //if the game is over
	protected static int[] arr;                 //to randomize and assign number
	protected static int[] newarr;              //to the card group
	protected static String[] picName;          //in case to assign the head pic
	protected static  ArrayList<Integer> newList;//same as above
	protected Card[][] cards;                   //the card array
	protected static RotateTransition[][] rotater;//the object used to rotate card
	protected Card selectedCard;                //to store the selected card
	protected Label leftMovesLabel;             //display the moves left
	protected Label scoreLabel;                 //display the score earned
	protected Timeline tl;                      //a timer for preview before game
  protected Timeline tlconti;

  //declare some necessary objects
  Scene themeScene, mainScene, instructionScene,
          playScene, exitScene, gameOverScene, congratScene;
  BorderPane mainPane, playPaneRoot, exitPane;
  GridPane themePane, mainMenuPane, gameOverPane, instructionPane, leftMain,
          rightMain, cardGrid, menuGrid, congratPane;
  Button themeButton, gameOverButton, backHomeButtomI, backHomeButtomP,
          backHomeButtomE, instructionButton, congratButton,
          playButton, exitButton, instructionExitButton;
  Label instructionLabel, playLabel, exitLabel;

  /**
   * main method to lauch the application.
   * @param args
   */
	public static void main(String[] args) {
		launch(args);
	}

  /**
   * the default constructor
   */
	public MemoryGame() {
		super();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("UCSD Memory Game");
    stage.setMinWidth(STAGE_WIDTH);
    stage.setMaxWidth(STAGE_WIDTH);
    stage.setMinHeight(STAGE_HEIGHT);
    stage.setMaxHeight(STAGE_HEIGHT);

		// the parent pane on the play scene
		BorderPane playPaneRoot = new BorderPane();

		// two children pane
		cardGrid = new GridPane();//pane displaying cards
    menuGrid = new GridPane();//pane displaying menu


		// the scene that allow users play the game
		Scene playScene = new Scene(playPaneRoot, STAGE_WIDTH, STAGE_WIDTH);

		playPaneRoot.setTop(null);       //GUI design
		playPaneRoot.setBottom(null);
		playPaneRoot.setLeft(null);
		playPaneRoot.setCenter(cardGrid);
		playPaneRoot.setRight(menuGrid);

		// set the width of the column of cardPane, GUI design
		cardGrid.getColumnConstraints().add(new ColumnConstraints(CARD_COL_R1));
		cardGrid.getColumnConstraints().add(new ColumnConstraints(CARD_COL_R2));
		cardGrid.getColumnConstraints().add(new ColumnConstraints(CARD_COL_R3));
		cardGrid.getColumnConstraints().add(new ColumnConstraints(CARD_COL_R4));
		cardGrid.getColumnConstraints().add(new ColumnConstraints(CARD_COL_R5));
		cardGrid.getColumnConstraints().add(new ColumnConstraints(CARD_COL_R6));
		cardGrid.getColumnConstraints().add(new ColumnConstraints(CARD_COL_R7));

    // set the height of the row of the cardPane, GUI design
		cardGrid.getRowConstraints().add(new RowConstraints(CARD_ROW_R1));
		cardGrid.getRowConstraints().add(new RowConstraints(CARD_ROW_R2));
		cardGrid.getRowConstraints().add(new RowConstraints(CARD_ROW_R3));
		cardGrid.getRowConstraints().add(new RowConstraints(CARD_ROW_R4));
		cardGrid.getRowConstraints().add(new RowConstraints(CARD_ROW_R5));
		cardGrid.getRowConstraints().add(new RowConstraints(CARD_ROW_R6));

		// set the width of the column of menu, GUI design
		menuGrid.setPrefSize(MENU_G_PREFSIZE_W , MENU_G_PREFSIZE_H );
		menuGrid.getColumnConstraints().add(new ColumnConstraints(MENU_G_COL_R1));
		menuGrid.getColumnConstraints().add(new ColumnConstraints(MENU_G_COL_R2));
		menuGrid.getColumnConstraints().add(new ColumnConstraints(MENU_G_COL_R3));

    // set the height of the row of menu
		menuGrid.getRowConstraints().add(new RowConstraints(MENU_G_ROW_R1));
		menuGrid.getRowConstraints().add(new RowConstraints(MENU_G_ROW_R2));
		menuGrid.getRowConstraints().add(new RowConstraints(MENU_G_ROW_R3));
		menuGrid.getRowConstraints().add(new RowConstraints(MENU_G_ROW_R4));
		menuGrid.getRowConstraints().add(new RowConstraints(MENU_G_ROW_R4));
		menuGrid.getRowConstraints().add(new RowConstraints(MENU_G_ROW_R4));

    Button exitPlayButton = new Button();//create the exit button
    exitPlayButton.setMaxSize(110, 78);  //set the button's size
    exitPlayButton.setMinSize(110, 78);  //set it to be transparent
    exitPlayButton.setStyle("-fx-background-color: transparent;");
    menuGrid.getChildren().add(exitPlayButton);   //put the Button in the scene
    GridPane.setConstraints(exitPlayButton, 2, 1);//put it on to specific seat
    exitPlayButton.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        stage.setScene(mainScene);                //go to main if clicked
      }
    });

    //set the background Image for the game interface
    Image bkqd = new Image(getClass().getResourceAsStream(BACKGROUND_NAME));
    BackgroundImage bk = new BackgroundImage
            (bkqd, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    playPaneRoot.setBackground(new Background(bk));

		cards = new Card[NUM_HEAD][NUM_PAIR];//creat array for Cards
		rotater = new RotateTransition[NUM_HEAD][NUM_PAIR];//and their rotater

		picName = new String[]
				{NAME_1, NAME_2, NAME_3, NAME_4, NAME_5, NAME_6};

    //set and initialize the musics and sound effects
		URL correctSound = getClass().getResource(CORRECT_MUSIC);
		MediaPlayer mpC = new MediaPlayer(new Media(correctSound.toString()));

		URL wrongSound = getClass().getResource(WRONG_MUSIC);
		MediaPlayer mpW = new MediaPlayer(new Media(wrongSound.toString()));

		URL flipSound = getClass().getResource(FLIP_MUSIC);
		MediaPlayer mpF = new MediaPlayer(new Media(flipSound.toString()));

		URL resource = getClass().getResource(BACKGROUND_MUSIC);
		MediaPlayer a =new MediaPlayer(new Media(resource.toString()));

		a.setOnEndOfMedia(new Runnable() {
			public void run() {
				a.seek(Duration.ZERO);//play the background music repeatedly
			}
		});

		a.play();                 //background music begins

    //FIXME show the count down

    //Set up the Timeline to show 3 secs preview before the game
    tl = new Timeline(new KeyFrame(Duration.seconds(PREVIEW_TIME),
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent actionEvent) {
                allFlipDown();
              }
            }
    ));

    newList = randomizePosition();

    //create card, set on its action, and assign it to random position
		for (int i = 0; i < cards.length; i++) {
			for (int j = 0; j < cards[i].length; j++) {  //for all cards

				Card c = new Card(picName[i], i);          //create a Card
				RotateTransition r = createRotator(c);     //create its Rotater

				cards[i][j] = c;                           //pass it in
				rotater[i][j] = r;

				c.setOnAction((ActionEvent event) -> {     //set on the Action
					if (c.isFound()) {                       //skip it if it's found
						return;
					}

					if (Card.leftMove <= 0) {                //end the game if there is
						gameOver = true;                       //no more moves left
            stage.setScene(gameOverScene);
            renewGame();
						return;
					}

					if (selectedCard == c) {                 //cancel the selection if
						selectedCard = null;                   //clicked again
						mpF.play();
						mpF.seek(mpF.getStartTime());          //add sound effect
						r.play();
						c.setGraphic(c.tailIm);                //flip back
						return;
					}

					if (selectedCard != null) {
						if (c.getCardID() == selectedCard.getCardID()) {
							r.play();
							c.setGraphic(c.im);
							c.setFound();
							selectedCard.setFound();              //flip up and stay (with mp3
							selectedCard = null;                  //if the cards are the same
              Card.leftMove += 2;

							switch (c.getCardID()) {              //increase the score
								case 0:                             //according to the stars
								case 1:
								case 2:
									Card.score += 10;
									scoreLabel.setText("" + Card.score);
									break;

								case 3:
								case 4:
									Card.score += 20;
									scoreLabel.setText("" + Card.score);
									break;

								case 5:
									Card.score += 30;
									scoreLabel.setText("" + Card.score);
									break;

							}

							c.leftMove -= 1;                       //update the leftmove num
							leftMovesLabel.setText("" + Card.leftMove);

							if (c.leftMove <= 0) {
                stage.setScene(gameOverScene);       //end the game if out of
                Card.leftMove = INITIAL_MOVE;        //moves
                Card.score = INITIAL_SCORE;
                renewGame();
								return;
							}

							mpC.play();                            //add the correct sound
							mpC.seek(mpC.getStartTime());
							if (allFound()) {
//FIXME transition animation
                renewGame();
                addCard();
                tl.play();
              }
							return;
						} else {
							try {
								mpW.play();
								mpW.seek(mpW.getStartTime());        //add the wrong sound
								c.leftMove -= 1;                     //update the move
								leftMovesLabel.setText("" + Card.leftMove);
								if (c.leftMove <= 0) {
                  stage.setScene(gameOverScene);
                  Card.leftMove = INITIAL_MOVE;      //update the move for new G
                  Card.score = INITIAL_SCORE;
                  selectedCard = null;
                  scoreLabel.setText("" + Card.score);
									return;
								}
								r.play();
								c.setGraphic(c.tailIm);              //flip back if wrong
								selectedCard.getR().play();
								selectedCard.setGraphic(selectedCard.tailIm);
								selectedCard = null;
								return;
							} catch (Exception e) {}
						}
					} else {
						selectedCard = c;                        //if there is no card
						mpF.play();                              //selected, set c selected
						mpF.seek(mpF.getStartTime());            //add flip sound
						r.play();
						c.setGraphic(c.im);
					}
				});

        cardGrid.getChildren().add(cards[i][j]);   //add it in the pane

			}
		}


		// label: score
		scoreLabel = new Label();//score for the player get
		scoreLabel.setText("" + Card.score);      //set the score to score label
		scoreLabel.setFont(new Font("Arial", 40));//change the font and color in the
		scoreLabel.setTextFill(Color.web("#FFFFFF"));//score label
		menuGrid.getChildren().add(scoreLabel);   //add the label to the pane and
		GridPane.setConstraints(scoreLabel, 1, 3);// assign a specific seat



		// Label: leftMoves
		leftMovesLabel = new Label();// left moves for players
		leftMovesLabel.setText("" + Card.leftMove);// set the number to label

		leftMovesLabel.setFont(new Font("Arial", 40));// change the font and color
		leftMovesLabel.setTextFill(Color.web("#FFFFFF"));
		menuGrid.getChildren().add(leftMovesLabel);   // add the label to the pane
		GridPane.setConstraints(leftMovesLabel, 1, 5);// and specific seat

		scoreLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				scoreLabel.setScaleX(1.5);//zoom out the score if mouse enters
				scoreLabel.setScaleY(1.5);
			}
		});

		scoreLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				scoreLabel.setScaleX(1);//zoom to original size when mouse exit
				scoreLabel.setScaleY(1);
			}
		});

		leftMovesLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				leftMovesLabel.setScaleX(1.5);//zoom out the score if mouse enters
				leftMovesLabel.setScaleY(1.5);
			}
		});

		leftMovesLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent e) {
				leftMovesLabel.setScaleX(1);//zoom to original size when mouse exit
				leftMovesLabel.setScaleY(1);
			}
		});

    //initialize buttons
    instructionButton = new Button();
    playButton = new Button();
    exitButton = new Button();
    backHomeButtomE = new Button("Back to home");
    backHomeButtomP = new Button("Back to home");
    backHomeButtomI = new Button("Back to home");
    themeButton = new Button();
    gameOverButton = new Button();
    congratButton = new Button();
    instructionExitButton = new Button();

//FIXME background image
    Image theme = new Image
            (getClass().getResourceAsStream("themeBackground.png"));
    ImageView themeaImgView = new ImageView(theme);//add the background image
    themeaImgView.setFitHeight(STAGE_HEIGHT);      // set the height of image
    themeaImgView.setFitWidth(STAGE_WIDTH);        // set the width of image
    themeButton.setMinHeight(STAGE_HEIGHT);        // set the width and height
    themeButton.setMaxHeight(STAGE_HEIGHT);        // of the them button
    themeButton.setMinWidth(STAGE_WIDTH);
    themeButton.setMaxWidth(STAGE_WIDTH);
    themeButton.setGraphic(themeaImgView);          // set the image

    themePane = new GridPane();

    themePane.getChildren().add(themeButton);       // add the theme button to
    GridPane.setConstraints(themeButton, 0, 0);     // the scene

    themeScene = new Scene(themePane, STAGE_WIDTH, STAGE_HEIGHT);

    Image gO = new Image                       // game over image
            (getClass().getResourceAsStream("GameOver.png"));
    ImageView gameOver = new ImageView(gO);    //set the width and height of
    gameOver.setFitHeight(STAGE_HEIGHT);       //the image
    gameOver.setFitWidth(STAGE_WIDTH);
    gameOverButton.setMinHeight(STAGE_HEIGHT); //set the width and height of
    gameOverButton.setMaxHeight(STAGE_HEIGHT); //gameOver button
    gameOverButton.setMinWidth(STAGE_WIDTH);
    gameOverButton.setMaxWidth(STAGE_WIDTH);
    gameOverButton.setGraphic(gameOver);

    gameOverPane = new GridPane();

    gameOverPane.getChildren().add(gameOverButton); // add the game over button
    GridPane.setConstraints(gameOverButton, 0, 0);  // to the pane

    gameOverScene = new Scene(gameOverPane, STAGE_WIDTH, STAGE_HEIGHT);

    Image cong = new Image                          // congrats image
            (getClass().getResourceAsStream("Congratulations.png"));
    ImageView congrat = new ImageView(cong);
    congrat.setFitHeight(STAGE_HEIGHT);             // set the width and height
    congrat.setFitWidth(STAGE_WIDTH);               // of the image
    congratButton.setMinHeight(STAGE_HEIGHT);       // set the size of button
    congratButton.setMaxHeight(STAGE_HEIGHT);
    congratButton.setMinWidth(STAGE_WIDTH);
    congratButton.setMaxWidth(STAGE_WIDTH);
    congratButton.setGraphic(congrat);

    congratPane = new GridPane();

    congratPane.getChildren().add(congratButton);    // add the congrats button
    GridPane.setConstraints(congratButton, 0, 0);    // to the pane

    congratScene = new Scene(congratPane, STAGE_WIDTH, STAGE_HEIGHT);

    mainPane = new BorderPane();
    mainMenuPane = new GridPane();

    leftMain = new GridPane();
    rightMain = new GridPane();


    mainPane.setCenter(mainMenuPane);  // set the main choosing pane
    mainPane.setLeft(leftMain);        // add something on the left and right
    mainPane.setRight(rightMain);      // board

    mainMenuPane.setMaxSize(STAGE_HEIGHT, STAGE_WIDTH);//size of mainMenu
    mainMenuPane.setMinSize(STAGE_HEIGHT, STAGE_WIDTH);


    leftMain.setMaxSize(MAIN_POSX, STAGE_HEIGHT);//size of the card board
    leftMain.setMinSize(MAIN_POSX, STAGE_HEIGHT);


    rightMain.setMaxSize(START_POSY, STAGE_HEIGHT);// set the size of the right
    rightMain.setMinSize(START_POSY, STAGE_HEIGHT);//one



    Image choosingBackg = new Image               // choosing a file with favafx
            (getClass().getResourceAsStream("chooseBackground.png"),
                    STAGE_WIDTH, STAGE_HEIGHT, false, true);
    BackgroundImage choosingBackgimg = new BackgroundImage
            (choosingBackg, null, null,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    mainPane.setBackground(new Background(choosingBackgimg));

    mainMenuPane.getChildren().addAll
            (instructionButton, playButton, exitButton);
    GridPane.setConstraints(playButton, 0, 1);        // arrange the stuff
    GridPane.setConstraints(instructionButton, 0, 2); // to the gridPane us to
    GridPane.setConstraints(exitButton, 0, 3);        //add the exit button

    // set the value of random arrauble set by Mircosrtt
    mainMenuPane.getRowConstraints().add(new RowConstraints(START_POSY));
    mainMenuPane.getRowConstraints().add(new RowConstraints(START_HEIGHT));
    mainMenuPane.getRowConstraints().add(new RowConstraints(START_POSY));


    instructionButton.setMaxSize(INSTR_WIDTH, INSTR_HEIGHT);//set img size
    instructionButton.setMinSize(INSTR_WIDTH, INSTR_HEIGHT);

    playButton.setMaxSize(START_WIDTH, START_HEIGHT);// set button size
    playButton.setMinSize(START_WIDTH, START_HEIGHT);

    exitButton.setMaxSize(EXIT_WIDTH, EXIT_HEIGHT);  //set exit button size
    exitButton.setMinSize(EXIT_WIDTH, EXIT_HEIGHT);

    //let the button be transparent so that user could see the picture
    instructionButton.setStyle("-fx-background-color: transparent;");
    playButton.setStyle("-fx-background-color: transparent;");
    exitButton.setStyle("-fx-background-color: transparent;");

    //set the Scene with the Pane
    mainScene = new Scene(mainPane, STAGE_WIDTH, STAGE_HEIGHT);

    // instruction scene
    instructionPane = new GridPane();
    //Set the whole window as a button to return
    //set the button's picture as special
    Image inStr = new Image
            (getClass().getResourceAsStream("Instruction.png"));
    ImageView instruction = new ImageView(inStr);
    instructionExitButton.setMinHeight(STAGE_HEIGHT);
    instructionExitButton.setMaxHeight(STAGE_HEIGHT);
    instructionExitButton.setMinWidth(STAGE_WIDTH);
    instructionExitButton.setMaxWidth(STAGE_WIDTH);
    instruction.setFitHeight(instructionExitButton.getHeight());
    instruction.setFitWidth(instructionExitButton.getWidth());
    //FIXME System.out.println(instruction.getFitWidth());
    instructionExitButton.setGraphic(instruction);

    //add the button into the pane, then into the scene
    instructionPane.getChildren().add(instructionExitButton);
    GridPane.setConstraints(instructionExitButton, 0, 0);

    instructionScene = new Scene(instructionPane, STAGE_WIDTH, STAGE_HEIGHT);
    // exit
    exitPane = new BorderPane();
    exitPane.setCenter(backHomeButtomE);
    exitPane.setTop(exitLabel);
    exitScene = new Scene(exitPane, STAGE_WIDTH, STAGE_HEIGHT);

    //set up transitions between each scene
    themeButton.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){stage.setScene(mainScene);}
    });

    gameOverButton.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){stage.setScene(mainScene);}
    });

    congratButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        stage.setScene(mainScene);                       //exit the game
      }
    });

    instructionExitButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        stage.setScene(mainScene);
      }
    });

    exitButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //System.out.println("I should exit");
        System.exit(0);                       //exit the game
      }
    });

    //swtich to the instruction scene
    instructionButton.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        stage.setScene(instructionScene);
      }
    });

    //show all the cards for 3 seconds to memorize
    playButton.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        stage.setScene(playScene);
        allFlipUp();                   //preview the picture to the user
		    tl.play();                     //get them back
      }
    });

    addCard();

    stage.setTitle("UCSD Memory Game");//set the title of the stage
    stage.setScene(themeScene);        //set the beginning picture
    stage.show();                      //show the stage to the usser

	}

  /**
   * This is a method used to filpDown all the cards
   */
  protected void allFlipDown() {
    for (int i = 0; i < cards.length; i++) {
      for (int j = 0; j < cards[i].length; j++) {
        Card c;                //get current card
        RotateTransition r;    //get current rotater
        c = cards[i][j];
        r = rotater[i][j];
        c.setGraphic(c.tailIm);//flip down
        r.play();              //play the flip animation
      }
    }
  }

  /**
   * This is a method used to filpUp all the cards
   */
  protected void allFlipUp() {
    for (int i = 0; i < cards.length; i++) {
      for (int j = 0; j < cards[i].length; j++) {
        Card c;
        RotateTransition r;
        c = cards[i][j];      //get curr card
        r = rotater[i][j];    //get curr rotater
        c.setGraphic(c.im);   //flip up
        r.play();             //play the flip animation
      }
    }
  }

  /**
   * This is a method used to judge if the card pairs are all found by the user
   * @return true if all the cards are found, false if not
   */
  protected boolean allFound() {
	  for (int i = 0; i < cards.length; i++) {
		  for (int j = 0; j < cards[i].length; j++) {
			  if (!cards[i][j].found) {
				  return false;           //if one card is not found, then it's not all
			  }                         //found
		  }
	  }
	  return true;
  }

  /**
   * This is a method used to randomize the location of cards
   * @return ArrayList<Integer>
   */
  protected ArrayList<Integer> randomizePosition() {
    //Shuffle the array to randomize the cards' positions
    arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    newarr = Card.randomizeArray(arr);
    newList = Card.arrayToArrList(newarr);

    return newList;
  }

  /**
   * This is a method to creat a rotater for specific Card
   * @param card
   * @return the rotator object
   */
  protected RotateTransition createRotator(Node card) {
    //set the animation details
	  RotateTransition rotator = new RotateTransition(Duration
		  	.millis(ROTATE_TIME), card);
	  rotator.setAxis(Rotate.Y_AXIS);
	  rotator.setFromAngle(ROTATE_ANGLE_START);
	  rotator.setToAngle(ROTATE_ANGLE_END);
	  rotator.setInterpolator(Interpolator
		  	.LINEAR);
	  rotator.setCycleCount(ROTATE_CYCLE);

  	return rotator;
  }

  /**
   * This is a method to add the cards on the Panel
   */
  protected void addCard() {

    for (int i = 0; i < cards.length; i++) {
      for (int j = 0; j < cards[i].length; j++) {  //for all cards

        int[] location = Card.getLocation(newList.get(0));
        newList.remove(0);                         //position taken

        GridPane.setConstraints(cards[i][j], location[1], location[0]);
      }
    }

  }

  /**
   * This is a method used to renew the card position
   */
  protected void renewGame() {
    newList = randomizePosition();
    Card.score = INITIAL_SCORE;
    Card.leftMove = INITIAL_MOVE;
    selectedCard = null;

    for (int i = 0; i < cards.length; i++) {
      for (int j = 0; j < cards[i].length; j++) {  //for all cards
        cards[i][j].found = false;
      }
    }
  }

  @Override
  public void handle(Event event) {}
}

