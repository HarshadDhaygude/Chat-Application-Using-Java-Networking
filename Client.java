import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.*;
import java.io.*;

public class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Client()
    {
        try{
            System.out.println("Sending request to server");
            socket = new Socket("127.0.0.1",2408);
            System.out.println("Connection Done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }catch (Exception e)
        {

        }
    }

    public void  startReading()
    {
        //thread-read karke deta rahega

        Runnable r1 = ()->{

            System.out.println("reader started..");

            try
            {
                while(true)
                {
                    String msg = br.readLine();
                    if(msg.equals("exits"))
                    {
                        System.out.println("Server terminated the chat");
                        socket.close();
                        break;
                
                    }

                
                    System.out.println("Server : "+msg);
              
                }
            }catch(Exception e)
            {
              e.printStackTrace();
            }
        };

        new Thread(r1).start();
    }

    public void startWriting()
    {
        //thread - data user lega and then send karega client tak
        Runnable r2 = ()->{
            System.out.println("writer started...");
            try
            {
                while(true)
                {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }


    public static void main(String[] args)
    {
       System.out.println("This is Client..."); 
       new Client();
    }
}

