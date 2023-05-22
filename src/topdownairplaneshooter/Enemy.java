package topdownairplaneshooter;

import java.io.IOException;
import java.util.Random;

public final class Enemy extends MovingObjectsBaseClass {

    private short IteratorX = 0, IteratorY = 0;
    private float dx_smooth = 0, dy_smooth = 0;
    private byte route;
    private final Random random = new Random();

    public Enemy(short x, short y, short x_boundary, short y_boundary)
            throws IOException {
        super(x, y, x_boundary, y_boundary);
        route = (byte) random.nextInt(4);
        if (route == 0 || route == 1) loadImage("resources/enemy_" + random.nextInt(5) + ".png");
        else loadImage("resources/enemy_B" + random.nextInt(5) + ".png");
        getImageDimensions();
    }

    @Override
    public void move() {
        
        int x_peek = this.width / 4;
        int y_peek = this.height / 4;
                
        if (x < x_peek) x = (short)(x_boundary - x_peek);
        else if (x > x_boundary - x_peek) x = (short)x_peek;
        if (y < y_peek) y = (short)(y_boundary - y_peek);
        else if (y > y_boundary - y_peek) y = (short)y_peek;
        
        short x_prev = x;
        short y_prev = y; 
        
        if (route == 0) {
            if (IteratorX < random.nextInt(4000)) {
                IteratorX += 1;
                x += IteratorX/20;
            } 
            else IteratorX = 0;
            if (IteratorY < random.nextInt(7000)) {
                IteratorY += 1;
                y += 9;
            }
            else {
                IteratorY = 0;
                y+=2;
                route=1;
            }
        }
        else if (route == 1) {
            if (IteratorX < random.nextInt(6000)) {
                IteratorX += 1;
                x -= IteratorX/20;
                y+=1;
            }
            else IteratorX = 0;
            if (IteratorY < random.nextInt(3000)) {
                IteratorY += 1;
                y += 8;
            }
            else {
                IteratorY = 0;
                route=0;
                y+=3;
            }
        }
        else if (route == 2) {
            if (IteratorX < random.nextInt(8000)) {
                IteratorX += 1;
                x += 4;
                y -= IteratorX/20;
            }
            else IteratorX = 0;
            if (IteratorY < random.nextInt(9000)) {
                IteratorY += 1;
                y -= 2;
            }
            else {
                IteratorY = 0;
                route=3;
                y -= 4;
            }
        }
        else {
            if (IteratorX < random.nextInt(7000)) {
                IteratorX += 1;
                y -= IteratorX/20;
                x -= 2;
            }
            else IteratorX = 0;
            if (IteratorY < random.nextInt(6000)) {
                IteratorY += 1;
                y -= 3;
            } 
            else {
                IteratorY = 0;
                route=2;
            }
        }
        
        int dx = x - x_prev;
        int dy = y - y_prev;
        
        x -= dx;
        y -= dy;
        
        dx_smooth += (dx - dx_smooth) * 0.1;
        dy_smooth += (dy - dy_smooth) * 0.1;
        
        x += dx_smooth;
        y += dy_smooth;
        
    }
}
