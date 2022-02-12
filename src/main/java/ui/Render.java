package ui;

import core.Board;
import engine.Window;
import ui.shader.Shader;
import engine.Utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Render {

    private Shader boardShader;
    private Shader pieceShader;

    public void init() throws Exception {
        // Create shader for board
        boardShader = new Shader();
        boardShader.createVertexShader(Utils.loadResource("/board.vs"));
        boardShader.createFragmentShader(Utils.loadResource("/board.fs"));
        boardShader.link();

        // Create shader for pieces
        pieceShader = new Shader();
        pieceShader.createVertexShader(Utils.loadResource("/piece.vs"));
        pieceShader.createFragmentShader(Utils.loadResource("/piece.fs"));
        pieceShader.link();

        // Load textures for pieces

    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private void renderTile(Tile tile) {
        // Render tile
        glBindVertexArray(tile.getVao());

        tile.enableVertexAttribs();
        glDrawElements(GL_TRIANGLES, tile.vertexCount(), GL_UNSIGNED_INT, 0);
        tile.disableVertexAttribs();

        glBindVertexArray(0);
    }

    private void renderPieces(int vao) {
        // Render piece
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    /**
     * Renders the board.
     * @param window The window object.
     * @param board The board that is being rendered.
     */
    private void renderBoard(Window window, Board board) {
        // We need to render the tiles and pieces codependently.
        // This ensures that each tile and piece are always on top of eachother.
        // Some ideas:
        // - Loop through every file and rank. This way, you can read from Board.Board[] and the Board.Tiles list,
        // which ensures you're getting the tile and piece associated with it on every iteration.
        // - Render independently - have two processes where the piece and the tile are rendered. Keep the process already for tiles, and add
        // a new loop that iterates over the board. If there is a piece, render it.

        boardShader.bind();

        // Set the board size of the window
        glViewport(board.boardOffsetX(), board.boardOffsetY(), board.boardWidth(), board.boardHeight());
        boardShader.setVec2(window.getRes2f(), "u_resolution");

        // Render the tiles first

        for (int pos = 0; pos < Board.SIZE * Board.SIZE; pos++) {
            this.renderTile(board.Tiles[pos]);
            //this.renderPieces(board.Board[pos]);
        }

        glBindVertexArray(0);
        boardShader.unbind();
    }

    public void render(Window window, Board board) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        this.renderBoard(window, board);

        // Restore state
        glBindVertexArray(0);
    }

    public void cleanup() {
        if (boardShader != null) {
            boardShader.cleanup();
        }
    }
}
