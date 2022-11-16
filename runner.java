import java.util.Scanner;

public class runner {

    public static void main(String [] args) {

        Scanner scnr = new Scanner(System.in);

        //selecting board type
        System.out.println("Would you like to play: \n a) 3X3 connect four \n b) 6X7 connect four ");
        System.out.println("(respond with 'a' or 'b)");
        String boardType = scnr.next();
        while (!(boardType.equals("a") || boardType.equals("b"))) {
            System.out.println("Please respond with either 'a' or 'b' to make your selection");
            boardType = scnr.next();
        }
        
        //player chooses whether to be X or O
        System.out.println("Would you like to be Red/X or Yellow/O? \n"
                            + "(Please respond with either X or O)");
        String playerNum = scnr.next();                   
        while (!(playerNum.equals("X") || playerNum.equals("O"))) {
            System.out.println("Please respond with either 'X' or 'O' to make your selection");
             playerNum = scnr.next();
        }
        //creates a new state
        State state = new State(boardType, playerNum);
        Board.printBoard(state.getBoardState());;
        ComputerChoice compChoice = new ComputerChoice();
        if (playerNum.equals("X")) {
            System.out.println("Alright X, you go first. The CPU will be O");
            System.out.println("Choose what column you want to place your piece by responding with the corresponding number");
            state.humanTurn = true;
            compChoice.team = -1;
        
        }else {
            System.out.println("Alright CPU, you go first. Player2 go next");
            compChoice.team = 1;
            state.XTurn = true;
            state.humanTurn = false;
            int comp = compChoice.makeSelection(state.getBoardState(),1);
            state.setBoardState(Integer.toString(comp), compChoice.team);
        }

        //keeps the game running in correct conditions
        while (!state.won && !state.filled) {
            String choice = scnr.next();
            //checks if the entry is a number in the correct range
            while (!state.validateInt(choice)) {
                 choice = scnr.next();
            }//checks if the row is already occupied
            while(state.occupied(state.boardState, Integer.parseInt(choice), state)) {
                choice = scnr.next();
            }
            state.setBoardState(choice, -compChoice.team);
            if (state.won){ break;}
            int comp = compChoice.makeSelection(state.getBoardState(), compChoice.team);
            state.setBoardState(Integer.toString(comp), compChoice.team);
        }

        //prints results
        if(state.won && !state.XTurn) {
            System.out.println("Game over, Red/X wins");
        }else if(state.won && state.XTurn) {
            System.out.println("Game over, Yellow/O wins");
        }else{
            System.out.println("Cat game");
        }

    }

}