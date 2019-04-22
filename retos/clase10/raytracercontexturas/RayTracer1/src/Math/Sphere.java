/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

import Scene.Material;
import Scene.Colour;
import Scene.Shader;

/**
 * This class keeps the information about a Sphere, for Ray Tracing purposes
 * @author htrefftz
 */
public class Sphere implements Intersectable {
    Point center;
    double radius;
    Material material;
    /**
     * Constructor
     * @param center Center of the Sphere
     * @param radius Radius of the Sphere
     * @param material Material of the Sphere
     */
    public Sphere(Point center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }
    
    /**
     * Intersect a Sphere with a ray. Returns the t value(s) for the ray at the solution(s)
     * (if any).
     * @param ray Ray on intersect the Sphere with
     * @return Solutions. May be 0, 1 or 2 solutions
     */
    @Override
    public Solutions intersect(Ray ray) {
        double a = Vector4.dotProduct(ray.u, ray.u);
        Vector4 centerOrigin = new Vector4(this.center, ray.p0);
        double b = 2 * (Vector4.dotProduct(centerOrigin,ray.u));
        double c = Vector4.dotProduct(centerOrigin, centerOrigin) - 
                this.radius * this.radius;
        double det = b*b - 4*a*c;
        if(det < 0) {
            // No solutions
            return new Solutions(0, 0, 0);
        } else if (det > 0) {
            // Two solutions
            double sol1 = (-b - Math.sqrt(det))/(2*a);
            double sol2 = (-b + Math.sqrt(det))/(2*a);
            return new Solutions(2, sol1, sol2);
        } else {
            // One solution
            double sol = (-b) / (2*a);
            return new Solutions(1, sol, 0);
        }
    }
    
    /**
     * Returns the normal of the sphere at point p.
     * Point p is assumed to be at the surface of the sphere
     * @param p point at the surface
     * @return normal at point p
     */
    @Override
    public Vector4 computeNormal(Point p) {
        Vector4 normal = new Vector4(center, p);
        normal.normalize();
        return normal;
    }
    
    /**
     * Call the shader to determine the color a the point of intersection
     * determined by the ray and parameter minT
     * @param ray ray that determines the point
     * @param minT value of parameter t
     * @return Colour determined by the shader for this point
     */
    @Override
    public Colour callShader(Ray ray, double minT) {
        Point point = ray.evaluate(minT);
        Vector4 normal = new Vector4(center, point);
        normal.normalize();
        UVCoordinates uvc = computeUV(point);
        return Shader.computeColor(point, normal, uvc,  material);
    }

    @Override
    public Material getMaterial() {
        return material;
    }    
    
    /**
     * Return the surface <u, v> coordinates for a point p on the surface
     * Both u and v are numbers between 0 an 1
     * ***
     * @param p
     * @return 
     */
    @Override
    public UVCoordinates computeUV(Point p) {
        
        // find the point coordinates relative to the center of the sphere
        double x = p.x - center.x;
        double y = p.y - center.y;
        double z = p.z - center.z;
        // find the spheric coordinates angles and arc corresponding to theta
        double phi = Math.asin(y/radius);
        double pr = Math.sqrt(x*x + z*z);
        double theta;
        // define the value of theta
        if(pr == 0) {
            theta = 0;
        } else {
            if(z >= 0) {
                theta = Math.asin(x/pr);
            } else {
                if(x >= 0) {
                    theta = Math.PI - Math.asin(x/pr);
                } else {
                    theta = -Math.PI - Math.asin(x/pr);
                }
            }
        }
        // calculate the s coordinate
        double s = (theta - (-Math.PI))/(2 * Math.PI);
        // calculate the t coordinate
        double t = (phi - (-Math.PI/2))/Math.PI;

        s = (s - 0.2) % 1;
        return new UVCoordinates(s, t);
        
    }
    
    @Override
    public String toString() {
        return "Sphere{" + "center=" + center + ", radius=" + radius + '}';
    }
    

    public static void main(String [] args) {
        Point center = new Point(0, 0, 0);
        double radius = 1d;
        Sphere sphere = new Sphere(center, radius, null);
        System.out.println(sphere.computeUV(new Point(-1, 1, -1)));
        System.out.println(sphere.computeUV(new Point(-1, 1,  1)));
        System.out.println(sphere.computeUV(new Point( 1, 1,  1)));
        System.out.println(sphere.computeUV(new Point( 1, 1, -1)));
    }
}
