import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

public class DumbAlgorithm {

    final private int size;
    final private Node[][] grid;

    private Stack<Character> completeFlows = new Stack<>(); // this is for backtracking
    private Grid gridInstance = new Grid();
    private Random myRandom = new Random();
    private char flowChar;

    public DumbAlgorithm(int size, String file) throws FileNotFoundException {
        Node[][] grid = gridInstance.readGrid(file, size);
        System.out.println("Original maze: ");
        printGrid(size, grid);
        System.out.println("Starting dumb algorithm...");
        this.size = size;
        this.grid = grid;
    }

    private int getHeight(){
        return grid.length;
    }

    private int getWidth(){
        return grid[0].length;
    }

    public void getSolution(){
        // going to pick a random char to start and iterate through until hasCompleteFlow is true for all  flows
        boolean hasCompleteFlow = false;

        while(!hasCompleteFlow) {

            if (isCompleteFlow(grid, size)) {
                hasCompleteFlow = true;
                break;
            }
            int randomFlow = myRandom.nextInt(gridInstance.mazeFlowColors.size()); //picking random flow

            flowChar = gridInstance.mazeFlowColors.get(randomFlow);

            while (completeFlows.contains(flowChar)) {
                randomFlow = myRandom.nextInt(gridInstance.mazeFlowColors.size());
                flowChar = gridInstance.mazeFlowColors.get(randomFlow);
            }

            Character.toUpperCase(flowChar);

            for (int i = 0; i < getHeight(); i++) {
                for (int j = 0; j < getWidth(); j++) {
                    if (grid[i][j].value == flowChar && i == gridInstance.getStartXPosition(flowChar) && j == gridInstance.getStartYPosition(flowChar)) {

                        if (!findCompleteFlows(flowChar, i, j)) {
                            delete(Character.toLowerCase(flowChar));

                            try {
                                flowChar = completeFlows.pop();
                                delete(Character.toLowerCase(flowChar));
                            } catch (EmptyStackException e) {

                            }
                        }
                        i = 10000;
                        j = 10000;
                    }
                }
            }
        }
        System.out.println("Dumb maze solution:");
        printGrid(size, grid);
    }

    private void delete(char flowChar){

        for (int i = 0; i < getHeight(); i++){
            for (int j = 0; j < getWidth(); j++){
                if (grid[i][j].value == flowChar){
                    grid[i][j].value = '_';
                }
            }
        }
    }

    private boolean findCompleteFlows(char flowChar, int startX, int startY) {
        int currentX = startX;
        int currentY = startY;

        char setFlowChar = Character.toLowerCase(flowChar);

        ArrayList<Integer> checkedDirections = new ArrayList<>();
        boolean flowIsFinished = false;

        while(!flowIsFinished){
            int next = myRandom.nextInt(4);

            while (checkedDirections.contains(next)){
                next = myRandom.nextInt(4);
            }
            switch(next){
                case 0: // check up
                    if(isFree(currentX-1, currentY)){
                        currentX -= 1;
                        checkedDirections.clear();
                        grid[currentX][currentY].value = setFlowChar;
                    }
                    else{
                        checkedDirections.add(0);
                    }
                    break;
                case 1: // check down
                    if(isFree(currentX + 1, currentY)){
                        currentX += 1;
                        checkedDirections.clear();
                        grid[currentX][currentY].value = setFlowChar;
                    }
                    else{
                        checkedDirections.add(1);
                    }
                    break;
                case 2: // check right
                    if(isFree(currentX, currentY+1)){
                        currentY += 1;
                        checkedDirections.clear();
                        grid[currentX][currentY].value = setFlowChar;
                    }
                    else{
                        checkedDirections.add(2);
                    }
                    break;
                case 3: //check left
                    if(isFree(currentX, currentY-1)){
                        currentY -= 1;
                        checkedDirections.clear();
                        grid[currentX][currentY].value = setFlowChar;
                    }
                    else{
                        checkedDirections.add(3);
                    }
                    break;
                default:
                    System.out.println("u messed up");
                    break;
            }

            if (isCompleteFlowWithinBounds(flowChar, currentX, currentY)){
                completeFlows.add(flowChar);
                flowIsFinished = true;
                return true;
            }

            if (checkedDirections.size() == 4){
                return false;
            }
        }

        if (flowIsFinished){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isFree(int x, int y){

        if(x >= 0 && x < size && y >= 0 && y < size){
            if(grid[x][y].value == '_'){
                return true;
            }
        }
        return false;
    }

    public boolean isCompleteFlowWithinBounds(char c, int x, int y){
        char goalFlow = Character.toUpperCase(c);

        try {
            if (x - 1 != gridInstance.getStartXPosition(c) && y != gridInstance.getStartYPosition(c) && grid[x-1][y].value == goalFlow){
                return true;
            }
        } catch (IndexOutOfBoundsException e){

        }
        try{
            if (x + 1 != gridInstance.getStartXPosition(c) && y != gridInstance.getStartYPosition(c) && grid[x+1][y].value == goalFlow){
                return true;
            }
        } catch (IndexOutOfBoundsException e){

        }
        try{
            if (x != gridInstance.getStartXPosition(c) && y - 1 != gridInstance.getStartYPosition(c) && grid[x][y-1].value == goalFlow){
                return true;
            }
        } catch (IndexOutOfBoundsException e){

        }
        try{
            if (x != gridInstance.getStartXPosition(c) && y + 1 != gridInstance.getStartYPosition(c) && grid[x][y+1].value == goalFlow){
                return true;
            }
        } catch (IndexOutOfBoundsException e) {

        }
        return false;
    }

    public boolean isCompleteFlow(Node[][] g, int x){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (grid[i][j].value == '_'){
                    return false;
                }
            }
        }
        if (gridInstance.mazeFlowColors.size() != completeFlows.size()){
            return false;
        }

        return true;
    }

    private void printGrid(int x, Node[][] grid){
        for (int i = 0; i < x; i++){
            for (int j = 0; j < x; j++){
                System.out.print(grid[i][j].value);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) throws FileNotFoundException {
        DumbAlgorithm dummy = new DumbAlgorithm(7, "7x7maze.txt");
        long startTime = System.nanoTime();
        dummy.getSolution();
        long duration = (System.nanoTime() - startTime);
        System.out.println("Duration: " + duration + " nano seconds");
    }
}
