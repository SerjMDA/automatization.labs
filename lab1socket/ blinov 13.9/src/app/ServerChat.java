package app;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ServerChat {

	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;

	private static final int maxClientsCount = 10;
	private static final clientThread[] threads = new clientThread[maxClientsCount];

	public static void main(String args[]) {

		int portNumber = 6289;

		System.out.println("Now using port number=" + portNumber);

		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e);
		}

		while (true) {
			try {
				clientSocket = serverSocket.accept();
				int i = 0;
				for (i = 0; i < maxClientsCount; i++) {
					if (threads[i] == null) {
						(threads[i] = new clientThread(clientSocket, threads)).start();
						break;
					}
				}
				if (i == maxClientsCount) {
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					os.println("Server too busy. Try later.");
					os.close();
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}

class clientThread extends Thread {

	private String clientName = null;
	private DataInputStream is = null;
	private PrintStream os = null;
	private Socket clientSocket = null;
	private final clientThread[] threads;
	private int maxClientsCount;

	public clientThread(Socket clientSocket, clientThread[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientsCount = threads.length;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		int maxClientsCount = this.maxClientsCount;
		clientThread[] threads = this.threads;

		try {

			is = new DataInputStream(clientSocket.getInputStream());
			os = new PrintStream(clientSocket.getOutputStream());
			String name;
			while (true) {
				os.println("Enter your name.");
				name = is.readLine().trim();
				if (name.indexOf('@') == -1) {
					break;
				} else {
					os.println("The name should not contain '@' character.");
				}
			}

			/* Приветствие нового пользователья чата */
			os.println("Welcome " + name + " to our chat room.\nto leave enter /quit in a new line.\n"
					+ "to sent a private message use @Name" + "to sent a file use @Name fileName.txt\n");
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] == this) {
						clientName = "@" + name;
						break;
					}
				}
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this) {
						threads[i].os.println("A new user " + name + " entered the chat room !");

					}
				}
			}
			System.out.println("A new user " + name + " entered the chat room !");
			/* Начало диалога */
			while (true) {
				String line = is.readLine();
				if (line.startsWith("/quit")) {
					break;
				}
				/* Отправка приватных сообщений */
				if (line.startsWith("@")) {
					String[] words = line.split("\\s", 2);
					if (words.length > 1 && words[1] != null) {
						words[1] = words[1].trim();
						if (!words[1].isEmpty()) {
							synchronized (this) {
								for (int i = 0; i < maxClientsCount; i++) {
									if (threads[i] != null && threads[i] != this && threads[i].clientName != null
											&& threads[i].clientName.equals(words[0])) {
										threads[i].os.println("<" + name + "> " + words[1]);
										/*
										 * Эхо сообщение,о том что сообщение клиента было доставлено другому клиенту
										 */

										try {
											Path originalPath = Paths.get("src/source/" + words[1]);
											System.out.print(originalPath.toString());
											Path copied = Paths
													.get(String.format("src/destination/client%s-client%s.jpg", 
															clientName, words[0]));
											Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
										} catch (IOException e) {
											System.out.println("This file doesn't exist");
										}

										break;
									}

								}
							}
						}
					}
				} else {
					/* Отправка сообщения всем клиентам */
					synchronized (this) {
						for (int i = 0; i < maxClientsCount; i++) {
							if (threads[i] != null && threads[i].clientName != null && threads[i] != this) {
								threads[i].os.println("<" + name + "> " + line);
							}
						}
					}
				}
			}
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this && threads[i].clientName != null) {
						threads[i].os.println("The user " + name + " is leaving the chat room !");

					}
				}

			}
			os.println("Bye " + name);
			System.out.println("The user " + name + " is leaving the chat room !");
			/*
			 * Очищаем потоки,чтобы новый клиент мог подключиться к серверу
			 */
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] == this) {
						threads[i] = null;
					}
				}
			}
			/*
			 * Закрываем входные,выходные потоки и сокет
			 */
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
		}
	}
}
