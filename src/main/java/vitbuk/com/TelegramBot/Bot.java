package vitbuk.com.TelegramBot;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class Bot implements LongPollingSingleThreadUpdateConsumer {

    static final String TOKEN = Constants.BOT_TOKEN;
    static final String USERNAME = Constants.BOT_USERNAME;


    @Override
    public void consume(List<Update> updates) {
        LongPollingSingleThreadUpdateConsumer.super.consume(updates);
    }

    @Override
    public void consume(Update update) {
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getText());
        }
    }
}
