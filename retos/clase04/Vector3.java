public class Vector3{

  protected double v1;
  protected double v2;
  protected double v3;

  Vector3(double v1, double v2, double v3){
    this.v1 = v1;
    this.v2 = v2;
    this.v3 = v3;
  }

  public static Vector3 crossProduct(Vector3 v, Vector3 u){
    double i = (v.v2 * u.v3) - (u.v2 * v.v3);
    double j = (v.v1 * u.v3) - (u.v1 * v.v3);
    double k = (v.v1 * u.v2) - (u.v1 * v.v2);
    
    return new Vector3(i, j, k);
  }

  public static double dotProduct(Vector3 v, Vector3 u){
    double tempV1 = v.v1 * u.v1;
    double tempV2 = v.v2 * u.v2;
    double tempV3 = v.v3 * u.v3;
    double result = tempV1+tempV2+tempV3;
    
    return result;
  }

  public double Magnitude(){
    double result = Math.sqrt(Math.pow(this.v1,2) + Math.pow(this.v2,2) + Math.pow(this.v3,2));
    return result;
  }

  public void Normalize(){
    double magnitude = this.Magnitude();
    this.v1 = this.v1/magnitude;
    this.v2 = this.v2/magnitude;
    this.v3 = this.v3/magnitude;
  }

  // public static void main(String[] args) {
  //   Vector3 v = new Vector3(1, 1, 1);
  //   Vector3 u = new Vector3(1, 2, 3);

  //   Vector3 w = crossProduct(v, u);
  //   double x = dotProduct(v, u);

  //   System.out.println(w.Magnitude());
  //   w.Normalize();
  //   System.out.println(w.Magnitude());

  // }
}