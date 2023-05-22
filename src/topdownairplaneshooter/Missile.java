package topdownairplaneshooter;

import java.io.IOException;
import java.util.Random;

public final class Missile extends MovingObjectsBaseClass {

    private final short MISSILE_SPEED = -30;
    private final Random random;

    public Missile(short x, short y, short x_boundary, short y_boundary)
            throws IOException {
        super(x, y, x_boundary, y_boundary);
        random=new Random();
        loadImage("resources/missile_" + random.nextInt(5) + ".png");
        getImageDimensions();
    }

    @Override
    public void move() {
        y += MISSILE_SPEED;
        x += random.nextInt(12)-random.nextInt(12);
        if (y >= y_boundary) visible = false;
    }
}
