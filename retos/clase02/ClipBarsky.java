
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class ClipBarsky {
 
  public static void main(String[] args) {
    SwingUtilities.invokeLater(ClipBarsky::run);
  }
 
  private static void run() {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    f.setTitle("Liang-Barsky");
 
    f.getContentPane().add(new ClipBarskyPanel());
    f.pack();
 
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }
}