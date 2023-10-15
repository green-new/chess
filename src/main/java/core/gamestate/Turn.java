package core.gamestate;

import core.Piece;

public class Turn implements IGameState {
    private int playerTurn;

    public Turn() {
        playerTurn = Piece.White;
    }

    public int getTurn() { return playerTurn; }
    public void swapTurn() {
        switch (playerTurn) {
            case Piece.White -> playerTurn = Piece.Black;
            case Piece.Black -> playerTurn = Piece.White;
        }
    }

    @Override
    public Turn query() {
        return this;
    }
}
