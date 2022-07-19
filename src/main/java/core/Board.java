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
        Board[37] = Piece.Black | Piece.Knight;
        Board[24] = Piece.White | Piece.King;
        Board[4] = Piece.White | Piece.Bishop;
    }

    public void init() {
        this.checkerboard = new Checkerboard();
    }

    public void cleanup() {
        Arrays.fill(this.Board, 0);
        checkerboard.cleanup();
    }

    public static int[] posToRankFile(int pos) {
        int rank = pos / SIZE;
        int file = pos % SIZE;
        return new int[] { rank, file };
    }
}
