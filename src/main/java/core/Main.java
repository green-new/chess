package core;

import engine.Engine;
import engine.IChess;

public class Main {
    public static void main(String[] args) {
        try {
            IChess gameLogic = new Chess();
            Engine eng = new Engine("Chess game", 864, 864, gameLogic);
            eng.run();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}
