package core;

import engine.Engine;

public class Main {
    public static void main(String[] args) {
        try {
            IChess gameLogic = new ChessGame();
            Engine eng = new Engine("Chess game", 1024, 1024, false, gameLogic);
            eng.run();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}
