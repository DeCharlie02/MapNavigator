import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GeoNetworkManager networkManager = new GeoNetworkManager();

        while (true) {
            System.out.println("1. Add Connection");
            System.out.println("2. Find Shortest Route");
            System.out.println("3. Print Network");
            System.out.println("4. Visualize Network");
            System.out.println("5. Save Network");
            System.out.println("6. Load Network");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter latitude for first coordinate: ");
                        double lat1 = scanner.nextDouble();
                        System.out.print("Enter longitude for first coordinate: ");
                        double lon1 = scanner.nextDouble();
                        GeoCoordinate coord1 = new GeoCoordinate(lat1, lon1);

                        System.out.print("Enter latitude for second coordinate: ");
                        double lat2 = scanner.nextDouble();
                        System.out.print("Enter longitude for second coordinate: ");
                        double lon2 = scanner.nextDouble();
                        GeoCoordinate coord2 = new GeoCoordinate(lat2, lon2);

                        networkManager.addConnection(coord1, coord2);
                        break;

                    case 2:
                        System.out.print("Enter latitude for start coordinate: ");
                        double startLat = scanner.nextDouble();
                        System.out.print("Enter longitude for start coordinate: ");
                        double startLon = scanner.nextDouble();
                        GeoCoordinate startCoord = new GeoCoordinate(startLat, startLon);

                        System.out.print("Enter latitude for end coordinate: ");
                        double endLat = scanner.nextDouble();
                        System.out.print("Enter longitude for end coordinate: ");
                        double endLon = scanner.nextDouble();
                        GeoCoordinate endCoord = new GeoCoordinate(endLat, endLon);

                        List<GeoCoordinate> route = networkManager.findShortestRoute(startCoord, endCoord);
                        System.out.println("Shortest Route: " + route);
                        break;

                    case 3:
                        networkManager.printNetwork();
                        break;

                    case 4:
                        visualizeNetwork(networkManager);
                        break;

                    case 5:
                        System.out.print("Enter file name to save network: ");
                        String saveFileName = scanner.nextLine();
                        networkManager.saveNetwork(saveFileName);
                        break;

                    case 6:
                        System.out.print("Enter file name to load network: ");
                        String loadFileName = scanner.nextLine();
                        networkManager.loadNetwork(loadFileName);
                        break;

                    case 7:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void visualizeNetwork(GeoNetworkManager networkManager) {
        JFrame frame = new JFrame("Network Visualization");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        SimpleDrawing drawingPanel = new SimpleDrawing(networkManager);
        frame.add(drawingPanel);

        frame.setVisible(true);
    }
}
