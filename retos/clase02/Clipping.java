
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Clipping {
 
  public static void main(String[] args) {
    SwingUtilities.invokeLater(Clipping::run);
  }
 
  private static void run() {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    f.setTitle("Line Clipping with mouse");
 
    f.getContentPane().add(new ClippingPanel());
    f.pack();
 
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }
}