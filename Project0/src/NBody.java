import java.io.*;
import java.util.*;

public class NBody {
    public static void main(String[] args) {
        /*
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
         */
        double T = 157788000.0;
        double dt = 25000.0;
        String filename = "data/planets.txt";

        double radius = readRadius(filename);
        Body[] Bodies = readBodies(filename);
        int N = Bodies.length;

        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");

        for (Body b: Bodies) {
            b.draw();
        }

        StdDraw.enableDoubleBuffering();

        double time = 0;
        while (time < T) {
            double[] xForces = new double[N];
            double[] yForces = new double[N];

            for (int i = 0; i < N; i++) {
                xForces[i] = Bodies[i].calcNetForceExertedByX(Bodies);
                yForces[i] = Bodies[i].calcNetForceExertedByY(Bodies);
            }

            for (int i = 0; i < N; i++) {
                Bodies[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Body b: Bodies) {
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        StdOut.printf("%d\n", Bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < Bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    Bodies[i].xxPos, Bodies[i].yyPos, Bodies[i].xxVel,
                    Bodies[i].yyVel, Bodies[i].mass, Bodies[i].imgFileName);
        }
    }

    /*
    public void init() {

    }
     */

    public static double readRadius(String data_file_path) {
        try {
            File f = new File(data_file_path);
            System.out.println("load file "+f);
            Scanner s = new Scanner(f);
            s.nextInt();
            return s.nextDouble();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Body[] readBodies(String data_file_path) {

        try {
            File f = new File(data_file_path);
            System.out.println("load file "+f);
            Scanner s = new Scanner(f);

            int num_of_bodies = s.nextInt();

            s.nextDouble();

            Body[] Bodies = new Body[num_of_bodies];
            double xP, yP, xV, yV, m;
            String img;

            for (int i=0; i<num_of_bodies; i++) {
                xP = s.nextDouble();
                yP = s.nextDouble();
                xV = s.nextDouble();
                yV = s.nextDouble();
                m = s.nextDouble();
                img = s.next();
                Bodies[i] = new Body(xP, yP, xV, yV, m, img);
            }

            return Bodies;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Body[1];
        }
    }
}
