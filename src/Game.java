import java.util.*;

public class Game {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            int numberOfPlayers = 0;

            // read number of players
            boolean correctNumberOfPlayers = false;
            List<Player> players = new ArrayList<>();

            while (!correctNumberOfPlayers) {
                try {
                    System.out.println("How many players?");
                    numberOfPlayers = scanner.nextInt();
                } catch (InputMismatchException exception) {
                    //pass

                } finally {
                    scanner.nextLine();
                }
                correctNumberOfPlayers = isCorrectNumberOfPlayers(numberOfPlayers);
            }

            //initialize players
            for (int i = 0; i < numberOfPlayers; i++) {
                System.out.printf("Enter name of player %d : %n", i + 1);
                String name = scanner.nextLine();
                Player newPlayer = new Player(name);
                players.add(newPlayer);
            }

            // deal cards for every player
            DeckOfCards deckOfCards = DeckOfCards.getInstance();
            for (Player p : players) {
                dealCardsForPlayer(deckOfCards, p);
            }

            //deal cards for dealer
            Player dealer = new Player("Dealer");
            players.add(dealer);
            List<Card> cards = deckOfCards.dealAndShuffle();
            dealCardsForDealer(dealer, cards);

            System.out.println("All players:");
            System.out.println(players);

            // find winnners
            List<Player> winners = findGameWinners(players);

            // announce the winners

            // do we have a draw?
            if (winners.size() > 1) {
                System.out.println("WE HAVE A DRAW!");
                System.out.println("Winners are:");
            } else {
                System.out.println("Winner is:");
            }
            System.out.println(winners);

            // play one more time?
            System.out.println("GAME OVER! Play one more time yes/no?");
            String command = scanner.nextLine();
            if (command.equals("no")) {
                break;
            }
        }

    }

    private static List<Player> findGameWinners(List<Player> players) {
        List<Player> persianWinPlayers = new ArrayList<>();
        Map<Integer, List<Player>> playerScores = new HashMap<>();


        for (Player p : players) {

            // if 'persian rule' winner (two ACEs)
            if (p.getScore() == 22 && p.getCards().size() == 2) {
                persianWinPlayers.add(p);
                break;
            }

            // if player is below 21
            if (p.getScore() > 21) {
                break;
            }
            // calculate diff to 21 for every player
            int diffTo21 = 21 - p.getScore();
            List<Player> playersWithSameScore = playerScores.get(diffTo21);
            if (playersWithSameScore == null) {
                playersWithSameScore = new ArrayList<>();
                playerScores.put(diffTo21, playersWithSameScore);
            }
            playersWithSameScore.add(p);
        }

        List<Integer> sortedDiffs = new ArrayList<>(playerScores.keySet());
        Collections.sort(sortedDiffs);

        List<Player> winners;
        // persian rule winners have priority above normal players
        if(persianWinPlayers.size() > 0) {
            System.out.println("PERSIAN RULE!");
            winners = persianWinPlayers;
        } else {
            winners = playerScores.get(sortedDiffs.get(0));
        }
        return winners;
    }

    private static void dealCardsForDealer(Player dealer, List<Card> cards) {
        for (Card card : cards) {
            System.out.println("Card  " + card + ", current score: " + dealer.updateCardsHand(card));
            if (dealer.getScore() >= 17) {
                break;
            }
        }
    }

    private static void dealCardsForPlayer(DeckOfCards deckOfCards, Player p) {
        List<Card> cards = deckOfCards.dealAndShuffle();
        System.out.println("Cards for player " + p.getName());
        for (Card card : cards) {
            System.out.println("Card  " + card + ", current score: " + p.updateCardsHand(card));
            if (p.getScore() >= 21) {
                break;
            }
            String command = "";
            while(!Arrays.asList("next", "pass").contains(command)) {
                System.out.println("next/pass?");
                command = scanner.nextLine();
            }


            if ("pass".equals(command)) {
                break;
            }
        }
    }

    private static boolean isCorrectNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers >= 1 && numberOfPlayers <=4){
            return true;
        } else {
            System.out.println("Min number of players is one, max is four");
            return false;
        }
    }


}
