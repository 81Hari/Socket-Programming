import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class BookStoreClient{
	
	public String recieveFromServer(Socket soc)
	{
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
	
	
	public void printServices()
	{
		System.out.println("\t Services \n");
		System.out.println("1.Search Book");
		System.out.println("2.Display All Books");
		System.out.println("3.Name of the Book that has maximum number of page");
		System.out.println("4.Discount for Book \n");
	}
	
	public void sendToServer(String msg, Socket soc)
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
	
	public static void main(String[] args) {
		
		BookStoreClient cust = new BookStoreClient();
		try{
		
			System.out.println("Client Started \n");
			Socket soc = new Socket("localhost",9080);
			
			cust.printServices();
			
			Scanner input = new Scanner(System.in);
			
			System.out.print("Enter the Service Number: ");
			String ch;
			ch = input.next();
			
			cust.sendToServer(ch,soc);
			String msg;
			System.out.println("");
			switch (ch) {
			case "1":
				System.out.print("Enter Bookid: ");
				String bookId;
				bookId = input.next();
				cust.sendToServer(bookId, soc);
				
				msg = cust.recieveFromServer(soc);
				System.out.println("Book Name: "+msg);
				TimeUnit.SECONDS.sleep(2);
				msg = cust.recieveFromServer(soc);
				System.out.println("Book Price: "+msg);
				TimeUnit.SECONDS.sleep(2);
				msg = cust.recieveFromServer(soc);
				System.out.println("Stock: "+msg);
				TimeUnit.SECONDS.sleep(2);
				msg = cust.recieveFromServer(soc);
				System.out.println("NoOfPages: "+msg);break;
				
			case "2":
				msg = cust.recieveFromServer(soc);
				int n = Integer.parseInt(msg);
				TimeUnit.SECONDS.sleep(2);
				for(int i=0; i<n;i++)
				{	
					msg = cust.recieveFromServer(soc);
					System.out.println("Book Id: "+msg);
					TimeUnit.SECONDS.sleep(2);
					msg = cust.recieveFromServer(soc);
					System.out.println("Book Name: "+msg);
					TimeUnit.SECONDS.sleep(2);
					msg = cust.recieveFromServer(soc);
					System.out.println("Book Price: "+msg);
					TimeUnit.SECONDS.sleep(2);
					msg = cust.recieveFromServer(soc);
					System.out.println("Stock: "+msg);
					TimeUnit.SECONDS.sleep(2);
					msg = cust.recieveFromServer(soc);
					System.out.println("NoOfPages: "+msg);
					TimeUnit.SECONDS.sleep(2);
					System.out.println("");
				}
				break;
			case "3":
				TimeUnit.SECONDS.sleep(2);
				msg = cust.recieveFromServer(soc);
				System.out.println("Book Id: "+msg);
				TimeUnit.SECONDS.sleep(2);
				msg = cust.recieveFromServer(soc);
				System.out.println("Book Name: "+msg);
				TimeUnit.SECONDS.sleep(2);
				msg = cust.recieveFromServer(soc);
				System.out.println("Book Price: "+msg);
				TimeUnit.SECONDS.sleep(2);
				msg = cust.recieveFromServer(soc);
				System.out.println("Stock: "+msg);
				
				break;
				
			case "4":
				System.out.print("Enter Book Id: ");
				String bookId1;
				bookId1 = input.next();
				cust.sendToServer(bookId1, soc);
				msg = cust.recieveFromServer(soc);
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Original Price: "+msg);
				TimeUnit.SECONDS.sleep(2);
				msg = cust.recieveFromServer(soc);
				System.out.println("Offer: "+msg);break;
				
				default:
					System.out.println("Invalid Service Number");
			}
			
			
		}
		catch(Exception e) {
		
			e.printStackTrace();
		}
		
	}
}