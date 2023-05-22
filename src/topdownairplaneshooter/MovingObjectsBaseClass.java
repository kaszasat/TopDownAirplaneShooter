package topdownairplaneshooter;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class MovingObjectsBaseClass {

    private final static HashMap<String, Image> imageCache = new HashMap<String, Image>();
    
    protected short x, y, width, height, x_boundary, y_boundary;
    protected boolean visible;
    protected Image image;

    protected MovingObjectsBaseClass(short x, short y, short x_boundary, short y_boundary) {
        this.x = x;
        this.y = y;
        this.x_boundary = x_boundary;
        this.y_boundary = y_boundary;
        visible = true;
    }

    protected void loadImage(String imageName) throws IOException {
        
        if(!imageCache.containsKey(imageName))
            imageCache.put(imageName, 
                    new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource(imageName))).getImage()
            );
        image = imageCache.get(imageName);

        //image = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource(imageName))).getImage();
        //image = new ImageIcon(imageName).getImage();
    }

    protected void getImageDimensions() {
        width = (short) image.getWidth(null);
        height = (short) image.getHeight(null);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width-3, height-3);
    }
    
    public abstract void move();
}
