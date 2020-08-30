import java.lang.Math;

public class Body {
    public static double GRAVITATIONAL_CONSTANT = 6.67e-11;

    public double xxPos, yyPos; //position
    public double xxVel, yyVel; //velocity
    public double mass;
    public String imgFileName;

    public Body(double xP, double yP,
                double xV, double yV,
                double m, String img) {
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }

    public Body(Body b) {
        xxPos=b.xxPos;
        yyPos=b.yyPos;
        xxVel=b.xxVel;
        yyVel=b.yyVel;
        mass=b.mass;
        imgFileName=b.imgFileName;
    }

    // calculate distance between this body and the input body
    public double calcDistance(Body b) {
        return Math.sqrt(Math.pow(xxPos-b.xxPos,2)+Math.pow(yyPos-b.yyPos,2));
    }

    // calculate the total force between this body and the input body
    public double calcForceExertedBy(Body b) {
        return GRAVITATIONAL_CONSTANT * mass * b.mass / (Math.pow(calcDistance(b),2));
    }

    //
    public double calcForceExertedByX(Body b) {
        return calcForceExertedBy(b) * (b.xxPos - xxPos) / calcDistance(b);
    }

    public double calcForceExertedByY(Body b) {
        return calcForceExertedBy(b) * (b.yyPos - yyPos) / calcDistance(b);
    }

    //
    public double calcNetForceExertedByX(Body[] bodies) {
        double net_x = 0.0;
        for (int i=0; i<bodies.length; i++) {
            if (checkIfSamePos(bodies[i])==false) {
                net_x += calcForceExertedByX(bodies[i]);
            }
        }
        return net_x;
    }

    public double calcNetForceExertedByY(Body[] bodies) {
        double net_y = 0.0;
        for (int i=0; i<bodies.length; i++) {
            if (checkIfSamePos(bodies[i])==false) {
                net_y += calcForceExertedByY(bodies[i]);
            }
        }
        return net_y;
    }

    public boolean checkIfSamePos(Body b) {
        return xxPos==b.xxPos && yyPos==b.yyPos;
    }

    // how much the forces exerted on the body will cause that body to accelerate
    public void update(double seconds, double x_force, double y_force) {
       double x_acceleration = x_force/mass;
       double y_acceleration = y_force/mass;

       xxVel += seconds * x_acceleration;
       yyVel += seconds * y_acceleration;

       xxPos += seconds * xxVel;
       yyPos += seconds * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
