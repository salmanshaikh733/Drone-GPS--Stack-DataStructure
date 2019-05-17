/**
 * @author Mshaik52 Student Number 250959996
 * This java class computes the path for the program and has 2 private and one main method
 * this program makes use of the Stack ADT.
 * To find the correct path to the customer from the UWO store.
 * @version 1.0
 *
 */

//import javafx.scene.control.Cell;

import java.io.IOException;

public class ComputePath {

    //intance variable to refrence and define the map
    Map cityMap;

    //create class to compute path
    public ComputePath(String filename) {
/**
 * @exception this try catch statement catches a IO ERROR
 */
        try {
            cityMap = new Map(filename);
        } catch (IOException e) {
            System.out.println("IO EXCEPTION, File not FOUND!!!!" );
        }
    }

    /**
     *
     * @param cell
     * @return boolean
     */
    //cell where drone currently is and will return true if any cells next to it are tower cells
    private boolean interference(MapCell cell) {

        int i = 0;
        boolean flag = false;

        while (flag == false && i < 6) {

            if (cell.getNeighbour(i) == null) {
                i++;
            } else if (cell.getNeighbour(i).isTower() == true) {
                flag = true;
                break;
            } else {
                flag = false;
                i++;
            }

        }

        return flag;
    }

    /**
     *
     * @param cell
     * @return MapCell
     */
    //method returns the next best cell for the drone to move to
    private MapCell nextCell(MapCell cell) {

        int i = 0;
        boolean flag = false;
        MapCell bestCell = null;
        MapCell listOfCells[] = new MapCell[6];
        //try to find the cells thief, free, highAlt, and customer and put them in a list
        try {
            //find highAltitude, thief and free cell. and store them in a list
            while (i < 6 && bestCell == null) {

                if (cell.getNeighbour(i) == null || cell.getNeighbour(i).isMarked()) {
                    i++;
                } else if (cell.getNeighbour(i).isFree()) {
                    listOfCells[i] = cell.getNeighbour(i);
                    i++;

                } else if (cell.getNeighbour(i).isHighAltitude()) {
                    listOfCells[i] = cell.getNeighbour(i);
                    i++;

                } else if (cell.getNeighbour(i).isThief()) {
                    listOfCells[i] = cell.getNeighbour(i);
                    i++;

                } else if (cell.getNeighbour(i).isCustomer()) {
                    listOfCells[i] = cell.getNeighbour(i);
                    break;

                } else {
                    i++;
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array Out of Bounds Exception " + i + " " + listOfCells[listOfCells.length - 1]);
        }

        //traverse the list of cells and find the best cell
        boolean found = false;
        //search through the list and find best cell
        if (found == false) {
            for (int a = 0; a < listOfCells.length; a++) {
                if (listOfCells[a] == null) {
                    continue;
                } else if (listOfCells[a].isCustomer()) {
                    bestCell = listOfCells[a];
                    found = true;

                }

            }

        }

        if (found == false) {
            for (int a = 0; a < listOfCells.length; a++) {
                if (listOfCells[a] == null) {
                    continue;
                } else if (listOfCells[a].isFree()) {
                    bestCell = listOfCells[a];
                    found = true;
                    break;

                }
            }
        }
        if (found == false) {
            for (int a = 0; a < listOfCells.length; a++) {
                if (listOfCells[a] == null) {
                    continue;
                } else if (listOfCells[a].isHighAltitude()) {
                    bestCell = listOfCells[a];
                    found = true;
                    break;
                }
            }

        }
        if (found == false) {
            for (int a = 0; a < listOfCells.length; a++) {
                if (listOfCells[a] == null) {
                    continue;
                } else if (listOfCells[a].isThief()) {
                    bestCell = listOfCells[a];
                    found = true;
                    break;
                }

            }
        }


        return bestCell;
    }

    /**
     *
     * @param args
     */

    //main method to compute the path
    public static void main(String[] args) {
        //store args[0]
        String inputFileName = args[0];
        ComputePath newPath = new ComputePath(inputFileName);
        //define a new stack
        MyStack<MapCell> newStack = new MyStack<MapCell>();

        //get initial value
        MapCell initialCell = newPath.cityMap.getStart();

        //push the initial cell into the stack
        newStack.push(initialCell);
        //mark the current cell as initial
        initialCell.isInitial();

        MapCell currentCell = initialCell;
        //while the stack is not empty and destination has not been reached do the following
        try {
            while (newStack.isEmpty() == false || newStack.peek().isCustomer() == false) {

                //get top of cell
                currentCell = newStack.peek();
                //if customer destination has been reached
                try {
                    if (currentCell.isCustomer()) {
                        System.out.println("Destination Found, Package Delivered");
                        break;

                    }
                    //if cell is next to is a tower backtrack and mark
                    if (newPath.interference(currentCell) == true) {

                        MapCell poppedCell = newStack.pop();
                        poppedCell.markOutStack();
                    }
                    //if reaches a dead end backtrack and mark
                    if (newPath.nextCell(newStack.peek()) == null) {
                        MapCell poppedCell = newStack.pop();
                        poppedCell.markOutStack();
                    }
                    //otherwise find the nextBest cell
                    else {
                        MapCell nextCell = newPath.nextCell(newStack.peek());
                        newStack.push(nextCell);
                        nextCell.markInStack();

                    }
                    //catch a null pointer exception, because destination not found
                } catch (NullPointerException e) {
                    System.out.println("Destination not found");
                    System.exit(0);

                }

            }
            //exception thrown because there is no viable path to destination
        } catch (EmptyStackException e) {
            System.out.println("Empty Stack Exception, no path!");
            System.exit(0);
        }
    }
}
