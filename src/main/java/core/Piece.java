package core;

public class Piece {
    public static final int None = 0;
    public static final int King = 1;
    public static final int Pawn = 2;
    public static final int Knight = 3;
    public static final int Bishop = 4;
    public static final int Rook = 5;
    public static final int Queen = 6;

    public static final int White = 8;
    public static final int Black = 16;

    public static int getColor(int piece) {
        return (piece & Piece.White) | (piece & Piece.Black);
    }
}
