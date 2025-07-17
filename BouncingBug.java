import java.util.Random;

public class BouncingBug extends Bug implements Moveable {
  private boolean goDown;
  private boolean goLeft;
  private static final int POINTS = 100;
  private Random randGen;
  private int[] speedNums;

  public BouncingBug(float x, float y, int dx, int dy) {
    super(x, y, POINTS);
    randGen = new Random();
    goDown = randGen.nextBoolean();
    goLeft = randGen.nextBoolean();
    speedNums = new int[2];
    speedNums[0] = dx;
    speedNums[1] = dy;
  }

  public void move() {
    if (shouldMove()) {
      float x = this.getX();
      if (this.goLeft) {
        this.setX(x - speedNums[0]);
        if (x - speedNums[0] < 0)
          this.goLeft = false;

      } else {
        this.setX(x + speedNums[0]);
        if (x + speedNums[0] > 800)
          this.goLeft = true;
      }
      float y = this.getY();
      if (this.goDown) {
        this.setY(y + speedNums[1]);
        if (y + speedNums[1] > 600)
          this.goDown = false;
      } else {
        this.setY(y - speedNums[1]);
        if (y - speedNums[0] < 0)
          this.goDown = true;
      }
      this.moveHitbox(this.getX(), this.getY());
    }
  }

  public boolean shouldMove() {
    return true;
  }
}
