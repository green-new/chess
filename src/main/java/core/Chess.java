package core;

import engine.IChess;
import engine.Utils;
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
    public void init() {
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
        int backgroundColor = 0x9b9b9b;

        window.setBackground(Utils.hexTo3f(backgroundColor));

        render.render(window, board);
    }

    @Override
    public void cleanup() {
        render.cleanup();
        board.cleanup();
    }
}
