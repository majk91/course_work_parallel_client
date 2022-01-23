package com.course_work_paralel_client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        final String serverHost = "localhost";
        Socket socketOfClient = null;
        BufferedWriter os = null;
        BufferedReader is = null;

        try {
            socketOfClient = new Socket(serverHost, 7777);

            // Создать поток выхода
            os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
            // Создать поток ввода
            is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

        } catch (UnknownHostException e) {
            System.err.println("Не удалось подключться к хосту " + serverHost);
            return;
        } catch (IOException e) {
            System.err.println("Не удалось создать I/O для соединения к " + serverHost);
            return;
        }

        try {

            // Write data to Client Socket.
            //os.write("Привет!  Время - " + new Date());
            //os.newLine();
            //os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.println("Print some search phrase: ");
                os.write(in.readLine());
            } catch (IOException e) {
                System.out.println("ERROR! "+e.getMessage());
            }
            os.newLine();
            os.flush();

            os.write("QUIT");
            os.newLine();
            os.flush();


            // Read data sent from the server.
            String responseLine;
            while ((responseLine = is.readLine()) != null) {
                System.out.println(responseLine);
                if (responseLine.indexOf("OK") != -1) {
                    break;
                }
            }

            os.close();
            is.close();
            socketOfClient.close();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}
