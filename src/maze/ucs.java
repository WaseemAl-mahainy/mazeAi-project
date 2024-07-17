package maze;

import java.awt.Point;
import java.util.*;

public class ucs {
    public static boolean searchPath(char[][] maze, int startX, int startY, List<Point> path) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Left, Right, Up, Down
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        Map<Point, Point> predecessors = new HashMap<>();
        queue.add(new Node(startX, startY, 0));
        visited[startY][startX] = true;
        predecessors.put(new Point(startX, startY), null);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int x = current.getX();
            int y = current.getY();
            int cost = current.getCost();

            if (maze[y][x] == 'E') { // Assuming 'E' is the end point
                reconstructPath(predecessors, new Point(x, y), path);
                return true;
            }

            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows && maze[newY][newX] != '#' && !visited[newY][newX]) {
                    queue.add(new Node(newX, newY, cost + 1));
                    visited[newY][newX] = true;
                    predecessors.put(new Point(newX, newY), new Point(x, y));
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
        Collections.reverse(path);
    }

    private static class Node {
        private int x;
        private int y;
        private int cost;

        public Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getCost() {
            return cost;
        }
    }
}
