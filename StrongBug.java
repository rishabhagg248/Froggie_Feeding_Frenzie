
public class StrongBug extends Bug implements Moveable {
  private int currentHealth;
  private final int MAX_HEALTH;
  private static final int POINTS = 500;

  public StrongBug(float x, float y, int health) {
    super(x, y, POINTS);
    currentHealth = MAX_HEALTH = health;
  }

  public boolean isDead() {
    return (currentHealth <= 0) ? true : false;
  }

  public boolean isEatenBy(Frog f) {
    if (this.getHitbox().doesCollide(f.getTongueHitbox())) {
      loseHealth();
      this.image.resize((int) (0.75 * image.width), (int) (0.75 * image.height));
      this.getHitbox().changeDimensions((float) (0.75 * image.width),
          (float) (0.75 * image.height));
      return true;
    }
    return false;
  }

  public void loseHealth() {
    currentHealth--;
  }

  @Override
  public void move() {
    if (shouldMove()) {
      if (this.getX() + 3 <= 800)
        this.setX(this.getX() + 3);
      else
        this.setX(0);
      this.moveHitbox(this.getX(), this.getY());
    }
  }

  @Override
  public boolean shouldMove() {
    return (currentHealth != MAX_HEALTH) ? true : false;
  }
}
