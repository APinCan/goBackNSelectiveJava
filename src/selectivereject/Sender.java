package selectivereject;

import java.util.Vector;

public class Sender{
	int windowSize=5;
	int frameMaxSize=7;
	Vector<Integer> currentWindow = new Vector<>();
	Vector<Integer> bufferWindow = new Vector<>();
	Receiver receiver;
	int notReceived=-1;
	
	public Sender(){
		for(int i=0;i<windowSize;i++) {
			currentWindow.add(i);
		}
		
	}
	
	public void run() {
		Frame frame = new Frame();
		setData(frame, "DATA");

		if(notReceived==-1) {
			if(currentWindow.lastElement().intValue()==frameMaxSize) {
				currentWindow.add(0);
			}
			else {
				currentWindow.add(currentWindow.lastElement().intValue()+1);
			}
			
			currentWindow.remove(0);
		}
		//뭔가 못받은게 있다면
		else {
			
		}
		receiver.receive(frame);
		
 		notReceived=-1;
		System.out.println("send Frame : "+frame.frameSequence);
	}
	
	public void receive(Frame frame) {
		String message=frame.message;
		
		if(frame.message.equals("ACK")) {
			
		}
		else {
			notReceived=frame.frameSequence;
		}
	}
	
	public void setData(Frame frame, String data) {
		frame.data=data;
		
		if(notReceived==-1) {
			frame.frameSequence=currentWindow.firstElement().intValue();
		}
		//뭔가 못받은게 있다면
		else {
			frame.frameSequence=notReceived;
		}
	}
}
