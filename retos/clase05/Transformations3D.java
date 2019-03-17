import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

/**
 * Esta clase recibe un formato .txt y apartir de la estructura establecidad en el formato dibuja
 * la imagen en cuestión utilizando el metodo paintComponent y analizando el archivo por medio de
 * un File Reader para luego dibujarlo en el panel.
 * Mediante la interaccion con el usuario (teclado), se aplican las transformaciones lineales de 
 * traslacion, rotacion y escalamiento.
 */
public class Transformations3D extends JPanel implements KeyListener {

  private final int pixelSize = 1;
  protected ArrayList<Point3> points;
  protected int[][] connections;


  public Transformations3D() {
    setPreferredSize(new Dimension(600, 500));
    setBackground(Color.WHITE);
    // add KeyListener
    this.setFocusable(true);
    this.requestFocusInWindow();

    this.addKeyListener(this);
    // instantiate the points array
    points = new ArrayList<>();

    try{
      // instantiate the connections between the points
      connections = readInitial();
    }catch(IOException e){
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
      escalation(1.5, 1, 1);
    } else if(tecla == KeyEvent.VK_J) {
      escalation(0.66, 1, 1);
    } else if(tecla == KeyEvent.VK_I) {
      escalation(1, 1.5, 1);
    } else if(tecla == KeyEvent.VK_K) {
      escalation(1, 0.66, 1);
    } else if(tecla == KeyEvent.VK_O) { // CHECK ESCALATION ON Z AXIS
      escalation(1, 1, 1.5);
    } else if(tecla == KeyEvent.VK_U) {
      escalation(1, 1, 0.66);
    } else if(tecla == KeyEvent.VK_D) { // x pos
      rotation(ANGLE, 1);               
    } else if(tecla == KeyEvent.VK_A) {
      rotation(-ANGLE, 1);
    } else if(tecla == KeyEvent.VK_W) {
      rotation(ANGLE, 2);
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

    perspective(-500);

    for(int i=0;i<connections.length;i++){
      int p1 = connections[i][0];
      int p2 = connections[i][1];

      g.drawLine((int) points.get(p1).x + (w/2), (h/2) - (int) points.get(p1).y, (int) points.get(p2).x + (w/2), (h/2) - (int) points.get(p2).y);

    }
  }

  // read a text file and get the original values of the points and lines to be drawn
  public int[][] readInitial() throws IOException{
    BufferedReader scan = new BufferedReader(new FileReader("inspiration2.txt"));
    
    int nPoints = Integer.parseInt(scan.readLine());

    // get each point
    for(int i=0; i < nPoints; i++){
      String data = scan.readLine();
      String[] coordinates = data.split(" ");
      points.add(new Point3(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[2]), 1));
    }

    // get the lines connecting each point
    int nLines = Integer.parseInt(scan.readLine());
    int[][] connectionsAux = new int[nLines][2]; 
    for(int i=0; i < nLines ;i++){
      String data = scan.readLine();
      String[] lines = data.split(" ");
      int p1 = Integer.parseInt(lines[0]);
      int p2 = Integer.parseInt(lines[1]);
      connectionsAux[i][0] = p1;
      connectionsAux[i][1] = p2;
    }

    return connectionsAux;
  }

  // In this method we multiply each poInt with the perspective matrix in order to visualize it in 3D
  public void perspective(double d){
    
    ArrayList<Point3> pointsAux = new ArrayList<>();
    double change = 1/d;
    double[][] mPerspective = {{1,0,0,0},
                               {0,1,0,0},
                               {0,0,1,0},
                               {0,0,change,0}};
    Matrix4x4 perspectiveM = new Matrix4x4(mPerspective);
    for(Point3 p: points){
      Point3 newPoint = Matrix4x4.times(perspectiveM,p);
      pointsAux.add(newPoint);
    }

    ArrayList<Point3> pointsAuxII = new ArrayList<>();
    for(Point3 p: pointsAux){
      double x = p.x/p.w;
      double y = p.y/p.w;
      double z = p.z/p.w;
      Point3 newPoint = new Point3(x,y,z,1);
      pointsAuxII.add(newPoint);
    }

    points = pointsAuxII;
  }

  // This method trnaslates the figure in all 3 axis
  public void translation(double dx, double dy, double dz){
    
    ArrayList<Point3> pointsAux = new ArrayList<>();

    double[][] mTrans = {{1,0,0,dx},
                         {0,1,0,dy},
                         {0,0,1,dz},
                         {0,0,0,1}};
    
    Matrix4x4 transM = new Matrix4x4(mTrans);
    for(Point3 p: points){
      Point3 newPoint = Matrix4x4.times(transM, p);
      pointsAux.add(newPoint);
    }

    points = pointsAux;
    
  }

