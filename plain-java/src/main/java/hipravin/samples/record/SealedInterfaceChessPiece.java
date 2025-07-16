package hipravin.samples.record;

public sealed interface SealedInterfaceChessPiece {

    record Bishop() implements SealedInterfaceChessPiece {

        @Override
        public String name() {
            return "Bishop";
        }
    }

    String name();
}
