import java.awt.*;
import java.util.*;

public class SceneObject {
    public Vertex[] vertices;
    public int[][]  edges;
    public Face[] faces;

    public Vertex position;
    public Vertex rotation;
    public Vertex size;

    public SceneObject(Vertex[] vertices, int[][] edges, Face[] faces, Vertex position, Vertex rotation, Vertex size) {
        this.vertices = vertices;
        this.edges = edges;
        this.faces = faces;
        this.position = position;
        this.rotation = rotation;
        this.size = size;
    }
    public SceneObject(SceneObject object) {
        vertices = object.vertices;
        edges = object.edges;
        faces = object.faces;
        position = object.position;
        rotation = object.rotation;
        size = object.size;
    }
    public SceneObject(SceneObject object, Vertex position, Vertex rotation, Vertex size) {
        vertices = object.vertices;
        edges = object.edges;
        faces = object.faces;
        this.position = position;
        this.rotation = rotation;
        this.size = size;
    }
    public SceneObject(String path, Vertex position, Vertex rotation, Vertex size) {
        readFromFile(path);
        this.position = position;
        this.rotation = rotation;
        this.size = size;
    }

    public void readFromFile(String path) {
        // Do stuff
    }

    public void translate(Vertex amount) { position = new Vertex(position.x + amount.x, position.y + amount.y, position.z + amount.z); }

    public void rotate(Vertex amount) { rotation = new Vertex(rotation.x + amount.x, rotation.y + amount.y, rotation.z + amount.z); }

    public Vertex[] getTrueVertices() {
        Vertex[] out = new Vertex[vertices.length];

        Vertex cosTheta = new Vertex(Math.cos(rotation.x), Math.cos(rotation.y), Math.cos(rotation.z));
        Vertex sinTheta = new Vertex(Math.sin(rotation.x), Math.sin(rotation.y), Math.sin(rotation.z));

        for (int i = 0; i < vertices.length; i++) {
            Vertex vertex = vertices[i];

            double x = vertex.x * size.x;
            double y = vertex.y * size.y;
            double z = vertex.z * size.z;

            Vertex truePosition = new Vertex();
            truePosition.x = (cosTheta.y * (sinTheta.z * y + cosTheta.z * x) - sinTheta.y * z) + position.x;
            truePosition.y = (sinTheta.x * (cosTheta.y * z + sinTheta.y * (sinTheta.z * y + cosTheta.z * x)) + cosTheta.x * (cosTheta.z * y - sinTheta.z * x)) + position.y;
            truePosition.z = (cosTheta.x * (cosTheta.y * z + sinTheta.y * (sinTheta.z * y + cosTheta.z * x)) - sinTheta.x * (cosTheta.z * y - sinTheta.z * x)) + position.z;

            out[i] = truePosition;
        }
        return out;
    }

    public Vertex[] getPerspectiveVertices(Camera camera) {
        Vertex[] trueVertices = getTrueVertices();
        Vertex[] out = new Vertex[trueVertices.length];

        for (int i = 0; i < trueVertices.length; i++) {
            out[i] = camera.calculatePerspectiveProjection(trueVertices[i]);
        }

        return out;
    }

    public void drawVertices(WindowManager windowManager, Camera camera, Color color, int size) {
        Vertex[] perspectiveProjection = getPerspectiveVertices(camera);
        int screenXOffset = windowManager.appWindow.getWidth() / 2;
        int screenYOffset = windowManager.appWindow.getHeight() / 2;

        for (Vertex i : perspectiveProjection) {
            int x = (int) (i.x * screenXOffset + screenXOffset - size / 2);
            int y = (int) (i.y * screenXOffset + screenYOffset - size / 2);

            windowManager.drawOval(color, true, x, y, size, size);
        }
    }

    public void drawWireframe(WindowManager windowManager, Camera camera, Color color, int thickness) {
        Vertex[] perspectiveProjection = getPerspectiveVertices(camera);
        int screenXOffset = windowManager.appWindow.getWidth() / 2;
        int screenYOffset = windowManager.appWindow.getHeight() / 2;

        for (int[] i : edges) {
            Vertex point1Perspective = perspectiveProjection[i[0]];
            Vertex point2Perspective = perspectiveProjection[i[1]];

            int x1 = (int) (point1Perspective.x * screenXOffset + screenXOffset - thickness / 2);
            int y1 = (int) (point1Perspective.y * screenXOffset + screenYOffset - thickness / 2);
            int x2 = (int) (point2Perspective.x * screenXOffset + screenXOffset - thickness / 2);
            int y2 = (int) (point2Perspective.y * screenXOffset + screenYOffset - thickness / 2);

            windowManager.drawLine(color, thickness, x1, y1, x2, y2);
        }
    }

    public void drawFaces(WindowManager windowManager, Camera camera) {
        Vertex[] perspectiveProjection = getPerspectiveVertices(camera);
        Vertex[] trueVertices = getTrueVertices();
        int screenWidth = windowManager.appWindow.getWidth();
        int screenHeight = windowManager.appWindow.getHeight();
        int screenXOffset = screenWidth / 2;
        int screenYOffset = screenHeight / 2;

        Double[] farthestFaces = new Double[faces.length];
        boolean[] dontDraw = new boolean[faces.length];
        Arrays.fill(dontDraw, false);
        for (int i = 0; i < faces.length; i++) {
            double averageDistance = 0;
            boolean faceOnScreen = false;
            boolean inClippingPlane = true;
            for (int x : faces[i].vertices) {
                if (perspectiveProjection[x].x > -1 && perspectiveProjection[x].x < 1 && perspectiveProjection[x].y > -1 && perspectiveProjection[x].y < 1) faceOnScreen = true;
                if (perspectiveProjection[x].z < camera.nearClippingPlane || perspectiveProjection[x].z > camera.farClippingPlane) inClippingPlane = false;

                averageDistance += Vertex.calculateDistance(camera.cameraPosition, trueVertices[x]);
            }
            if (!faceOnScreen || !inClippingPlane) dontDraw[i] = true;
            farthestFaces[i] = averageDistance;
        }

        Double[] dupe = farthestFaces.clone();
        int[] sortedIndexes = new int[farthestFaces.length];
        Arrays.sort(farthestFaces, Collections.reverseOrder());

        for (int i = 0; i < farthestFaces.length; i++) {
            int index = -1;
            for (int x = 0; x < dupe.length; x++) {
                if (Objects.equals(dupe[x], farthestFaces[i])) {
                    index = x;
                    dupe[x] = -1d;
                    break;
                }
            }

            sortedIndexes[i] = index;
        }

        for (int i : sortedIndexes) {
            if (!dontDraw[i]) {
                Face face = faces[i];

                int[] xPoints = new int[face.vertices.length];
                int[] yPoints = new int[face.vertices.length];
                for (int x = 0; x < face.vertices.length; x++) {
                    Vertex vertex = perspectiveProjection[face.vertices[x]];
                    xPoints[x] = (int) (vertex.x * screenXOffset + screenXOffset);
                    yPoints[x] = (int) (vertex.y * screenXOffset + screenYOffset);
                }

                windowManager.drawPolygon(face.color, true, xPoints, yPoints);
            }
        }
    }
}
