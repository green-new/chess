package ui;

import engine.Engine;

import static org.lwjgl.opengl.GL11.*;

public abstract class Render {
    protected final Engine engine;

    protected Render(Engine engine) {
        this.engine = engine;
    }

    abstract public void render();
    public void clear() { glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); }
}
