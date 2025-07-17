public class CirclingBug extends Bug implements Moveable {

  private float circleCenter[];
  private double radius;
  private static final int POINTS = 200;
  private double ticks;
  private int[] tintColor;

  public CirclingBug(float circleX, float circleY, double radius, int[] tintColor) {
    super(circleX, circleY, POINTS);
    circleCenter = new float[2];
    circleCenter[0] = circleX;
    circleCenter[1] = circleY;
    this.radius = radius;
    this.tintColor = tintColor;
    ticks = 0.0;
  }

  @Override
  public void draw() {
    super.processing.tint(tintColor[0], tintColor[1], tintColor[2]);
    super.draw();
    super.processing.tint(255, 255, 255);
  }

  public void move() {
    if (shouldMove()) {
      ticks += 0.05;
      this.setX((float) (radius * Math.cos(ticks) + circleCenter[0]));
      this.setY((float) (radius * Math.sin(ticks) + circleCenter[1]));
      this.moveHitbox(this.getX(), this.getY());
    }
  }

  public boolean shouldMove() {
    return true;
  }
}
