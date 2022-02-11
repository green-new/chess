package ui;

import core.Board;
import engine.Window;
import ui.shader.Shader;
import engine.Utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Render {

    private Shader shaderProgram;

    public void init() throws Exception {
        // Create shader for board
        shaderProgram = new Shader();
        shaderProgram.createVertexShader(Utils.loadResource("/board.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/board.fs"));
        shaderProgram.link();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void renderBoard(Window window, Board board) {
        shaderProgram.bind();

        // Set the board size of the window
        glViewport((int) (window.getWidth() * 0.05f), (int) (window.getHeight() * 0.05f), (int) (window.getHeight() * 0.90f), (int) (window.getHeight() * 0.90f));
        shaderProgram.setVec2(window.getResolutionf(), "u_resolution");

        for (Tile tile : board.Tiles) {
            glBindVertexArray(tile.getVao());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

            // Restore state
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glBindVertexArray(0);
        }

        // Restore state
        glBindVertexArray(0);
        shaderProgram.unbind();
    }

    public void render(Window window) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        // Restore state
        glBindVertexArray(0);
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }


}
