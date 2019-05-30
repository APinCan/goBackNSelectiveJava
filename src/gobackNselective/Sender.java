package gobackNselective;

import java.util.Vector;

public class Sender{
	int windowSize=5;
	int frameMaxSize=7;
	int sendingdata=0;
	int firstIndex=0;
	int lastIndex=4;
	Vector<Integer> currentWindow = new Vector<>();
	Vector<Integer> bufferWindow = new Vector<>();
	
	public Sender(){
		for(int i=0;i<windowSize;i++) {
			currentWindow.add(i);
		}
	}
	
	public void send() {
		SendThread send = new SendThread();
		Frame frame = new Frame();
		//프레임에 집어넣을 데이터와 순서번호 설정
		setDataFrame(frame);
		
		//보낼 데이터를 버퍼에 담고 전송
		addBufferWindow();
		System.out.print("Sender | seq : "+currentWindow.get(0)+" send --> ");
		//데이터를 5초간 전송
		send.start();
		//전송이 끝나면 receiver는 잘 받았다는 신호 전솓
		Receiver receiver = new Receiver();
		String reuslt=receiver.receive(frame);
		String receive=checkACKNAK(frame, reuslt);
		
		System.out.println(receive);
	}
	
	public String checkACKNAK(Frame frame, String result) {
		String reuslt;
		int seq=frame.frameNumber;
		
		//ACK라면
		if(result.contains("ACK")) {
			//다음 프레임을 넘겨라 라고 전달
			result="ACK "+(seq+1);
			bufferWindow.remove(0);
			return result;
		}
		//NAK라면 그전꺼 재전달
		else {
			reuslt="NAK "+seq+" resend";
			return result;
		}
	}
	
	public void addBufferWindow() {
		//맨처음의 순서번호를 삭제하고 버퍼에 추가
		int addBufferWindow = currentWindow.firstElement().intValue();
		currentWindow.remove(0);
		bufferWindow.add(addBufferWindow);
	}
	
	public void setDataFrame(Frame frame) {
		//보낼 데이터를 만들어냄
		//여기서는 1,2,3,4.....10,11,12 등의 데이터를 보냄
		frame.data = ""+sendingdata;
		sendingdata++;
		//프레임의 순서번호를 지정, 현재 윈도우의 맨 앞에 있는 순서번호
		frame.frameNumber=currentWindow.get(currentWindow.get(0));
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
