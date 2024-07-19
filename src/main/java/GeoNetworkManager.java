import java.io.*;
import java.util.*;

public class GeoNetworkManager {
    private Map<GeoCoordinate, List<GeoCoordinate>> network;

    public GeoNetworkManager() {
        network = new HashMap<GeoCoordinate, List<GeoCoordinate>>();
    }

    public void addConnection(GeoCoordinate coord1, GeoCoordinate coord2) {
        if (!network.containsKey(coord1)) {
            network.put(coord1, new ArrayList<GeoCoordinate>());
        }
        network.get(coord1).add(coord2);

        if (!network.containsKey(coord2)) {
            network.put(coord2, new ArrayList<GeoCoordinate>());
        }
        network.get(coord2).add(coord1);
    }

    public List<GeoCoordinate> findShortestRoute(GeoCoordinate start, GeoCoordinate end) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new Comparator<Node>() {
            public int compare(Node n1, Node n2) {
                return Double.compare(n1.distance, n2.distance);
            }
        });
        Map<GeoCoordinate, Double> distances = new HashMap<>();
        Map<GeoCoordinate, GeoCoordinate> previous = new HashMap<>();
        Set<GeoCoordinate> visited = new HashSet<>();

        // Initialize distances and priority queue
        for (GeoCoordinate node : network.keySet()) {
            distances.put(node, Double.MAX_VALUE);
            previous.put(node, null);
        }
        distances.put(start, 0.0);
        priorityQueue.add(new Node(start, 0.0));

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            GeoCoordinate current = currentNode.coordinate;

            // Check if we reached the end node
            if (current.equals(end)) {
                List<GeoCoordinate> path = new ArrayList<>();
                for (GeoCoordinate at = end; at != null; at = previous.get(at)) {
                    path.add(at);
                }
                Collections.reverse(path);
                return path;
            }

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);

            // Update distances for neighbors
            for (GeoCoordinate neighbor : network.getOrDefault(current, Collections.emptyList())) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                double newDist = distances.get(current) + current.distanceTo(neighbor);
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    priorityQueue.add(new Node(neighbor, newDist));
                }
            }
        }

        return new ArrayList<>(); // Return an empty list if no path is found
    }

    public void printNetwork() {
        for (Map.Entry<GeoCoordinate, List<GeoCoordinate>> entry : network.entrySet()) {
            System.out.println("Node: " + entry.getKey());
            System.out.println("Connections: " + entry.getValue());
        }
    }

    public Map<GeoCoordinate, List<GeoCoordinate>> getNetwork() {
        return network;
    }

    public void saveNetwork(String fileName) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(network);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void loadNetwork(String fileName) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream(fileName));
                network = (Map<GeoCoordinate, List<GeoCoordinate>>) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (ois != null) {
                    try {
                        ois.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    public GeoCoordinate findCentralNode() {
        GeoCoordinate centralNode = null;
        double minAverageDistance = Double.MAX_VALUE;

        for (GeoCoordinate node : network.keySet()) {
            double totalDistance = 0;
            for (GeoCoordinate otherNode : network.keySet()) {
                if (!node.equals(otherNode)) {
                    totalDistance += findShortestRoute(node, otherNode).size();
                }
            }
            double averageDistance = totalDistance / (network.size() - 1);
            if (averageDistance < minAverageDistance) {
                minAverageDistance = averageDistance;
                centralNode = node;
            }
        }
        return centralNode;
    }

    private class Node {
        GeoCoordinate coordinate;
        double distance;

        Node(GeoCoordinate coordinate, double distance) {
            this.coordinate = coordinate;
            this.distance = distance;
        }
    }
}
