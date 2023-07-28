package view;

import java.util.Scanner;

import model.GameManager;

public class consoleGame {
    public static void main(String[] args) {
        GameManager gm = new GameManager();
        Scanner sc = new Scanner(System.in);
        gm.generateSudoku();
        while (! gm.isGameOver()) {
            gm.printAnswer();
            gm.printGameBoard();
            System.out.println("Input:row col val");
            String[] input = sc.nextLine().strip().split(" ");
            
            if (gm.makeMove(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2]))){
                System.out.println("you are right!");
            }else{
                System.out.println("you are wrong!");
            }
        }
        System.out.println("you finish the game!");
    }
}
