package topdownairplaneshooter;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AirPlaneModel extends MovingObjectsBaseClass {

    private short dx, dy;
    private float dx_smooth = 0, dy_smooth = 0;
    private final List<Missile> missiles;

    public AirPlaneModel(short x, short y, short x_boundary, short y_boundary)
            throws IOException {
        super(x, y, x_boundary, y_boundary);
        missiles = new ArrayList<>();
        loadImage("resources/fighterPlane.png");
        getImageDimensions();
    }
    
    @Override
    public void move() {
        if(Keyboard.isKeyPressed(KeyEvent.VK_SPACE)){
            try {
                missiles.add(new Missile(
                        (short)(x + width - 23),
                        (short)(y + (height / 2)-30),
                        x_boundary, 
                        y_boundary)
                );
            } catch (IOException ex) {
                Logger.getLogger(AirPlaneModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        dx = 0;
        dx += Keyboard.isKeyPressed(KeyEvent.VK_LEFT) ? -17 : 0;
        dx += Keyboard.isKeyPressed(KeyEvent.VK_RIGHT) ? 17 : 0;
        
        dx_smooth += (dx - dx_smooth) * 0.1;
        
        dy = 0;
        dy += Keyboard.isKeyPressed(KeyEvent.VK_UP) ? -17 : 0;
        dy += Keyboard.isKeyPressed(KeyEvent.VK_DOWN) ? 17 : 0;
        
        dy_smooth += (dy - dy_smooth) * 0.1;
        
        x += dx_smooth;
        y += dy_smooth;

        //if ((x += dx) < 5) x = 1;
        //if ((y += dy) < 5) y = 1;
        
        int x_peek = this.width / 4;
        int y_peek = this.height / 4;
                
        if (x < x_peek) x = (short)(x_boundary - x_peek);
        else if (x > x_boundary - x_peek) x = (short)x_peek;
        if (y < y_peek) y = (short)(y_boundary - y_peek);
        else if (y > y_boundary - y_peek) y = (short)y_peek;
    }

    public List<Missile> getMissiles() {
        return missiles;
    }
}
