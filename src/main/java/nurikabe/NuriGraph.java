package nurikabe;

import java.util.*;

public abstract class NuriGraph {
    private Set<NuriSquare> members = new HashSet<>();

    NuriGraph(NuriSquare node) {
        //members will never be empty!
        members.add(node);
        node.setGraph(this);
    }

    public abstract boolean isBlack();

    public abstract Optional<Integer> getTargetSize();

    public int size() {
        return members.size();
    }

    public void add(NuriSquare node){
        node.setGraph(this);
        members.add(node);

    }

    public Set<NuriSquare> getMembers(){
        Set<NuriSquare> output = new HashSet<>();
        output.addAll(members);
        return output;
    }

    public Set<NuriSquare> getCandidates() {
        Set<NuriSquare> output = new HashSet<>();
        members.stream()
                .flatMap(NuriSquare::neighbors)
                .filter(ns -> ns.getGraph() == null)
                .forEach(output::add);
        return output;
    }

    public Set<NuriGraph> getBorders() {
        Set<NuriGraph> output = new HashSet<>();
        members.stream()
                .flatMap(NuriSquare::neighbors)
                .filter(ns -> ns.getGraph() != null)
                .map(NuriSquare::getGraph)
                .forEach(output::add);
        return output;
    }

    public void consume(NuriGraph otherGraph) {
        for(NuriSquare node :otherGraph.members){
            this.add(node);
        }
    }
}
