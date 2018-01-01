package grids;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A grids.GridBoard represents a rectangular set of Es such as pieces of a chessboard.
 */
public class GridBoard<E extends Orthogonal> {
    private final List<List<E>> board;
    public final int width;
    public final int height;

    public GridBoard(int width, int height, BiFunction<Coordinate, GridBoard<E>, ? extends E> producer) {
        board = new ArrayList<>();
        this.width = width;
        this.height = height;
        for (int i = 0; i < width; i++) {
            List<E> column = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                column.add(producer.apply(new Coordinate(i, j), this));
            }
            board.add(column);

        }
    }

    public void ForEach(Consumer<? super E> method) {
        for (List<E> column : board) {
            for (E square : column) {
                method.accept(square);
            }
        }
    }

    public E get(Coordinate c) {

        return isInBounds(c) ? board.get(c.x).get(c.y) : null;
    }

    public boolean isInBounds(Coordinate c){
        return c.x >=0 && c.x <width && c.y>=0 && c.y<height;
    }

    public void set(Coordinate c, E value) {
        board.get(c.x).set(c.y, value);
    }

    public void apply(Coordinate c, UnaryOperator<E> operation) {
        set(c, operation.apply(get(c)));
    }

    public Stream<E> stream() {
        return board.stream().flatMap(List::stream);
    }


}
