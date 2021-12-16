import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.Collections;

public class BookStoreServer{
	
	ArrayList<String> id, name;
	ArrayList<Integer> stock, noOfPages;
	ArrayList<Float> price;
	
	public String recieveFromClient(Socket soc){

		String msg;
		msg = new String();
		try
		{
		InputStreamReader in = new InputStreamReader(soc.getInputStream());
		BufferedReader recieve = new BufferedReader(in);
		
		msg = recieve.readLine();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	return msg;
	}

	public void sendToClient(String msg, Socket soc)
	{
		try {
		PrintWriter send = new PrintWriter(soc.getOutputStream());
		send.println(msg);
		send.flush();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public BookStoreServer readBookDetails(BookStoreServer ser) {
		
		try {
			ser.id = new ArrayList<String>();
			ser.name = new ArrayList<String>();
			ser.price = new ArrayList<Float>();
			ser.stock = new ArrayList<Integer>();
			ser.noOfPages = new ArrayList<Integer>();
			
		FileReader fReader = new FileReader("BookStore.txt");
		BufferedReader bReader = new BufferedReader(fReader);
		String line = bReader.readLine();
		line = bReader.readLine();
		
		while(line!=null)
		{
			
			int j=0;
			int k=0;
			StringBuilder bookId,bookName,bookPrice,bookStock,bookNoOfPages;
			bookId = new StringBuilder();
			bookName = new StringBuilder();
			bookPrice = new StringBuilder();
			bookStock = new StringBuilder();
			bookNoOfPages = new StringBuilder();
			
			while(j<line.length()) {
				if(line.charAt(j)=='-')
				{
					k++;
				}
				else if(k==0)
				{
					bookId = bookId.append(line.charAt(j));
				}
				else if(k==1) {
					bookName = bookName.append(line.charAt(j)); 
				}
				else if(k==2) {
					bookPrice = bookPrice.append(line.charAt(j));
				}
				else if(k==3) {
					bookStock = bookStock.append(line.charAt(j));
				}
				else if(k==4) {
					bookNoOfPages = bookNoOfPages.append(line.charAt(j));
				}
				j++;
					
			}
			ser.id.add(bookId.toString());
			ser.name.add(bookName.toString());
			ser.price.add(Float.parseFloat(bookPrice.toString()));
			ser.stock.add(Integer.parseInt(bookStock.toString()));
			ser.noOfPages.add(Integer.parseInt(bookNoOfPages.toString()));
			line = bReader.readLine();
		}
		}
		catch(Exception e){
		
			e.printStackTrace();
		}
		
	return ser;
		
	}
	
	public static void main(String[] args) {
		BookStoreServer ser = new BookStoreServer();
		try
		{
			System.out.println("\t \t SERVER \n");
			System.out.println("Waiting for Clients..");
			ServerSocket sS = new ServerSocket(9080);
			Socket soc = sS.accept();
			System.out.println("Connection established");
			String msg;
			
			msg = ser.recieveFromClient(soc);
			
			
			
			ser = ser.readBookDetails(ser);
				
			switch(msg)
			{
			case "1":
			int in;
			String sBookId;
			sBookId = ser.recieveFromClient(soc);
			in = ser.id.indexOf(sBookId);
			ser.sendToClient(ser.name.get(in), soc);
			TimeUnit.SECONDS.sleep(2);
			ser.sendToClient(Float.toString(ser.price.get(in)), soc);
			TimeUnit.SECONDS.sleep(2);
			ser.sendToClient(Integer.toString(ser.stock.get(in)), soc);
			TimeUnit.SECONDS.sleep(2);
			ser.sendToClient(Integer.toString(ser.noOfPages.get(in)), soc);break;
			
			case "2":
				int n = ser.id.size();
				ser.sendToClient(Integer.toString(n), soc);
				TimeUnit.SECONDS.sleep(2);
				for(int i =0;i<n;i++)
				{
					ser.sendToClient(ser.id.get(i), soc);
					TimeUnit.SECONDS.sleep(2);
					ser.sendToClient(ser.name.get(i), soc);
					TimeUnit.SECONDS.sleep(2);
					ser.sendToClient(Float.toString(ser.price.get(i)), soc);
					TimeUnit.SECONDS.sleep(2);
					ser.sendToClient(Integer.toString(ser.stock.get(i)), soc);
					TimeUnit.SECONDS.sleep(2);
					ser.sendToClient(Integer.toString(ser.noOfPages.get(i)), soc);
					TimeUnit.SECONDS.sleep(2);
				}
				break;
			case "3":
				int maxpg = Collections.max(ser.noOfPages);
				int in1 = ser.noOfPages.indexOf(maxpg);
				TimeUnit.SECONDS.sleep(2);
				ser.sendToClient(ser.id.get(in1), soc);
				TimeUnit.SECONDS.sleep(2);
				ser.sendToClient(ser.name.get(in1), soc);
				TimeUnit.SECONDS.sleep(2);
				ser.sendToClient(Float.toString(ser.price.get(in1)), soc);
				TimeUnit.SECONDS.sleep(2);
				ser.sendToClient(Integer.toString(ser.stock.get(in1)), soc);
				TimeUnit.SECONDS.sleep(2);
				break;
			case "4":
				int in3;
				String sBookId1;
				sBookId1 = ser.recieveFromClient(soc);
				in3 = ser.id.indexOf(sBookId1);
				float redPrice;
				redPrice = ser.price.get(in3);
				redPrice = (redPrice - redPrice*0.15f);
				TimeUnit.SECONDS.sleep(2);
				ser.sendToClient(Float.toString(ser.price.get(in3)), soc);
				TimeUnit.SECONDS.sleep(2);
				ser.sendToClient(Float.toString(redPrice), soc);break;
				
				
			default:
			}
				
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
}
