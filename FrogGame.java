// importing all the required functions
import java.io.File;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.Random;

/*
 * creates the game and runs all the logics of the game
 * 
 * @param gameActors - an array list of GameActor objects
 * 
 * @param score - a variable that stores the current points earned by the player
 * 
 * @param backgroundImg - used to store the image of the background
 * 
 * @param isGameOver - the boolean variable which tells whether the game is over or not
 * 
 * @param randGen - a random variable
 * 
 * @param BUG_COUNT - a final variable which stores the number of bugs that should be in the game
 * 
 */
public class FrogGame extends PApplet {

  private ArrayList<GameActor> gameActors; // array list of the gameActors in the game
  private int score; // the player's current score
  private PImage backgroundImg; // the image to use for the background
  private boolean isGameOver; // keeps track if the game is over, is true if the game is over
  private Random randGen; // random number generator
  private static final int BUG_COUNT = 5; // how many bugs should be on the screen at all times

  public static void main(String[] args) {
    PApplet.main("FrogGame");
  }

  @Override
  public void settings() {
    // TODO #1 call PApplet's size() method giving it 800 as the width and 600 as the height
    this.size(800, 600);
  }

  @Override
  public void setup() {
    // TODO #2 add PApplet method calls from write-up
    this.getSurface().setTitle("Froggie Feeding Frenzie"); // set title of window
    this.imageMode(PApplet.CENTER); // images when drawn will use x,y as their center
    this.rectMode(PApplet.CENTER); // rectangles when drawn will use x,y as their center
    this.focused = true; // window is "active" upon start up
    this.textAlign(PApplet.CENTER); // text written to screen will have center alignment
    this.textSize(30); // text is 30 pt
    // TODO #3 initialize randGen
    randGen = new Random();
    // TODO #4 load in and save the backgroundImg
    backgroundImg = loadImage("images" + File.separator + "background.jpg");
    // TODO #5 initialize gameActors to an empty ArrayList
    gameActors = new ArrayList<GameActor>();
    // TODO #7 set the processing variable for all classes where necessary (update this as needed)
    FrogGame test;
    test = this;
    Tongue.setProcessing(test);
    GameActor.setProcessing(test);
    Hitbox.setProcessing(test);
    // TODO #16 call initGame()
    initGame();
  }

  @Override
  public void draw() {
    if (!isGameOver) {
      // TODO #6 call PApplet's image() method to draw the backgroundImg at the center of the screen
      image(backgroundImg, 400, 300);
      // TODO #8 draw every GameActor in the ArrayList to the screen
      for (int i = 0; i < gameActors.size(); i++) {
        gameActors.get(i).draw();
      }

      // TODO #9 update the code you wrote for TODO #8 to have all Movable GameActors move
      for (int i = 0; i < gameActors.size(); i++) {
        if (gameActors.get(i) instanceof CirclingBug) {
          CirclingBug a = (CirclingBug) gameActors.get(i);
          a.move();
        } else if (gameActors.get(i) instanceof StrongBug) {
          StrongBug a = (StrongBug) gameActors.get(i);
          a.move();
        } else if (gameActors.get(i) instanceof BouncingBug) {
          BouncingBug a = (BouncingBug) gameActors.get(i);
          a.move();
        } else if (gameActors.get(i) instanceof Frog) {
          Frog froggie = (Frog) gameActors.get(i);
          if (froggie.shouldMove()) {
            froggie.move();
          }
        }
      }
      // TODO #19 run all the game logic checks
      runGameLogicChecks();
      // TODO #14 print "Health: " + frog's health at (80,40) and "Score: " + score at (240,40)
      // to the screen
      // (note in the code logic this step to be performed takes place AFTER TODO #19)
      for (int i = 0; i < gameActors.size(); i++) {
        if (gameActors.get(i) instanceof Frog) {
          Frog frog = (Frog) gameActors.get(i);
          int health = frog.getHealth();
          text("Health: " + health, 80, 40);
          text("Score: " + score, 240, 40);
        }
      }
    }

    // TODO #20 update the code you wrote above to do the following:
    // (1) if the game is over, do NONE of the above steps. Instead print "GAME OVER" to
    // the center of the screen.
    // (2) otherwise do the above steps
    else if (isGameOver) {
      text("GAME OVER", 400, 300);
    }
  }

  private void addNewBug() {
    // TODO #10 implement this method, see below for more details.
    // This creates a bug of a random type and adds it to the list of GameActors.
    // (1) generate a random number in the range [0,4)
    // (2) generate a random x value in the range [0, windowWidth) for the bug
    // (3) generate a random y value in the range [0, windowHeight - 150) for the bug
    // (4) depending on the value generated in step (1)
    // create the following bug and add it to the arraylist
    // 0 -> a new regular Bug at (x,y) that is worth 25 points
    // 1 -> a new BouncingBug at (x,y) that has a dx of 2 and a dy of 5
    // 2 -> a new CirclingBug at (x,y) with a radius of 25 and a random set of RGB values [0,256)
    // 3 -> a new StrongBug at (x,y) with an initial health of 3
    int typeOfBug = randGen.nextInt(4);
    int x = randGen.nextInt(800);
    int y = randGen.nextInt(450);
    if (typeOfBug == 0) {
      Bug a = new Bug((float) x, (float) y, 25);
      gameActors.add(a);
    } else if (typeOfBug == 1) {
      BouncingBug a = new BouncingBug((float) x, (float) y, 2, 5);
      gameActors.add(a);
    } else if (typeOfBug == 2) {
      int r = randGen.nextInt(256);
      int g = randGen.nextInt(256);
      int b = randGen.nextInt(256);
      int[] c = {r, g, b};
      CirclingBug a = new CirclingBug((float) x, (float) y, 25.00, c);
      gameActors.add(a);
    } else if (typeOfBug == 3) {
      StrongBug a = new StrongBug((float) x, (float) y, 3);
      gameActors.add(a);
    }
  }

