import java.io.File;

public class Bug extends GameActor {
  private static final String IMG_PATH = "images" + File.separator + "bug.png";
  private int points;

  public Bug(float x, float y, int points) {
    super(x, y, IMG_PATH);
    this.points = points;
  }

  public int getPoints() {
    return this.points;
  }

  public boolean isEatenBy(Frog F) {
    boolean b = this.getHitbox().doesCollide(F.getTongueHitbox());
    return (b == true) ? true : false;
  }
}
