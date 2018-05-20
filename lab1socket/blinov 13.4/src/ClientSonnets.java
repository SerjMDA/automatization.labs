
import java.io.*;
import java.net.Socket;

public class ClientSonnets {
	public static void main(String[] args) {
		BufferedReader socketReader = null;
		try (Socket socket = new Socket("localhost", 6289);) {
			String message = null;
			StringBuilder sb = new StringBuilder();
			socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			System.out.println("Connected");
			System.out.println("------------");
			System.out.println("Shakespeare's complete sonnets");
			while ((message = socketReader.readLine()) != null) {
				sb.append(message);
				sb.append("\n");
			}

			System.out.println(sb.toString());
			System.out.println("--------");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}