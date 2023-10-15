package core;

import core.gamestate.IGameState;
import engine.*;

import static org.lwjgl.glfw.GLFW.*;

public class ChessGame implements IChess {
    private final GameContext context = new GameContext();
    public static Engine engine = null;
    public ChessGame() {

    }

    @Override
    public void init(Engine engine) {
        ChessGame.engine = engine;
        this.context.forEachSystem(sys -> {
            sys.init();
            this.assignGameStates(sys);
        });
    }

    @Override
    public void input(Window window) {
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            System.out.println("Hello!!!!");
        }
    }

    @Override
    public void update(float time) {
        this.context.forEachSystem(sys -> {
            sys.update(time);
        });
    }

    @Override
    public void render(Window window) {
        int backgroundColor = 0x9b9b9b;
        window.setBackground(Utils.hexTo3f(backgroundColor));
        render.render();
    }

    @Override
    public void cleanup() {
        render.cleanup();
        board.clear();
    }

    public Map<Integer, IGameState> getStates() {
        return this.context.
    }
}
