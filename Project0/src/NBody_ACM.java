import acm.graphics.*;
import acm.program.*;

import java.awt.*;
import java.io.*;
import java.util.*;

public class NBody_ACM extends GraphicsProgram {
    private double T = 157788000.0;
    private double dt = 25000.0;
    private String filename = "data\\planets.txt";
    private int num_of_bodies;
    private double radius;
    private Body[] Bodies;

    private static final int APPLICATION_WIDTH = 700;
    private static final int APPLICATION_HEIGHT = 700;

    private double scale_min;
    private double scale_max;

    private GImage[] body_imgs;
    private GLabel sim_time_label;

    private static final int DELAY = 10;

    public void init() {
        radius = readRadius(filename);
        Bodies = readBodies(filename);
        num_of_bodies = Bodies.length;

        this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.setBackground(Color.BLACK);
        setScale(-radius, radius);
        setBodyImgs();
        sim_time_label = new GLabel("Year: ", 50, 50);
        sim_time_label.setColor(Color.WHITE);
        sim_time_label.setFont(String.valueOf(Font.BOLD));
        add(sim_time_label);
    }

    public void run() {
        String total_year = String.format("%.2f", T/(3600*24*365));

        for (double time=0; time<=T; time+=dt) {
            double[] xNetForce = new double[num_of_bodies];
            double[] yNetForce = new double[num_of_bodies];

            for (int i=0; i<num_of_bodies; i++) {
                xNetForce[i] = Bodies[i].calcNetForceExertedByX(Bodies);
                yNetForce[i] = Bodies[i].calcNetForceExertedByY(Bodies);
            }

            for (int i=0; i<num_of_bodies; i++) {
                Bodies[i].update(dt, xNetForce[i], yNetForce[i]);

                double xPos_on_screen = setScaleX(Bodies[i].xxPos);
                double yPos_on_screen = setScaleX(Bodies[i].yyPos);

                body_imgs[i].setLocation(xPos_on_screen, yPos_on_screen);
            }

            String current_year = String.format("%.2f", time/(3600*24*365));

            sim_time_label.setLabel("Year:  "+current_year+" / "+total_year);

            pause(DELAY);
        }
    }

    public void setBodyImgs() {
        body_imgs = new GImage[num_of_bodies];
        for (int i=0; i<num_of_bodies; i++) {
            double xPos_on_screen = setScaleX(Bodies[i].xxPos);
            double yPos_on_screen = setScaleX(Bodies[i].yyPos);
            body_imgs[i] = new GImage("images\\"+Bodies[i].imgFileName, xPos_on_screen, yPos_on_screen);
            add(body_imgs[i]);
        }
    }

    public void setScale(double min_input, double max_input) {
        double size = max_input - min_input;
        if (size == 0.0) throw new IllegalArgumentException("the min and max are the same");
        scale_min = min_input;
        scale_max = max_input;
    }

    public double setScaleX(double x) {
        return APPLICATION_WIDTH  * (x - scale_min) / (scale_max - scale_min);
    }

    public double setScaleY(double y) {
        return APPLICATION_HEIGHT  * (scale_max - y) / (scale_max - scale_min);
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
