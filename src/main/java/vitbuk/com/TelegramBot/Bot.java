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

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = createSendMessage(update);
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            SendPhoto photoMessage = createPhotoMessage(update);

            try {
                telegramClient.execute(photoMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    private SendMessage createSendMessage(Update update) {
        String message_text = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();

        return SendMessage
                .builder()
                .chatId(chat_id)
                .text(message_text)
                .build();
    }

    private SendPhoto createPhotoMessage(Update update) {
        // Set variables
        long chat_id = update.getMessage().getChatId();

        // Array with photo objects with different sizes
        // We will get the biggest photo from that array
        List<PhotoSize> photos = update.getMessage().getPhoto();
        // Know file_id
        String f_id = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                .map(PhotoSize::getFileId)
                .orElse("");
        // Know photo width
        int f_width = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                .map(PhotoSize::getWidth)
                .orElse(0);
        // Know photo height
        int f_height = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                .map(PhotoSize::getHeight)
                .orElse(0);
        // Set photo caption
        String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);

        return SendPhoto
                .builder()
                .chatId(chat_id)
                .photo(new InputFile(f_id))
                .caption(caption)
                .build();
    }
}
