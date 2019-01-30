
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Clase en la que está la implementación del algoritmo de Bresenham.
 * Tomado de Rosettacode.org. y adaptado por: Rafael Villegas & Felipe Cortes.
 * 
 * En este caso específico se dibujan lineas que conectan N puntos entre si de forma alternante.
 */
class BresenhamPanel extends JPanel {
 
  private final int pixelSize = 1;

  BresenhamPanel() {
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
    int x1 = -maxX, x2 = maxX * -2 / 3, x3 = maxX * 2 / 3, x4 = maxX;
    int y1 = -maxY, y2 = maxY * -2 / 3, y3 = maxY * 2 / 3, y4 = maxY;

    Map.Entry<ArrayList<Integer>, ArrayList<Integer>> coordenadas = getPoints(0, 0, 100, 5);

    // Paint the lines connecting the points
    for(int i = 0; i < coordenadas.getKey().size(); i++) {

      if(i == coordenadas.getKey().size() - 1) {
        drawLine(g, coordenadas.getKey().get(i), coordenadas.getValue().get(i), coordenadas.getKey().get(1), coordenadas.getValue().get(1));
      }
      else if(i == coordenadas.getKey().size() - 2) {
        drawLine(g, coordenadas.getKey().get(i), coordenadas.getValue().get(i), coordenadas.getKey().get(0), coordenadas.getValue().get(0));
      }
      else {
        drawLine(g, coordenadas.getKey().get(i), coordenadas.getValue().get(i), coordenadas.getKey().get(i+2), coordenadas.getValue().get(i+2));
      }

    }

  }

  private void plot(Graphics g, int x, int y) {
    int w = (getWidth() - 1) / pixelSize;
    int h = (getHeight() - 1) / pixelSize;
    int maxX = (w - 1) / 2;
    int maxY = (h - 1) / 2;

    int borderX = getWidth() - ((2 * maxX + 1) * pixelSize + 1);
    int borderY = getHeight() - ((2 * maxY + 1) * pixelSize + 1);
    int left = (x + maxX) * pixelSize + borderX / 2;
    int top = (y + maxY) * pixelSize + borderY / 2;

    g.setColor(Color.black);
    g.drawOval(left, top, pixelSize, pixelSize);
  }

  private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
    // delta of exact value and rounded value of the dependent variable
    int d = 0;

    int dx = Math.abs(x2 - x1);
    int dy = Math.abs(y2 - y1);

    int dx2 = 2 * dx; // slope scaling factors to
    int dy2 = 2 * dy; // avoid floating point

    int ix = x1 < x2 ? 1 : -1; // increment direction
    int iy = y1 < y2 ? 1 : -1;

    int x = x1;
    int y = y1;

    if (dx >= dy) {
      while (true) {
        plot(g, x, y);
        if (x == x2)
          break;
        x += ix;
        d += dy2;
        if (d > dx) {
          y += iy;
          d -= dx2;
        }
      }
    } else {
        while (true) {
          plot(g, x, y);
            if (y == y2)
              break;
            y += iy;
            d += dx2;
            if (d > dy) {
              x += ix;
              d -= dy2;
            }
        }
      }
  }

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