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
			 * ������ �ֱ�
			 */
			Frame frame = new Frame();
			setData(frame, "data");
			
			System.out.println("send Frame : "+frame.frameSequence);
			
			receiver.receive(frame);
			/*
			 * ���ۿ� �Ǿ� seq����
			 */
			bufferWindow.add(currentWindow.firstElement().intValue());
			/*
			 * ������ ������ ũ�� ����
			 */
			//�����͸� �������� �Ǿ��� ������� ���̰�
			currentWindow.remove(0);
//			//�� �������� ������amx���� ũ������ seq�� �ֱ�
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
		
		//sender�� ack�� �޾ƾ� �����츦 �ø�
		if(message.equals("ACK")) {			
			for(int i=0; i<bufferWindow.size();i++) {
				//�� �������� ������amx���� ũ������ seq�� �ֱ�
				if(currentWindow.lastElement().intValue()==frameMaxSize) {
					currentWindow.add(0);
				}
				else { 
					currentWindow.add(currentWindow.lastElement().intValue()+1);
				}
			
			}
			bufferWindow.clear();
		}	
		//NAK�� �޴°��
		else {
			resendIndex=frame.frameSequence;
			
			//frame.frameSequence�������� �ٽ� �����ؾߵ�
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
		//data�� �ְ�
		frame.data=data;
			//���� �������� �Ǿ��� ���� sequence�ѹ��� �ֱ�
	 	frame.frameSequence=currentWindow.firstElement().intValue();

	}
}
