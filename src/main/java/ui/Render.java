package ui;

import core.Board;
import core.Piece;
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

    private void renderCheckerboard(Checkerboard checkerboard) {
        // Render tile
        glBindVertexArray(checkerboard.getVao());

        checkerboard.enableVertexAttribs();
        glDrawElements(GL_TRIANGLES, checkerboard.vertexCount(), GL_UNSIGNED_INT, 0);
        checkerboard.disableVertexAttribs();

        glBindVertexArray(0);
    }

    private void renderPieces(int[] board) {
        for (int rank = 0; rank < Board.SIZE; rank++) {
            for (int file = 0; file < Board.SIZE; file++) {
                int piece = board[rank * Board.SIZE + file];
                int color = Piece.getColor(piece);

                if (color == Piece.White) {
                    
                } else if (color == Piece.Black) {

                } else {
                    return;
                }
            }
        }
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
        boardShader.setVec2(board.getRes2f(), "u_resolution");
        boardShader.setVec2(board.getOffset2f(), "u_offset");

        this.renderCheckerboard(board.checkerboard);
        this.renderPieces(board.Board);
        // glViewport(0, 0, );

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
