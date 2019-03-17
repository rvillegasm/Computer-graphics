import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    for(int i=0; i < nPoints; i++){
      String data = scan.readLine();
      String[] coordinates = data.split(" ");
      points[i][0] = Integer.parseInt(coordinates[0]);
      points[i][1] = Integer.parseInt(coordinates[1]); 
    }

    int nLines = Integer.parseInt(scan.readLine());
    for(int i=0; i < nLines ;i++){
      String data = scan.readLine();
      String[] lines = data.split(" ");
      int p1 = Integer.parseInt(lines[0]);
      int p2 = Integer.parseInt(lines[1]);
      g.drawLine(points[p1][0] + (w/2), (h/2) - points[p1][1], points[p2][0] + (w/2), (h/2) - points[p2][1]);
    }

  }
}