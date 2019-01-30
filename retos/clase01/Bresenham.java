
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Bresenham {
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Bresenham::run);
    }
 
    private static void run() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setTitle("Bresenham");
 
        f.getContentPane().add(new BresenhamPanel());
        f.pack();
 
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}