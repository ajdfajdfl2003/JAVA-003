package idv.angus.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer02 {
    /**
     * 雖然每個進來的 request，都會用多線程處理，
     * 但每次 new 出來的線程並沒有被重複使用
     */
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8802);
        while (true) {
            Socket socketAccept = socket.accept();
            new Thread(() -> service(socketAccept)).start();
        }
    }

    private static void service(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello, nio2";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
