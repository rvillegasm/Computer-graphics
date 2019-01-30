/*
 * Ejemplo básico en Java2D
 * 
 * Tomado de el Tutorial de Java2D de ZetTutorial: http://zetcode.com/tutorials/java2dtutorial/
 * 
 * Java tiene un tutorial oficial para Java2D: http://docs.oracle.com/javase/tutorial/2d/index.html
 * 
 * Algoritmo que dibuja una recta en el primer octante usando bresenham
 * 
 */

package points;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.util.Random; 


public class Points extends JPanel {

  /*
   * En esta función se dibuja.
   * La función es llamada por Java2D.
   * Se recibe una variable Graphics, que contiene la información del contexto
   * gráfico.
   * Es necesario hacerle un cast a Graphics2D para trabajar en Java2D.
   */
    @Override
  public void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2d = (Graphics2D) g;

      g2d.setColor(Color.blue);

      // size es el tamaño de la ventana.
      Dimension size = getSize();
      // Insets son los bordes y los títulos de la ventana.
      Insets insets = getInsets();

      int w =  size.width - insets.left - insets.right;
      int h =  size.height - insets.top - insets.bottom;

      // Generador de números Random
      // Se va a utilizar nextInt, que devuelve un entero.
    //   Random r = new Random();

    //   // Generar 1000 puntos
    //   for (int i=0; i<1000; i++) {
    //       int x = Math.abs(r.nextInt()) % w;
    //       int y = Math.abs(r.nextInt()) % h;
    //       // Así se pinta un punto
    //       g2d.drawLine(x, y, x, y)Random r = new Random();

    //   // Generar 1000 puntos
    //   for (int i=0; i<1000; i++) {
    //       int x = Math.abs(r.nextInt()) % w;
    //       int y = Math.abs(r.nextInt()) % h;
    //       // Así se pinta un punto
    //       g2d.drawLine(x, y, x, y);
    //   };
    //   }
      g2d.setColor(Color.GREEN);
      g2d.drawLine(150, 250 , 350, 250);
      g2d.setColor(Color.RED);
      g2d.drawLine(250, 350, 250, 150);

      g2d.setColor(Color.BLUE);

      bresenham(40, 40, 200, 80, g2d, w, h);

  }

  private static void bresenham(int x1, int y1, int x2, int y2, Graphics2D g2d, int w, int h) {
    int dy = y2 - y1;
    int dx = x2 - x1;
    int incE = 2 * dy;
    int incNE = 2 * dy - 2 * dx;

    int d = 2 * dy - dx;
    int y = y1;

    for(int x = x1; x <= x2; x++) {
      g2d.drawLine(x + (w/2), (h/2) - y, x + (w/2), (h/2) - y);
      
      if(d <= 0) {
        d += incE;
      }
      else {
        d += incNE;
        y += 1;
      }
    }
    
  }


  public static void main(String[] args) {
      // Crear un nuevo Frame
      JFrame frame = new JFrame("Points");
      // Al cerrar el frame, termina la ejecución de este programa
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // Agregar un JPanel que se llama Points (esta clase)
      frame.add(new Points());
      // Asignarle tamaño
      frame.setSize(500, 500);
      // Poner el frame en el centro de la pantalla
      frame.setLocationRelativeTo(null);
      // Mostrar el frame
      frame.setVisible(true);
  }
}