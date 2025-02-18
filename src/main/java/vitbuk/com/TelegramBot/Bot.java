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
    public void consume(List<Update> updates) {
        LongPollingSingleThreadUpdateConsumer.super.consume(updates);
    }

    @Override
    public void consume(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Create your send message object
            SendMessage sendMessage = new SendMessage(update.getMessage().getChatId(), update.getMessage().getText());
            try {
                // Execute it
                telegramClient.execute(method);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
