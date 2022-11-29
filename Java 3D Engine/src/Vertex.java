public class Vertex {
    public double x, y, z;

    public Vertex() {}
    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static double calculateDistance(Vertex a, Vertex b) {
        double x1 = a.x;
        double y1 = a.y;
        double z1 = a.z;
        double x2 = b.x;
        double y2 = b.y;
        double z2 = b.z;
        return (y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1) + (z2 - z1) * (z2 - z1);
    }
}
