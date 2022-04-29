package client;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try(Socket socket = new Socket("127.0.0.1", 8000);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            JSONObject jsonObj = new JSONObject();

            //jsonObj.put("HttpMethod", "GET");
            //jsonObj.put("Entity", "users");
            //jsonObj.put("id", 1);
            //writer.write(jsonObj.toString());

            System.out.println("Connected to server.");
            System.out.println("Choose HttpMethod(POST, GET, PUT, DELETE):");
            jsonObj.put("HttpMethod", System.in.toString());
            System.out.println("Choose entity(users, boards, cards, lists):");
            jsonObj.put("Entity", System.in.toString());
            String entity = System.in.toString();

            writer.newLine();
            writer.flush();

            String response = reader.readLine();
            System.out.println("Response: " + response);

        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
