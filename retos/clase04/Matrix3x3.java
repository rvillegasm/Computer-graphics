public class Matrix3x3{
  
  protected double[][] matrix;
  
  Matrix3x3(){
    matrix = new double[3][3];
  }

  Matrix3x3(double[][] m) {
    matrix = new double[3][3];

    for(int i = 0; i < m.length; i++) {
      for(int j = 0; j < m.length; j++) {
        matrix[i][j] = m[i][j];
      }
    }
  }

  public void add(double n, int row, int col) {
    matrix[row][col] = n;
  }

  public static Point2 times(Matrix3x3 matrix, Point2 point){
    double tempX = (matrix.matrix[0][0]*point.x)+(matrix.matrix[0][1]*point.y)+(matrix.matrix[0][2]*point.w);
    double tempY = (matrix.matrix[1][0]*point.x)+(matrix.matrix[1][1]*point.y)+(matrix.matrix[1][2]*point.w);
    double tempW = (matrix.matrix[2][0]*point.x)+(matrix.matrix[2][1]*point.y)+(matrix.matrix[2][2]*point.w);   
    
    return new Point2(tempX, tempY, tempW);    
  }

  public static Matrix3x3 times(Matrix3x3 matrix1, Matrix3x3 matrix2){
    Matrix3x3 returnMatrix = new Matrix3x3();
    double temp = 0;
    
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        for(int k=0;k<3;k++){
          temp += matrix1.matrix[i][k]*matrix2.matrix[k][j];
        }
        returnMatrix.add(temp, i, j);
      }
    }
    return returnMatrix;
  }

  

  public static void main(String[] args) {
    double[][] m = {{1, 0, 0},
                    {0, 1, 0},
                    {0, 0, 1}};
    Matrix3x3 mat = new Matrix3x3(m);

    Point2 p = new Point2(2, 1, 1);

    Point2 mp = times(mat, p);

    System.out.println(mp.x);
    System.out.println(mp.y);
    System.out.println(mp.w);
  }
}