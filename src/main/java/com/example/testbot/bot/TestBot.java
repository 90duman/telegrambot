package com.example.testbot.bot;

import com.example.testbot.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class TestBot extends TelegramLongPollingBot {

    Logger logger = Logger.getLogger(Weather.class.getName());

    @Value("${bot.username}")
    private String username;
    @Value("${bot.token}")
    private String token;

    ReplyKeyboardMarkup replyKeyMarkup = new ReplyKeyboardMarkup();

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            logger.info("Запрос команд: " + message);
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            java.lang.String text = message.getText();
            if ("Старт".equals(text)) {
                sendMsg(message, "Данный бот позволяеть узнать погоду. " +
                        "Если в командах отсутствует ваш город то напишите названия города");
            } else if ("download".equals(text)) {
                sendMsg(message, urlDownload);
            } else {
                try {
                    sendMsg(message, Weather.getWeather(message.getText(), model));
                } catch (IOException e) {
                    sendMsg(message, "Город не найден!");
                }
            }
        }
    }

    String urlDownload = "http://172.17.1.65:8080/download/customers.xlsx";

    private List<Customer> createTestData() {
        List<Customer> customers = new ArrayList<Customer>();
        for (Customer customer : Arrays.asList(new Customer("Vernon", "Barlow", "0123456789", "test1@simplesolution.dev"),
                new Customer("Maud", "Brock", "0123456788", "test2@simplesolution.dev"),
                new Customer("Chyna", "Cowan", "0123456787", "test3@simplesolution.dev"),
                new Customer("Krisha", "Tierney", "0123456786", "test4@simplesolution.dev"),
                new Customer("Sherry", "Rosas", "0123456785", "test5@simplesolution.dev"))) {
            customers.add(customer);
        }
        return customers;
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();
        KeyboardRow keyboardRow5 = new KeyboardRow();

        keyboardRow1.add(new KeyboardButton(Commands.START.getValue()));
        keyboardRow2.add(new KeyboardButton(Commands.ALMATY.getValue()));
        keyboardRow3.add(new KeyboardButton(Commands.OSKEMEN.getValue()));
        keyboardRow4.add(new KeyboardButton(Commands.URZHAR.getValue()));
        keyboardRow5.add(new KeyboardButton(Commands.DOWNLOAD.getValue()));
        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow2);
        keyboardRowList.add(keyboardRow3);
        keyboardRowList.add(keyboardRow4);
        keyboardRowList.add(keyboardRow5);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

}
