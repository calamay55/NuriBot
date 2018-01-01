package nurikabe;

import java.util.Optional;

public class WhiteGraph extends NuriGraph{

    public final Integer target;

    public WhiteGraph(NuriSquare node, Integer target) {
        super(node);
        this.target=target;
    }

    @Override
    public boolean isBlack() {
        return false;
    }

    @Override
    public Optional<Integer> getTargetSize() {
        return Optional.ofNullable(target);
    }
}
