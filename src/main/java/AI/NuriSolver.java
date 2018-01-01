package AI;

import grids.Cardinal;
import nurikabe.*;
import web.NuriWebInterface;

import java.util.*;
import java.util.stream.Collectors;

public class NuriSolver {
    private final NuriWebInterface game;
    private final Set<NuriGraph> seenGraphs = new HashSet<>();
    private final NuriBoard board;
    private final int blackSquareTarget;
    public static boolean debug = false;
    private final Set<NuriSquare> initialSquares;

    public NuriSolver(NuriWebInterface game){
        this.game = game;
        this.board = game.getBoard();
        initialSquares = board.stream().filter(NuriSquare::isWhite).collect(Collectors.toSet());
        blackSquareTarget = board.height*board.width - initialSquares.stream().map(NuriSquare::getGraph).map(NuriGraph::getTargetSize).mapToInt(Optional::get).sum();
    }


    public void run(){
        board.stream().forEach(this::fillUnreachable);

        long unsolvedCount = board.stream().filter(ns->ns.getGraph()==null).count();
        while(unsolvedCount>0){
            board.stream().forEach(this::parseSquare);
            seenGraphs.clear();
            long newUnsolvedCount = board.stream().filter(ns->ns.getGraph()==null).count();



            if(newUnsolvedCount == unsolvedCount){
                System.out.println("I lost.");
                return;
            }
            unsolvedCount = newUnsolvedCount;
        }
    }

    private void fillUnreachable(NuriSquare nuriSquare) {
        if(nuriSquare.getGraph()!=null){
            return;
        }

        if(initialSquares.stream().noneMatch(sq->sq.getGraph().getTargetSize().get() > sq.getCoordinate().orthogonalDistanceTo(nuriSquare.getCoordinate()))){
           setBlack(nuriSquare);
        }
    }


    /**
     *
     * @param square
     */

    private void parseSquare(NuriSquare square){
        NuriGraph graph = square.getGraph();
        if(graph==null){
            padBetweenIslands(square);
            blackenSurroundedSquare(square);
            preventPool(square);
        }
        else if(!seenGraphs.contains(graph)){
            seenGraphs.add(graph);
            parseGraph(graph);
        }

            //TODO
    }

    private void blackenSurroundedSquare(NuriSquare square) {
        if(square.neighbors().map(NuriSquare::getGraph).allMatch(g->g!=null && g.isBlack())){
            setBlack(square);
        }
    }

    private void parseGraph(NuriGraph graph){
        borderIslandIfComplete(graph);
        takeForcedMoves(graph);
    }

    private void takeForcedMoves(NuriGraph graph) {
        if(graph.getCandidates().size()==1){
            //TODO: If black size is full, don't take forced move for black

            NuriSquare forcedMove = graph.getCandidates().iterator().next();

            if(graph.isBlack()){
                setBlack(forcedMove);
            }
            else{
                setWhite(forcedMove);
            }
        }
    }

    private void preventPool(NuriSquare square){
        for(Cardinal c : Cardinal.values()){
            NuriSquare cw = square.getNeighbor(c);
            NuriSquare ccw = square.getNeighbor(c.counterClockwise());
            NuriSquare corner = cw==null? null : cw.getNeighbor(c.counterClockwise());

            if(cw != null && ccw!=null && corner!=null && cw.isBlack() && ccw.isBlack() && corner.isBlack()){
                setWhite(square);
                System.out.println(board);
            }
        }
    }


    private void padBetweenIslands(NuriSquare square){
        Set<NuriGraph> neighborIslands = square.neighbors()
                .map(NuriSquare::getGraph)
                .filter(g->g!=null && !g.isBlack() && g.getTargetSize().isPresent())
                .collect(Collectors.toSet());

        if(neighborIslands.size()>1){
            setBlack(square);
        }
    }

    private void borderIslandIfComplete(NuriGraph graph){
        if(!graph.isBlack() && graph.getTargetSize().isPresent()){
            if(graph.getTargetSize().get() == graph.size()){
                graph.getCandidates().forEach(this::setBlack);
            }
        }
    }

    private void setBlack(NuriSquare nuriSquare) {

        if(nuriSquare.isBlack()){return;}
        if(nuriSquare.isWhite()){throw new IllegalStateException();}

        List<NuriGraph> adjacentBlacks = nuriSquare.neighbors()
                .map(NuriSquare::getGraph)
                .filter(Objects::nonNull)
                .filter(NuriGraph::isBlack)
                .collect(Collectors.toList());

        if(adjacentBlacks.isEmpty()){
            BlackGraph newGraph = new BlackGraph(nuriSquare);
            seenGraphs.add(newGraph);
        }
        else{
            NuriGraph newGraph = adjacentBlacks.remove(0);
            newGraph.add(nuriSquare);

            for(NuriGraph otherGraph : adjacentBlacks){
                if(!newGraph.equals(otherGraph)){
                    newGraph.consume(otherGraph);
                }
            }
        }

        if(debug){
            System.out.println("Black: "+nuriSquare.getCoordinate().plus(1,1));
        }
        game.click(nuriSquare.getCoordinate());
    }

    private void setWhite(NuriSquare nuriSquare) {

        if (nuriSquare.isWhite()){return;}
        if (nuriSquare.isBlack()){throw new IllegalArgumentException();}

        List<NuriGraph> adjacentWhites = nuriSquare.neighbors()
                .map(NuriSquare::getGraph)
                .filter(Objects::nonNull)
                .filter(ng->!ng.isBlack())
                .distinct()
                .collect(Collectors.toList());

        if(adjacentWhites.isEmpty()){
            WhiteGraph newGraph = new WhiteGraph(nuriSquare, null);
            seenGraphs.add(newGraph);
        }
        else{
            NuriGraph[] islands = adjacentWhites.stream()
                    .filter(g->g.getTargetSize().isPresent())
                    .toArray(NuriGraph[]::new);
            if(islands.length>1) {
                throw new IllegalStateException("Intersecting white graphs at "+nuriSquare.getCoordinate());
            }
            else if(islands.length==1) {
                islands[0].add(nuriSquare);
            }
        }

        for(NuriGraph borderGraph : adjacentWhites){
            if(!borderGraph.getTargetSize().isPresent()){
                nuriSquare.getGraph().consume(borderGraph);
            }
        }

        if(debug){
            System.out.println("White: "+nuriSquare.getCoordinate().plus(1,1));
        }
        game.rightClick(nuriSquare.getCoordinate());
        borderIslandIfComplete(nuriSquare.getGraph());
    }

}
