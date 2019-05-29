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
		//�����ӿ� ������� �����Ϳ� ������ȣ ����
		setDataFrame(frame);
		
		//���� �����͸� ���ۿ� ��� ����
		addBufferWindow();
		//�����͸� 5�ʰ� ����
		send.start();
		//������ ������ receiver�� �� �޾Ҵٴ� ��ȣ ����
		Receiver receiver = new Receiver();
		String reuslt=receiver.receive(frame);
		checkACKNAK(reuslt);
	}
	
	public void checkACKNAK(String result) {
		if(result.contains("ACK")) {
			
		}
	}
	
	public void addBufferWindow() {
		//��ó���� ������ȣ�� �����ϰ� ���ۿ� �߰�
		int addBufferWindow = currentWindow.firstElement().intValue();
		currentWindow.remove(0);
		bufferWindow.add(addBufferWindow);
	}
	
	public void setDataFrame(Frame frame) {
		//���� �����͸� ����
		//���⼭�� 1,2,3,4.....10,11,12 ���� �����͸� ����
		frame.data = ""+sendingdata;
		sendingdata++;
		//�������� ������ȣ�� ����, ���� �������� �� �տ� �ִ� ������ȣ
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