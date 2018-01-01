package nurikabe;

import java.util.Optional;

public class BlackGraph extends NuriGraph{

    public BlackGraph(NuriSquare node) {
        super(node);
    }

    @Override
    public boolean isBlack() {
        return true;
    }

    @Override
    public Optional<Integer> getTargetSize() {
        return Optional.empty();
    }
}
