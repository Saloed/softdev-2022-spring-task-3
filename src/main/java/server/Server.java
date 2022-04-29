package server;

import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import server.Controller.ServerController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        ServerController serverController = new ServerController();

        try(ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server started");

            while (true)
            try (Socket socket = server.accept();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            )
            {
                //JSONObject request = new JSONObject(reader.readLine());
                //String httpMethod = request.getString("HttpMethod");
                //String entity = request.getString("Entity");
                //int id = request.getInt("id");
                //String user = "";
                //if(httpMethod.equals("GET"))
                //user = serverController.getOne(entity, id);
                //System.out.println(user);
                //String response = "Hello from server: " + user;
                //writer.write(response);

                writer.newLine();
                writer.flush();

            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
