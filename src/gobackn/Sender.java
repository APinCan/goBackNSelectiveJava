package gobackn;

import java.util.Vector;

public class Sender {
	int windowSize=5;
	int frameMaxSize=7;
	Vector<Integer> currentWindow = new Vector<>();
	Vector<Integer> bufferWindow = new Vector<>();
	
	public Sender(){
		for(int i=0;i<windowSize;i++) {
			currentWindow.add(i);
		}
	}
	
	public void send() {
		Frame frame = new Frame();
		setData(frame, "data");
		
// 		System.out.print("Sender | seq : "+currentWindow.firstElement().intValue()+" send --> ");
		System.out.println("Sender   | seq : "+currentWindow.firstElement().intValue()+" send --> Receiver");

       		Receiver receiver = new Receiver();
   		String result=receiver.receive(frame);
 		if(result.contains("ACK")) {
			currentWindow.remove(0);
			
//			int nextsequence = frame.frameSequence+1;
//			if(nextsequence > frameMaxSize) {
//				nextsequence=0;
//			}
			
//			System.out.println("ACK "+nextsequence);
			System.out.println("ACK "+frame.frameSequence);
			//제대로 받았다면 버퍼에서 보낸것 제거
			bufferWindow.remove(0);
			//현재 윈도우를 한칸늘림
//  			currentWindow.add(currentWindow.lastElement().intValue()+1);
			if(currentWindow.lastElement().intValue()==7) {
				currentWindow.add(0);
			}
			else {
				currentWindow.add(currentWindow.lastElement().intValue()+1);
			}
			
 			System.out.println("------------------------------------------------------");
 		}
		else {
			System.out.println("NAK "+frame.frameSequence + " | resend");
			//그전의 seq를 재전송해얗암
			
		}
 	}
	
	public void setData(Frame frame, String data) {
		frame.data=data;
		//현재 윈도우의 맨앞의 값을 sequence넘버에 넣기
 		frame.frameSequence=currentWindow.firstElement().intValue();
		//버퍼에 맨앞 시퀀스를 넣고
		bufferWindow.add(currentWindow.firstElement());
	}
}
