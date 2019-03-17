import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Esta clase recibe un formato .txt y apartir de la estructura establecidad en el formato dibuja
 * la imagen en cuestión utilizando el metodo paintComponent y analizando el archivo por medio de
 * un File Reader para luego dibujarlo en el panel
 */
public class BobRoss extends JPanel{

  private final int pixelSize = 1;

  BobRoss() {
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

    try{
      readPoints(g, w, h);
    }catch(IOException e){
      System.out.println(e);
    }
  }

  public void readPoints(Graphics g, int w, int h) throws IOException{
    BufferedReader scan = new BufferedReader(new FileReader("inspiration.txt"));
    
    int nPoints = Integer.parseInt(scan.readLine());
    int[][] points = new int[nPoints][2];

    ArrayList<Point2> vectorPoints = new ArrayList<>();
    Matrix3x3 trans = new Matrix3x3();
    trans.add(1, 0, 0);
    trans.add(0, 0, 1);
    trans.add(20, 0, 2);
    trans.add(0, 1, 0);
    trans.add(1, 1, 1);
    trans.add(40, 1, 2);
    trans.add(0, 2, 0);
    trans.add(0, 2, 1);
    trans.add(1, 2, 2);

    for(int i=0; i < nPoints; i++){
      String data = scan.readLine();
      String[] coordinates = data.split(" ");
      points[i][0] = Integer.parseInt(coordinates[0]);
      points[i][1] = Integer.parseInt(coordinates[1]);

      vectorPoints.add(new Point2(points[i][0], points[i][1], 1));
    }

    int nLines = Integer.parseInt(scan.readLine());
    int[][] conections = new int[nLines][2]; 
    for(int i=0; i < nLines ;i++){
      String data = scan.readLine();
      String[] lines = data.split(" ");
      int p1 = Integer.parseInt(lines[0]);
      int p2 = Integer.parseInt(lines[1]);
      conections[i][0] = p1;
      conections[i][1] = p2;
      g.drawLine(points[p1][0] + (w/2), (h/2) - points[p1][1], points[p2][0] + (w/2), (h/2) - points[p2][1]);
    }

    ArrayList<Point2> modVectorPoints = new ArrayList<>();
    for(Point2 v: vectorPoints) {
      Point2 u = Matrix3x3.times(trans, v);
      modVectorPoints.add(u);
    }



    for(int i=0; i < nLines ;i++){
      g.setColor(Color.GREEN);
      g.drawLine((int) modVectorPoints.get(conections[i][0]).x + (w/2), (h/2) - (int) modVectorPoints.get(conections[i][0]).y, (int) modVectorPoints.get(conections[i][1]).x + (w/2), (h/2) - (int) modVectorPoints.get(conections[i][1]).y);
    }

  }
}