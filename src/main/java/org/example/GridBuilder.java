package org.example;

import java.util.Arrays;
import java.util.List;

public class GridBuilder {

    public static char[][] buildGrid(List<DecodingSecret> secrets) {

        int maxX = 0;
        int maxY = 0;

        for (DecodingSecret secret : secrets) {

            if (secret.x > maxX) {
                maxX = secret.x;
            }

            if (secret.y > maxY) {
                maxY = secret.y;
            }
        }

        int width = maxX + 1;
        int height = maxY + 1;

        char[][] grid = new char[height][width];

        for (char[] row : grid) {
            Arrays.fill(row, ' ');
        }

        for (DecodingSecret secret : secrets) {
            grid[secret.y][secret.x] = secret.character.charAt(0);
        }

        return grid;
    }

    public static void printGrid(char[][] grid) {

        for (int y = grid.length - 1; y >= 0; y--) {
            System.out.println(new String(grid[y]));
        }
    }
}
