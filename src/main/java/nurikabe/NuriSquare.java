package nurikabe;

import grids.Coordinate;
import grids.GridBoard;
import grids.Orthogonal;

public class NuriSquare extends Orthogonal<NuriSquare> {
    private NuriGraph graph;

    public NuriSquare(Coordinate c, GridBoard board) {
        super(c, board);
        graph=null;
    }

    public boolean isBlack(){
        return graph!=null && graph.isBlack();
    }

    public boolean isWhite(){
        return graph!=null && !graph.isBlack();
    }

    public NuriGraph getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        if(isBlack()){
            return "x";
        }
        else if (isWhite()) {
            if(graph.getTargetSize().isPresent()){
                return Integer.toString(graph.getTargetSize().get());
            }
            else{
                return "?";
            }
        }
        else {
            return " ";
        }
    }

    public void setGraph(NuriGraph graph) {
        this.graph = graph;
    }

}
