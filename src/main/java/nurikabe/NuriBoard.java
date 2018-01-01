package nurikabe;

import grids.Coordinate;
import grids.GridBoard;

import java.util.function.BiFunction;

/**
 * The game board for a game of Nurikabe.  It contains n by m NuriSquares and many NuriGraphs.
 */
public class NuriBoard extends GridBoard<NuriSquare>{
    public NuriBoard(int width, int height){
        this(width, height, NuriSquare::new);
    }

    public NuriBoard(int width, int height, BiFunction<Coordinate, GridBoard<NuriSquare>, NuriSquare> producer){
        super(width, height, producer);
    }


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(int y=0; y<height; y++){
            for(int x=0; x<height; x++){
                output.append(get(new Coordinate(x,y)));
            }
            output.append('\n');
        }
        return output.toString();
    }
}
