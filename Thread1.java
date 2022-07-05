package os;
import java.util.*;
public class Thread1 implements Runnable{
	process[] arr;
	ArrayList<process> readyQ;
	ArrayList<process> ioQ = new ArrayList<process>();
	public static int time = 0;
	
	public Thread1(process[] arr, ArrayList<process> readyQ) {
		this.arr = arr;
		this.readyQ = readyQ;
	}
	
	public void run(){
		System.out.println("THREAD 1 IS ENTERED!!!!!!!!!!!!!!!!!!!!!!!!!");
		for(int i=0; i<arr.length; i++) {
			
			try {
				Thread.sleep(arr[i].getDelay());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int arrivalTime = 0;
			for(int j=i; j>=0; j--) {
				arrivalTime += arr[j].getDelay();
			}
			arr[i].setArrivalTime(arrivalTime);
			time += arr[i].getDelay();
			readyQ.add(arr[i]);
			System.out.println("process "+arr[i].getPid()+" is added to the ready queue at "+time);
		}
		System.out.println("THREAD 1 HAS FINISHED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	
}
