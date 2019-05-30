package gobackNselective;

import java.util.Vector;

public class Sender{
	int windowSize=5;
	int frameMaxSize=7;
	Vector<Integer> currentWindow = new Vector<>();
	Vector<Integer> bufferWindow = new Vector<>();
	Receiver receiver;
	int resendIndex=0;
	
	public Sender(){
		for(int i=0;i<windowSize;i++) {
			currentWindow.add(i);
		}
		
	}
	
	public void run() {
			/*
			 * 데이터 넣기
			 */
			Frame frame = new Frame();
			setData(frame, "data");
			
			System.out.println("send Frame : "+frame.frameSequence);
			
			receiver.receive(frame);
			/*
			 * 버퍼에 맨앞 seq저장
			 */
			bufferWindow.add(currentWindow.firstElement().intValue());
			/*
			 * 프레임 윈도우 크기 조절
			 */
			//데이터를 보냈으니 맨앞의 윈도우는 줄이고
			currentWindow.remove(0);
//			//맨 마지막에 프레임amx보다 크지않은 seq를 넣기
//			if(currentWindow.lastElement().intValue()==frameMaxSize) {
//				currentWindow.add(0);
//			}
//			else {
//				currentWindow.add(currentWindow.lastElement().intValue()+1);
//			}
//	}
	}
	
	public void receive(Frame frame) {
		String message = frame.message;
		
		//sender는 ack를 받아야 윈도우를 늘림
		if(message.equals("ACK")) {			
			for(int i=0; i<bufferWindow.size();i++) {
				//맨 마지막에 프레임amx보다 크지않은 seq를 넣기
				if(currentWindow.lastElement().intValue()==frameMaxSize) {
					currentWindow.add(0);
				}
				else { 
					currentWindow.add(currentWindow.lastElement().intValue()+1);
				}
			
			}
			bufferWindow.clear();
		}	
		//NAK를 받는경우
		else {
			resendIndex=frame.frameSequence;
			
			//frame.frameSequence에서부터 다시 시작해야됨
			int index=bufferWindow.indexOf(resendIndex);
			
			for(int i =index ;  i<bufferWindow.size();i++) {
				currentWindow.add(i, bufferWindow.elementAt(i));
			}
			
//			for(int i=bufferWindow.indexOf(resendIndex);i<bufferWindow.size();i++) {
//				currentWindow.add(i, bufferWindow.firstElement());
//			}
			
			bufferWindow.clear();
		}
	}

	public void setData(Frame frame, String data) {
		//data를 넣고
		frame.data=data;
			//현재 윈도우의 맨앞의 값을 sequence넘버에 넣기
	 	frame.frameSequence=currentWindow.firstElement().intValue();

	}
}
