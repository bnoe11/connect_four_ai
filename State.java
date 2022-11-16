import java.util.ArrayList;
import java.util.Arrays;

public class State {
    
    int boardState[][];
    boolean won;
    boolean XTurn;
    boolean humanTurn;
    boolean filled;

    //array list making it easier to print out the boards
    static ArrayList<Integer> smallAlpha = new ArrayList<Integer>(
        Arrays.asList(0, 1, 2)
    );
    static ArrayList<Integer> largeAlpha = new ArrayList<Integer>(
        Arrays.asList(0, 1, 2, 3, 4, 5, 6)
    );

    //constructor
    public State(String type, String turn) {
        if (type.equals("a")) {

            this.boardState = new int [3][3];

        }else if (type.equals("b")) {
            this.boardState = new int [6][7];
        }if (turn.equals("X")) {
            this.XTurn = true;
        }else {
            this.XTurn = false;
        }
        this.won = false;
        this.filled = false;
    }

    //constructor 
    public State (int [][] boardState) {
        this.boardState = boardState;
    }

    //getter
    public int[][] getBoardState() {
        return boardState;
    }
    
    //setter
    public void setBoardState(String choice, int team) {

        //checks to make sure entry is an int
        if (!validateInt(choice)) {
            return;
        }
        //checks to make sure the column is valid
        int numChoice = Integer.parseInt(choice);
        if (boardState.length == 3 && !smallAlpha.contains(numChoice)) {
            System.out.println("Please select a column using the corresponding number: ");
            return;
        }
        if (boardState.length == 6 && !largeAlpha.contains(numChoice)) {
            System.out.println("Please select a column using the corresponding number: ");
            return;
        }

        //updates board, checks if someone won, updates turn
        this.boardState = place(boardState, numChoice, team);
        Board checkBoard = new Board();
        this.filled = checkBoard.checkFilled(boardState);
        Board.printBoard(boardState);
        this.won = checkBoard.checkWon(getBoardState(), team);
        this.humanTurn = !humanTurn;
        this.XTurn = !XTurn;
    }

    //checks to make sure entry is a integer
    public boolean validateInt(String choice) {
        try {
            int numChoice = Integer.parseInt(choice);
            return true;
        }catch(NumberFormatException e) {
            System.out.println("Make sure your entry is a number corresponding with a column");
        }return false;
    }
    public int[][] place(int board[][], int numChoice, int team) {
                //places either a 1 or a -1 in the correct spot
                for (int j = board.length - 1; j >= 0; j--) {
                    if (board[j][numChoice] == 0) {
                        if  (team == 1) {
                            board[j][numChoice] = 1;
                        }else if (team == -1) {
                            board[j][numChoice] = -1;
                        }
                        return board;
                    }
                 }
              return board;
     }
    
    //checks if a row is filled
    public boolean occupied(int[][] boardState, int numChoice, State state) {
        int fillCount = 0;
        for (int i = 0; i < boardState.length; i++) {
            if (boardState[i][numChoice] != 0) {
                fillCount ++;
             }
        }   if (fillCount == boardState.length) {
                if (state.humanTurn) {System.out.println("Please choose a column that isn't filled");}
                return true;
                }
        return false;
    }

    

    //returns a list of possible moves
    public ArrayList<Integer> get_valid_moves(int [][] boardState) {
        ArrayList<Integer> valid_moves = new ArrayList<>();

        State tempState = new State(boardState);

        for (int i = 0; i < boardState[0].length; i++) {
            if (!occupied(boardState, i, tempState)) {
                valid_moves.add(i);
            }
        }
        return valid_moves;
    }
}
