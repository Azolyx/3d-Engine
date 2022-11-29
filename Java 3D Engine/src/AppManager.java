import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AppManager {
    public WindowManager windowManager = new WindowManager();
    public Thread thread;
    public boolean running = false;
    public Engine engine = new Engine();

    public void init() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        windowManager.init(this, "3d Engine", screenSize.width, screenSize.height, 3);
        running = true;
        thread = new Thread(this::ready);
        thread.start();
    }

    public void ready() {
        start();

        int fps = 60;
        long frameLength = 1000 / fps;
        long lastFrame = System.currentTimeMillis();
        while (running) {
            long deltaTime = System.currentTimeMillis() - lastFrame;
            long waitTime = frameLength - deltaTime;
            if (waitTime < 0) { waitTime = 0; }
            try { Thread.sleep(waitTime); }
            catch (InterruptedException e) { e.printStackTrace(); }

            tick();

            lastFrame = System.currentTimeMillis();
        }
    }

    public void start() {
        engine.init(windowManager);
    }

    public void tick() {
        engine.tick();
        windowManager.drawScreen();
    }

    public void stop() {
        running = false;
        thread.interrupt();
        windowManager.appWindow.dispose();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        engine.mousePressed(e);
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void keyTyped(KeyEvent e) {
        engine.keyTyped(e);
    }

    public void keyPressed(KeyEvent e) {
        engine.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        engine.keyReleased(e);
    }
}
