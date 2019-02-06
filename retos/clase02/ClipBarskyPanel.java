
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Clase en la que está la implementación del algoritmo de Bresenham.
 * Tomado de Rosettacode.org. y adaptado por: Rafael Villegas & Felipe Cortes.
 * 
 * En este caso específico se dibujan lineas que conectan N puntos entre si de forma alternante.
 */
class ClipBarskyPanel extends JPanel {
 
  private final int pixelSize = 1;

  ClipBarskyPanel() {
    setPreferredSize(new Dimension(600, 500));
    setBackground(Color.WHITE);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    int w = (getWidth() - 1) / pixelSize;
    int h = (getHeight() - 1) / pixelSize;
    int maxX = (w - 1) / 2;
    int maxY = (h - 1) / 2;

    g.drawLine(0 + (w/2), (h/2) - 0, 100 + (w/2), (h/2) - 0);
    g.drawLine(100 + (w/2), (h/2) - 0, 100 + (w/2), (h/2) - 100);
    g.drawLine(100 + (w/2), (h/2) - 100, 0 + (w/2), (h/2) - 100);
    g.drawLine(0 + (w/2), (h/2) - 100, 0 + (w/2), (h/2) - 0);

    String message = liangBarsky(g, 0, 0, 100, 100, -100, -100, 150, 150, w, h);

    System.out.println(message);
    

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

    if ((p1 == 0 && q1 < 0) || (p2 == 0 && q2 < 0) || (p3== 0 && q3 < 0) || (p4 == 0 && q4 < 0)) {
      return "Linea Paralela al Clipping Zone";
    }

    float u1 = q1/p1;
    float u2 = q2/p2;
    float u3 = q3/p3;
    float u4 = q4/p4;

    if(p1 < 0.0f){
      entrantes.add(u1);
    }else {
      salientes.add(u1);
    }

    if(p2 < 0.0f){
      entrantes.add(u2);
    }else {
      salientes.add(u2);
    }

    if(p3 < 0.0f){
      entrantes.add(u3);
    }else {
      salientes.add(u3);
    }

    if(p4 < 0.0f){
      entrantes.add(u4);
    }else {
      salientes.add(u4);
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


    //Dibujamos la linea original de rojo
    g.setColor(Color.RED);
    g.drawLine((int) (x1 + (w/2)), (int) ((h/2) - y1), (int) ((w/2) + x2), (int) ((h/2) - y2));

    //Dibujamos la linea que esta por dentro del rectangulo
    g.setColor(Color.GREEN);
    g.drawLine((int) (xn1 + (w/2)), (int) ((h/2) - yn1), (int) ((w/2) + xn2), (int) ((h/2) - yn2));

    return "Success";
    
  }

}