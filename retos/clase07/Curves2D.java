import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Curves2D extends JPanel {

  PointT[][] controlPoints = new PointT[3][4];

  public Curves2D() {
    setBackground(Color.WHITE);

    controlPoints[0][0] = new PointT(-150, -100, 1100);
    controlPoints[1][0] = new PointT(-150, 0, 1000);
    controlPoints[2][0] = new PointT(-150, -100, 900);

    controlPoints[0][1] = new PointT(-50, 0, 1100);
    controlPoints[1][1] = new PointT(-50, 0, 1000);
    controlPoints[2][1] = new PointT(-50, 0, 900);

    controlPoints[0][2] = new PointT(50, 0, 1100);
    controlPoints[1][2] = new PointT(50, 0, 1000);
    controlPoints[2][2] = new PointT(50, 0, 900);

    controlPoints[0][3] = new PointT(150, 100, 1100);
    controlPoints[1][3] = new PointT(150, 0, 1000);
    controlPoints[2][3] = new PointT(150, 100, 900);
  }

  @Override
  public void paintComponent(Graphics g) {
    
    super.paintComponent(g);
    this.removeAll();
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLACK);
        

    ArrayList<PointT> pointsToBeDrawn = new ArrayList<>(); 

    for(double u = 0; u <= 1; u += 0.01) {
      for(double v = 0; v <= 1; v += 0.01) {
        
        double pX = bezierPoints(u, v, controlPoints, 'x');
        double pY = bezierPoints(u, v, controlPoints, 'y');
        double pZ = bezierPoints(u, v, controlPoints, 'z');

        pointsToBeDrawn.add(new PointT(pX, pY, pZ));
      }
    }

    for(int i = 0; i < pointsToBeDrawn.size()-1; i++) {
      Line2D line = new Line2D.Double(pointsToBeDrawn.get(i).x, pointsToBeDrawn.get(i).y, pointsToBeDrawn.get(i+1).x, (int) pointsToBeDrawn.get(i+1).y);

      int origeny = getHeight() / 2;
      int origenx = (getWidth() / 2);
      double x1 = line.getX1() + origenx;
      double y1 = origeny - line.getY1();
      double x2 = line.getX2() + origenx;
      double y2 = origeny - line.getY2();
      line.setLine(x1, y1, x2, y2);
      g2d.draw(line);
    }

  }

  private double factorial(double n) {

    double fact = 1;

    while(n > 0) {
      fact *= n;
      n -= 1;
    }

    return fact;
  }

  private double permutation(double n, double k) {
    return factorial(n) / (factorial(k) * factorial(n-k));
  }

  private double bez(double u, double k, double n) {
    return permutation(n, k) * Math.pow(u, k) * Math.pow(1-u, n-k);
  }

  public double bezierPoints(double u, double v, PointT[][] points, char var) {
    double p = 0;

    if(var == 'x') {

      for(int j = 0; j < points.length; j++) {
        for(int k = 0; k < points[0].length; k++) {
          p = points[j][k].x * bez(u, j, points.length-1) * bez(v, k, points[0].length-1);
        }
      }
    }
    else if(var == 'y') {

      for(int j = 0; j < points.length; j++) {
        for(int k = 0; k < points[0].length; k++) {
          p = points[j][k].y * bez(u, j, points.length-1) * bez(v, k, points[0].length-1);
        }
      }
    }
    else {

      for(int j = 0; j < points.length; j++) {
        for(int k = 0; k < points[0].length; k++) {
          p = points[j][k].z * bez(u, j, points.length-1) * bez(v, k, points[0].length-1);
        }
      }
    }
    
    System.out.println(p);
    
    return p;
  }



  public static void main(String[] args) {

    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setTitle("2D Curves");
    Curves2D curve = new Curves2D();
    f.add(curve);
    f.setSize(600, 500);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
}

}