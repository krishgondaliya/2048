package game;

import java.util.ArrayList;

public class Board {
    private int[][] gameBoard;               // the game board array
    private ArrayList<BoardSpot> openSpaces; // the ArrayList of open spots: board cells without numbers.

    
    public Board() {
        gameBoard = new int[4][4];
        openSpaces = new ArrayList<>();
    }

    
    public Board ( int[][] board ) {
        gameBoard = new int[board.length][board[0].length];
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                gameBoard[r][c] = board[r][c];
            }
        }
        openSpaces = new ArrayList<>();
    }

   
    public void updateOpenSpaces()
    {
        openSpaces = new ArrayList<BoardSpot>();
        for (int row = 0 ; row < gameBoard.length; row++)
        {
            for (int col = 0; col < gameBoard[0].length; col++)
            {
                if (gameBoard[row][col] == 0)
                {
                    openSpaces.add(new BoardSpot(row,col));
                }
            }
        }
    }

   
    public void addRandomTile() 
    {
        if (!openSpaces.isEmpty()) 
        {
            int randomIndex = StdRandom.uniform(0, openSpaces.size());
            BoardSpot randomSpot = openSpaces.get(randomIndex);
            int row = randomSpot.getRow();
            int col = randomSpot.getCol();
            int randomValue = (StdRandom.uniform(0, 100) <= 90) ? 2 : 4;
            gameBoard[row][col] = randomValue;
            openSpaces.remove(randomIndex);
        }
        }

    public void swipeLeft() 
    {
        
        for (int i = 0; i < gameBoard.length; i++)
        {
            int count = 0;
            int[] a = gameBoard[i];
            for (int j = 0; j < a.length; j++) 
            {
                if (a[j] != 0) 
                {
                    a[count++] = a[j];
                }
            }
            for (int b = count; b < a.length;  b++)
            {
                a[b] = 0;
            }
            gameBoard[i] = a;
        } 
    }    

   
    public void mergeLeft() 
    {
        for (int x = 0; x < gameBoard.length; x++)
        {
            for (int y = 0; y + 1 < gameBoard[x].length; y++)
            {
                if ((gameBoard[x][y] == gameBoard[x][y+1]))
                {
                    if (!(gameBoard[x][y] == 0))
                    {
                        gameBoard[x][y] = 2 * gameBoard[x][y];
                        gameBoard[x][y+1] = 0;
                    }
                }
            }
        }
    }


    public void rotateBoard() {
        transpose();
        flipRows();
    }

    public void transpose()
    {
        int[][] grid = new int[4][4];
        for (int i = 0; i < gameBoard.length; i++)
        {
            for (int j = 0; j < gameBoard[i].length; j++)
            {
                grid[j][i] = gameBoard[i][j];
            }
        }
        gameBoard = grid;
    }


    public void flipRows()
    {
         int[][] grid = new int[4][4];
        for (int i = 0; i < gameBoard.length; i++)
        {
            int x = 3;
            for (int j = 0; j < gameBoard[i].length; j++)
            {
                grid[i][x] = gameBoard[i][j];
                x--;
            }
        }
        gameBoard = grid;
    }
   
    public void makeMove(char letter) {
        if (letter == 'L')
        {
            swipeLeft();
            mergeLeft();
            swipeLeft();
        }
        if (letter == 'R')
        {
            rotateBoard();
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
            rotateBoard();
        }
        if (letter == 'D')
        {
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
            rotateBoard();
            rotateBoard();
        }
        if (letter == 'U')
        {
            rotateBoard();
            rotateBoard();
            rotateBoard();
            swipeLeft();
            mergeLeft();
            swipeLeft();
            rotateBoard();
            
        }
    }
    
  
    public int showScore() {
        int score = 0;
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                score += gameBoard[r][c];
            }
        }
        return score;
    }

    public void print() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }
    
    public void printOpenSpaces() {
        for ( int r = 0; r < gameBoard.length; r++ ) {
            for ( int c = 0; c < gameBoard[r].length; c++ ) {
                String g = Integer.toString(gameBoard[r][c]);
                for ( BoardSpot bs : getOpenSpaces() ) {
                    if (r == bs.getRow() && c == bs.getCol()) {
                        g = "**";
                    }
                }
                StdOut.print((g.equals("0")) ? "-" : g);
                for ( int o = 0; o < (5 - g.length()); o++ ) {
                    StdOut.print(" ");
                }
            }
            StdOut.println();
        }
    }

   
    public Board(long seed) {
        StdRandom.setSeed(seed);
        gameBoard = new int[4][4];
    }

    
    public ArrayList<BoardSpot> getOpenSpaces() {
        return openSpaces;
    }

    public int[][] getBoard() {
        return gameBoard;
    }
}
