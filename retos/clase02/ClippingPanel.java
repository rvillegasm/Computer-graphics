
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;


/**
 * Implementacion del algoritmo de clipping (Liang-Barsky)
 */
class ClippingPanel extends JPanel implements MouseListener {
 
  private final int pixelSize = 1;
  //Coordenadas del mouse
  private int xInicial = 0;
  private int yInicial = 0;
  private int xFinal = 0;
  private int yFinal = 0;
  private boolean calularClipping = false;

  ClippingPanel() {
    setPreferredSize(new Dimension(600, 500));
    setBackground(Color.WHITE);
    this.addMouseListener(this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}
  
  //Mouse Methods
  @Override
  public void mousePressed(MouseEvent e)
  {
    xInicial = e.getX();
    yInicial = e.getY();
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
    xFinal = e.getX();
    yFinal = e.getY();
    calularClipping = true;
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    int w = (getWidth() - 1) / pixelSize;
    int h = (getHeight() - 1) / pixelSize;
    int maxX = (w - 1) / 2;
    int maxY = (h - 1) / 2;

    Map.Entry<ArrayList<Integer>, ArrayList<Integer>> coordenadas = getPoints(0, 0, 200, 5);

    g.setColor(Color.RED);

    // pintar lineas al principio
    if(!calularClipping) {
      for(int i = 0; i < coordenadas.getKey().size(); i++) {
        for(int j = 0; j < coordenadas.getKey().size(); j++) {
          if(j == i) {
            continue;
          }
          g.drawLine(coordenadas.getKey().get(i) + (w/2), (h/2) - coordenadas.getValue().get(i), coordenadas.getKey().get(j) + (w/2), (h/2) - coordenadas.getValue().get(j));
        }
      }
    }
    // calcular clipping para el cuadro seleccionado con el mouse
    else {
      for(int i = 0; i < coordenadas.getKey().size(); i++) {
        for(int j = 0; j < coordenadas.getKey().size(); j++) {
          if(j == i) {
            continue;
          }
          String message = liangBarsky(g, xInicial-300, 250-yInicial, xFinal-300, 250-yFinal, coordenadas.getKey().get(i), coordenadas.getValue().get(i), coordenadas.getKey().get(j), coordenadas.getValue().get(j), w, h);
          //System.out.println(coordenadas.getKey().get(i) + ", " + coordenadas.getKey().get(i));
        }
        // dibujar el cuadrado
        g.setColor(Color.BLACK);

        g.drawLine(xInicial, yInicial, xInicial, yFinal);
        g.drawLine(xInicial, yFinal, xFinal, yFinal);
        g.drawLine(xFinal, yFinal, xFinal, yInicial);
        g.drawLine(xFinal, yInicial, xInicial, yInicial);
        g.setColor(Color.RED);        
      }  
    }
  }

  private static float max(ArrayList<Float> arreglo) {
    float nmax = 0.0f;
    for(float n : arreglo) {
      if(n > nmax) {
        nmax = n;
      }
    }
    return nmax;
  }

  private static float min(ArrayList<Float> arreglo) {
    float nmin = 1.0f;
    for(float n : arreglo) {
      if(n < nmin) {
        nmin = n;
      }
    }
    return nmin;
  }

  private String liangBarsky(Graphics g, float xmin, float ymin, float xmax, float ymax, float x1, float y1, float x2, float y2, int w, int h){
    
    float p1 = -(x2 - x1);
    float p2 = -p1;
    float p3 = -(y2 - y1);
    float p4 = -p3;

    float q1 = x1 - xmin;
    float q2 = xmax - x1;
    float q3 = y1 - ymin;
    float q4 = ymax - y1;

    ArrayList<Float> entrantes = new ArrayList<Float>();
    ArrayList<Float> salientes = new ArrayList<Float>();

    entrantes.add(0.0f);
    salientes.add(1.0f);

    if ((p1 == 0 && q1 < 0) || (p2 == 0 && q2 < 0) || (p3 == 0 && q3 < 0) || (p4 == 0 && q4 < 0)) {
      g.setColor(Color.RED);
      g.drawLine((int) (x1 + (w/2)), (int) ((h/2) - y1), (int) ((w/2) + x2), (int) ((h/2) - y2));
      return "Linea Paralela al Clipping Zone";  
    }

    if (p1 != 0) {
      float u1 = q1 / p1;
      float u2 = q2 / p2;
      if (p1 < 0) {
        entrantes.add(u1); 
        salientes.add(u2); 
      } else {
        entrantes.add(u2);
        salientes.add(u1);
      }
    }
    if (p3 != 0) {
      float u3 = q3 / p3;
      float u4 = q4 / p4;
      if (p3 < 0) {
        entrantes.add(u3);
        salientes.add(u4);
      } else {
        entrantes.add(u4);
        salientes.add(u3);
      }
    }

    //Encontramos el maximo y minimo en los entrantes y los salientes
    float begin = max(entrantes);
    float end = min(salientes);

    //Verificamos si esta dentro o fuera del Clip

    if(begin > end){
      g.setColor(Color.RED);
      g.drawLine((int) (x1 + (w/2)), (int) ((h/2) - y1), (int) ((w/2) + x2), (int) ((h/2) - y2));  
      return "Esta por fuera de la zona";
    }


    //Calculamos los nuevos puntos que estan dentro del Clip
    float xn1 = x1 + p2 * begin;
    float yn1 = y1 + p4 * begin;

    float xn2 = x1 + p2 * end;
    float yn2 = y1 + p4 * end;


    //Dibujamos la mitad de la linea original de rojo
    g.setColor(Color.RED);
    g.drawLine((int) (x1 + (w/2)), (int) ((h/2) - y1), (int) ((w/2) + xn1), (int) ((h/2) - yn1));

    //Dibujamos la linea que esta por dentro del rectangulo
    g.setColor(Color.GREEN);
    g.drawLine((int) (xn1 + (w/2)), (int) ((h/2) - yn1), (int) ((w/2) + xn2), (int) ((h/2) - yn2));

    // Dibujamos el resto de la linea original de rojo
    g.setColor(Color.RED);
    g.drawLine((int) (xn2 + (w/2)), (int) ((h/2) - yn2), (int) ((w/2) + x2), (int) ((h/2) - y2));

    return "Success";
    
  }

  // encontrar las coordenadas de los puntos en el circulo
  private static Map.Entry<ArrayList<Integer>, ArrayList<Integer>> getPoints(int x0, int y0, int r, int numberOfPoints) {
    ArrayList<Integer> x = new ArrayList<>(numberOfPoints);
    ArrayList<Integer> y = new ArrayList<>(numberOfPoints);

    double angle = 0;

    for(int i = 0; i < numberOfPoints; i++) {

      angle = i * (360/numberOfPoints);

      x.add(i,(int) (x0 + r * Math.cos(Math.toRadians(angle))));
      y.add(i,(int) (y0 + r * Math.sin(Math.toRadians(angle))));

    }

    Map.Entry<ArrayList<Integer>, ArrayList<Integer>> coordenadas = new AbstractMap.SimpleImmutableEntry(x, y);

    return coordenadas;
  }

}