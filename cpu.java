package os;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class cpu{
	public static void main(String[]args) throws InterruptedException{
			File file = new File("C:\\Users\\Twitter Store\\eclipse-workspace\\os\\src\\os\\data.txt");
			FileReader fr;
			BufferedReader br;
			String line;
			int lineNum = 0;
			
			process[] array = new process[100];
			ArrayList<process> readyQ = new ArrayList<process>();
			ArrayList<process> ioQ = new ArrayList<process>();
			int i=0;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null) {
				array[i] = new process();
				String[] arr;
				arr = line.split(" ");
				ArrayList<Integer> cpu = new ArrayList<Integer>();
				ArrayList<Integer> io = new ArrayList<Integer>();
				array[i].setDelay(Integer.parseInt(arr[0]));
				array[i].setPid(Integer.parseInt(arr[1]));
				for(int j=2; j<arr.length; j+=2) {
					cpu.add(Integer.parseInt(arr[j]));
				}
				for(int j=3; j<arr.length; j+=2) {
					io.add(Integer.parseInt(arr[j]));
				}
				array[i].setCpuBurst(cpu);
				array[i].setIoBurst(io);
				
				i++;
				lineNum++;
			}
			fr.close();
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		process[] pros = new process[lineNum];
		for(int k=0; k<lineNum; k++) {
			pros[k] = array[k];
		}
		
		
		System.out.println("Choose what algorithm you want to run!");
		System.out.println("1. Round Robin\n2. Shortest Remaining Time First");
		Scanner in = new Scanner(System.in);
		int input = in.nextInt();
		while(input != 1 && input != 2) {
			System.out.println("Please enter a valid option");
			input = in.nextInt();
		}
		in.close();
		
		Thread t1 = new Thread(new Thread1(pros, readyQ));
		Thread t2 = new Thread(new Thread2_RR(readyQ, ioQ));
		Thread t2_SRTF = new Thread(new Thread2_SRTF(readyQ, ioQ));
		Thread t3 = new Thread(new Thread3(readyQ, ioQ));
		
		t1.start();
		if(input == 1) 
			t2.start();
		else if(input == 2) 
			t2_SRTF.start();
		
		t3.start();
		
		t1.join();
		if(input == 1)
			t2.join();
		else if(input == 2)
			t2_SRTF.join();
		t3.join();
		
		double cpuUtilization;
		if(input == 1)
			cpuUtilization = Thread2_RR.getCpuUtilization();
		else
			cpuUtilization = Thread2_SRTF.getCpuUtilization();
		
		System.out.println("Cpu utilization is: "+cpuUtilization);
		
		int sumResponse = 0;
		for(int h=0; h<pros.length; h++) {
			//System.out.println(pros[h].getResponseTime());
			sumResponse += pros[h].getResponseTime();
		}
		double avgResponse = (double)sumResponse/pros.length;
		System.out.println("Average Response Time is "+avgResponse);
	}
}
