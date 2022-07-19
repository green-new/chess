package ui;

import core.Board;
import core.Piece;
import engine.Texture;
import engine.Window;
import org.lwjgl.system.MemoryUtil;
import ui.shader.Shader;
import engine.Utils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Render {

    private Shader boardShader;
    private Shader pieceShader;
    private Texture piecemap;

    public Render() {

    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private void renderCheckerboard(Checkerboard checkerboard) {
        boardShader.bind();
        glViewport(checkerboard.boardOffsetX(), checkerboard.boardOffsetY(), checkerboard.boardWidth(), checkerboard.boardHeight());
        boardShader.setVec2(checkerboard.getRes2f(), "u_resolution");
        boardShader.setVec2(checkerboard.getOffset2f(), "u_offset");

        // Render tile
        glBindVertexArray(checkerboard.getVao());
        checkerboard.enableVertexAttribs();

        glDrawElements(GL_TRIANGLES, checkerboard.vertexCount(), GL_UNSIGNED_INT, 0);

        checkerboard.disableVertexAttribs();
        glBindVertexArray(0);

        boardShader.unbind();
    }

    private void renderPieces(Board board) {
        int boardWidth = board.checkerboard.boardWidth();
        int boardHeight = board.checkerboard.boardHeight();
        int desiredPieceW = boardWidth / Board.SIZE;
        int desiredPieceH = boardHeight / Board.SIZE;
        int vao;
        int vbo;
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        // Render pieces on the board
        for (int rank = 0; rank < Board.SIZE; rank++) {
            for (int file = 0; file < Board.SIZE; file++) {
                if (board.Board[rank * Board.SIZE + file] > 0) {
                    int piece = board.Board[rank * Board.SIZE + file];
                    // Get the piece to render.
                    // Evaluate xo, yo screen coords for the piece.
                    // The width and height act as the steps for the evaluation.
                    // xo = desiredPieceW * rank + board.checkerboard.boardOffsetX();
                    // yo = desiredPieceH * file + board.checkerboard.boardOffsetY();
                    // Next, get the pixel data for the desired piece from the piecemap.
                    // The piece map is width = 1000, height = 255, 32-bit color.
                    // deltaX = 1000 / 6 pieces = 167 px/piece for width.
                    // deltaY = 255 / 2 pieces = 128 px/piece for height.
                    // Screen coords.
                    float xo = desiredPieceW * rank + board.checkerboard.boardOffsetX();
                    float yo = desiredPieceH * file + board.checkerboard.boardOffsetY();
                    // On-texture coords.
                    float txo =
                    float tyo =
                    float scale = 1.0f;
                    FloatBuffer verts = MemoryUtil.memAllocFloat(24);
                    verts.put(
                            new float[]{ xo, yo + (???), 0.0f, 0.0f,
                            xo, yo,                 0.0f, 1.0f,
                            xo + (???), yo, 1.0f, 1.0f,

                            xo, yo + (???), 0.0f, 0.0f,
                            xo + (???), yo, 1.0f, 1.0f,
                            xo + (???), yo + (???), 1.0f, 0.0f }
                    );
                    pieceShader.bind();

                    glActiveTexture(GL_TEXTURE0);
                    glBindVertexArray(vao);
                    glBindTexture(GL_TEXTURE_2D, piecemap.texid);
                    glBindBuffer(GL_ARRAY_BUFFER, vbo);
                    glBufferSubData(GL_ARRAY_BUFFER, 0, verts);
                    glBindBuffer(GL_ARRAY_BUFFER, 0);
                    glDrawArrays(GL_TRIANGLES, 0, 6);
                    // Use the basicquad shape to render the piece, since the vertices do not change (their positions).
                    // The VBO does change however (pixel data), so it must be a dynamic_draw call. The vao does not change -> texture and position stay the same both in value and relative position to each other.
                    // Must use the pieceshader shader since it has texture coordinates.
                }
            }
        }
        // Render piece on hand later
    }

    public void render(Window window, Board board) {
        clear();

        this.renderCheckerboard(board.checkerboard);
        this.renderPieces(board);

        // Restore state
        glBindVertexArray(0);
    }

    public void cleanup() {
        if (boardShader != null) {
            boardShader.cleanup();
        }
        piecemap.cleanup();
    }

    public void init() {
        try {
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

            // Load piecemap texture
            piecemap = new Texture("../chess/src/main/resources/texture/piecemap.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
