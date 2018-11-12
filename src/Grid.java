import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Grid {

    Node[][] grid;
    int width;
    int height;
    ArrayList<Character> mazeFlowColors = new ArrayList<>();

    int[] BCords = new int[2];
    int[] ACords = new int[2];
    int[] QCords = new int[2];
    int[] YCords = new int[2];
    int[] GCords = new int[2];
    int[] KCords = new int[2];
    int[] WCords = new int[2];
    int[] RCords = new int[2];
    int[] PCords = new int[2];
    int[] DCords = new int[2];
    int[] OCords = new int[2];
    // allocating memory space for cordinates of color flows

    public Grid(){
        this.grid = grid;
        this.width = width;
        this.height = height;
    }

    public int getStartXPosition(char flow){
        switch(flow){
            case 'B':
                return BCords[0];
            case 'A':
                return ACords[0];
            case 'Q':
                return QCords[0];
            case 'Y':
                return YCords[0];
            case 'G':
                return GCords[0];
            case 'K':
                return KCords[0];
            case 'W':
                return WCords[0];
            case 'R':
                return RCords[0];
            case 'P':
                return PCords[0];
            case 'D':
                return DCords[0];
            case 'O':
                return OCords[0];
            default:
                return -1;
        }
    }

    public int getStartYPosition(char flow){
        switch(flow){
            case 'B':
                return BCords[1];
            case 'A':
                return ACords[1];
            case 'Q':
                return QCords[1];
            case 'Y':
                return YCords[1];
            case 'G':
                return GCords[1];
            case 'K':
                return KCords[1];
            case 'W':
                return WCords[1];
            case 'R':
                return RCords[1];
            case 'P':
                return PCords[1];
            case 'D':
                return DCords[1];
            case 'O':
                return OCords[1];
            default:
                return -1;
        }
    }

    public Node[][] readGrid(String file, int size) throws FileNotFoundException {

        grid = new Node[size][size]; // allocate memory for maze
        width = size;
        height = size;

        Scanner scan = new Scanner(new FileReader(file));
        for (int i = 0; i < width; i++){
            String line = scan.next();

            for (int j = 0; j < height; j++){
                char value = line.charAt(j);
                grid[i][j] = new Node(i, j, value);

                if (grid[i][j].value != '_'){

                    if (!mazeFlowColors.contains(grid[i][j].value)){
                        mazeFlowColors.add(grid[i][j].value);

                        switch(grid[i][j].value){
                            case 'B':
                                BCords[0] = i;
                                BCords[1] = j;
                                break;
                            case 'A':
                                ACords[0] = i;
                                ACords[1] = j;
                                break;
                            case 'Q':
                                QCords[0] = i;
                                QCords[1] = j;
                                break;
                            case 'K':
                                KCords[0] = i;
                                KCords[1] = j;
                                break;
                            case 'P':
                                PCords[0] = i;
                                PCords[1] = j;
                                break;
                            case 'W':
                                WCords[0] = i;
                                WCords[1] = j;
                                break;
                            case 'O':
                                OCords[0] = i;
                                OCords[1] = j;
                                break;
                            case 'Y':
                                YCords[0] = i;
                                YCords[1] = j;
                                break;
                            case 'G':
                                GCords[0] = i;
                                GCords[1] = j;
                                break;
                            case 'D':
                                DCords[0] = i;
                                DCords[1] = j;
                                break;
                            case 'R':
                                RCords[0] = i;
                                RCords[1] = j;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
        return grid;
    }
}
