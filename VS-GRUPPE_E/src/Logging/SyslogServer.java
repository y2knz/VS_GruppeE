package Logging;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;

public class SyslogServer {

    private static final int SYSLOG_PORT = 514; //Syslog standard port: 514
    private static final int DISCOVERY_PORT = 8888; //activity discovery port: 8888
    private static final int MAX_LENGTH = 1024; //maximale laenge nach rfc 5424

    public static void main(String[] args) {
    	//Startet die beiden threads für discovery und syslog server
        new Thread(SyslogServer::runSyslogServer).start();
        new Thread(SyslogServer::runDiscoveryServer).start();
    }

    private static void runSyslogServer() {
        try (DatagramSocket socket = new DatagramSocket(SYSLOG_PORT)) {
        	System.out.println("Syslog Server started");
            byte[] buffer = new byte[MAX_LENGTH];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received from " + packet.getAddress() + ": " + message);
            }
        } catch (SocketException e) {
            System.err.println("Socket exception in syslog server: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO exception in syslog server: " + e.getMessage());
        }
    }

    private static void runDiscoveryServer() {
        try (DatagramSocket socket = new DatagramSocket(DISCOVERY_PORT)) {
        	System.out.println("Discovery Server started");
            byte[] buffer = new byte[0]; 
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[MAX_LENGTH], MAX_LENGTH);
                socket.receive(packet);
                DatagramPacket response = new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
                socket.send(response);
                System.out.println("Discovery response sent to " + packet.getAddress());
            }
        } catch (SocketException e) {
            System.err.println("Socket exception in discovery server: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO exception in discovery server: " + e.getMessage());
        }
    }
}