package gobackNselective;

import java.util.Random;
import java.util.Vector;

public class Receiver{
	int windowSize=5;
	int frameMaxSize=7;
	Vector<Integer> currentWindow = new Vector<>();
	Vector<Integer> bufferWindow = new Vector<>();
	Sender sender;
	
	public Receiver() {
		for(int i=0;i<windowSize;i++) {
			currentWindow.add(i);
		}
	}
	
	public void run() {
		//보낼 데이터 설정
		Frame frame = new Frame();
		Random random = new Random();
		
		if(random.nextInt(2)==1) {
			setData(frame, "DATA", "ACK");
			System.out.println("send ACK : "+frame.frameSequence);
			
			//버퍼윈도우의 크기만큼 윈도우 늘리기
			for(int i=0;i<bufferWindow.size();i++) {
			//마지막에 프레임amx보다 크지않은 seq를 넣기
			if(currentWindow.lastElement().intValue()==frameMaxSize) {
				currentWindow.add(0);
			}
			else {
				currentWindow.add(currentWindow.lastElement().intValue()+1);
			}
			
			}
			bufferWindow.clear();
		}
		else {
			setData(frame, "DATA", "NAK");
			int index=bufferWindow.indexOf(frame.frameSequence);

			
			for(int i=index;i<bufferWindow.size();i++) {
				currentWindow.add(i, bufferWindow.elementAt(i));
			}
			bufferWindow.clear();
			

			System.out.println("send NAK : "+frame.frameSequence);

		}
		
		sender.receive(frame);
	}
	
	public void receive(Frame frame) {	
		/*
		 * 버퍼에 추가
		 */
  		bufferWindow.add(frame.frameSequence);
		/*
		 * 윈도우 크기조절
		 */
		currentWindow.remove(0);
//		//맨 마지막에 프레임amx보다 크지않은 seq를 넣기
//		if(currentWindow.lastElement().intValue()==frameMaxSize) {
//			currentWindow.add(0);
//		}
//		else {
//			currentWindow.add(currentWindow.lastElement().intValue()+1);
//		}
		
   	}
	
	public void setData(Frame frame, String data, String message) {
		//data를 넣고
		frame.data=data;
		
		if(message.equals("ACK")) {	
			//현재 윈도우의 맨앞의 값을 sequence넘버에 넣기
	 		frame.frameSequence=currentWindow.firstElement().intValue();
		}
		else {
			//버퍼의 값을 넣기
			frame.frameSequence=bufferWindow.firstElement().intValue();
		}

 		//frame에 같이 보낼 메세지
 		frame.message=message;
	}
}
