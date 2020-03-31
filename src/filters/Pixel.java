package filters;

public class Pixel {

    public double r;
    public double g;
    public double b;
    public int x;
    public int y;
    public Pixel[] neighborX = new Pixel[4];
    public Pixel[] neighborCross = new Pixel[4];
    public Pixel[] neighbor3X3 = new Pixel[8];

    public Pixel(double r, double g, double b, int x, int y) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.x = x;
        this.y = y;
    }
}
