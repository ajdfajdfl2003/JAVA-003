package idv.angus.java003.task06;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class HttpServer01 {
    /**
     * 單一線程處理進來的 request，沒效率
     */
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8801);
        while (true) {
            Socket socketAccept = socket.accept();
            service(socketAccept);
        }
    }

    private static void service(Socket socket) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println("HTTP/1.1 200 OK");
        printWriter.println("Content-Type:text/html;charset=utf-8");
        String body = "hello, nio1";
        printWriter.println("Content-Length:" + body.getBytes().length);
        printWriter.println();
        printWriter.write(body);
        printWriter.close();
        socket.close();
    }
}
