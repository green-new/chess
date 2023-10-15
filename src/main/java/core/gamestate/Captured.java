package core.gamestate;

import core.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Captured implements IGameState {
    private final List<Integer> captured;

    public Captured() {
        captured = new ArrayList<Integer>();
    }

    public void sort() {
        Collections.sort(captured);
    }

    public void add(int piece) {
        captured.add(piece);
        sort();
    }
    public void clear() { captured.clear(); }
    // Should be no need to remove from the captured list until after game restart.

    public List<Integer> queryCapturedPieces(int color) {
        // Should be sorted automatically.
        return captured
                .stream()
                .filter(piece -> Piece.getColor(piece) == color)
                .collect(Collectors.toList());
    }

    public void forEach(Consumer<Integer> consumer) {
        captured.forEach(consumer);
    }

    @Override
    public Captured query() {
        return this;
    }
}
