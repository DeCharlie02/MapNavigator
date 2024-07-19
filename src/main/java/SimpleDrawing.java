import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class SimpleDrawing extends JPanel {
    private GeoNetworkManager networkManager;
    private Image mapImage;
    private JButton refreshButton;

    public SimpleDrawing(GeoNetworkManager networkManager) {
        this.networkManager = networkManager;
        this.mapImage = new ImageIcon("map.png").getImage(); // Ensure map.png is in the classpath

        // Add a refresh button
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        this.add(refreshButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImage, 0, 0, this.getWidth(), this.getHeight(), this);
        drawNetwork(g);
    }

    private void drawNetwork(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);

        for (Map.Entry<GeoCoordinate, List<GeoCoordinate>> entry : networkManager.getNetwork().entrySet()) {
            GeoCoordinate node = entry.getKey();
            int x = (int) (node.getLongitude() * this.getWidth() / 360 + this.getWidth() / 2);
            int y = (int) (-node.getLatitude() * this.getHeight() / 180 + this.getHeight() / 2);

            for (GeoCoordinate neighbor : entry.getValue()) {
                int nx = (int) (neighbor.getLongitude() * this.getWidth() / 360 + this.getWidth() / 2);
                int ny = (int) (-neighbor.getLatitude() * this.getHeight() / 180 + this.getHeight() / 2);
                g2d.drawLine(x, y, nx, ny);
            }
        }
    }
}
