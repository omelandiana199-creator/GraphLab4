public class Main {
    public static void main(String[] args) {

        Graph<String> myGraph = new Graph<>();

        myGraph.addEdge("A", "B");
        myGraph.addEdge("B", "C");
        myGraph.addEdge("C", "D");
        myGraph.addEdge("D", "A");
        myGraph.addEdge("A", "C");

        myGraph.generateTextFile("ResultGraph.md");

    }
}