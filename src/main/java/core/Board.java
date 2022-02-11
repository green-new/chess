package core;

import ui.Tile;

public class Board {
    public static final int SIZE = 8;

    public Tile[] Tiles;

    public byte[] Board;

    public Board() {
        Board = new byte[SIZE * SIZE];
        this.Tiles = new Tile[SIZE * SIZE];
    }

    public void init() {
        for (int rank = 0; rank < SIZE; rank++) {
            for (int file = 0; file < SIZE; file++) {
                this.Tiles[SIZE * rank + file] = new Tile(rank, file);
            }
        }
    }
}
