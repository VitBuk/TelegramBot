package vitbuk.com.TelegramBot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

public class Bot implements LongPollingSingleThreadUpdateConsumer {
    static final String TOKEN = Constants.BOT_TOKEN;
    static final String USERNAME = Constants.BOT_USERNAME;

    TelegramClient telegramClient = new OkHttpTelegramClient(TOKEN);

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String ChatId = String.valueOf(update.getMessage().getChatId());
            SendMessage sendMessage = new SendMessage(ChatId, update.getMessage().getText());
            try {
                telegramClient.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
