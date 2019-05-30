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
		//���� ������ ����
		Frame frame = new Frame();
		Random random = new Random();
		
		if(random.nextInt(2)==1) {
			setData(frame, "DATA", "ACK");
			System.out.println("send ACK : "+frame.frameSequence);
			
			//������������ ũ�⸸ŭ ������ �ø���
			for(int i=0;i<bufferWindow.size();i++) {
			//�������� ������amx���� ũ������ seq�� �ֱ�
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
		 * ���ۿ� �߰�
		 */
  		bufferWindow.add(frame.frameSequence);
		/*
		 * ������ ũ������
		 */
		currentWindow.remove(0);
//		//�� �������� ������amx���� ũ������ seq�� �ֱ�
//		if(currentWindow.lastElement().intValue()==frameMaxSize) {
//			currentWindow.add(0);
//		}
//		else {
//			currentWindow.add(currentWindow.lastElement().intValue()+1);
//		}
		
   	}
	
	public void setData(Frame frame, String data, String message) {
		//data�� �ְ�
		frame.data=data;
		
		if(message.equals("ACK")) {	
			//���� �������� �Ǿ��� ���� sequence�ѹ��� �ֱ�
	 		frame.frameSequence=currentWindow.firstElement().intValue();
		}
		else {
			//������ ���� �ֱ�
			frame.frameSequence=bufferWindow.firstElement().intValue();
		}

 		//frame�� ���� ���� �޼���
 		frame.message=message;
	}
}
