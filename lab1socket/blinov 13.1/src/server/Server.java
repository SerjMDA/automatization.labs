package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//
public class Server {
	/**
	 * Специальная "обёртка" для ArrayList, которая обеспечивает доступ к массиву из
	 * разных нитей
	 */
	private List<Connection> connections = Collections.synchronizedList(new ArrayList<Connection>());
	private ServerSocket server;
	   private static final String file = "users.txt";
	    private static final Path path = Paths.get(file);

	/**
	 * Конструктор создаёт сервер. Затем для каждого подключения создаётся объект
	 * Connection и добавляет его в список подключений.
	 */
	    static void addUserToFile(String name) throws IOException {
	        if (!Files.isExecutable(path)) {
	            Files.createFile(path);
	        }
	        Stream<String> lines = Files.lines(path);
	        List<String> replaced = lines.collect(Collectors.toList());
	        if(!replaced.contains(name)){
	            replaced.add(name);
	        }
	        Files.write(path, replaced);
	        lines.close();
	    }

	    static void removeUserFromFile(String name) throws IOException {
	        Stream<String> lines = Files.lines(path);
	        List<String> replaced = lines.collect(Collectors.toList());
	        replaced.removeIf(s -> s.equals(name));
	        Files.write(path, replaced);
	        lines.close();
	    }

	public Server() {
		try {
			server = new ServerSocket(6289);

			while (true) {
				System.out.println("Сервер запущен.Ожидание пользователей.");
				Socket socket = server.accept();

				// Создаём объект Connection и добавляем его в список
				Connection con = new Connection(socket);
				connections.add(con);

				/**
				 * Инициализирует нить и запускает метод run(), которая выполняется одновременно
				 * с остальной программой
				 */
				con.start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}

	/**
	 * Закрывает все потоки всех соединений а также серверный сокет
	 */
	private void closeAll() {
		try {
			server.close();

			// Перебор всех Connection и вызов метода close() для каждого. Блок
			// synchronized {} необходим для правильного доступа к одним данным
			// их разных нитей
			synchronized (connections) {
				Iterator<Connection> iter = connections.iterator();
				while (iter.hasNext()) {
					((Connection) iter.next()).close();
				}
			}
		} catch (Exception e) {
			System.err.println("При закрытии потоков произошла ошибка!");
		}
	}

	/**
	 * Класс содержит данные, относящиеся к конкретному подключению: Расширяет
	 * Thread и в методе run() получает информацию от пользователя и пересылает её
	 * другим
	 */
	private class Connection extends Thread {
		private BufferedReader in;
		private PrintWriter out;
		private Socket socket;

		private String name = "";

		// Инициализирует поля объекта и получает имя пользователя

		public Connection(Socket socket) {
			this.socket = socket;

			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

			} catch (IOException e) {
				e.printStackTrace();
				close();
			}
		}

		/**
		 * Запрашивает ник пользователя и ожидает от него сообщений,при получении
		 * каждого сообщения оно вместе с ником отправляется другим пользователям
		 */
		@Override
		public void run() {
			try {
				name = in.readLine();
				// Отправляем всем пользователям сообщение о том, что зашёл новый пользователь
				synchronized (connections) {
					Iterator<Connection> iter = connections.iterator();
					while (iter.hasNext()) {
						((Connection) iter.next()).out.println(name + " присоединился");
					}
				}

				String str = "";
				while (true) {
					str = in.readLine();
					if (str.equals("exit"))
						break;

					// Отправляем всем клиентам сообщение
					synchronized (connections) {
						Iterator<Connection> iter = connections.iterator();
						while (iter.hasNext()) {
							((Connection) iter.next()).out.println(name + ": " + str);
						}
					}
				}

				synchronized (connections) {
					Iterator<Connection> iter = connections.iterator();
					while (iter.hasNext()) {
						((Connection) iter.next()).out.println(name + " вышел");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}

		/**
		 * Закрывает входные и выходные потоки и сокет
		 */
		public void close() {
			try {
				in.close();
				out.close();
				socket.close();

				/** Если больше нет пользователей то закрываем сервер */
				connections.remove(this);
				if (connections.size() == 0) {
					Server.this.closeAll();
					System.exit(0);
				}
			} catch (Exception e) {
				System.err.println("При закрытии потоков произошла ошибка");
			}
		}
	}
}
