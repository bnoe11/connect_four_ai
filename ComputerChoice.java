import java.util.ArrayList;

public class ComputerChoice {
    public int team;
    public boolean CPU_won = false;
    public boolean human_won = false;
    public int depth = 3;

    // constructor
    public ComputerChoice() {
    }

    //method that allows computer to make selection 
    public int makeSelection(int[][] boardChoice, int team) {

        depth = 0;
        int AI_team = team;

        //allows for the minimax search to reach a terminal state every time
        if (boardChoice.length == 3) {
            depth = 999999;
        }else if (boardChoice.length == 6) {
            depth = 8;
        }


        ArrayList<Integer> col_and_score = new ArrayList<>();
        //col_and_score.clear();
        col_and_score = mini_max(boardChoice, depth, true, AI_team, -1, -Integer.MAX_VALUE, Integer.MAX_VALUE);
        
        return (int) col_and_score.get(0);

       
        /*int score =  0;
        int bestScore = 0;
        int bestCol = 0;*/

        

        /*for (int i = 0; i < valid_moves.size(); i++) {
            int [][] boardCopy = clone(boardChoice);
            newState.place(boardCopy, valid_moves.get(i), AI_team);
            score = newBoard.move_rating(boardCopy, AI_team);
            if (score > bestScore) {
                bestScore = score;
                bestCol = i;
            }
        }
        return bestCol;*/

    }


    //minimax
    public ArrayList mini_max(int [][] board, int depth, boolean maximizing_team, int AI_team, int prev_move, int alpha, int beta) {

        Board checkBoard = new Board();
        ArrayList<Object> col_and_score = new ArrayList<>();
        ArrayList<Integer> valid_moves = new ArrayList<>();
        State state = new State(board);
        valid_moves = state.get_valid_moves(board);
        int value = 0; 
        int minmax_score = 0;
        boolean AI_won = checkBoard.checkWon(board, AI_team);
        boolean h_won = checkBoard.checkWon(board, -AI_team);
        boolean filled = checkBoard.checkFilled(board);

         //base case, checks for terminal nodes 
         if (depth == 0 || AI_won || h_won|| filled) {
            //check terminal states
            if ( AI_won || h_won|| filled) {
                if (AI_won == true) {
                    col_and_score.add(prev_move);
                    //System.out.println(prev_move);
                    col_and_score.add(100000000);
                    return col_and_score;
                }else if (h_won == true) {
                    col_and_score.add(prev_move);
                    col_and_score.add(-100000000);
                    return col_and_score;
                }else {
                    col_and_score.add(prev_move);
                    col_and_score.add(0);
                    return col_and_score;
                }
            }
            //depth is 0
            else {
                col_and_score.add(null);
                col_and_score.add(checkBoard.move_rating(board, AI_team));
                return col_and_score;
            }
        }
        if (maximizing_team) {
            int best_col = valid_moves.get(0);
            value = -Integer.MAX_VALUE;
            for (int i = 0; i < valid_moves.size(); i++) {
                int [][] board_copy = clone(board);
                State newState = new State(board_copy);
                newState.place(board_copy, valid_moves.get(i), AI_team);
                ArrayList<Object> score = mini_max(board_copy, depth-1, false, AI_team, i, alpha, beta);
                minmax_score =  (int) score.get(1);
                if (minmax_score > value) {
                    value = minmax_score;
                    best_col = valid_moves.get(i);
                }
                if(Integer.compare(value, alpha) > 0){
                    alpha = value;
                }
                if(alpha >= beta){
                    break;
                }
            }
            col_and_score.add(best_col);
            col_and_score.add(value);
            return col_and_score;
        }else {
            int best_col = valid_moves.get(0);
            value = Integer.MAX_VALUE;
            for (int i = 0; i < valid_moves.size(); i++) {
                int [][] board_copy = clone(board);
                State newState = new State(board_copy);
                newState.place(board_copy, valid_moves.get(i), -AI_team);
                ArrayList<Object> score = mini_max(board_copy, depth-1, true, AI_team, i, alpha, beta);
                minmax_score =  (int) score.get(1);
                if (minmax_score < value) {
                    value = minmax_score;
                    best_col = valid_moves.get(i);
                }
                if(Integer.compare(value, beta) < 0){
                    beta = value;
                }
                if(alpha >= beta){
                    break;
                }
            }
            col_and_score.add(best_col);
            col_and_score.add(value);
            return col_and_score;
        }

    }



    // creates a seperate copy in memeory of a given 2D array
    public int[][] clone(int[][] twod_array) {
        int[][] copy = new int[twod_array.length][twod_array[0].length];

        for (int i = 0; i < twod_array.length; i++) {
            for (int j = 0; j < twod_array[0].length; j++) {
                copy[i][j] = twod_array[i][j];
            }
        }
        return copy;

    }
}
