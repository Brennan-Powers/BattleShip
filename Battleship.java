/**
 * Created by Brennan Powers on 11/13/2017
 */

package com.example.nick.battleship;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Battleship {
    //-1=nothing, 0=miss, 1=hit, 2 or greater = ship

    public static class Board {
        int numOfShipsOnBoard;
        int rows;
        int columns;
        int[][] boardArray;
        Ship listOfShips[];

        public Board() {
            rows = 10;
            columns = 10;
            numOfShipsOnBoard = 0;
            listOfShips = new Ship[5];
            boardArray = new int[rows][columns];
            initBoard();
        }

        public Board(int r, int c) {
            rows = r;
            columns = c;
            numOfShipsOnBoard = 0;
            listOfShips = new Ship[5];
            boardArray = new int[rows][columns];
            initBoard();
        }

        public void initBoard() {
            for(int r=0 ; r < rows ; r++ )
                for(int c=0 ; c < columns ; c++ )
                    boardArray[r][c]=-1;
        } //-1=nothing, 0=miss, 1=hit, 2>=ship

        public int numRows() {
            return rows;
        }
        public int numCol() {
            return columns;
        }
    }

    public static boolean placeShip(Ship a, Board b, int row, int column, int ori) {
        //ori is orientation, 0 is down and 1 is to the right
        if(row >= b.rows || column >= b.columns) {
            //System.out.println("The ship goes off the board, please choose a different spot. R1");
            return false;
        }

        //down orientation
        if(ori == 0) {
            int counter = 0;
            if(a.getShipSize() + row > b.rows) {
                //System.out.println("The ship goes off the board, please choose a different spot. R2");
                return false;

            }
            for(int i = 0 ; i < a.getShipSize() ; i++) {
                if(b.boardArray[row+i][column] == -1) {
                    counter++;
                } else {
                    //System.out.println("There is already a ship in that space, please choose a different spot. R3");
                    return false;
                }
            }
            if(counter == a.getShipSize()) {
                for(int i = 0 ; i < a.getShipSize() ; i++) {
                    b.boardArray[row+i][column] = a.ID;
                }
                b.listOfShips[b.numOfShipsOnBoard] = a;
                b.numOfShipsOnBoard++;
                return true;
            }
        }
        else if (ori == 1) {
            int counter = 0;
            if(a.getShipSize() + column > b.columns) {
                //System.out.println("The ship goes off the board, please choose a different spot. R4");
                return false;
            }
            for(int i = 0 ; i < a.getShipSize() ; i++) {
                if(b.boardArray[row][column + i] == -1) {
                    counter++;
                } else {
                    //System.out.println("There is already a ship in that space, please choose a different spot. R5");
                    return false;
                }
            }
            if(counter == a.getShipSize()) {
                for(int i = 0 ; i < a.getShipSize() ; i++) {
                    b.boardArray[row][column + i] = a.ID;
                }
                b.listOfShips[b.numOfShipsOnBoard] = a;
                b.numOfShipsOnBoard++;
                return true;
            }
        }
        return false;
    }

    public static int shoot(Board b, int row, int column) {
        if (row >= b.rows || column >= b.columns) {
            //System.out.println("That is not a valid place to shoot. It is not on the board. Try again.");
            return 0;
        }

        if (b.boardArray[row][column] == -1) {
            //System.out.println("You missed.");
            b.boardArray[row][column] = 0;
            return 1;
        } else if (b.boardArray[row][column] >= 2) {
            //System.out.println("You hit a ship!");
            int hit = b.boardArray[row][column];
            for (int i = 0; i < b.listOfShips.length; i++) {
                if (hit == b.listOfShips[i].ID) {
                    b.listOfShips[i].timesHit++;
                    if (b.listOfShips[i].checkFloat() == false) {
                        //System.out.println("You sunk your opponents " + b.listOfShips[i].name);
                        b.numOfShipsOnBoard--;
                        b.boardArray[row][column] = 1;
                        return 3;
                    } else {
                        break;
                    }
                }
            }
            b.boardArray[row][column] = 1;
            return 2;
        } else if (b.boardArray[row][column] == 0 || b.boardArray[row][column] == 1) {
            //System.out.println("You already shot there. Try again somewhere else.");
            return 0;
        } else {
            //System.out.println("Something has gone wrong, that cell is probably incorrectly numbered.");
            return 0;
        }
    }

    /*
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Number of players (1 or 2): ");
        int numPlayers = input.nextInt();

        //System.out.print("Number of rows: ");
        //int rows = input.nextInt();
        //System.out.print("Number of columns: ");
        //int columns = input.nextInt();


        if(numPlayers != 1 && numPlayers != 2) {
            System.out.println("That is an invalid number of players. Single player has been selected.");
            numPlayers = 1;
        }

        int rows = 10;
        int columns = 10;

        //com.example.nick.battleship.Ship(size, ID, name)
        Ship Destroyer  = new Ship(2, 2, "Destroyer");
        Ship Submarine  = new Ship(3, 3, "Submarine");
        Ship Cruiser    = new Ship(3, 4, "Cruiser");
        Ship Battleship = new Ship(4, 5, "com.example.nick.battleship.Battleship");
        Ship Carrier    = new Ship(5, 6, "Carrier");


        Board P1 = new Board(rows, columns);
        Board P2 = new Board(rows, columns);

        if(numPlayers == 1) //single player. AI is random shot with no intelligence{
            //Have player 1 place ships
            //placeShip(com.example.nick.battleship.Ship, board, row, column, orientation(0=down, 1=right)
            placeShip(Destroyer, P1, 0, 2, 0);
            placeShip(Submarine, P1, 2, 1, 1);
            placeShip(Cruiser, P1, 1, 7, 0);
            placeShip(Battleship, P1, 9, 3, 1);
            placeShip(Carrier, P1, 4, 0, 1);


            do {
                int randomRow = ThreadLocalRandom.current().nextInt(0, rows);
                int randomCol = ThreadLocalRandom.current().nextInt(0, columns);
                int randomOri = ThreadLocalRandom.current().nextInt(0, 2);

                if(P2.numOfShipsOnBoard == 0)
                    placeShip(Destroyer, P2, randomRow, randomCol, randomOri);
                if(P2.numOfShipsOnBoard == 1)
                    placeShip(Submarine, P2, randomRow, randomCol, randomOri);
                if(P2.numOfShipsOnBoard == 2)
                    placeShip(Cruiser, P2, randomRow, randomCol, randomOri);
                if(P2.numOfShipsOnBoard == 3)
                    placeShip(Battleship, P2, randomRow, randomCol, randomOri);
                if(P2.numOfShipsOnBoard == 4)
                    placeShip(Carrier, P2, randomRow, randomCol, randomOri);

            } while(P2.numOfShipsOnBoard != P1.numOfShipsOnBoard);

            //System.out.println(Arrays.deepToString(P2.boardArray).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

            do {
                System.out.println("P1 turn.");
                System.out.println(Arrays.deepToString(P2.boardArray).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

                Scanner shot2 = new Scanner(System.in);
                System.out.print("Row to shoot at: ");
                int rs2 = shot2.nextInt();
                System.out.print("Column to shoot at: ");
                int cs2 = shot2.nextInt();
                shoot(P2, rs2, cs2);

                //AI turn
                System.out.println("AI's turn.");
                int randomRow = ThreadLocalRandom.current().nextInt(0, rows);
                int randomCol = ThreadLocalRandom.current().nextInt(0, columns);
                shoot(P1, randomRow, randomCol);

            } while(P1.numOfShipsOnBoard != 0 && P2.numOfShipsOnBoard != 0);
            System.out.println("You have sunk all of your opponents ships.");
            if(P1.numOfShipsOnBoard == 0)
                System.out.println("AI wins!");
            if(P2.numOfShipsOnBoard == 0)
                System.out.println("You win!");
        }

        if(numPlayers == 2) {
            //placeShip(com.example.nick.battleship.Ship, board, row, column, orientation(0=down, 1=right)
            placeShip(Destroyer, P1, 0, 2, 0);
            placeShip(Submarine, P1, 2, 1, 1);
            placeShip(Cruiser, P1, 1, 7, 0);
            placeShip(Battleship, P1, 9, 3, 1);
            placeShip(Carrier, P1, 4, 0, 1);

            placeShip(Destroyer, P2, 0, 0, 0);
            placeShip(Submarine, P2, 0, 1, 0);
            placeShip(Cruiser, P2, 0, 2, 0);
            placeShip(Battleship, P2, 0, 3, 0);
            placeShip(Carrier, P2, 0, 4, 0);



            //GUI placement of ships?
            //manually having player type in coordinates takes a long time
            //drag and drop or something?
            //or just click img of ship off to side, then click a cell on the board to place it there
            //placeShip() should still take care of errors with that way.


            //2 player game play after ships have been placed
            do {
                System.out.println("P2 turn.");
                System.out.println(Arrays.deepToString(P1.boardArray).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

                Scanner shot = new Scanner(System.in);
                System.out.print("Row to shoot at: ");
                int rs = shot.nextInt();
                System.out.print("Column to shoot at: ");
                int cs = shot.nextInt();
                shoot(P1, rs, cs);


                System.out.println("P1 turn.");
                System.out.println(Arrays.deepToString(P2.boardArray).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

                Scanner shot2 = new Scanner(System.in);
                System.out.print("Row to shoot at: ");
                int rs2 = shot2.nextInt();
                System.out.print("Column to shoot at: ");
                int cs2 = shot2.nextInt();
                shoot(P2, rs2, cs2);

            } while(P1.numOfShipsOnBoard != 0 && P2.numOfShipsOnBoard != 0);
            System.out.println("You have sunk all of your opponents ships.");
            if(P1.numOfShipsOnBoard == 0)
                System.out.println("Player 2 wins!");
            if(P2.numOfShipsOnBoard == 0)
                System.out.println("Player 1 wins!");
        }//end of 2 player game
        //-1=nothing, 0=miss, 1=hit, 2>=ship
    }*/
}
