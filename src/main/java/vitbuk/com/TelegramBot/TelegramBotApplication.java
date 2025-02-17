package vitbuk.com.TelegramBot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class TelegramBotApplication {

	public static void main(String[] args) {
		try {
			String botToken = Bot.TOKEN;
			TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
			botsApplication.registerBot(botToken, new Bot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}