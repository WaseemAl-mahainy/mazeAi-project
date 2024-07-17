package maze;

import java.awt.Point;
import java.util.List;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

public class dfs {
    public static boolean searchPath(char[][] maze, int startX, int startY, List<Point> path) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Left, Right, Up, Down
        Stack<Point> stack = new Stack<>();
        Map<Point, Point> predecessors = new HashMap<>();

        stack.push(new Point(startX, startY));
        visited[startY][startX] = true;
        predecessors.put(new Point(startX, startY), null);

        while (!stack.isEmpty()) {
            Point current = stack.pop();
            int x = current.x;
            int y = current.y;

            if (maze[y][x] == 'E') { // Assuming 'E' is the end point
                reconstructPath(predecessors, current, path);
                return true;
            }

            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows && maze[newY][newX] != '#' && !visited[newY][newX]) {
                    stack.push(new Point(newX, newY));
                    visited[newY][newX] = true;
                    predecessors.put(new Point(newX, newY), current);
                }
            }
        }
        return false;
    }

    private static void reconstructPath(Map<Point, Point> predecessors, Point end, List<Point> path) {
        for (Point at = end; at != null; at = predecessors.get(at)) {
            path.add(at);
        }
        // Reverse the path to start from the beginning
        java.util.Collections.reverse(path);
    }
}
