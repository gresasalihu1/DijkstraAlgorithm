package dijkstra;

import models.Edge;
import models.Graph;
import models.Node;

import java.util.*;

import dijkstra.AlgoritmiDijkstra.NodeComparator;

public class AlgoritmiDijkstra {
    private boolean safe = false;
    private String message = null;

    private Graph graph;

    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distances;

    private PriorityQueue<Node> unvisited;
    private HashSet<Node> visited;

    public class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node node1, Node node2) {
            int distanca = distances.get(node1) - distances.get(node2);
            return distanca;
        }
    };

    public AlgoritmiDijkstra(Graph graph) {
        this.graph = graph;
        predecessors = new HashMap<>();
        distances = new HashMap<>();
        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }

        visited = new HashSet<>();

        safe = evaluate();
    }

    private boolean evaluate() {
        // nese nuk caktohet nyja burimore
        if (graph.getSource() == null) {
            message = "Duhet te caktohet nyja burimore";
            return false;
        }
        // nese nuk caktohet nyja e destinacionit
        if (graph.getDestination() == null) {
            message = "Duhet te caktohet nyja e destinacionit";
            return false;
        }
        // nese mbet ndonje nyje e palidhur
        for (Node node : graph.getNodes()) {
            if (!graph.isNodeReachable(node)) {
                message = "Cdo nyje duhet te jete e lidhur ne graf";
                return false;
            }

        }

        return true;
    }

    public void run() throws IllegalStateException {
        if (!safe) {
            throw new IllegalStateException(message);
        }

        unvisited = new PriorityQueue<>(graph.getNodes().size(), new NodeComparator());

        Node source = graph.getSource();
        distances.put(source, 0);
        visited.add(source);

        for (Edge neighbor : getNeighbors(source)) {
            Node adjacent = getAdjacent(neighbor, source);
            distances.put(adjacent, neighbor.getWeight());
            predecessors.put(adjacent, source);
            unvisited.add(adjacent);
        }
        // perderisa nuk i kemi vizituar te gjitha nyjet
        while (!unvisited.isEmpty()) {
            Node current = unvisited.poll();
            updateDistance(current);
            unvisited.remove(current);
            visited.add(current);

        }
       

        for (Node node : graph.getNodes()) {
            node.setPath(getPath(node));
            updateDistance(node);
        }

        graph.setSolved(true);

    }

    // funksioni qe perdoret per te caktuar distancen e rruges me te shkurte
    private void updateDistance(Node node) {
        int distance = distances.get(node);
        for (Edge neighbor : getNeighbors(node)) {
            Node adjacent = getAdjacent(neighbor, node);
            if (visited.contains(adjacent))
                continue;

            int current_dist = distances.get(adjacent);

            int new_dist = distance + neighbor.getWeight();

            if (new_dist < current_dist) {
                distances.put(adjacent, new_dist);
                predecessors.put(adjacent, node);
                unvisited.add(adjacent);
            }

        }
    }

    // Metoda per me e marr nyjen fqinje nyjes me degen perkatese qe vjen si input
    private Node getAdjacent(Edge edge, Node node) {
        if (edge.getNodeOne() != node && edge.getNodeTwo() != node)
            return null;

        return node == edge.getNodeTwo() ? edge.getNodeOne() : edge.getNodeTwo();
    }

    // Metoda per me i marr deget fqinje te nyjes perkatese qe vjen si input
    private List<Edge> getNeighbors(Node node) {
        List<Edge> neighbors = new ArrayList<>();

        for (Edge edge : graph.getEdges()) {
            if (edge.getNodeOne() == node || edge.getNodeTwo() == node)
                neighbors.add(edge);
        }

        return neighbors;
    }

    // funksioni qe mundeson marrjen e distances se nje nyjeje
    public Integer getDistance(Node node) {
        return distances.get(node);
    }

    // per te mundesuar marrjen e rruges se destinacionit
    public List<Node> getDestinationPath() {
    	System.out.println("Distanca nga  " + graph.getSource() + " ne  " + graph.getDestination() + " eshte:"
                + distances.get(graph.getDestination()))
        return getPath(graph.getDestination());
    }

    // metoda qe mundson marrjen e path te nyjes aktuale
    public List<Node> getPath(Node node) {
        List<Node> path = new ArrayList<>();

        Node current = node;
        path.add(current);
        while (current != graph.getSource()) {
            current = predecessors.get(current);
            path.add(current);

        }
        ;

        return path;
    }

}
