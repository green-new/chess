package core;

import core.gamestate.Board;
import core.gamestate.Captured;
import core.gamestate.IGameState;
import core.gamestate.Turn;
import core.ids.GameStates;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class GameContext {
    private final Map<Integer, IGameState> gameStates;
    private final Set<Integer> activeGameStates;

    public GameContext() {
        this.activeGameStates = new HashSet<>();
        this.gameStates = Map.ofEntries(
                Map.entry(GameStates.Board, new Board()),
                Map.entry(GameStates.Turn, new Turn()),
                Map.entry(GameStates.Captured, new Captured())
        );
        activeGameStates.addAll(gameStates.keySet());
    }

    public void forEachState(Consumer<IGameState> consumer) {
        for (Integer key : activeGameStates) {
            consumer.accept(gameStates.get(key));
        }
    }
    public Map<Integer, IGameState>
    public boolean activateGameState(Integer key) { return activeGameStates.remove(key); }
    public boolean deactivateGameState(Integer key) {
        return activeGameStates.add(key);
    }
    public boolean isGameStateActive(Integer key) {
        return !activeGameStates.contains(key);
    }
    public <T extends IGameState> T getGameState(Integer key) { return (T) gameStates.get(key); }
}