  public void rotation(double angle, int axis) {

    Point3 pivot = centroidSearch();

    translation(-pivot.x, -pivot.y, -pivot.z);

    ArrayList<Point3> pointsAux = new ArrayList<>();
    switch(axis){
      case 1:
        double[][] mRotateX = {{1,               0,                0, 0},
                               {0, Math.cos(angle), -Math.sin(angle), 0},
                               {0, Math.sin(angle), Math.cos(angle) , 0},
                               {0,               0, 0               , 1}};

        Matrix4x4 rotateX = new Matrix4x4(mRotateX);

        for(Point3 p: points) {
          Point3 newPoint = Matrix4x4.times(rotateX, p);
          pointsAux.add(newPoint);
        }

        points = pointsAux;
        break;
      
      case 2:
        double[][] mRotateY = {{Math.cos(angle), 0, -Math.sin(angle), 0},
                               {0              , 1, 0               , 0},
                               {Math.sin(angle), 0, Math.cos(angle) , 0},
                               {0              , 0, 0               , 1}};

        Matrix4x4 rotateY = new Matrix4x4(mRotateY);

        for(Point3 p: points) {
          Point3 newPoint = Matrix4x4.times(rotateY, p);
          pointsAux.add(newPoint);
        }

        points = pointsAux;
        break;

      case 3:
        double[][] mRotateZ = {{Math.cos(angle), -Math.sin(angle), 0, 0},
                               {Math.sin(angle),  Math.cos(angle), 0, 0},
                               {0              ,  0              , 1, 0},
                               {0              ,  0              , 0, 1}};

        Matrix4x4 rotateZ = new Matrix4x4(mRotateZ);

        for(Point3 p: points) {
          Point3 newPoint = Matrix4x4.times(rotateZ, p);
          pointsAux.add(newPoint);
        }

        points = pointsAux;
        break;
    }

    translation(pivot.x, pivot.y, pivot.z);

  }

  //This metod escalates figure 
  public void escalation(double sX, double sY, double sZ){

    Point3 pivot = centroidSearch();
    
    ArrayList<Point3> pointsAux = new ArrayList<>();

    translation(-pivot.x, -pivot.y, -pivot.z);

    double[][] mScale = {{sX, 0, 0, 0},
                         {0, sY, 0, 0},
                         {0, 0, sZ, 0},
                         {0, 0, 0, 1}};

    Matrix4x4 scaleM = new Matrix4x4(mScale);
    for(Point3 p: points){
      Point3 newPoint = Matrix4x4.times(scaleM, p);
      pointsAux.add(newPoint);
    }

    points = pointsAux;

    translation(pivot.x, pivot.y, pivot.z);
  }

  // Get the centroid of a geometric figure using the Geometric method
  private Point3 centroidSearch(){
    
    double xFactor = 0;
    double yFactor = 0;
    double zFactor = 0;

    for(Point3 p: points) {
      xFactor += p.x;
      yFactor += p.y;
      zFactor += p.z;
    }
    
    return new Point3(xFactor/points.size(), yFactor/points.size(), zFactor/points.size(), 1);
  }

// ------------------------------------------------------------------------------------------------------------







  // // Function used to move the drawing to the right, left, up or down
  // public void translation(int direction, double distance){

  //   ArrayList<Point2> pointsAux = new ArrayList<>();

  //   switch(direction){
  //     case 1:  //Right Case
  //       double[][] mRight = {{1, 0, distance},
  //                            {0, 1, 0},
  //                            {0, 0, 1}};

  //       Matrix3x3 transRight = new Matrix3x3(mRight);
  //       for (int i=0;i<points.size();i++){

  //         Point2 newPoint = Matrix3x3.times(transRight, points.get(i));
  //         pointsAux.add(newPoint);

  //       }
  //       points = pointsAux;
  //       break;

  //     case 2:  //Up Case
  //       double[][] mUp = {{1, 0, 0},
  //                        {0, 1, distance},
  //                        {0, 0, 1}};

  //       Matrix3x3 transUp = new Matrix3x3(mUp);
  //       for (int i=0;i<points.size();i++){

  //         Point2 newPoint = Matrix3x3.times(transUp, points.get(i));
  //         pointsAux.add(newPoint);

  //       }
  //       points = pointsAux;
  //       break;

  //     case 3:  //Left Case
  //       double[][] mLeft = {{1, 0, -distance},
  //                           {0, 1, 0},
  //                           {0, 0, 1}};

  //       Matrix3x3 transLeft = new Matrix3x3(mLeft);
  //       for (int i=0;i<points.size();i++){

