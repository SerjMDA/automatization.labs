
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class ServerSonnets {

	private static ServerSocket serverSocket;
	public static void main(String[] args) {
		try {
		     serverSocket = new ServerSocket(6289);
			System.out.println("Server is listening");
			while (true) {
				final Socket socket = serverSocket.accept();
				System.out.println("Now using hosts : " + socket);
				Runnable runnable = () -> handleClientRequest(socket);
				new Thread(runnable).start(); // новый поток
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void handleClientRequest(Socket socket) {
		BufferedWriter socketWriter = null;
		try {
			socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			String outMsg = getSonet();

			socketWriter.write(outMsg);
			socketWriter.flush();
			Thread.sleep(4000);
			System.out.println("User exited from chat : " + socket);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			//закрвыем сокет
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
//отправляем соннет подключенному клиенту
	public static String getSonet() throws IOException {
		return new String(Files
				.readAllBytes(Paths.get("src/sonets/" + (new SecureRandom().nextInt(4) + 1) + ".txt")));
	}
}