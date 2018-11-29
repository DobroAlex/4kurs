/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author myfamily
 */
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class CalculClient
{
    public BufferedReader in;
    public PrintWriter out;
    public Socket socket;
    public int start, end;
    public ArrayList<Integer> denoms;
    public String serverAdress;
    public int port;
    public int selfNum; 
    public int AmountOfFinded;
    public long startTime;
    public long elapsedTime;
    public javax.swing.JTextArea outputArea;
    public  CalculClient (String serverAdress, int port, javax.swing.JTextArea outputArea)
    {
        this.serverAdress = serverAdress;
        this.port = port;
        this.outputArea = outputArea;
        this.startTime = java.lang.System.currentTimeMillis();
        denoms = new ArrayList<Integer>();
    }
        public void ConnectToServer () throws IOException
    {
        
        this.socket = new Socket(this.serverAdress, this.port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.selfNum = Integer.parseInt(in.readLine());	/*Поглощаем номер клиента с сервера*/
	    
	//System.out.println(this.selfNum+":" + in.readLine());	//"Ваш интревал поиска
        this.start = Integer.parseInt(in.readLine().trim());
        this.outputArea.setText(this.outputArea.getText() +this.selfNum+":" + "С сервера пришло: start = " + this.start + "\n");
        this.end = Integer.parseInt(in.readLine());
        this.outputArea.setText(this.outputArea.getText() +this.selfNum+":" +  "С сервера пришло: end = " + this.end + "\n" );
        int amountOfDenoms = Integer.parseInt(in.readLine());
        //System.out.println("Ожидаем " + amountOfDenoms + " делителей с сервера");
        for (int i = 0; i < amountOfDenoms; i++)
        {
            denoms.add(Integer.parseInt(in.readLine()));
        }
        /*System.out.println("Делители:");
        for (Integer  I : denoms) 
        {
            System.out.println(I);
        }*/
        
    }
    public ArrayList<Integer> findNums()
    {
        ArrayList<Integer> ans = CalcUtils.getSomeSpecificNumbers(this.start, this.end, this.denoms);
        this.AmountOfFinded = ans.size();
        return ans;
    }
    public void sendResponseAndClose()
    {
        this.elapsedTime = (java.lang.System.currentTimeMillis() - this.startTime);
        this.outputArea.setText(this.outputArea.getText() + this.selfNum + ":" + 
                "Нашли " + this.AmountOfFinded + 
                " подходящих чисел на интервале [" + this.start + " ; " + this.end + "]" + 
                "За " + this.elapsedTime + "мс" + "\n");
        this.out.println(this.AmountOfFinded);
        this.out.println(this.elapsedTime);
    }
}
        
    
     
