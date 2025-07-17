// importing the required functions
import java.io.File;

/*
 * Frog Class creates and performs functions on Frog objects
 * 
 * @param health - variable which stores the health of the frog
 * 
 * @param IMG_PATH - stores the image path for the image of the frog
 * 
 * @param isDragging - boolean variable which tells whether the frog is beign dragged or not
 * 
 * @param oldMouseX - the variable which is used to store the previous x coordinate of the mouse
 * 
 * @param oldMouseY - the variable which is used to store the previous y coordinate of the mouse
 * 
 * @param tongue - an ocject of the type Tongue
 * 
 */
public class Frog extends GameActor implements Moveable {

  private int health;
  private static final String IMG_PATH = "images" + File.separator + "frog.png";
  private boolean isDragging;
  private float oldMouseX;
  private float oldMouseY;
  private Tongue tongue;

  /*
   * the constructor of the frog object
   * 
   * @param x - takes the x coordinate of the frog and sends it to the super constructor
   * 
   * @param y - takes the y coordinate of the frog and sends it to the super constructor
   * 
   * @param health - takes the health of the frog and assigns it to the health of the frog
   * 
   */
  public Frog(float x, float y, int health) {

    super(x, y, IMG_PATH);
    this.health = health;
    tongue = new Tongue(x, y);

  }

  /*
   * draws the frog and the tongue
   */
  public void draw() {

    super.draw();
    tongue.draw();
    tongue.extend(this.getX(), -2);

  }

  /*
   * get method that returns the health of the frog
   */
  public int getHealth() {

    return this.health;

  }

  /*
   * if the tongue is active then it returns the hitbox object of the tongue
   */
  public Hitbox getTongueHitbox() {

    return this.tongue.getHitbox();

  }

  /*
   * if the health is 0 or less than 0, the method returns true else it retruns false
   */
  public boolean isDead() {

    if (this.health <= 0) {
      return true;
    }
    return false;

  }

  /*
   * checks if the hitbox of the frog collides with the hitbox of the frog
   */
  public boolean isHitBy(Bug b) {

    return this.getHitbox().doesCollide(b.getHitbox());

  }

  /*
   * checks if the mouse is over the frog and returns true or false accordingly
   */
  public boolean isMouseOver() {

    // creates the edges of the frog object
    float leftEdge = this.getX() - (this.image.width / 2);
    float rightEdge = this.getX() + (this.image.width / 2);
    float top = this.getY() - (this.image.height / 2);
    float bottom = this.getY() + (this.image.height / 2);

    // checks if coordinates of the mouse are between the edges of the frog square
    if (processing.mouseX > leftEdge && processing.mouseX < rightEdge && processing.mouseY > top
        && processing.mouseY < bottom) {
      return true;
    }
    return false;

  }

  /*
   * when this method is called, it reduces the health of the frog
   */
  public void loseHealth() {

    this.health--;

  }

  /*
   * checks if the mouse is pressed if it is pressed, it changes isDragging to true so that move
   * works
   */
  public void mousePressed() {

    if (processing.mousePressed) {
      oldMouseX = processing.mouseX;
      oldMouseY = processing.mouseY;
      this.isDragging = true;
    }

  }

  /*
   * checks if mouse is released if it is released, it changes isDragging to false so that move
   * stops working
   */
  public void mouseReleased() {

    if (!processing.mousePressed) {
      this.isDragging = false;
    }

  }

  /*
   * if isDragging is true, it starts moving the frog
   */
  public void move() {

    if (isDragging) {
      // dx and dy save the difference between the current coordinates of the mouse and old
      // coordinates
      float dx = processing.mouseX - oldMouseX;
      float dy = processing.mouseY - oldMouseY;
      // set the x and y coordinates of the frog according to the dx and dy
      this.setX(this.getX() + dx);
      this.setY(this.getY() + dy);
      // sets the the old coordinates of x and y as the current position of frog
      oldMouseX = this.getX();
      oldMouseY = this.getY();
      // update the start position of the frog
      tongue.updateStartPoint(oldMouseX, oldMouseY);
      // move the hitbox of the frog
      this.moveHitbox(this.getX() + dx, this.getY() + dy);
    }

  }

  /*
   * this returns whether the frog object should be moving or not
   */
  public boolean shouldMove() {

    return isDragging;

  }

  /*
   * starts the attack of the tongue
   */
  public void startAttack() {

    tongue.reset();
    tongue.activate();

  }

  /*
   * stops the attack of the tongue
   */
  public void stopAttack() {

    tongue.deactivate();
    tongue.updateEndPoint(this.getX(), this.getY());

  }

  /*
   * returns whether the tongue has hit the boundary or not
   */
  public boolean tongueHitBoundary() {

    return tongue.hitScreenBoundary();

  }

}
