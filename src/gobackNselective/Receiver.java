package gobackNselective;

import java.util.Vector;

public class Receiver {
	int windowSize=5;
	int frameMaxSize=7;
	int sendingdata=0;
	int firstIndex=0;
	int lastIndex=4;
	Vector<Integer> currentWindow = new Vector<>();
	Vector<Integer> bufferWindow = new Vector<>();
	
	public String receive(Frame frame) {
		SendThread send = new SendThread();
		
		send.start();
		
		return null;
		
	}
	
	class SendThread extends Thread{
		@Override
		public void run() {
			for(int i=0;i<5;i++) {
				System.out.println("Sending...");
				try {
					Thread.sleep(1000);
				} catch(Exception e) {
					
				}

			}
		}
	}
}
