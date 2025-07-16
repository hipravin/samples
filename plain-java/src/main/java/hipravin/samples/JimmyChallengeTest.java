package hipravin.samples;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JimmyChallengeTest {

    //100 trapdoors, openened in random order, 12 / 16 players pass. Can change trapdoor every 5 opens.

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        Map<Integer, Long> passCount = new TreeMap<>();
        long iterations = 100_000;
        Random r = new Random(3);
        for (int i = 0; i < iterations; i++) {
            var board = Board.init(16, 100, 4, r);
//            var board = Board.init(3, 15, 1, r);
            var passed = board.simulate();

            passed.forEach(id -> {
                passCount.merge(id, 1L, Long::sum);
            });
        }
        passCount.forEach((id, count) -> {
            System.out.printf("%d - %d, %.4f %n", id, count, (double) count / iterations);
        });
    }

    record Player(int id) {
    }

    public static class Board {

        private final Random random;
        private final LinkedHashSet<Integer> available;
        private final Map<Integer, Player> cellPlayers;
        private final int playerPassCount;
        private int player0Pos;

        public Board(LinkedHashSet<Integer> available, Map<Integer, Player> cellPalyers, int playerPassCount, Random random) {
            this.random = random;
            this.available = available;
            this.cellPlayers = cellPalyers;
            this.playerPassCount = playerPassCount;

            this.player0Pos = cellPlayers.entrySet()
                    .stream().filter(e -> e.getValue().id() == 0)
                    .map(e -> e.getKey())
                    .findAny().orElseThrow();
        }

        public static Board init(int playerCount, int cellCount, int playerPassCount, Random random) {
            if (playerCount > cellCount) {
                throw new IllegalArgumentException("Player count > cell count" + playerCount + " > " + cellCount);
            }
            List<Integer> cellNumbers = IntStream.range(0, cellCount).boxed().collect(Collectors.toCollection(ArrayList::new));
            Collections.shuffle(cellNumbers, random);
            var available = new LinkedHashSet<>(cellNumbers);

            List<Integer> choice = IntStream.range(0, cellCount).boxed().collect(Collectors.toCollection(ArrayList::new));
            Collections.shuffle(choice);
            var playerCells = choice.stream().limit(playerCount).collect(Collectors.toCollection(LinkedHashSet::new));
            Map<Integer, Player> cellPlayers = new HashMap<>();
            int playerId = 0;
            for (Integer playerCell : playerCells) {
                cellPlayers.put(playerCell, new Player(playerId++));
            }

            return new Board(available, cellPlayers, playerPassCount, random);
        }

        private void openCell() {
            if (gameFinished()) {
                return;
            }

            int cellToOpen = available.iterator().next();
            available.remove(cellToOpen);

            Player p = cellPlayers.remove(cellToOpen);
            if(p != null && p.id() == 0) {
                player0Pos = -1;
            }
            if(player0Pos >= 0 && !gameFinished()) {
                //change pos for pplayer 0
                List<Integer> options = new ArrayList<>(available);
                options.removeAll(cellPlayers.keySet());

                if (!options.isEmpty()) {
                    int moveToCell = options.get(random.nextInt(options.size()));

                    cellPlayers.remove(player0Pos);
                    player0Pos = moveToCell;
                    cellPlayers.put(player0Pos, new Player(0));
                }
            }
        }

        private boolean gameFinished() {
            return cellPlayers.size() <= playerPassCount;
        }

        /**
         * @return player ids that passed the game
         */
        public Set<Integer> simulate() {
            while (!gameFinished()) {
                openCell();
            }
            return cellPlayers.values().stream().map(Player::id)
                    .collect(Collectors.toSet());
        }


    }


}
