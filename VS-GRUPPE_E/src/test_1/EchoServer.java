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

class EchoServer {
  private static final int PORT = 4713;
  private static final int BUFSIZE = 512;

  public static void main(final String[] args) {
    try (DatagramSocket socket = new DatagramSocket(PORT)) {
      DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
      DatagramPacket packetOut = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

      System.out.println("Server gestartet ...");

      while (true) {
        socket.receive(packetIn);
        System.out.println(packetIn.getAddress());
        
        var m1 = new SyslogMessage(//
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
                new TextMessage("'su root' failed for lonvick on /dev/pts/8"));
        
        System.out.println(
            "Received: " + packetIn.getLength() + " bytes: " + new String(packetIn.getData()));
        packetOut.setData(packetIn.getData());
        packetOut.setLength(packetIn.getLength());
        // mehr Eigenschaften von packetOut setzen...
        socket.send(packetOut);
      }
    } catch (IOException e) {
      System.err.println(e);
    }
  }
}
