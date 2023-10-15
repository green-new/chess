package core;

import engine.Engine;

public interface IChess {
    void init(Engine engine);
    void input();
    void render();
    void update(float interval);
    void destroy();
}
