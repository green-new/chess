package core;

import ui.Tile;

import static org.lwjgl.opengl.GL11.glViewport;

public class Board {
    public static final int SIZE = 8;

    public Tile[] Tiles;

    public byte[] Board;

    public static final int BlackSquareColor = 0x5A0A24;

    public static final int WhiteSquareColor = 0xA37F80;

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

    public static float normalize(int x) {
        return 2.0f * (float)(x / SIZE) - 1.0f;
    }
}
