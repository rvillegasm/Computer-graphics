/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 *
 * @author htrefftz
 */
public class EventoTeclado extends JPanel
    implements KeyListener {
    Ellipse2D.Double ovalo1;
    
    public EventoTeclado() {
        ovalo1 = new Ellipse2D.Double(10, 10, 50, 50);
        // El panel, por defecto no es "focusable". 
        // Hay que incluir estas líneas para que el panel pueda
        // agregarse como KeyListsener.
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addKeyListener(this);
    }
    
    @Override
  public void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2d = (Graphics2D) g;

      g2d.setColor(Color.BLUE);      
      g2d.fill(ovalo1);
      g2d.draw(ovalo1);
      
  }

    @Override
  public void keyPressed(KeyEvent e) {
      int tecla = e.getKeyCode();
      //System.out.println("Key pressed");
      if(tecla == KeyEvent.VK_UP) {
          ovalo1.y -= 20;
      } else if (tecla == KeyEvent.VK_DOWN) {
          ovalo1.y += 20;
      } else if (tecla == KeyEvent.VK_RIGHT) {
          ovalo1.x += 20;
      } else if(tecla == KeyEvent.VK_LEFT) {
          ovalo1.x -= 29;
      }
      repaint();    
  }
    @Override
  public void keyReleased(KeyEvent e) {}
    @Override
  public void keyTyped(KeyEvent e) {}
  
  public static void main(String[] args) {
      // Crear un nuevo Frame
      JFrame frame = new JFrame("Ovalos");
      // Al cerrar el frame, termina la ejecución de este programa
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // Agregar un JPanel que se llama Points (esta clase)
      EventoTeclado et = new EventoTeclado();
      frame.add(et);
      // Asignarle tamaño
      frame.setSize(250, 200);
      // Poner el frame en el centro de la pantalla
      frame.setLocationRelativeTo(null);
      // Mostrar el frame
      frame.setVisible(true);
  }
    
    
}
