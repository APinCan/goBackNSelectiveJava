package gobackn;

public class GoBackN extends Thread {
	Sender sender;
	
	public GoBackN() {
		sender = new Sender();
	}
	
	@Override
	public void run() {
		try{
			while(true) {
				sender.send();
				Thread.sleep(1000);
			}
		} catch(Exception e) {
			System.out.println("Error!");
		}

	}
}
