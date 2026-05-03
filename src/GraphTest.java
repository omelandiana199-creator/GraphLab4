import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void testTask1_HasCycle() {
        // Граф із Завдання 1: 0–1, 1–2, 2–3, 3–0, 2–4
        Graph graphWithCycle = new Graph();
        graphWithCycle.addEdge(0, 1);
        graphWithCycle.addEdge(1, 2);
        graphWithCycle.addEdge(2, 3);
        graphWithCycle.addEdge(3, 0); // Тут замикається цикл (0-1-2-3-0)
        graphWithCycle.addEdge(2, 4);

        assertTrue(graphWithCycle.hasCycle(), "Граф повинен містити цикл");

        // Дерево
        Graph treeGraph = new Graph();
        treeGraph.addEdge(0, 1);
        treeGraph.addEdge(0, 2);
        treeGraph.addEdge(1, 3);

        assertFalse(treeGraph.hasCycle(), "Дерево не повинно містити циклів");
    }

    @Test
    void testTask2_CityMap() {
        // Граф міста із Завдання 2 (Райони 0-7)
        Graph city = new Graph();
        city.addEdge(0, 1);
        city.addEdge(0, 3);
        city.addEdge(1, 2);
        city.addEdge(1, 4);
        city.addEdge(2, 5);
        city.addEdge(3, 4);
        city.addEdge(4, 6);
        city.addEdge(5, 7);
        city.addEdge(6, 7);

        // 5.2: Найкоротший шлях
        List<Integer> shortestPath = city.shortestPathBfs(0, 7);
        assertFalse(shortestPath.isEmpty());
        assertEquals(7, shortestPath.get(shortestPath.size() - 1), "Кінцева вершина має бути 7");
        assertTrue(shortestPath.size() == 5, "Мінімальна кількість доріг має складати 4 (5 вершин у шляху)");

        // 5.3: BFS-рівні (відстані)
        Map<Integer, Integer> distances = city.bfsDistances(0);
        assertEquals(0, distances.get(0));
        assertEquals(1, distances.get(1));
        assertEquals(2, distances.get(2)); // Шлях 0-1-2
        assertEquals(1, distances.get(3)); // Шлях 0-3
        assertEquals(2, distances.get(4)); // Шлях 0-1-4 або 0-3-4
        assertEquals(3, distances.get(5)); // Шлях 0-1-2-5
        assertEquals(3, distances.get(6)); // Шлях 0-1-4-6 або 0-3-4-6
        assertEquals(4, distances.get(7)); // Найкоротша відстань до 7 — це 4 ребра

        // 5.4: DFS-шлях
        List<Integer> dfsPath = city.dfsPath(0, 7);
        assertFalse(dfsPath.isEmpty());
        assertEquals(0, dfsPath.get(0), "Шлях має починатися з 0");
        assertEquals(7, dfsPath.get(dfsPath.size() - 1), "Шлях має закінчуватися в 7");
    }

    @Test
    void testTask3_IsBipartite() {
        // Двочастковий граф (квадрат)
        Graph bipartiteGraph = new Graph();
        bipartiteGraph.addEdge(0, 1);
        bipartiteGraph.addEdge(1, 2);
        bipartiteGraph.addEdge(2, 3);
        bipartiteGraph.addEdge(3, 0);

        assertTrue(bipartiteGraph.isBipartite(), "Граф-квадрат є двочастковим");

        // Не двочастковий граф (трикутник)
        Graph nonBipartiteGraph = new Graph();
        nonBipartiteGraph.addEdge(0, 1);
        nonBipartiteGraph.addEdge(1, 2);
        nonBipartiteGraph.addEdge(2, 0); // Непарний цикл робить його недвочастковим

        assertFalse(nonBipartiteGraph.isBipartite(), "Граф-трикутник не є двочастковим");
    }
}