package gobackn;

import java.util.Random;
import java.util.Vector;

public class Receiver {
	int windowSize=5;
	int frameMaxSize=7;
	Vector<Integer> currentWindow = new Vector<>();
	Vector<Integer> bufferWindow = new Vector<>();
	
	public Receiver() {
		for(int i=0;i<windowSize;i++) {
			currentWindow.add(i);
		}
	}
	
	public String receive(Frame frame) {
//		SendThread send = new SendThread();
		
//		send.start();
		Random random = new Random();
		String result;
		
		if(random.nextInt(2)==1) {
			System.out.print("Receiver | seq : "+frame.frameSequence);
			
			frame.frameSequence++;
			if(frame.frameSequence>frameMaxSize) {
				frame.frameSequence=0;
			}
			
			result = "ACK "+frame.frameSequence;
			currentWindow.remove(0);
			if(currentWindow.lastElement().intValue()==7) {
				currentWindow.add(0);
			}
			else {
				currentWindow.add(currentWindow.lastElement().intValue()+1);
			}
			
//			System.out.print("Receiver | seq : "+(frame.frameSequence-1)+" send --> ");
			System.out.print(" send --> ");
		}
		else {
			result = "NAK "+frame.frameSequence;
			System.out.print("Receiver | seq : "+frame.frameSequence+" send --> ");
		}
		
		return result;
		
	}
}