  @Override
  public void mousePressed() {
    // TODO #11 if mouse is over the Frog call its mousePressed method
    for (int i = 0; i < gameActors.size(); i++) {
      if (gameActors.get(i) instanceof Frog) {
        Frog a = (Frog) gameActors.get(i);
        if (a.isMouseOver()) {
          a.mousePressed();
        }
      }
    }
  }

  @Override
  public void mouseReleased() {
    // TODO #12 call the Frog's mouseReleased method
    for (int i = 0; i < gameActors.size(); i++) {
      if (gameActors.get(i) instanceof Frog) {
        Frog a = (Frog) gameActors.get(i);
        a.mouseReleased();
      }
    }
  }

  @Override
  public void keyPressed() {
    // TODO #13 if the key is a space, have the frog starts attacking
    char space = ' ';
    if (key == space) {
      Frog frog = (Frog) gameActors.get(0);
      frog.startAttack();
    }
    // TODO #17 if the key is a lowercase 'r', reset the game to its initial state
    char r = 'r';
    if (key == r) {
      initGame();
    }
  }

  public void initGame() {
    // TODO #15 implement this method, see below for more details. This methods sets the game to
    // its initial state before playing.
    // (1) set the score to 0
    // (2) make the game NOT over
    // (3) clear out the arraylist
    // (4) create and add Frog with 100 health to the list. Its x value should be half the
    // width of the screen. Its y value should be the height of the screen minus 100
    // (5) add new bugs (of random varieties) to the list UP TO the BUG_COUNT
    this.score = 0;
    this.isGameOver = false;
    gameActors.clear();
    Frog frog = new Frog(400, 500, 100);
    gameActors.add(frog);
    for (int i = 0; i < BUG_COUNT; i++) {
      addNewBug();
    }
  }

  private void runGameLogicChecks() {
    // TODO #18 implement this method, see below for details. This method runs all nessisary
    // game logic checks. Feel free to decompose it into smaller helper methods.
    // (1) if the Frog's tongue hits the edge of the screen, then it stops attacking
    // (2) Check every bug to see if it has been hit by the Frog.
    // (a) if a non-StrongBug is hit do the following
    // (a1) stop the frog's attack
    // (a2) remove it from the game
    // (a3) update the score
    // (a4) add a new bug (of a random variety) to the game
    // (b) of a StrongBug is hit do the following
    // (b1) stop the frog's attack
    // (b2) the StrongBug takes damage and loses health
    // (b3) if the StrongBug is dead do steps a1 - a4
    // (3) check if the frog hits any of the bugs
    // (a) if it hit any of the bugs it takes damage and loses health
    // NOTE: it can be hit my multiple bugs at the same time loses health for each.
    // Ex. is hit by 2 different bugs simultanously then should take 2 damage.
    // (b) if the frog is dead then update the game so it is over
    Frog frog = new Frog(0, 0, 0);
    for (int i = 0; i < gameActors.size(); i++) {
      if (gameActors.get(i) instanceof Frog) {
        frog = (Frog) gameActors.get(i);
      }
    }
    if (frog.tongueHitBoundary()) {
      frog.stopAttack();
    }
    for (int i = 0; i < gameActors.size(); i++) {
      if (gameActors.get(i) instanceof CirclingBug) {
        CirclingBug a = (CirclingBug) gameActors.get(i);
        if (a.isEatenBy(frog)) {
          frog.stopAttack();
          gameActors.remove(i);
          score += a.getPoints();
          addNewBug();
        }
      } else if (gameActors.get(i) instanceof Bug) {
        Bug a = (Bug) gameActors.get(i);
        if (a.isEatenBy(frog)) {
          frog.stopAttack();
          gameActors.remove(i);
          score += a.getPoints();
          addNewBug();
        }
      } else if (gameActors.get(i) instanceof BouncingBug) {
        BouncingBug a = (BouncingBug) gameActors.get(i);
        if (a.isEatenBy(frog)) {
          frog.stopAttack();
          gameActors.remove(i);
          score += a.getPoints();
          addNewBug();
        }
      } else if (gameActors.get(i) instanceof StrongBug) {
        StrongBug a = (StrongBug) gameActors.get(i);
        if (a.isEatenBy(frog)) {
          frog.stopAttack();
          if (a.isDead()) {
            gameActors.remove(i);
            score += a.getPoints();
            addNewBug();
          }
        }
      }
    }
    for (int i = 0; i < gameActors.size(); i++) {
      if (gameActors.get(i) instanceof Bug) {
        if (frog.getHitbox().doesCollide(gameActors.get(i).getHitbox())) {
          frog.loseHealth();
          if (frog.isDead()) {
            isGameOver = true;
          }
        }
      }
    }
  }
}
