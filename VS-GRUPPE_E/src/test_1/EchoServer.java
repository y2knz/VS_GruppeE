package test_1;

import test_1.AsciiChars;
import test_1.StructuredData;
import test_1.SyslogMessage;
import test_1.StructuredData.Element;
import test_1.StructuredData.Param;
import test_1.SyslogMessage.BinaryMessage;
import test_1.SyslogMessage.Facility;
import test_1.SyslogMessage.Severity;
import test_1.SyslogMessage.TextMessage;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class EchoServer {
  //private static final int PORT = 4713;
  private static final int BUFSIZE = 512;
  private static final int SYSLOG_PORT = 514;
  private static final int DISCOVERY_PORT = 8888;
  private static final int MAX_MESSAGE_LENGTH = 1024;

  public static void main(final String[] args) {
    try /*(DatagramSocket socket = new DatagramSocket(PORT)*/ {
    	
   //Create the two ports required
      DatagramSocket syslogSocket = new DatagramSocket(SYSLOG_PORT);
      DatagramSocket discoverySocket = new DatagramSocket(DISCOVERY_PORT);
        
      DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
      DatagramPacket packetOut = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

      System.out.println("Server gestartet ...");

      while (true) {
    	discoverySocket.receive(packetIn);
    	
    	InetAddress ip = packetIn.getAddress();
        System.out.println(ip);
        
        /*var m1 = new SyslogMessage(//
                Facility.SECURITY1, //
                Severity.CRITICAL, //
                new AsciiChars.L255("mymachine.example.com"), //
                new AsciiChars.L048("su"), //
                new AsciiChars.L128(""), //
                new AsciiChars.L032("ID47"),//
                new StructuredData()//
                        .add(Element.newTimeQuality(true, true))
                        .add(new Element("exampleSDID@32473")
                                .add(new Param("iut", "3"))
                                .add(new Param("eventSource", "Application"))
                                .add(new Param("eventID", "1011")))
                        .add(new Element("examplePriority@32473")
                                .add(new Param("class", "high"))),
                new TextMessage("'su root' failed for lonvick on /dev/pts/8"));*/
        
        
        DatagramPacket syslogPacket = new DatagramPacket(packetIn.getData(), packetIn.getLength());
        syslogSocket.receive(syslogPacket);
        
        System.out.println(
            "Received: " + packetIn.getLength() + " bytes: " + new String(packetIn.getData()));
        packetOut.setData(packetIn.getData());
        packetOut.setLength(packetIn.getLength());
        // mehr Eigenschaften von packetOut setzen...
        discoverySocket.send(packetOut);
      }
    } catch (IOException e) {
      System.err.println(e);
    }
  }
}
