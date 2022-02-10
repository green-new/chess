package core;

import engine.IChess;
import engine.Window;
import ui.Render;

import static org.lwjgl.glfw.GLFW.*;

public class Chess implements IChess {

    private final Render render;

    private final Board board;

    public Chess() {
        render = new Render();
        board = new Board();
    }

    @Override
    public void init() throws Exception {
        render.init();
        board.init();
    }

    @Override
    public void input(Window window) {
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            System.out.println("Hello!!!!");
        }
    }

    @Override
    public void update(float interval) {
    }

    @Override
    public void render(Window window) {
        render.render(window);
        render.renderBoard(board);
    }

    @Override
    public void cleanup() {
        render.cleanup();
    }
}
