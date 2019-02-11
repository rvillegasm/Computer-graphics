public class Matrix4x4{
  
  protected double[][] matrix;
  
  Matrix4x4(){
    matrix = new double[4][4];
  }

  private void add(double n, int row, int col) {
    matrix[row][col] = n;
  }

  protected static Point3 times(Matrix4x4 matrix, Point3 point){
    double tempX = (matrix.matrix[0][0]*point.x)+(matrix.matrix[0][1]*point.y)+(matrix.matrix[0][2]*point.z)+(matrix.matrix[0][3]*point.w);
    double tempY = (matrix.matrix[1][0]*point.x)+(matrix.matrix[1][1]*point.y)+(matrix.matrix[1][2]*point.z)+(matrix.matrix[1][3]*point.w);
    double tempZ = (matrix.matrix[2][0]*point.x)+(matrix.matrix[2][1]*point.y)+(matrix.matrix[2][2]*point.z)+(matrix.matrix[2][3]*point.w);
    double tempW = (matrix.matrix[3][0]*point.x)+(matrix.matrix[3][1]*point.y)+(matrix.matrix[3][2]*point.z)+(matrix.matrix[3][3]*point.w);
    
    return new Point3(tempX, tempY, tempZ, tempW);    
  }

  protected static Matrix4x4 times(Matrix4x4 matrix1, Matrix4x4 matrix2){
    Matrix4x4 returnMatrix = new Matrix4x4();
    double temp = 0;
    
    for(int i=0;i<4;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<4;k++){
          temp += matrix1.matrix[i][k]*matrix2.matrix[k][j];
        }
        returnMatrix.add(temp, i, j);
      }
    }
    return returnMatrix;
  }

  // public static void main(String[] args) {
  //   Matrix4x4 m1 = new Matrix4x4();
  //   m1.add(1, 0, 0);
  //   m1.add(1, 0, 1);
  //   m1.add(1, 0, 2);
  //   m1.add(1, 0, 3);
  //   m1.add(1, 1, 0);
  //   m1.add(1, 1, 1);
  //   m1.add(1, 1, 2);
  //   m1.add(1, 1, 3);
  //   m1.add(1, 2, 0);
  //   m1.add(1, 2, 1);
  //   m1.add(1, 2, 2);
  //   m1.add(1, 2, 3);
  //   m1.add(1, 3, 0);
  //   m1.add(1, 3, 1);
  //   m1.add(1, 3, 2);
  //   m1.add(1, 3, 3);

  //   Matrix4x4 m2 = new Matrix4x4();
  //   m2.add(1, 0, 0);
  //   m2.add(2, 0, 1);
  //   m2.add(3, 0, 2);
  //   m2.add(4, 0, 3);
  //   m2.add(5, 1, 0);
  //   m2.add(6, 1, 1);
  //   m2.add(7, 1, 2);
  //   m2.add(8, 1, 3);
  //   m2.add(9, 2, 0);
  //   m2.add(10, 2, 1);
  //   m2.add(11, 2, 2);
  //   m2.add(12, 2, 3);
  //   m2.add(13, 3, 0);
  //   m2.add(14, 3, 1);
  //   m2.add(15, 3, 2);
  //   m2.add(16, 3, 3);

  //   Matrix4x4 m3 = times(m1, m2);

  //   for(int i=0;i<4;i++){
  //     for(int j=0;j<4;j++){
  //       System.out.println(m3.matrix[i][j]);
  //     }
  //   }
  // }
}