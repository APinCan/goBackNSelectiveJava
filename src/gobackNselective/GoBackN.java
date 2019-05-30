package gobackNselective;

public class GoBackN extends Thread {
	Sender sender;
	Receiver receiver;
	
	public GoBackN() {
		sender = new Sender();
		receiver = new Receiver();
		
		sender.receiver=receiver;
		receiver.sender=sender;
	}
	
	@Override
	public void run() {
		int flag=0;
		
		try{
			while(true) {	
      				sender.run();
				if(flag==2) {
 					receiver.run();
					flag=0;
				}
  				flag++;
  				Thread.sleep(500);
			}
		} catch(Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}

	}
}
