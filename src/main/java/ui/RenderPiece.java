package ui;

import core.*;
import core.gamestate.Board;
import core.gamestate.Captured;
import core.ids.GameStates;
import core.ids.ResourceIds;
import engine.Engine;
import engine.Utils;
import org.lwjgl.system.MemoryUtil;
import ui.shader.Shader;

import java.nio.FloatBuffer;
import java.util.Map;

import static org.lwjgl.opengl.GL30.*;

public class RenderPiece extends Render {
    private Board board;
    private Checkerboard cb;
    private Captured caps;
    public RenderPiece(Engine engine) {
        super(engine, states);
    }

    @Override
    public void init() {
        try {
            shader.createVertexShader(Utils.loadResource("/board.vs"));
            shader.createFragmentShader(Utils.loadResource("/board.fs"));
            shader.link();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render() {
        Board board = (Board) this.states.get(GameStates.Board).query();
        Captured captured = (Captured) this.states.get(GameStates.Captured).query();

        // Render pieces on the board
        board.forEach(this::renderPiece);
        // Render captured pieces
        captured.forEach(this::renderCapturedPiece);
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }

    private void renderCapturedPiece(Integer integer) {
        // Render captured pieces from both sides.
    }

    private void renderPiece(Integer piece, Position position) {
        // Evaluate xo, yo screen coords for the piece.
        // The width and height act as the steps for the evaluation.
        // xo = desiredPieceW * rank + board.checkerboard.boardOffsetX();
        // yo = desiredPieceH * file + board.checkerboard.boardOffsetY();
        // Next, get the pixel data for the desired piece from the piecemap.
        // The piece map is width = 1000, height = 255, 32-bit color.
        // deltaX = 1000 / 6 pieces = 167 px/piece for width.
        // deltaY = 255 / 2 pieces = 128 px/piece for height.
        // Screen coords.
        int pieceType = Piece.getType(piece);
        int pieceColor = Piece.getColor(piece);
        int boardWidth = cb.boardWidth();
        int boardHeight = cb.boardHeight();
        int desiredPieceW = boardWidth / Board.SIZE;
        int desiredPieceH = boardHeight / Board.SIZE;
        int rank = position.rank;
        int file = position.file;
        float xo = desiredPieceW * rank + cb.boardOffsetX();
        float yo = desiredPieceH * file + cb.boardOffsetY();
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
        shader.bind();

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
