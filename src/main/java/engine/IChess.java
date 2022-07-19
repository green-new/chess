package engine;

public interface IChess {

    void init();

    void input(Window window);

    void update(float interval);

    void render(Window window);

    void cleanup();
}
