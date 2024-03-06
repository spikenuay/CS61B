import java.util.Scanner;
public class NBody {
    public static  double readRadius(String file_name){
        In in = new In(file_name);
        in.readInt(); 
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String file_name){
        In in = new In(file_name);
        int p_num = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[p_num];
        for(int i = 0; i < p_num; i = i + 1){
            planets[i] = new Planet(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
        }
        return planets;
    }

    public static void main(String[] args){
        /* Input the universe radius (radius) and time (T) */
        Scanner scanner = new Scanner(System.in);
        double T = Double.parseDouble(scanner.next());
        double dt = Double.parseDouble(scanner.next());
        String filename = scanner.next();
        scanner.close();
        
        /* Use readRadius to retrieve the radius and readPlanets to obtain an array of planets. */
        double radius = readRadius(filename);
        Planet[] Planets = readPlanets(filename);
        int n = Planets.length;

        /* Utilize StdDraw to set the background of the universe. */
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius ,radius);
        StdDraw.picture(0, 0, "images/starfield.jpg");
        for(int i = 0; i <= n - 1; i = i + 1){
            Planets[i].draw();
        }

        
        double t = 0;
        double[] net_x_arrays = new double[n];
        double[] net_y_arrays = new double[n];
        while (t < T) {
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radius, 2 * radius);

            for(int i = 0; i <= n - 1; i = i + 1){
                net_x_arrays[i] = Planets[i].calcNetForceExertedByX(Planets);
                net_y_arrays[i] = Planets[i].calcNetForceExertedByY(Planets);
            }
            for(int i = 0; i <= n - 1; i += 1){
                Planets[i].update(dt, net_x_arrays[i], net_y_arrays[i]);   
            }
            for(int i = 0; i <= n - 1; i += 1){
                Planets[i].draw();  
            }
            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        } 

        StdOut.printf("%d\n", Planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            Planets[i].xxPos, Planets[i].yyPos, Planets[i].xxVel,
            Planets[i].yyVel, Planets[i].mass, Planets[i].imgFileName);   
        }
    }
}
