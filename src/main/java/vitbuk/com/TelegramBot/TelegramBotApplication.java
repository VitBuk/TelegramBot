package vitbuk.com.TelegramBot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class TelegramBotApplication {

	public static void main(String[] args) {
		try {
			TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
			botsApplication.registerBot(Constants.BOT_TOKEN, new Bot(Constants.BOT_TOKEN));
			System.out.println("Bot started successfully!");
			Thread.currentThread().join();
		} catch (TelegramApiException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}