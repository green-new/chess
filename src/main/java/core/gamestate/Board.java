package core.gamestate;

import core.Piece;
import core.Position;
import ui.PiecePositionConsumer;

import java.util.Arrays;

public class Board implements IGameState {
    public static final int SIZE = 8;
    private final int[] board;

    public Board() {
        board = new int[SIZE * SIZE];
        board[54] = Piece.Black | Piece.King;
        board[37] = Piece.Black | Piece.Knight;
        board[24] = Piece.White | Piece.King;
        board[4] = Piece.White | Piece.Bishop;
    }

    public void clear() {
        Arrays.fill(this.board, 0);
    }

    public static Position posToRankFile(int pos) {
        int rank = pos / SIZE;
        int file = pos % SIZE;
        return new Position(rank, file);
    }

    public void forEach(PiecePositionConsumer consumer) {
        int i = 0;
        for (Integer piece : board) {
            consumer.accept(piece, posToRankFile(i));
            ++i;
        }
    }

    public int getPiece(int i) { return getPiece(posToRankFile(i)); }
    public int getPiece(Position pos) { return getPiece(pos.rank, pos.file); }
    public int getPiece(int rank, int file) { return board[rank * Board.SIZE + file]; }

    @Override
    public Board query() {
        return this;
    }
}
