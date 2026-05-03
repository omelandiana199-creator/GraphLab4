import java.util.*;

public class Graph {
    // Зберігання графу у вигляді списку суміжності
    private final Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(int u, int v) {
        adjacencyList.putIfAbsent(u, new ArrayList<>());
        adjacencyList.putIfAbsent(v, new ArrayList<>());
        adjacencyList.get(u).add(v);
        adjacencyList.get(v).add(u);
    }

     // Виявлення циклу в неорієнтованому графі за допомогою DFS
    public boolean hasCycle() {
        Set<Integer> visited = new HashSet<>();
        for (int vertex : adjacencyList.keySet()) {
            if (!visited.contains(vertex)) {
                if (hasCycleDFS(vertex, visited, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasCycleDFS(int vertex, Set<Integer> visited, int parent) {
        visited.add(vertex);
        for (int neighbor : adjacencyList.getOrDefault(vertex, Collections.emptyList())) {
            if (!visited.contains(neighbor)) {
                if (hasCycleDFS(neighbor, visited, vertex)) {
                    return true;
                }
            } else if (neighbor != parent) {

                return true;
            }
        }
        return false;
    }

    //Найкоротший шлях (BFS)

    public List<Integer> shortestPathBfs(int start, int target) {
        Map<Integer, Integer> predecessors = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == target) {
                return buildPath(predecessors, target);
            }
            for (int neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    predecessors.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Integer> buildPath(Map<Integer, Integer> predecessors, int target) {
        LinkedList<Integer> path = new LinkedList<>();
        Integer step = target;
        while (step != null) {
            path.addFirst(step);
            step = predecessors.get(step);
        }
        return path;
    }

    // Відстані від стартової вершини (BFS-рівні)
    public Map<Integer, Integer> bfsDistances(int startVertex) {
        Map<Integer, Integer> distances = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();

        distances.put(startVertex, 0); // Відстань до самого себе = 0
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            int currentDistance = distances.get(current);

            for (int neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                if (!distances.containsKey(neighbor)) {
                    distances.put(neighbor, currentDistance + 1);
                    queue.add(neighbor);
                }
            }
        }
        return distances;
    }

    // Один можливий шлях DFS
    public List<Integer> dfsPath(int start, int target) {
        List<Integer> path = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        if (dfsPathRecursive(start, target, visited, path)) {
            return path;
        }
        return Collections.emptyList();
    }

    private boolean dfsPathRecursive(int current, int target, Set<Integer> visited, List<Integer> path) {
        visited.add(current);
        path.add(current);

        if (current == target) return true;

        for (int neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
            if (!visited.contains(neighbor)) {
                if (dfsPathRecursive(neighbor, target, visited, path)) {
                    return true;
                }
            }
        }
        // Якщо шлях зайшов у тупик, прибираємо вершину
        path.remove(path.size() - 1);
        return false;
    }

    //Перевірка графу на двочастковість
    public boolean isBipartite() {
        Map<Integer, Integer> colors = new HashMap<>();

        for (int startVertex : adjacencyList.keySet()) {
            if (!colors.containsKey(startVertex)) {
                Queue<Integer> queue = new LinkedList<>();
                queue.add(startVertex);
                colors.put(startVertex, 0); // 'Розфарбовуємо' у колір 0

                while (!queue.isEmpty()) {
                    int current = queue.poll();
                    int currentColor = colors.get(current);
                    int nextColor = 1 - currentColor; // Наступний колір буде 1

                    for (int neighbor : adjacencyList.getOrDefault(current, Collections.emptyList())) {
                        if (!colors.containsKey(neighbor)) {
                            colors.put(neighbor, nextColor);
                            queue.add(neighbor);
                        } else if (colors.get(neighbor) == currentColor) {
                            return false; // Знайдено сусідів однакового кольору
                        }
                    }
                }
            }
        }
        return true;
    }
}