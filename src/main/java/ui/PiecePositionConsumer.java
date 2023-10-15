package ui;

import core.Position;

@FunctionalInterface
public interface PiecePositionConsumer {
    void accept(Integer piece, Position position);
}
