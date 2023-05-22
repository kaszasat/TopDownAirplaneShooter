package topdownairplaneshooter;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public final class TopDownAirplaneShooter extends JFrame {
    public TopDownAirplaneShooter(short width, short height) throws IOException {
        add(new Sky(width, height));
        setTitle("Top Down Airplane Shooter");
        this.pack();
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setResizable(false);
    }
    
    public static void main(String[] args) {
        
        // Disable DPI scaling to avoid drawing outside the screen
        System.setProperty("sun.java2d.uiScale", "1");
        
        // Get width and height
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        
        EventQueue.invokeLater(() -> {
            TopDownAirplaneShooter ex;
            try {
                ex = new TopDownAirplaneShooter((short)width, (short)height);
            } catch (IOException ex1) {
                Logger.getLogger(TopDownAirplaneShooter.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
            ex.setVisible(true);
        });
    }    
}
