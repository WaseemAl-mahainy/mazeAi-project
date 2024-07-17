package maze;

import java.awt.Point;
import java.util.*;

public class greddy {
    public static boolean searchPath(char[][] maze, int startX, int startY, int targetX, int targetY, List<Point> path) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Left, Right, Up, Down
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getH));
        
        Node startNode = new Node(startX, startY, heuristic(startX, startY, targetX, targetY), null);
        queue.add(startNode);
        visited[startY][startX] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int x = current.getX();
            int y = current.getY();

            if (x == targetX && y == targetY) {
                reconstructPath(current, path);
                return true;
            }

            for (int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows && maze[newY][newX] != '#' && !visited[newY][newX]) {
                    Node newNode = new Node(newX, newY, heuristic(newX, newY, targetX, targetY), current);
                    queue.add(newNode);
                    visited[newY][newX] = true;
                }
            }
        }
        return false;
    }

    private static int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2); // Manhattan distance heuristic
    }

    private static void reconstructPath(Node current, List<Point> path) {
        while (current != null) {
            path.add(0, new Point(current.getX(), current.getY()));
            current = current.getParent();
        }
    }

    private static class Node {
        private int x;
        private int y;
        private int h; // Heuristic value
        private Node parent;

        public Node(int x, int y, int h, Node parent) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.parent = parent;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getH() {
            return h;
        }

        public Node getParent() {
            return parent;
        }
    }
}
