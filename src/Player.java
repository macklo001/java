
import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private int score = 0;
    private List<Card> cards = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getScore() {
        int score = 0;
        for (Card c: cards) {
            score += c.getValue();
        }
        return score;
    }

    public int updateCardsHand(Card card) {
        this.score += card.getValue();
        cards.add(card);
        return this.getScore();
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
