
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Clase principal para la implementacion del algoritmo de bresenham en los 8 octantes.
 * Referirse a la clase BresenhamPanel para ver la implementacion especifica del mismo.
 */
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