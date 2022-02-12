package core;

import ui.Tile;

import java.util.Arrays;

public class Board {
    public static final int SIZE = 8;

    public Tile[] Tiles;

    public int[] Board;

    public Board() {
        Board = new int[SIZE * SIZE];
        Tiles = new Tile[SIZE * SIZE];

        Board[54] = Piece.Black | Piece.King;
        Board[24] = Piece.White | Piece.King;
        Board[4] = Piece.White | Piece.Bishop;
    }

    /*
    Create the tiles for the board.
     */
    public void init() {
        for (int rank = 0; rank < SIZE; rank++) {
            for (int file = 0; file < SIZE; file++) {
                Tiles[(rank * SIZE) + file] = new Tile(rank, file);
            }
        }
    }

    public void cleanup() {
        Arrays.fill(this.Board, 0);
        for (Tile tile : Tiles) {
            tile.cleanup();
        }
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
}
