package MemoryGame;

import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is an object that could be flipped and featured by optional head.
 */
public class Card extends Button {
  protected final Double HEIGHT = 210.0;//set the height of the card
  protected final Double WIDTH = 135.0; //set the width of the card
  private final Image PIC_TAIL = new Image(getClass().
          getResourceAsStream("tailPicture.png"));//set the tail picture
  protected final ImageView tailIm = new ImageView(PIC_TAIL);
  protected static int leftMove = 5;   //declare and set the initial moves
  protected static int score = 0;       //declare and set the initial moves
  private Image image;                  //to set up the head picture
  private RotateTransition r;           //in track of its rotater
  protected boolean found = false;      //see if the card is found
  protected ImageView im;               //the head picture
  private int cardID;                   //the ID of the card

  /*
   * This is a method to get the car's rotater
   * @return rotater of the card
   */
  protected RotateTransition getR() {
    return r;
  }

  /**
   * this is the constructor that can pass in the head picture
   * and initialize some parameter
   * @param pic
   * @param ID
   */
  public Card (String pic, int ID) {
    super(); //call the super class to create an instance
    image = new Image(getClass().getResourceAsStream(pic));
    im = new ImageView(image); //set the head picture

    im.setFitHeight(HEIGHT);   //set up the card's head
    im.setFitWidth(WIDTH);
    tailIm.setFitWidth(WIDTH);
    tailIm.setFitHeight(HEIGHT);
    this.setGraphic(tailIm);   //by default set it as the tail
    this.setMinHeight(HEIGHT);
    this.setMaxHeight(HEIGHT);
    this.setMinWidth(WIDTH);
    this.setMaxWidth(WIDTH);
    this.setCardID(ID);        //initialize the ID of the card
  }

  public boolean isFound() {
    return found;
  }

  public void setFound() {
    this.found = true;
  }

  public int getCardID() {
    return cardID;
  }

  public void setCardID(int cardID) {
    this.cardID = cardID;
  }

  /**
   * This is the method to help randomize the postion of the cards
   * @param array
   * @return an random array
   */
  public static int[] randomizeArray(int[] array){
    Random rgen = new Random();  // Random number generator

    for (int i=0; i<array.length; i++) {
      int randomPosition = rgen.nextInt(array.length);
      int temp = array[i];
      array[i] = array[randomPosition];
      array[randomPosition] = temp;
    }

    return array;
  }

  /**
   * this is the method to assign the position of a card according to the
   * randomized array
   * @param currentValue
   * @return the Card's location in the Card's group
   */
  public static int[] getLocation(int currentValue) {
    //assign the location
    switch (currentValue) {

	    case 1: return new int[]{1,1};

	    case 2: return new int[]{1,3};

	    case 3: return new int[]{1,5};

	    case 4: return new int[]{1,7};

	    case 5: return new int[]{3,1};

	    case 6: return new int[]{3,3};

	    case 7: return new int[]{3,5};

	    case 8: return new int[]{3,7};

	    case 9: return new int[]{5,1};

    	case 10: return new int[]{5,3};

	    case 11: return new int[]{5,5};

	    case 12: return new int[]{5,7};
    }

    return new int[]{-1, -1};
  }

  /**
   * This is the method to convert integer into location
   * @param shuffledArray
   * @return ArrayList<Integer> of location
   */
  public static ArrayList<Integer> arrayToArrList(int[] shuffledArray) {
    ArrayList<Integer> shuffledList =new ArrayList<Integer>();

    //convert the int into integer ArrayList
    for (int lgt = 0; lgt < shuffledArray.length; lgt++) {
      Integer newint = new Integer(shuffledArray[lgt]);
      shuffledList.add(newint);
    }
    return shuffledList; //return the object
  }
}
