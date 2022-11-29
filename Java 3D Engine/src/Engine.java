import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Engine {
    public Point mousePosition = MouseInfo.getPointerInfo().getLocation();
    public Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), null);
    public double sensitivity = 0.005d;
    public double moveSpeed = 0.1;
    public boolean mouseTrapped = false;
    public boolean wPressed = false;
    public boolean aPressed = false;
    public boolean sPressed = false;
    public boolean dPressed = false;
    public boolean qPressed = false;
    public boolean ePressed = false;

    public WindowManager windowManager;
    public Camera camera;
    public Scene scene = new Scene();

    public void init(WindowManager windowManager) {
        this.windowManager = windowManager;
        Vertex cameraPosition = new Vertex(0, 0, 0);
        Vertex cameraRotation = new Vertex(0, 0, 0);
        Vertex displaySurfacePosition = new Vertex(0, 0, 1);
        camera = new Camera(cameraPosition, cameraRotation, displaySurfacePosition, 1, 1000);

        scene.addObject("Plane", Models.plane);
        scene.getObject("Plane").translate(new Vertex(0, 1, 5));
        scene.addObject("Cube", Models.cube);
        scene.getObject("Cube").translate(new Vertex(3, 0, 5));
        scene.addObject("Pyramid", Models.pyramid);
        scene.getObject("Pyramid").translate(new Vertex(6, 0, 5));
        scene.addObject("Colored Cube", Models.testCube);
        scene.getObject("Colored Cube").translate(new Vertex(9, 0, 5));
        scene.addObject("Spinning Cube", Models.testCube);
        scene.getObject("Spinning Cube").translate(new Vertex(12, 0, 5));
        scene.addObject("Moving Cube", Models.testCube);
        scene.getObject("Moving Cube").translate(new Vertex(15, 1, 5));
    }

    public void mousePressed(MouseEvent e) {
        lockMouse();
        mouseTrapped = true;
        mousePosition = MouseInfo.getPointerInfo().getLocation();
    }

    public void keyTyped(KeyEvent e) {
        if (e.getExtendedKeyCode() == 27) {
            mouseTrapped = false;
        }
    }

    public void keyPressed(KeyEvent e) {
        String letter = String.valueOf(e.getKeyChar()).toLowerCase();
        switch (letter) {
            case "w" -> wPressed = true;
            case "a" -> aPressed = true;
            case "s" -> sPressed = true;
            case "d" -> dPressed = true;
            case "q" -> qPressed = true;
            case "e" -> ePressed = true;
        }
    }
    public void keyReleased(KeyEvent e) {
        String letter = String.valueOf(e.getKeyChar()).toLowerCase();
        switch (letter) {
            case "w" -> wPressed = false;
            case "a" -> aPressed = false;
            case "s" -> sPressed = false;
            case "d" -> dPressed = false;
            case "q" -> qPressed = false;
            case "e" -> ePressed = false;
        }
    }

    public void controlCamera() {
        if (mouseTrapped) {
            windowManager.appWindow.setCursor(blankCursor);
        } else {
            windowManager.appWindow.setCursor(Cursor.getDefaultCursor());
        }

        Point mouseMovement = new Point();
        mouseMovement.x = MouseInfo.getPointerInfo().getLocation().x - mousePosition.x;
        mouseMovement.y = MouseInfo.getPointerInfo().getLocation().y - mousePosition.y;
        if (mouseTrapped) lockMouse();

        Vertex forward = new Vertex();
        forward.x = Math.sin(camera.cameraRotation.y);
        forward.z = Math.cos(camera.cameraRotation.y);

        Vertex right = new Vertex();
        right.x = Math.sin(camera.cameraRotation.y + Math.PI / 2);
        right.z = Math.cos(camera.cameraRotation.y + Math.PI / 2);

        if (wPressed) camera.translate(new Vertex(moveSpeed * forward.x, 0, moveSpeed * forward.z));
        if (aPressed) camera.translate(new Vertex(-moveSpeed * right.x, 0, -moveSpeed * right.z));
        if (sPressed) camera.translate(new Vertex(-moveSpeed * forward.x, 0, -moveSpeed * forward.z));
        if (dPressed) camera.translate(new Vertex(moveSpeed * right.x, 0, moveSpeed * right.z));
        if (qPressed) camera.translate(new Vertex(0, -moveSpeed, 0));
        if (ePressed) camera.translate(new Vertex(0, moveSpeed, 0));

        if (mouseTrapped) camera.rotate(new Vertex(mouseMovement.y * sensitivity * -1, mouseMovement.x * sensitivity, 0));
    }

    public void lockMouse() {
        try {
            Robot robot = new Robot();
            robot.mouseMove(windowManager.appWindow.getX() + windowManager.appWindow.getWidth() / 2, windowManager.appWindow.getY() + windowManager.appWindow.getHeight() / 2);
            mousePosition = new Point(windowManager.appWindow.getX() + windowManager.appWindow.getWidth() / 2, windowManager.appWindow.getY() + windowManager.appWindow.getHeight() / 2);
        } catch (AWTException e) { e.printStackTrace(); }
    }

    public void tick() {
        controlCamera();

        scene.getObject("Spinning Cube").rotate(new Vertex(0.01, 0.01, 0));
        scene.getObject("Moving Cube").translate(new Vertex(0, -0.01, 0));

        scene.drawScene(windowManager, camera);
    }
}
