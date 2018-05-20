package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//Обеспечивает работу программы в режиме клиента

public class Client {
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
	private Scanner scan;

	// Запрашивает у пользователя ник и организовывает обмен сообщениями с сервером

	public Client() {
		scan = new Scanner(System.in);

		try {
			// Подключаемся к серверу и получаем потоки(in и out) для передачи сообщений
			socket = new Socket("localhost", 6289);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			System.out.println("Введите свой ник:");
			out.println(scan.nextLine());

			//   Выводим все входящие сообщения в консоль
			Resender resend = new Resender();
			resend.start();

			/**
			 * Пока пользователь не введёт "exit" отправляем на сервер всё, что введено в
			 * консоль
			 */
			String str = "";
			while (!str.equals("exit")) {
				str = scan.nextLine();
				out.println(str);
			}
			resend.setStop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	// Закрывает входные и выходные потоки и сокет

	private void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (Exception e) {
			System.err.println("При закрытии потоков произошла ошибка");
		}
	}

	/**
	 * Класс все сообщения от сервера в консоль. Работает пока не будет вызван метод
	 * setStop().
	 */
	private class Resender extends Thread {

		private boolean stoped;

		// Прекращает отправку сообщений
		public void setStop() {
			stoped = true;
		}

		/**
		 * Считывает сообщения от сервера и печатает их в консоль.Останавлиается вызовом
		 * метода setStop()
		 * 
		 */
		@Override
		public void run() {
			try {
				while (!stoped) {
					String str = in.readLine();
					System.out.println(str);
				}
			} catch (IOException e) {
				System.err.println("Произошла ошибка при получении сообщения");
				e.printStackTrace();
			}
		}
	}

}
