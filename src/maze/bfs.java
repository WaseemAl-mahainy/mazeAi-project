package maze;

import java.awt.Point;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class bfs {
    public static boolean searchPath(char[][] maze, int startX, int startY, List<Point> path) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Left, Right, Up, Down
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY));
        visited[startY][startX] = true;

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            int x = current.x;
            int y = current.y;

            if (maze[y][x] == 'E') { // Assuming 'E' is the end point
                path.add(current);
                return true;
            }

            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows && maze[newY][newX] != '#' && !visited[newY][newX]) {
                    queue.add(new Point(newX, newY));
                    visited[newY][newX] = true;
                    path.add(new Point(newX, newY));
                }
            }
        }
        return false;
    }
}
