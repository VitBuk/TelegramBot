package vitbuk.com.TelegramBot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Comparator;
import java.util.List;

public class Bot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;

    public Bot(String botToken) {
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        // if user use /start command
        if (messageText.equals("/start")) {
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(messageText)
                    .build();
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            //if user use /pic command
        } else if (messageText.equals("/pic")) {
            SendPhoto photoMessage = createPhotoMessage(chatId);

            try {
                telegramClient.execute(photoMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            //other cases, where we dont understand user
        } else {
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Unknown command")
                    .build();
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    private SendPhoto createPhotoMessage(long chatId) {
        return SendPhoto
                .builder()
                .chatId(chatId)
                .photo(new InputFile(Constants.CAESAR_PIC_URL))
                .caption("Caesar")
                .build();
    }
}
