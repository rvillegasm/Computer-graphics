import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class BobRossFrame {
 
  public static void main(String[] args) {
    SwingUtilities.invokeLater(BobRossFrame::run);
  }
 
  private static void run() {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    f.setTitle("Bob Ross");
 
    f.getContentPane().add(new BobRoss());
    f.pack();
 
    f.setLocationRelativeTo(null);
    f.setVisible(true);
  }
}