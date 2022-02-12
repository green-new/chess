package core;

import org.joml.Vector2f;
import ui.Checkerboard;

import java.util.Arrays;
public class Board {
    public static final int SIZE = 8;

    public int[] Board;

    public Checkerboard checkerboard;

    public Board() {
        Board = new int[SIZE * SIZE];
        Board[54] = Piece.Black | Piece.King;
        Board[24] = Piece.White | Piece.King;
        Board[4] = Piece.White | Piece.Bishop;
    }

    /*
    Create the tiles for the board.
     */
    public void init() {
        this.checkerboard = new Checkerboard();
    }

    public void cleanup() {
        Arrays.fill(this.Board, 0);
        checkerboard.cleanup();
    }

    /*
    The board width in pixels.
     */
    public int boardWidth() {
        return 800;
    }

    /*
    The board height in pixels.
     */
    public int boardHeight() {
        return 800;
    }

    /*
    The board x offset in pixels.
     */
    public int boardOffsetX() {
        return 112;
    }

    /*
    The board y offset in pixels.
    */
    public int boardOffsetY() {
        return 112;
    }

    public static int[] posToRankFile(int pos) {
        int rank = pos / SIZE;
        int file = pos % SIZE;
        return new int[] { rank, file };
    }

    public Vector2f getRes2f() {
        return new Vector2f((float)this.boardWidth(), (float)this.boardHeight());
    }

    public Vector2f getOffset2f() {
        return new Vector2f((float)this.boardOffsetX(), (float)this.boardOffsetY());
    }
}
