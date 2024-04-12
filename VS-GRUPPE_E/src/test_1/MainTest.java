package test_1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import test_1.SyslogMessage.Facility;
import test_1.SyslogMessage.Message;
import test_1.SyslogMessage.Severity;
import test_1.SyslogMessage.TextMessage;

public class MainTest {
	
	private static final int SYSLOG_PORT = 514; 
    private static final int MAX_MESSAGE_LENGTH = 1024; 

    public static void sendMessage(String hostname, String appName, String messageText) {
        try {
            DatagramSocket socket = new DatagramSocket();

            AsciiChars.L255 host = new AsciiChars.L255(hostname);
            AsciiChars.L048 appNameChars = new AsciiChars.L048(appName);
            AsciiChars.L128 procId = new AsciiChars.L128("");
            AsciiChars.L032 msgId = new AsciiChars.L032("ID47");
            SyslogMessage.Message message = new SyslogMessage.TextMessage(messageText);
            SyslogMessage syslogMessage = new SyslogMessage(
                    SyslogMessage.Facility.USER,
                    SyslogMessage.Severity.INFORMATIONAL,
                    host,
                    appNameChars,
                    procId,
                    msgId,
                    null,
                    message
            );

            byte[] sendData = syslogMessage.toString().getBytes();

            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(), SYSLOG_PORT);

            socket.send(packet);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendMessage("localhost", "MyApp", "This is a test message from SyslogMessageSender");
        System.out.println("Gestartet");
    }

}
