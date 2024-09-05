/* Names:
Erick Aguilar Hernandez
Jonathan Her 
Vanessa Estrada
*/

import java.util.Random;
import java.util.Scanner;

public class BlackJack {
    /* Declare and initialize variables and class */
    private static Card[] cards = new Card[52];
    private static int currentCardIndex = 0;
    private static int playerScore = 0;
    private static int dealerScore = 0;
    private static int wager = 0;

    public static void main(String[] args) {
        /* Initialize Scanner and other variables */
        Scanner scanner = new Scanner(System.in);
        boolean turn = true;
        String playerDecision;

        /* Sets up infinite loop of Black Jack games until user decides to stop */
        while (turn) {
            initializeDeck(); // Initializes the deck
            shuffleDeck(); // Shuffles the deck
            currentCardIndex = 0; // Reset the card index after shuffling
            
            int playerTotal = 0; // Initializes player hand
            int dealerTotal = 0; // Initializes dealer hand
            int bet = 0; // Declares betting wage
            
            bet = bettingWage(scanner, wager); // Asks the player if they want to bet
            playerTotal = dealInitialPlayerCards(); // Deals player starting hand
            dealerTotal = dealInitialDealerCards(); // Deals dealer starting hand

            playerTotal = playerTurn(scanner, playerTotal); // Starts the Player's turn
            if (playerTotal > 21) { // if statement for if the player loses
                System.out.println("You busted! Dealer wins.");
                System.out.println("You lost $" + bet + "!");
                dealerScore++; // Adds a point to the Dealer's Score if you bust
                continue;
            }
            
            dealerTotal = dealerTurn(dealerTotal); // Starts the Dealer's turn
            determineWinner(playerTotal, dealerTotal, bet); // Determines whether the player or dealer had the higher count under 22

            System.out.println("Would you like to play another hand? (yes/no)"); // Asks the player if they want to continue
            playerDecision = scanner.nextLine().toLowerCase();
            
            /* While loop ensures a yes or no answer */
            while (!(playerDecision.equals("no") || playerDecision.equals("yes"))) {
                System.out.println("Invalid action. Please type 'yes' or 'no'.");
                playerDecision = scanner.nextLine().toLowerCase();
            }
            /* Stops the loop and ends the game */
            if (playerDecision.equals("no")) {
                turn = false;
            }
        }
        /* After breaking out of the loop, the scoreboard is displayed and the program ends */
        System.out.println("Final Score - Player: " + playerScore + ", Dealer: " + dealerScore);
        System.out.println("Thanks for playing!");
    }

    /* Generates the Suits and Ranks of all 52 cards in the deck */
    private static void initializeDeck() {
        String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int rankIndex = 0;
        int suitIndex = 0;

        for (int i = 0; i < cards.length; i++) {
            String rank = RANKS[rankIndex];
            String suit = SUITS[suitIndex];
            int value;

            /*if statements will initialize special ranks if they are royalty or aces */
            if (rank.equals("Jack") || rank.equals("Queen") || rank.equals("King")) {
                value = 10;
            } else if (rank.equals("Ace")) {
                value = 11;
            } else {
                value = Integer.parseInt(rank);
            }

            cards[i] = new Card(value, suit, rank); // Calls Card class to initialize card

            suitIndex++;
            /* Ensures the suit index doesn't go past 4 (there are only 4 suits in the game) */
            if (suitIndex == 4) {
                suitIndex = 0;
                rankIndex++;
            }
        }
    }

    /* Method shuffles the deck */
    private static void shuffleDeck() {
        Random random = new Random(); // Initializes randomizer
        for (int i = 0; i < cards.length; i++) { // For loop reads through the whole deck
            int index = random.nextInt(cards.length); // The index will be random each time the loop runs
            Card temp = cards[i]; // Creates temporary value to store the current index of the array
            cards[i] = cards[index]; // Current index of the array becomes equal to the random index selected
            cards[index] = temp; // The random index selected becomes the current index's previous value
        }
    }

    /* Method deals the player's starting hannd */
    private static int dealInitialPlayerCards() {
        Card card1 = dealCard(); // Declares card variables and calls on dealCard method 
        Card card2 = dealCard();
        System.out.println("Your cards: " + card1 + " and " + card2); // Player's hand is dealt
        return card1.getValue() + card2.getValue(); //displays the value of the player's card
    }

    private static int dealInitialDealerCards() {
        Card card1 = dealCard();
        System.out.println("Dealer's card: " + card1); // Dealer's hand is dealt
        return card1.getValue(); //displays the value of the delaer's card
    }

    private static int playerTurn(Scanner scanner, int playerTotal) {
        while (true) {
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("hit")) {  //if player types in hit, then the following program will be executed
                Card newCard = dealCard();
                playerTotal += newCard.getValue();
                System.out.println("You drew a " + newCard); //prints out that the player has drew a card
                if (playerTotal > 21) {
                    System.out.println("You busted! Dealer wins."); //prints out that the dealer has won
                    return 0;
                }
            } else if (action.equals("stand")) { //executed if the player types stand instead of hit
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'."); 
                //prints out when an action that isn't defined in inputed
            }
        }
        return playerTotal;
    }

    private static int dealerTurn(int dealerTotal) {
        while (dealerTotal < 17) {
            Card newCard = dealCard();
            dealerTotal += newCard.getValue();
        }
        System.out.println("Dealer's total is " + dealerTotal); //prints out what the dealer's total amount is
        return dealerTotal;
    }

    private static void determineWinner(int playerTotal, int dealerTotal, int bet) {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!"); //prints out that the player has won
            playerScore++;
            bet = bet * 2;
            System.out.println("You won $" + bet + "!");
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!"); //prints out that the game is a tie
        } else {
            System.out.println("Dealer wins!"); //prints out dealer has won
            System.out.println("You lost $" + bet + "!");
        }
    }

    private static Card dealCard() {
        return cards[currentCardIndex++];
    }

    /* Method that asks player if they want to bet */
    private static int bettingWage(Scanner scanner, int wager) {
        System.out.println("Would you like to place a bet? ");
        String decision = scanner.nextLine().toLowerCase();
        if (decision.equals("yes")) { // If yes, the player is prompted to bet an amount
            System.out.println("How much would you like to bet? ");
            wager = scanner.nextInt(); // User input on the wager
            scanner.nextLine(); // Getting the end of line character off the keyboard buffer
            System.out.println("Winnings will be doubled if you win. Good Luck!");
            System.out.println("");
        }
        return wager;
    }
}
