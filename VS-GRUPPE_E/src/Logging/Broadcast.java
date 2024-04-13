package Logging;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Broadcast {
	


	    private static final int PORT = 8888; // Port, an den die Broadcast-Nachricht gesendet wird

	    public static void main(String[] args) {
	        sendBroadcast("Hallo Netzwerk!");
	    }

	    public static void sendBroadcast(String message) {
	        try (DatagramSocket socket = new DatagramSocket()) {
	            socket.setBroadcast(true);  // Ermöglicht das Senden von Broadcast-Nachrichten
	            byte[] buffer = message.getBytes();

	            // Erstelle ein DatagramPacket für die Nachricht
	            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), PORT);

	            // Sende das Packet
	            socket.send(packet);
	            System.out.println("Broadcast-Nachricht gesendet: " + message);
	        } catch (SocketException e) {
	            System.err.println("SocketException: " + e.getMessage());
	        } catch (IOException e) {
	            System.err.println("IOException: " + e.getMessage());
	        }
	    
	}
}


