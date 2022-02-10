package ui;

import core.Board;
import engine.Window;
import org.joml.Matrix4f;
import ui.shader.Shader;
import engine.Utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Render {
    private Shader shaderProgram;

    public void init() throws Exception {
        // Create shader
        shaderProgram = new Shader();
        shaderProgram.createVertexShader(Utils.loadResource("/tile.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/tile.fs"));
        shaderProgram.link();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void renderBoard(Board board) {
        shaderProgram.bind();
        shaderProgram.setFloat(Board.SIZE, "BoardSize");
        for (Tile tile : board.Tiles) {
            glBindVertexArray(tile.getVao());
            //glEnableVertexAttribArray(0);
            int rank = tile.getRank();
            int file = tile.getFile();

            glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
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
        shaderProgram.bind();

        //glBindVertexArray(tile.getVao());
        //glEnableVertexAttribArray(0);
        //glDrawArrays(GL_TRIANGLES, 0, tile.getVertexCount());

        // Restore state
        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }


}
