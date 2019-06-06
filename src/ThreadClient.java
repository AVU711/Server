

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadClient implements Runnable {

    private  CatVector catVector;
    private  Socket socket;

    public ThreadClient(CatVector catVector, Socket socket){
        this.catVector = catVector;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Commands commands = new Commands(catVector);

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                System.out.println("Ждём сообщения");
                Object object =  in.readObject();
                Request request = (Request) object;
                System.out.println(request.getCommand());
                String answer = commands.doCommand(request);
                out.writeObject(answer);
                out.flush();

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
