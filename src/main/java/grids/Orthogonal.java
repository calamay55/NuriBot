package grids;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A node on a GridBoard graph.
 *
 */

public abstract class Orthogonal <E extends Orthogonal> {
    public final Coordinate coordinate;
    protected final GridBoard<E> board;

    public Orthogonal(Coordinate c, GridBoard<E> board) {
        this.coordinate = c;
        this.board = board;
    }


    public E getNeighbor(Cardinal cardinal) {
        return board.get(this.coordinate.next(cardinal));
    }

    public Stream<? extends E> neighbors() {
        return Arrays.stream(Cardinal.values()).map(this::getNeighbor).filter(Objects::nonNull);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
