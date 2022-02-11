package engine;

public class Engine implements Runnable {

    private final Window window;

    private final IChess gameLogic;

    private final Timer timer;

    public Engine(String title, int width, int height, boolean resizable, IChess gameLogic) {
        this.window = new Window(width, height, title, resizable);
        this.gameLogic = gameLogic;
        this.timer = new Timer();
    }

    @Override
    public void run() {
        try {
            init();
            loop();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        gameLogic.init();
    }

    protected void loop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1.0f / 20.0f;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();
        }

        window.cleanup();
    }

    protected void cleanup() {
        gameLogic.cleanup();
    }

    protected void input() {
        gameLogic.input(window);
    }

    protected void update(float interval) {
        gameLogic.update(interval);
    }

    protected void render() {
        gameLogic.render(window);
        window.update();
    }
}
