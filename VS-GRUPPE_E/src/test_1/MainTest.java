package test_1;
import test_1.SyslogMessage.Facility;
import test_1.SyslogMessage.Message;
import test_1.SyslogMessage.Severity;
import test_1.SyslogMessage.TextMessage;

public class MainTest {
	
	public static void main(String[] args) {
		
		AsciiChars.L255 host = new AsciiChars.L255("localhost");
	    AsciiChars.L048 appName = new AsciiChars.L048("MyApp");
	    AsciiChars.L128 procId = new AsciiChars.L128("12345");
	    AsciiChars.L032 msgId = new AsciiChars.L032("ID123");
	    Message message = new TextMessage("This is a test message");
	    SyslogMessage syslogMessage = new SyslogMessage(Facility.USER, Severity.INFORMATIONAL, host, appName, procId, msgId, null, message);
	    
		
		
		
	}

}
