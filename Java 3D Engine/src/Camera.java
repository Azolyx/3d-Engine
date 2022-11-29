public class Camera {
    public Vertex cameraPosition;
    public Vertex cameraRotation;
    public Vertex displayPlanePosition;
    public double nearClippingPlane;
    public double farClippingPlane;

    public Camera(Vertex cameraPosition, Vertex theta, Vertex displayPlanePosition, double nearClippingPlane, double farClippingPlane) {
        this.cameraPosition = cameraPosition;
        this.cameraRotation = theta;
        this.displayPlanePosition = displayPlanePosition;
        this.nearClippingPlane = nearClippingPlane;
        this.farClippingPlane = farClippingPlane;
    }

    public void translate(Vertex amount) { cameraPosition = new Vertex(cameraPosition.x + amount.x, cameraPosition.y + amount.y, cameraPosition.z + amount.z); }

    public void rotate(Vertex amount) { cameraRotation = new Vertex(cameraRotation.x + amount.x, cameraRotation.y + amount.y, cameraRotation.z + amount.z); }

    public Vertex calculatePerspectiveProjection(Vertex a) {
        // Source for calculations: https://en.wikipedia.org/wiki/3D_projection#Mathematical_formula

        double x = a.x - cameraPosition.x;
        double y = a.y - cameraPosition.y;
        double z = a.z - cameraPosition.z;

        Vertex cosTheta = new Vertex(Math.cos(cameraRotation.x), Math.cos(cameraRotation.y), Math.cos(cameraRotation.z));
        Vertex sinTheta = new Vertex(Math.sin(cameraRotation.x), Math.sin(cameraRotation.y), Math.sin(cameraRotation.z));

        Vertex relativePosition = new Vertex();
        relativePosition.x = cosTheta.y * (sinTheta.z * y + cosTheta.z * x) - sinTheta.y * z;
        relativePosition.y = sinTheta.x * (cosTheta.y * z + sinTheta.y * (sinTheta.z * y + cosTheta.z * x)) + cosTheta.x * (cosTheta.z * y - sinTheta.z * x);
        relativePosition.z = cosTheta.x * (cosTheta.y * z + sinTheta.y * (sinTheta.z * y + cosTheta.z * x)) - sinTheta.x * (cosTheta.z * y - sinTheta.z * x);

        Vertex out = new Vertex();
        out.x = (displayPlanePosition.z / relativePosition.z) * relativePosition.x + displayPlanePosition.x;
        out.y = (displayPlanePosition.z / relativePosition.z) * relativePosition.y + displayPlanePosition.y;
        out.z = relativePosition.z;

        return out;
    }
}
