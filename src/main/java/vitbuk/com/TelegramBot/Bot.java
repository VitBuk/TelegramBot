package vitbuk.com.TelegramBot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
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

            //if user use /markup command
        } else if (messageText.equals("/markup")) {
            SendMessage message = SendMessage // Create a message object object
                    .builder()
                    .chatId(chatId)
                    .text("Here is your keyboard")
                    .build();

            // Add the keyboard to the message
            message.setReplyMarkup(ReplyKeyboardMarkup
                    .builder()
                    // Add first row of 3 buttons
                    .keyboardRow(new KeyboardRow("Caesar", "Row 1 Button 2", "Row 1 Button 3"))
                    // Add second row of 3 buttons
                    .keyboardRow(new KeyboardRow("Row 2 Button 1", "Row 2 Button 2", "Row 2 Button 3"))
                    .build());
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            // when user tries something that is not a command
        } else if (messageText.equals("Caesar")) {
            // Send a picture to the user
            SendPhoto msg = SendPhoto
                    .builder()
                    .chatId(chatId)
                    // This time will send the picture using a URL
                    .photo(new InputFile(Constants.CAESAR_PIC_URL))
                    .caption("Clicked button: " + messageText)
                    .build();
            try {
                telegramClient.execute(msg); // Call method to send the photo
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else if (messageText.equals("/hide")) {
            // Hide the keyboard
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Keyboard hidden")
                    .replyMarkup(new ReplyKeyboardRemove(true,false))
                    .build();

            try {
                telegramClient.execute(message); // Call method to send the photo
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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
