package engine;

import core.IChess;

import java.beans.JavaBean;
import java.util.Map;

public class Engine implements Runnable {
    private final ResourceManager srcManager;
    private final Window window;
    private final IChess gameLogic;
    private final Timer timer;
    private double deltaTime = 0.0f;

    public Engine(String title, int width, int height, boolean resizable, IChess gameLogic) {
        this.window = new Window(width, height, title, resizable);
        this.gameLogic = gameLogic;
        this.timer = new Timer();
        this.srcManager = new ResourceManager();
    }

    @Override
    public void run() {
        try {
            init();
            loop();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        gameLogic.init(this);
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
                deltaTime = accumulator;
                update(interval);
                accumulator -= interval;
            }

            render();
            window.update();
        }

        window.destroy();
    }

    public <U extends Map<Integer, T>, T> void addResource(Integer key, Resource<U, T> src) { this.srcManager.put(key, src); }
    public void removeResource(Integer key) { this.srcManager.remove(key); }
    public Resource<?, ?> getResource(Integer key) { return this.srcManager.get(key); }
    public <T> T getResource(Integer resourceType, Integer assetKey) { //noinspection unchecked
        return (T) this.getResource(resourceType).get(assetKey); }
    public Window getWindow() { return this.window; }
    public double getCurrentTime() { return this.timer.getTime(); }
    public double getDeltaTime() { return this.deltaTime; }
    public IChess getLogic() { return this.gameLogic; }

    protected void destroy() { gameLogic.destroy(); }
    protected void input() { gameLogic.input(); }
    protected void update(float interval) { gameLogic.update(interval); }
    protected void render() { gameLogic.render(); }
}
