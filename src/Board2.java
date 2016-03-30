import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Sam on 30/03/2016.
 */
public abstract class Board2<Move> {

    abstract Player nextPlayer();

    abstract Set<Move> availableMoves();

    abstract int value();

    abstract Board2<Move> play(Move move);

    // Constructs the game tree of the board using the minimax algorithm
    // (without alpha-beta pruning):
//    public GameTree2<Move> tree() {
//        if (availableMoves().isEmpty())
//            return new GameTree2<>
//                    (this,
//                            new LinkedHashMap<>(),
//                            value());
//        else
//            return (nextPlayer() == Player.MAXIMIZER ? maxTree() : minTree());
//    }
//
//    // Two helper methods for that, which call the above method tree:
//    public GameTree2<Move> maxTree() {
//        assert (!availableMoves().isEmpty());
//
//        int optimalOutcome = Integer.MIN_VALUE;
//        LinkedHashMap<Move, GameTree2<Move>> children
//                = new LinkedHashMap<>();
//
//        for (Move m : availableMoves()) {
//            GameTree2<Move> subtree = play(m).tree();
//            children.put(m, subtree);
//            optimalOutcome = Math.max(optimalOutcome, subtree.optimalOutcome());
//        }
//
//        return new GameTree2<>(this, children, optimalOutcome);
//    }
//
//    public GameTree2<Move> minTree() {
//        assert (!availableMoves().isEmpty());
//
//        int optimalOutcome = Integer.MAX_VALUE;
//        LinkedHashMap<Move, GameTree2<Move>> children
//                = new LinkedHashMap<>();
//
//        for (Move m : availableMoves()) {
//            GameTree2<Move> subtree = play(m).tree();
//            children.put(m, subtree);
//            optimalOutcome = Math.min(optimalOutcome, subtree.optimalOutcome());
//        }
//
//        return new GameTree2<Move>(this, children, optimalOutcome);
//    }

    public GameTree2<Move> heuristicTree(int level, int alpha, int beta) {
        if (level == 0) {
            if (availableMoves().isEmpty()) {
                return new GameTree2<>(this, new LinkedHashMap<>(), value());
            } else {
                return new GameTree2<>(this, new LinkedHashMap<>(), beta - alpha);
            }
        } else {
            LinkedHashMap<Move, GameTree2<Move>> children = new LinkedHashMap<>();
            if (nextPlayer().equals(Player.MAXIMIZER)) {
                int v = Integer.MIN_VALUE;
                for (Move m : availableMoves()) {
                    v = Math.max(v, heuristicTree(level - 1, alpha, beta).optimalOutcome());
//                    alpha = Math.max(alpha, v);
                    if (v > alpha) {
                        alpha = v;
                        children.put(m, play(m).heuristicTree(level - 1, alpha, beta));
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
                return new GameTree2<>(this, children, v);
            } else {
                int v = Integer.MAX_VALUE;
                for (Move m : availableMoves()) {
                    v = Math.min(v, heuristicTree(level - 1, alpha, beta).optimalOutcome());
//                    beta = Math.min(beta, v);
                    if (v < beta) {
                        beta = v;
                        children.put(m, play(m).heuristicTree(level - 1, alpha, beta));
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
                return new GameTree2<>(this, children, v);
            }
        }
    }

}
