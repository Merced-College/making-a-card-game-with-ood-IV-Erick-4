public class Card {
        private int value; // Declares private value
        private String suit; // Declares private suit
        private String rank; // Declares private rank

        public Card(int value, String suit, String rank) {
            this.value = value; // Setter for value
            this.suit = suit; // Setter for suit
            this.rank = rank; // Setter for rank
        }

        public int getValue() {
            return value; // Getter for value
        }

        public String getSuit() {
            return suit; // Getter for suit
        }

        public String getRank() {
            return rank; // Getter for rank
        }

        @Override
        public String toString() {
            return rank + " of " + suit; // Getter that returns the card's rank and suit
        }
    }