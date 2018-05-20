package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;



public class ServerChat {

	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;

	private static final int maxClientsCount = 8;
	private static final clientThread[] threads = new clientThread[maxClientsCount];

	public static void main(String args[]) {

		int portNumber = 6289;

		System.out.println("Server is listening,port number is " + portNumber);

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
					os.println("Server too busy.");
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
				os.println("Enter your nickname.");
				name = is.readLine().trim();
				if (name.indexOf('@') == -1) {
					break;
				} else {
					os.println("The name should not contain '@'");
				}
			}

			/* Приветствие нового пользователя */
			os.println("Welcome " + name + " to our chat room.\n" + "To sent a private message use @Name");
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] == this) {
						clientName = "@" + name;

					}
				}
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this) {
						threads[i].os.println(" A new user " + name + " entered the chat room !");

					}
				}
			}
			System.out.println("A new user " + name + " entered the chat room !");
			/* Начало чата */
			while (true) {
				String line = is.readLine();
				if (line.startsWith("/quit")) {
					break;
				}
				/* отправка приватных сообщений */
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

										break;
									}
								}
							}
						}
					}
				} else {
					/* Публичные сообщения */
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
                    if (threads[i] != null && threads[i] != this
                            && threads[i].clientName != null) {
                        threads[i].os.println("*** The user " + name
                                + " is leaving the chat room !!! ***");

                    }
                }

            }
           
			/*
			 * обнуляем поток
			 */
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] == this) {
						threads[i] = null;
					}
				}
			}
			/*
			 * Остановка входных,выходных потоков и сокета
			 */
			is.close();
			os.close();
			clientSocket.close();
		} catch (IOException e) {
		}
	}
}