  //         Point2 newPoint = Matrix3x3.times(transLeft, points.get(i));
  //         pointsAux.add(newPoint);

  //       }
  //       points = pointsAux;
  //       break;

  //     case 4:  //Down Case
  //       double[][] mDown = {{1, 0, 0},
  //                           {0, 1, -distance},
  //                           {0, 0, 1}};

  //       Matrix3x3 transDown = new Matrix3x3(mDown);
  //       for (int i=0;i<points.size();i++){

  //         Point2 newPoint = Matrix3x3.times(transDown, points.get(i));
  //         pointsAux.add(newPoint);

  //       }
  //       points = pointsAux;
  //       break;
      
  //     default:
  //       System.out.println("Error");
        
  //   }
  // }

  // // Function used to rotate the drawing to a certain degree, either clockwise or anti-clockwise
  // public void rotation(int direction){

  //   final double THETA = Math.PI / 18;

  //   ArrayList<Point2> pointsAux = new ArrayList<>();

  //   switch(direction){ 
  //     case 1: //Rotate clockwise
  //       double[][] mRotateR = {{Math.cos(THETA), -Math.sin(THETA), 0},
  //                              {Math.sin(THETA),  Math.cos(THETA), 0},
  //                              {0              ,  0              , 1}};

  //       Matrix3x3 rotateR = new Matrix3x3(mRotateR);
  //       for (int i=0;i<points.size();i++){

  //         Point2 newPoint = Matrix3x3.times(rotateR, points.get(i));
  //         pointsAux.add(newPoint);

  //       }
  //       points = pointsAux;
  //       break;
      
  //     case 2: //Rotate anti-clockwise
  //       double[][] mRotateL = {{Math.cos(THETA),  Math.sin(THETA), 0},
  //                              {-Math.sin(THETA), Math.cos(THETA), 0},
  //                              {0              ,  0              , 1}};

  //       Matrix3x3 rotateL = new Matrix3x3(mRotateL);
  //       for (int i=0;i<points.size();i++){

  //         Point2 newPoint = Matrix3x3.times(rotateL, points.get(i));
  //         pointsAux.add(newPoint);

  //       }
  //       points = pointsAux;
  //       break;
      
  //     default:
  //       System.out.println("Error");
  //   }
  // }

  // // Function used to scale the drawing on it's on axis
  // public void escalation(double rate){

  //   // get the centroid
  //   Point2 centroid = centroidSearch();

  //   ArrayList<Point2> pointsAux = new ArrayList<>();

  //   // translate it to the origin
  //   double[][] mCenter = {{1, 0, -centroid.x},
  //                         {0, 1, -centroid.y},
  //                         {0, 0, 1}};

  //   Matrix3x3 transC = new Matrix3x3(mCenter);
  //   for (int i=0;i<points.size();i++){

  //     Point2 newPoint = Matrix3x3.times(transC, points.get(i));
  //     pointsAux.add(newPoint);

  //   }
  //   points = pointsAux;

  //   ArrayList<Point2> pointsAux2 = new ArrayList<>();

  //   // Calculate Scale Matrix
  //   double[][] mScale = {{rate, 0   , 0},
  //                        {0   , rate, 0},
  //                        {0   , 0   , 1}};

  //   Matrix3x3 scale = new Matrix3x3(mScale);

  //   // Scale points
  //   for(int i=0;i<points.size();i++){

  //     Point2 newPoint = Matrix3x3.times(scale, points.get(i));
  //     pointsAux2.add(newPoint);

  //   }
  //   points = pointsAux2;

  //   ArrayList<Point2> pointsAux3 = new ArrayList<>();

  //   // translate to it's original position
  //   double[][] mReturn = {{1, 0, centroid.x},
  //                         {0, 1, centroid.y},
  //                         {0, 0, 1}};

  //   Matrix3x3 transR = new Matrix3x3(mReturn);
  //   for (int i=0;i<points.size();i++){

  //     Point2 newPoint = Matrix3x3.times(transR, points.get(i));
  //     pointsAux3.add(newPoint);

  //   }
  //   points = pointsAux3;
  // }

  public static void main(String[] args) {
    // Crear un nuevo Frame
    JFrame frame = new JFrame("Transformations");
    // Al cerrar el frame, termina la ejecución de este programa
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Agregar un JPanel que se llama Points (esta clase)
    Transformations3D t3D = new Transformations3D();
    frame.add(t3D);
    // Asignarle tamaño
    frame.setSize(600, 500);
    // Poner el frame en el centro de la pantalla
    frame.setLocationRelativeTo(null);
    // Mostrar el frame
    frame.setVisible(true);
}
}