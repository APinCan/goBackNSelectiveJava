package selectivereject;

import java.util.Random;
import java.util.Vector;

public class Receiver{
	int windowSize=5;
	int frameMaxSize=7;
	Vector<Integer> currentWindow = new Vector<>();
	Vector<Integer> bufferWindow = new Vector<>();
	Sender sender;
	int notReceived=0;
	
	public Receiver() {
		for(int i=0;i<windowSize;i++) {
			currentWindow.add(i);
		}
	}
	
	public void run() {
		Frame frame = new Frame();
		Random random = new Random();
		
		if(random.nextInt(2)==1) {
			setData(frame, "DATA" , "ACK");
			System.out.println("send ACK : "+frame.frameSequence);
		}
		else {
			setData(frame, "DATA", "NAK");
			System.out.println("send NAK : "+frame.frameSequence);
			
			if(currentWindow.firstElement().intValue()==0) {
				currentWindow.add(0, 7);
				currentWindow.remove(currentWindow.lastElement());
			}
			else {
				currentWindow.add(0, currentWindow.firstElement().intValue()-1);
				currentWindow.remove(currentWindow.lastElement());
			}
		}
		bufferWindow.clear();
		
		sender.receive(frame);
	}
	
	public void receive(Frame frame) {
		bufferWindow.add(frame.frameSequence);
		
		currentWindow.remove(0);
		//맨 마지막에 프레임amx보다 크지않은 seq를 넣기
		if(currentWindow.lastElement().intValue()==frameMaxSize) {
			currentWindow.add(0);
		}
		else {
			currentWindow.add(currentWindow.lastElement().intValue()+1);
		}
		
	}
	
	public void setData(Frame frame,  String data, String message) {
		frame.data=data;
		
		if(message.equals("ACK")) {
			frame.frameSequence=currentWindow.firstElement().intValue();
		}
		else {
			frame.frameSequence=bufferWindow.elementAt(1);
		}
		
		frame.message=message;
	}
	
}
