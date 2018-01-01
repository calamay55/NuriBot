package nurikabe;

import grids.Coordinate;
import grids.GridBoard;

public class NuriBoardLoader {
    final int[][] values;
    public NuriBoardLoader(int[][] values){
        this.values = values;
    }

    public NuriSquare getSquare(Coordinate c, GridBoard<NuriSquare> gb){
        NuriSquare output = new NuriSquare(c, gb);
        int value = get(c);
        if(value>0){
            output.setGraph(new WhiteGraph(output, value));
        }
        return output;
    }

    private int get(Coordinate c){
        return values[c.x][c.y];
    }
}
