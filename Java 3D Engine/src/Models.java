import java.awt.*;

public class Models {
    public static SceneObject testCube = new SceneObject(
            new Vertex[]{
                    new Vertex(1, 1, 1),
                    new Vertex(-1, 1, 1),
                    new Vertex(-1, -1, 1),
                    new Vertex(1, -1, 1),
                    new Vertex(1, 1, -1),
                    new Vertex(-1, 1, -1),
                    new Vertex(-1, -1, -1),
                    new Vertex(1, -1, -1)
            },
            new int[][]{
                    {0, 1},
                    {1, 2},
                    {2, 3},
                    {3, 0},
                    {4, 5},
                    {5, 6},
                    {6, 7},
                    {7, 4},
                    {0, 4},
                    {1, 5},
                    {2, 6},
                    {3, 7}
            },
            new Face[]{
                    new Face(Color.BLUE, new int[]{0, 1, 2, 3}),
                    new Face(Color.GREEN, new int[]{4, 5, 6, 7}),
                    new Face(Color.RED, new int[]{0, 1, 5, 4}),
                    new Face(Color.YELLOW, new int[]{2, 3, 7, 6}),
                    new Face(Color.PINK, new int[]{0, 3, 7, 4}),
                    new Face(Color.GRAY, new int[]{1, 2, 6, 5})
            },
            new Vertex(0, 0, 0),
            new Vertex(0, 0, 0),
            new Vertex(1, 1, 1)
    );
    public static SceneObject cube = new SceneObject(
            new Vertex[]{
                    new Vertex(1, 1, 1),
                    new Vertex(-1, 1, 1),
                    new Vertex(-1, -1, 1),
                    new Vertex(1, -1, 1),
                    new Vertex(1, 1, -1),
                    new Vertex(-1, 1, -1),
                    new Vertex(-1, -1, -1),
                    new Vertex(1, -1, -1)
            },
            new int[][]{
                    {0, 1},
                    {1, 2},
                    {2, 3},
                    {3, 0},
                    {4, 5},
                    {5, 6},
                    {6, 7},
                    {7, 4},
                    {0, 4},
                    {1, 5},
                    {2, 6},
                    {3, 7}
            },
            new Face[]{
                    new Face(Color.BLACK, new int[]{0, 1, 2, 3}),
                    new Face(Color.BLACK, new int[]{4, 5, 6, 7}),
                    new Face(Color.BLACK, new int[]{0, 1, 5, 4}),
                    new Face(Color.BLACK, new int[]{2, 3, 7, 6}),
                    new Face(Color.BLACK, new int[]{0, 3, 7, 4}),
                    new Face(Color.BLACK, new int[]{1, 2, 6, 5})
            },
            new Vertex(0, 0, 0),
            new Vertex(0, 0, 0),
            new Vertex(1, 1, 1)
    );
    public static SceneObject pyramid = new SceneObject(
            new Vertex[]{
                    new Vertex(1, 1, 1),
                    new Vertex(1, 1, -1),
                    new Vertex(-1, 1, -1),
                    new Vertex(-1, 1, 1),
                    new Vertex(0, -1, 0)
            },
            new int[][]{
                    {0, 1},
                    {1, 2},
                    {2, 3},
                    {3, 0},
                    {0, 4},
                    {1, 4},
                    {2, 4},
                    {3, 4}
            },
            new Face[]{
                    new Face(Color.BLACK, new int[]{0, 1, 2, 3}),
                    new Face(Color.BLACK, new int[]{0, 1, 4}),
                    new Face(Color.BLACK, new int[]{1, 2, 4}),
                    new Face(Color.BLACK, new int[]{2, 3, 4}),
                    new Face(Color.BLACK, new int[]{3, 0, 4})
            },
            new Vertex(0, 0, 0),
            new Vertex(0, 0, 0),
            new Vertex(1, 1, 1)
    );
    public static SceneObject plane = new SceneObject(
            new Vertex[]{
                    new Vertex(1, 0, 1),
                    new Vertex(-1, 0, 1),
                    new Vertex(-1, 0, -1),
                    new Vertex(1, 0, -1)
            },
            new int[][]{
                    {0, 1},
                    {1, 2},
                    {2, 3},
                    {3, 0}
            },
            new Face[]{
                    new Face(Color.BLACK, new int[] {0, 1, 2, 3})
            },
            new Vertex(0, 0, 0),
            new Vertex(0, 0, 0),
            new Vertex(1, 1, 1)
    );
}