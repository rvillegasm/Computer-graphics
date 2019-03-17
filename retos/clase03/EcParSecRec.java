import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;

/**
 * Clase encargada de representar las ecuaciones parametricas de un segmento de recta
 * Incluye una funcion para encontrar los parametros en los que se interseptan dos rectas
 * @author Rafael Villegas, Felipe Cortes
 */
public class EcParSecRec {

  protected double x1;
  protected double x2;
  protected double y1;
  protected double y2;

  EcParSecRec(double x1, double x2, double y1, double y2) {
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
  }
  
  public static Map.Entry solve(EcParSecRec e1, EcParSecRec e2) {

    double u2 = ((e2.y1 - e1.y1) * (e1.x2 - e1.x1) - (e2.x1 - e1.x1) * (e1.y2 - e1.y1)) /
                ((e2.x2 - e2.x1) * (e1.y2 - e1.y1) - (e2.y2 - e2.y1) * (e1.x2 - e1.x1));
    
    double u1 = (e2.x1 + ((e2.x2 - e2.x1) * u2) - e1.x1) / 
                         (e1.x2 - e1.x1);
    
    return new SimpleImmutableEntry<>(u1, u2);
  }

  // public static void main(String[] args) {
  //   EcParSecRec e1 = new EcParSecRec(0, 4, 0, 4);
  //   EcParSecRec e2 = new EcParSecRec(0, 4, 4, 0);

  //   Map.Entry<Double, Double> uPair = solve(e1, e2);

  //   System.out.println(uPair.getKey());
  //   System.out.println(uPair.getValue());
  // }

}