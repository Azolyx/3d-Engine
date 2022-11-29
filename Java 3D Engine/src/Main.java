public class Main {
    public AppManager appManager = new AppManager();

    Main() {
        appManager.init();
    }

    public static void main(String[] args) { new Main(); }
}