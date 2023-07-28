package view;

import java.util.Scanner;

import model.GameManager;

public class ConsoleGame {
    public static void main(String[] args) {

        GameManager gm = new GameManager();
        Scanner sc = new Scanner(System.in);
        int row, col, val;
        boolean diffCheck = false;
        while (!gm.isGameOver()) {
            try {
                if (!diffCheck) {
                    System.out.println("Choose difficulty");
                    System.out.print("1 = easy, 2 = normal, 3 = hard:");
                    int difficulty = Integer.parseInt(sc.nextLine());
                    if (difficulty == 0) {
                        gm.setDifficulty(1);
                    } else if (difficulty == 1) {
                        gm.setDifficulty(30);
                    } else if (difficulty == 2) {
                        gm.setDifficulty(40);
                    } else if (difficulty == 3) {
                        gm.setDifficulty(50);
                    } else {
                        System.out.println("Please choose from 1-3");
                        continue;
                    }
                    gm.generateSudoku();
                    gm.printAnswer();
                    diffCheck = true;
                }
                gm.printGameBoard();
                System.out.print("Input:row col val(space in between0 1 2):");
                String[] input = sc.nextLine().strip().split(" ");
                row = Integer.parseInt(input[0]);
                col = Integer.parseInt(input[1]);
                val = Integer.parseInt(input[2]);
                if (!gm.isCellEmpty(row, col)) {
                    System.out.println("This cell is already full!");
                    continue;
                }

                if (gm.makeMove(row, col, val)) {
                    System.out.println("you are right!");
                } else {
                    System.out.println("you are wrong!");
                }
                
            } catch (Exception e) {
                System.out.println("Please enter the right format!");
            }
        }
        System.out.println("you finish the game!");
    }
}
