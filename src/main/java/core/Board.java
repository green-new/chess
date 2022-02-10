package core;

import ui.Tile;

import static org.lwjgl.opengl.GL11.glViewport;

public class Board {
    public static final int SIZE = 8;

    public Tile[] Tiles;

    public byte[] Board;

    private final int blackSquareColor = 0x554413;

    private final int whiteSquareColor = 0x313546;

    public Board() {
        Board = new byte[SIZE * SIZE];
        this.Tiles = new Tile[SIZE * SIZE];
    }

    public void init() {
        for (int rank = 0; rank < SIZE; rank++) {
            for (int file = 0; file < SIZE; file++) {
                System.out.println("Generating tile... " + (SIZE * rank + file));
                this.Tiles[SIZE * rank + file] = new Tile(rank, file);
            }
        }
    }
}
