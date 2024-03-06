public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP, double yP, double xV, 
                double yV, double m, String img){
            xxPos = xP;
            yyPos = yP;
            xxVel = xV;
            yyVel = yV;
            mass = m;
            imgFileName = img;
    }

    public Planet(Planet b){
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }

    public double calcDistance(Planet b){
        double r;
        double dx,dy;
        dx = b.xxPos - this.xxPos;
        dy = b.yyPos - this.yyPos;
        r = Math.sqrt(dx * dx + dy * dy);
        return r;
    }

    public double calcForceExertedBy(Planet b){
        double g = 6.67e-11;
        double r = calcDistance(b);
        double F = (g * this.mass * b.mass) / (r * r);
        return F;
    }

    public double calcForceExertedByX(Planet b){
        double dx = b.xxPos - this.xxPos;
        double F = calcForceExertedBy(b);
        double r = calcDistance(b);
        double F_x = F * dx / r;
        return F_x;
    }

    public double calcForceExertedByY(Planet b){
        double dy = b.yyPos - this.yyPos;
        double F = calcForceExertedBy(b);
        double r = calcDistance(b);
        double F_y = F * dy / r;
        return F_y;
    }

    public double calcNetForceExertedByX(Planet[] array){
        double net_f_x = 0;
        for(int i = 0; i <= array.length - 1; i = i + 1){
            if(this.equals(array[i]) == false){
                net_f_x = net_f_x + calcForceExertedByX(array[i]);
            }
        }
        return net_f_x;
    }

    public double calcNetForceExertedByY(Planet[] array){
        double net_f_y = 0;
        for(int i = 0; i <= array.length - 1; i = i + 1){
            if(this.equals(array[i]) == false){
                net_f_y = net_f_y + calcForceExertedByY(array[i]);
            }
        }
        return net_f_y;
    }

    public void update(double dt, double fx,double fy){
        double a_net_x = fx / this.mass;
        double a_net_y = fy / this.mass;
        this.xxVel = this.xxVel + dt * a_net_x;
        this.yyVel = this.yyVel + dt * a_net_y;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel; 
    }

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }

}