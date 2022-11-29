import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Scene {
    HashMap<String, SceneObject> objects = new HashMap();

    public void addObject(String name, SceneObject object) {
        objects.put(name, new SceneObject(object));
    }

    public void removeObject(String name) {
        objects.remove(name);
    }

    public SceneObject getObject(String name) {
        return objects.get(name);
    }

    public SceneObject getCombinedScene() {
        LinkedList<Vertex> combinedVertices = new LinkedList<>();
        LinkedList<Integer[]> combinedEdges = new LinkedList<>();
        LinkedList<Face> combinedFaces = new LinkedList<>();
        int offset = 0;
        for (SceneObject i : objects.values()) {
            Vertex[] trueVertices = i.getTrueVertices();
            combinedVertices.addAll(Arrays.asList(trueVertices));

            for (int[] x : i.edges) {
                Integer[] value = new Integer[] {x[0] + offset, x[1] + offset};
                combinedEdges.add(value);
            }
            for (Face x : i.faces) {
                int[] faceVertices = new int[x.vertices.length];
                for (int y = 0; y < faceVertices.length; y++) { faceVertices[y] = x.vertices[y] + offset; }
                combinedFaces.add(new Face(x.color, faceVertices));
            }

            offset += trueVertices.length;
        }

        Vertex[] vertices = new Vertex[combinedVertices.size()];
        int[][] edges = new int[combinedEdges.size()][2];
        Face[] faces = new Face[combinedFaces.size()];

        for (int i = 0; i < vertices.length; i++) { vertices[i] = combinedVertices.get(i); }
        for (int i = 0; i < edges.length; i++) {
            edges[i][0] = combinedEdges.get(i)[0];
            edges[i][1] = combinedEdges.get(i)[1];
        }
        for (int i = 0; i < faces.length; i++) { faces[i] = combinedFaces.get(i); }

        return new SceneObject(vertices, edges, faces, new Vertex(0, 0, 0), new Vertex(0, 0, 0), new Vertex(1, 1, 1));
    }

    public void drawScene(WindowManager windowManager, Camera camera) {
        getCombinedScene().drawFaces(windowManager, camera);
    }

    public void drawWireframe(WindowManager windowManager, Camera camera, Color color, int thickness) {
        getCombinedScene().drawWireframe(windowManager, camera, color, thickness);
    }

    public void drawVertices(WindowManager windowManager, Camera camera, Color color, int size) {
        getCombinedScene().drawVertices(windowManager, camera, color, size);
    }
}
