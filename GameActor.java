//import processing.core.PImage;

public class GameActor {
  private float coordinates[];
  private Hitbox hitbox;
  protected processing.core.PImage image;
  protected static processing.core.PApplet processing;
  
  public GameActor(float x, float y, String imgPath)
  {
    coordinates=new float[2];
    this.coordinates[0]=x;
    this.coordinates[1]=y;
    image= processing.loadImage(imgPath);
    hitbox=new Hitbox(x,y,image.width,image.height);
    
  }
  public void draw()
  {
    processing.image(image, this.coordinates[0], this.coordinates[1]);
  }
  
  public Hitbox getHitbox()
  {
    return this.hitbox;
  }
  public float getX()
  {
    return this.coordinates[0];
  }
  public float getY()
  {
    return this.coordinates[1];
  }
  public void moveHitbox(float x, float y)
  {
    this.hitbox.setPosition(x, y);
  }
  public static void setProcessing(processing.core.PApplet processing)
  {
    GameActor.processing=processing;
  }
  public void setX(float newX)
  {
    coordinates[0]=newX;
  }
  
  public void setY(float newY)
  {
    coordinates[1]=newY;
  }
  
}
