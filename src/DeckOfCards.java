

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class DeckOfCards {

    private static final DeckOfCards INSTANCE = new DeckOfCards();

    private DeckOfCards(){}

    public static DeckOfCards getInstance() {
        return INSTANCE;
    }

    public List<Card> dealAndShuffle() {
        List<Card> cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards);
        return cards;
    }
}
