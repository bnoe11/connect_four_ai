

public class Board {
    
    public static final int WINDOW = 4;
    //prints out a graphic representation of the board
    public static void printBoard(int boardState[][]) {
        char [] alphabet = {'a', 'b', 'c', 'd', 'e', 'f'};
        System.out.print(" ");
        for (int i = 0; i < boardState[0].length; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i = 0; i < boardState.length; i++) {
            System.out.print(alphabet[i]);
            for (int j = 0; j < boardState[0].length; j++) {
                //allows the correct symbol to be printed
                String symb = assertSymbol(i, j, boardState);
                System.out.print("|" + symb);
                
            }System.out.println();
        }

    }

    //determines if the token should be an X or O 
    public static String assertSymbol(int i, int j, int[][] boardState) {
        String symb;
        if(boardState[i][j] == 0) {
            symb = " ";
        }else if (boardState[i][j] == 1) {
            symb = "X";
        }else{
            symb = "O";
        }
        return symb;
    }

    //checks to see if someone won
    public boolean checkWon(int[][] boardState, int team) {

        //check if won for 3X3 boards
        if (boardState.length == 3) {
            //checking for horizontal winners
            for (int j = 0; j < boardState.length; j++) {
                if (boardState[j][0] == boardState[j][1]
                 && boardState[j][1] == boardState[j][2]
                && boardState[j][0] == team) {
                    return true;
                }
            }
            //checking for vertical winners
            for (int j = 0; j < boardState.length; j++) {
                if (boardState[0][j] == boardState[1][j]
                && boardState[1][j] == boardState[2][j]
                && boardState[0][j] == team) {
                    return true;
                }
            }
            //checking for diaganols
            if(boardState[2][0] == boardState[1][1] && boardState[1][1] == boardState[0][2]
               && boardState[2][0] == team) {
                    return true;
            }
             if(boardState[0][0] == boardState[1][1] && boardState[1][1] == boardState[2][2]
             && boardState[0][0] == team) {
                  return true;
          }
          
        }

        //check if won for 6 X 7 boards
        if (boardState.length == 6) {
            
            //horizontal check
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 6; j++) {
                    if (boardState[j][i] == boardState[j][i+1] && boardState[j][i+1] == boardState[j][i+2]
                    && boardState[j][i+2] == boardState[j][i+3] && boardState[j][i] != 0) {
                        return true;
                    }
                }
            }
            //vertical check
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 7; j++) {
                    if (boardState[i][j] == boardState[i+1][j] && boardState[i+1][j] == boardState[i+2][j]
                    && boardState[i+2][j] == boardState[i+3][j] && boardState[i][j] != 0) {
                        return true;
                    }
                }
            }
            
            //diaganol checks
            for (int i = 3; i < boardState.length; i++) {
                for (int j = 0; j < boardState[0].length - 3; j++) {
                    if (boardState[i][j] == boardState[i-1][j+1] && boardState[i-1][j+1] == boardState[i-2][j+2]
                    && boardState[i-2][j+2] == boardState[i-3][j+3] && boardState[i][j] != 0) {
                        return true;
                    }
                }
            }
            for(int i = 3; i < boardState.length; i++) {
                for (int j = 3; j < boardState[0].length; j++) {
                    if (boardState[i][j] == boardState[i-1][j-1] && boardState[i-1][j-1] == boardState[i-2][j-2]
                    && boardState[i-2][j-2] == boardState[i-3][j-3] && boardState[i][j] != 0) {
                        return true;
                    }
                }
            }

          }
              
        return false;
    }

    //checks if board is filled
    public boolean checkFilled(int [][] boardState) {
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[0].length; j++) {
                if (boardState[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    //assigns a score to each move
    public int move_rating(int[][] boardState, int team) {
        int score = 0;
        int team_counter = 0;
        int empty_counter = 0;
        int enemy_counter = 0;

        //for 3X3 boards, only 0 is returned (no heuristic!!!)
        if (boardState.length == 3) {
 
            return 0;
        }
        
        //for 6X7 boards
        else {

            //horizontal
            for (int i = 0; i < boardState.length; i++) {
                for (int j = 0; j < boardState[0].length - 3; j++) {
                    for (int k = 0; k < WINDOW; k++) {
                        if (boardState[i][j+k] == team) {
                            team_counter++;
                        }
                        else if (boardState[i][j+k] == 0) {
                            empty_counter++;
                        }
                        else if (boardState[i][j+k] == -team) {
                            enemy_counter++;
                        }
                    }
                    score += score(team_counter, empty_counter, enemy_counter, 6);
                    team_counter = 0;
                    empty_counter = 0;
                    enemy_counter = 0;
                }
            }
           

            //vertical
            for (int i = 0; i < boardState[0].length; i++) {
                for (int j = 0; j < boardState.length-3; j++) {
                    for (int k = 0; k < WINDOW; k ++) {
                        if (boardState[j+k][i] == team) {
                            team_counter++;
                        }
                        else if (boardState[j+k][i] == 0) {
                            empty_counter++;
                        }
                        else if (boardState[j+k][i] == -team) {
                            enemy_counter++;
                        }
                    }
                    score += score(team_counter, empty_counter, enemy_counter, 6);
                    team_counter = 0;
                    empty_counter = 0;
                    enemy_counter = 0;
                }
            }
            

            //upwards diaganols
            for (int i = 0; i < boardState.length - 3; i++) {
                for (int j = 0; j < boardState[0].length - 3; j++) {
                    for (int k = 0; k < WINDOW; k++) {
                        if (boardState[i+k][j+k] == team) {
                            team_counter++;
                        }
                        else if (boardState[i+k][j+k] == 0) {
                            empty_counter++;
                        }
                        else if (boardState[i+k][j+k] == -team) {
                            enemy_counter++;
                        }
                    }
                    score += score(team_counter, empty_counter, enemy_counter, 6);
                    team_counter = 0;
                    empty_counter = 0;
                    enemy_counter = 0;
                   
                }
               
            }

            //downwards diagonals
            for (int i = 0; i < boardState.length - 3; i++) {
                for (int j = 0; j < boardState[0].length - 3; j++) {
                    for (int k = 0; k < WINDOW; k++) {
                        if (boardState[i + 3 - k][j + k] == team) {
                            team_counter++;
                        }
                        else if (boardState[i][j] == 0) {
                            empty_counter++;
                        }
                        else if (boardState[i][j] == -team) {
                            enemy_counter++;
                        }
                    }
                    score += score(team_counter, empty_counter, enemy_counter, 6);
                    team_counter = 0;
                    empty_counter = 0;
                    enemy_counter = 0;
                }
            }
            
            //score for center (in theory more preferable)
            /*for (int i = 0; i < boardState.length; i++) {
                if (boardState[i][3] == team) {
                    team_counter++;
                }
            }*/

            score += team_counter * 3;

        }


        return score;
    }

    //returns a score depending on what types of pieces are in each row, col, diag
    public static int score(int team_counter, int empty_counter, int enemy_counter, int board_length) {

        if (board_length == 3) {
            int score = 0;
            if (team_counter == 3) {
                score += 10;
            }else if (team_counter == 2 && empty_counter == 1) {
                score += 5;
            }else if (enemy_counter == 3) {
                score -=80;
            }
            return score;
        }
        else {
            int score = 0;
            if (team_counter == 4) {
                score += 1000;
            }else if (team_counter == 3 && empty_counter == 1) {
                score += 10;
            }else if (team_counter == 2 && empty_counter ==2) {
                score += 2;
            }else if(enemy_counter == 3 && empty_counter == 1) {
                score -= 80;
            }

            return score;
        }
        
    }

}
