import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class Transformations3d extends JPanel implements KeyListener {

  private final int pixelSize = 1;
  ArrayList<Edge> edges;

  public Transformations3d() {
    setPreferredSize(new Dimension(600, 500));
    setBackground(Color.WHITE);
    // add KeyListener
    this.setFocusable(true);
    this.requestFocusInWindow();

    this.addKeyListener(this);
    
    edges = new ArrayList<>();

    try {
      readFile();
    } catch(IOException e) {
      System.out.println(e);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int tecla = e.getKeyCode();

    final double ANGLE = Math.PI / 18;

    if(tecla == KeyEvent.VK_RIGHT) {
      translation(10, 0, 0);
    } else if (tecla == KeyEvent.VK_LEFT) {
      translation(-10, 0, 0);
    } else if (tecla == KeyEvent.VK_UP) {
      translation(0, 10, 0);
    } else if(tecla == KeyEvent.VK_DOWN) {
      translation(0, -10, 0);
    } else if(tecla == KeyEvent.VK_M) {
      translation(0, 0, 10);
    } else if(tecla == KeyEvent.VK_N) {
      translation(0, 0, -10);
    } else if(tecla == KeyEvent.VK_L) {
      //scalation(1.5, 1, 1);
    } else if(tecla == KeyEvent.VK_J) {
      //escalation(0.66, 1, 1);
    } else if(tecla == KeyEvent.VK_I) {
      //escalation(1, 1.5, 1);
    } else if(tecla == KeyEvent.VK_K) {
      //escalation(1, 0.66, 1);
    } else if(tecla == KeyEvent.VK_O) { // CHECK ESCALATION ON Z AXIS
      //escalation(1, 1, 1.5);
    } else if(tecla == KeyEvent.VK_U) {
      //escalation(1, 1, 0.66);
    } else if(tecla == KeyEvent.VK_D) { // x pos
      //rotation(ANGLE, 1);               
    } else if(tecla == KeyEvent.VK_A) {
      //rotation(-ANGLE, 1);
    } else if(tecla == KeyEvent.VK_W) {
      //rotation(ANGLE, 2);
    }
    // }
    // } else if(tecla == KeyEvent.VK_A){
    //   rotation(1);
    // } else if(tecla == KeyEvent.VK_D){
    //   rotation(2);
    // } else if(tecla == KeyEvent.VK_S){
    //   escalation(0.66);
    // }
    repaint();    
  }
    @Override
  public void keyReleased(KeyEvent e) {}

    @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    int w = (getWidth() - 1) / pixelSize;
    int h = (getHeight() - 1) / pixelSize;

    Graphics2D g2d = (Graphics2D) g;
    perspective(-500);

    for(Edge e: edges) {
      Line2D line = new Line2D.Double((w/2) + e.point1.x, (h/2) - e.point1.y, (w/2) + e.point2.x, (h/2) - e.point2.y);
      g2d.draw(line);
    }
  }

  private void perspective(double d) {

    double change = 1 / d;

    double[][] mPersp = {{1,0,0,0},
                        {0,1,0,0},
                        {0,0,1,0},
                        {0,0,change,0}};

    Matrix4x4 persp = new Matrix4x4(mPersp);

    for(int i=0;i < edges.size();i++) {
      Point3 p1 = edges.get(i).point1;
      Point3 p2 = edges.get(i).point2;

      Point3 p3 = Matrix4x4.times(persp, p1);
      Point3 p4 = Matrix4x4.times(persp, p2);

      p3.x = p3.x/p3.w;
      p3.y = p3.y/p3.w;
      p3.z = p3.z/p3.w;
      p3.w = 1;

      p4.x = p4.x/p4.w;
      p4.y = p4.y/p4.w;
      p4.z = p4.z/p4.w;
      p4.w = 1;

      edges.get(i).point1 = p3;
      edges.get(i).point2 = p4;
    }

  }

  private void translation(double dx, double dy, double dz) {

    double[][] mTrans = {{1,0,0,dx},
                         {0,1,0,dy},
                         {0,0,1,dz},
                         {0,0,0,1}};

    Matrix4x4 trans = new Matrix4x4(mTrans);
    for(int i=0;i<edges.size();i++){
      Point3 p1 = edges.get(i).point1;
      Point3 p2 = edges.get(i).point2;

      Point3 p3 = Matrix4x4.times(trans, p1);
      Point3 p4 = Matrix4x4.times(trans, p2);

      edges.get(i).point1 = p3;
      edges.get(i).point2 = p4;
    }

  }

  private void readFile() throws IOException {

    BufferedReader scan = new BufferedReader(new FileReader("inspiration2.txt"));
    
    int nPoints = Integer.parseInt(scan.readLine());

    ArrayList<Point3> points = new ArrayList<>();

    for(int i = 0; i < nPoints; i++) {
      String data = scan.readLine();
      String[] coordinates = data.split(" ");
      Point3 p1 = new Point3(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[2]), 1);
      points.add(p1);
    }

    int numLines = Integer.parseInt(scan.readLine());
    for(int i = 0; i < numLines; i++) {
      String data = scan.readLine();
      String[] positions = data.split(" ");
      int p1 = Integer.parseInt(positions[0]);
      int p2 = Integer.parseInt(positions[1]);
      Edge linea = new Edge(points.get(p1), points.get(p2));
      edges.add(linea);
    }
    scan.close();
  }

  public static void main(String[] args) {
    // Crear un nuevo Frame
    JFrame frame = new JFrame("Transformations");
    // Al cerrar el frame, termina la ejecución de este programa
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Agregar un JPanel que se llama Points (esta clase)
    Transformations3d t3d = new Transformations3d();
    frame.add(t3d);
    // Asignarle tamaño
    frame.setSize(600, 500);
    // Poner el frame en el centro de la pantalla
    frame.setLocationRelativeTo(null);
    // Mostrar el frame
    frame.setVisible(true);
}

}