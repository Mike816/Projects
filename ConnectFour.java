import java.io.*;
import java.util.*;


public class ConnectFour {
    private String[][] board;
    private int connectNum;
    private String turn = "O";

    // A _ on the board represent empty, and O for first, and X for second 
    
    //custom game
    public ConnectFour(int row, int col, int myConnectNum){
        board = new String[row][col];
        for(String[] t : board){
            Arrays.fill(t, "_");
        }
        
        connectNum = myConnectNum;
    }


    //returns col count
    public int getCol(){
        return board[0].length; 
    }
    
    //prints the board
    public void printBoard(){
        for(int i = 0; i< board.length; i++){
            for(int t = 0; t < board[0].length; t++){
                System.out.print(board[i][t] + " ");
            }
            System.out.println();
        }

    }
    //returns the row, -1 if something went wrong
    public int makeMove(int col){
        for(int i = board.length-1; i >= 0; i--){
            if(board[i][col].equals("_")){
                board[i][col] = turn;
                if(turn.equals("O")){
                    turn = "X";
                }
                else{
                    turn = "O";
                }
                return(i);
                
            }
        }
        return -1;
        
    }

   

    //returns the stri g at row, col of the board
    public String getPoint(int row, int col){
        return this.board[row][col];
    }

    //checks if someone has won, the input is the col and row value of the last move
    public boolean checkWin(int col_last_move, int row_last_move){
        int min_col = Math.max(0, col_last_move - connectNum);
        int max_col = Math.min(board[0].length-1, col_last_move + connectNum);

        int min_row= Math.max(0, row_last_move - connectNum );
        int max_row = Math.min(board.length-1, row_last_move + connectNum);

        String myVal = board[row_last_move][col_last_move];
        boolean hasWin = true;

        //horizontal
        int col = min_col;
        int counter = 0;
        while(col<max_col){
            if(counter >= connectNum){
               return true;
            } 
            for(int i = 0; i<connectNum; i++){
                if(board[row_last_move][col+i].equals(myVal) ){
                  counter++;
                }
                else{
                   counter = 0;
                }
            }
            col++;
       }


        //vertical
        for(int row = min_row; row < max_row; row++){
            hasWin = true;
            for(int i = 0; i<connectNum; i++){
                if(!board[row+i][col_last_move].equals(myVal)){
                    hasWin = false;
                    break;
                }
            }
            if(hasWin){
                return true; 
            }
        } 


        return checkDiagonalWin(col_last_move, row_last_move);
    }

    //check diagonalWins
    public boolean checkDiagonalWin(int lastColMove, int lastRowMove) {
        String myVal = board[lastRowMove][lastColMove];
        int count = 1;
        int x = 1;
        int row = lastRowMove - x;
        int col = lastColMove - x;
    
        // Check diagonal up-left
        while (row >= 0 && col >= 0 && board[row][col].equals(myVal)) {
            count++;
            row--;
            col--;
        }
    
        row = lastRowMove + x;
        col = lastColMove + x;
    
        // Check diagonal down-right
        while (row < board.length && col < board[0].length && board[row][col].equals(myVal)) {
            count++;
            row++;
            col++;
        }
    
        if (count >= connectNum) {
            return true;
        }
    
        count = 1;
        row = lastRowMove - x;
        col = lastColMove + x;
    
        // Check diagonal up-right
        while (row >= 0 && col < board[0].length && board[row][col].equals(myVal)) {
            count++;
            row--;
            col++;
        }
    
        row = lastRowMove + x;
        col = lastColMove - x;
    
        // Check diagonal down-left
        while (row < board.length && col >= 0 && board[row][col].equals(myVal)) {
            count++;
            row++;
            col--;
        }
    
        return count >= connectNum;
    }
    
    public static void main(String[] args) throws IOException {
        int row_last_move;
        int col = 7;
        int row = 6;
        int connect_Num = 4 ;

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to connect x! Would you like to start a default game or a custom one?");
        System.out.println("Please type D for default or anything for custom");
        String gameType = input.readLine();
        
        //selects the game type
        if(gameType.equals("D")){
            
        }
        else{
            while(true){
                try{
                    System.out.println("How many rows should the board have?");
                    row = Integer.parseInt(input.readLine());
    
                    System.out.println("How many col should the game have?");
                    col = Integer.parseInt(input.readLine());
    
                    System.out.println("How many in a row do you need to win?");
                    connect_Num = Integer.parseInt(input.readLine());
                    break;

                }
                catch(Exception e){
                    System.out.println("Something went wrong, please enter the information again");
                }
            }

        }
        //intializes the game
        ConnectFour game = new ConnectFour(row, col, connect_Num);
        game.printBoard();
        int placement_col;

        //allow the player to input moves
        while(true){
            try {
                System.out.println("Please enter your move, input a number from 1 to " +  col);
                placement_col = Integer.parseInt(input.readLine()) -1;
                row_last_move = game.makeMove(placement_col); 
                game.printBoard();
                if(game.checkWin(placement_col, row_last_move)){
                    System.out.println("Game Over!");
                    System.out.println(game.getPoint(row_last_move, placement_col) + " Won!");
                    break;
                }
            
            } 
            // try catch in case user inputs something outsides of the parameters
            catch (Exception e) {

            }   
          
        }


    }   
}
