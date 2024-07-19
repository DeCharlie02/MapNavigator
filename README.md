# MapNavigator

## Project Overview

Map_Navigator is a Java-based application that manages and visualizes a network of geographic coordinates. It includes tools for adding connections, finding the shortest route between points, and visualizing the network on a world map. The core files of this project are `GeoCoordinate.java`, `GeoNetworkManager.java`, `Main.java`, and `SimpleDrawing.java`.

### GeoCoordinate.java

Defines a class representing geographic coordinates with methods for calculating distance, equality, and string representation.

#### Features:
- **Constructor, Getters, and Distance Calculation:** Initializes latitude and longitude, provides getter methods, and calculates distance using the Haversine formula.
- **Override Methods:** `equals` and `hashCode` ensure `GeoCoordinate` objects can be used as keys in maps. `toString` provides a readable string representation.

### GeoNetworkManager.java

Manages a network of geographic coordinates, allowing for adding connections, finding the shortest route, and other utilities.

#### Features:
- **Constructor:** Initializes the network as a `HashMap`.
- **addConnection:** Adds bidirectional connections between two coordinates.
- **findShortestRoute:** Implements Dijkstra's algorithm for finding the shortest path between two coordinates.
- **Utility Methods:** `printNetwork`, `getNetwork`, `saveNetwork`, and `loadNetwork` for debugging, accessing, and serializing the network.
- **findCentralNode:** Calculates the central node by finding the node with the minimum average shortest path distance to all other nodes.

### Main.java

Provides a console interface for interacting with the network manager, including adding connections, finding routes, printing the network, visualizing it, and saving/loading the network.

#### Features:
- **Menu System:** Continuously prompts the user for actions and invokes corresponding methods in `GeoNetworkManager`.
- **Exception Handling:** Uses try-catch blocks to handle potential errors gracefully.
- **visualizeNetwork:** Creates a `JFrame` and adds the `SimpleDrawing` panel for network visualization.

### SimpleDrawing.java

Defines a custom `JPanel` that visualizes the network on top of a background map image.

#### Features:
- **Constructor:** Initializes the network manager and loads a map image. Adds a refresh button to repaint the component.
- **paintComponent:** Overridden to draw the map image and the network.
- **drawNetwork:** Draws lines between connected coordinates, translating geographic coordinates to screen coordinates.

## Example Usage

### Connections:

- New York (40.7, -74.0) ↔️ Sydney (33.86, 151.2)
- Sydney (33.86, 151.2) ↔️ Dubai (25.2, 55.27)
- Dubai (25.2, 55.27) ↔️ Berlin (52.52, 13.4)
- Berlin (52.52, 13.4) ↔️ Tokyo (35.68, 139.69)
- Tokyo (35.68, 139.69) ↔️ Moscow (55.75, 37.61)
- Moscow (55.75, 37.61) ↔️ New York (40.7, -74.0)

### Output Example:

#### Network Visualization:

The following image shows the visual representation of the network with the provided coordinates and connections:

![output](https://github.com/user-attachments/assets/35c3d63f-2c4f-4569-b503-7d6e5febbfd4)

This visualization demonstrates the connections between major cities around the world, providing an intuitive representation of geographic relationships and distances.
