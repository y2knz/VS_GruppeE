package test_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SyslogServerTest1 {
	private static final int SYSLOG_PORT = 514;
    private static final int DISCOVERY_PORT = 8888;
    private static final int MAX_MESSAGE_LENGTH = 1024; // Max length of syslog message

    public static void main(String[] args) {
        try {
            // Create socket for syslog standard port
            DatagramSocket syslogSocket = new DatagramSocket(SYSLOG_PORT);
            
            // Create socket for discovery port
            DatagramSocket discoverySocket = new DatagramSocket(DISCOVERY_PORT);

            byte[] receiveData = new byte[MAX_MESSAGE_LENGTH];

            System.out.println("Syslog server started...");

            while (true) {
                // Receive message on discovery port
                DatagramPacket discoveryPacket = new DatagramPacket(receiveData, receiveData.length);
                discoverySocket.receive(discoveryPacket);

                // Extract client IP address
                InetAddress clientAddress = discoveryPacket.getAddress();
                int clientPort = discoveryPacket.getPort();

                // Respond to discovery request with server IP address
                String serverAddress = InetAddress.getLocalHost().getHostAddress();
                byte[] responseData = serverAddress.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                discoverySocket.send(responsePacket);

                // Receive syslog message
                DatagramPacket syslogPacket = new DatagramPacket(receiveData, receiveData.length);
                syslogSocket.receive(syslogPacket);

                // Extract syslog message
                String syslogMessage = new String(syslogPacket.getData(), 0, syslogPacket.getLength());

                // Print syslog message along with client IP address
                System.out.println("Received syslog message from " + clientAddress.getHostAddress() + ": " + syslogMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
