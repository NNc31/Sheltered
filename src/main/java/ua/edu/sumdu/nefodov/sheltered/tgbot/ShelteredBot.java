package ua.edu.sumdu.nefodov.sheltered.tgbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.edu.sumdu.nefodov.sheltered.application.model.Coordinates;
import ua.edu.sumdu.nefodov.sheltered.tgbot.config.BotConfig;
import ua.edu.sumdu.nefodov.sheltered.tgbot.model.TelegramUser;
import ua.edu.sumdu.nefodov.sheltered.tgbot.repository.TGUserRepository;
import ua.edu.sumdu.nefodov.sheltered.tgbot.service.ShelterFinderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ShelteredBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final TGUserRepository userRepository;
    private final ShelterFinderService shelterService;
    private static final String WELCOME_TEXT = "Привіт! Будь ласка, поширте свою локацію\n" +
            "Так ми зможемо знайти сховище для тебе";
    private static final String USER_NOT_FOUND = "Не знайдено збережених даних\n" +
            "Будь ласка, поширте свою локацію";
    private static final String WRONG_COMMAND = "Незнайома операція :(";
    private static final String FIND_SHELTER_COMMAND = "Знайти найближче сховище";
    private static final String FILTER_SHELTER_COMMAND = "Профільтрувати сховища";
    private static final String SHARE_LOCATION_COMMAND = "Поширити місцезнаходження";

    @Autowired
    public ShelteredBot (@Qualifier("botConfig") BotConfig config, TGUserRepository userRepository, ShelterFinderService shelterService) {
        super(config.getToken());
        this.config = config;
        this.userRepository = userRepository;
        this.shelterService = shelterService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (text.equals("/start")) {
                SendMessage sm = new SendMessage();
                sm.setChatId(chatId);
                sm.setText(WELCOME_TEXT);

                List<KeyboardRow> krList = new ArrayList<>();
                KeyboardRow kr = new KeyboardRow();
                KeyboardButton kb = new KeyboardButton(SHARE_LOCATION_COMMAND);
                kb.setRequestLocation(true);
                kr.add(kb);
                krList.add(kr);

                ReplyKeyboardMarkup rkm = new ReplyKeyboardMarkup(krList);
                rkm.setResizeKeyboard(true);
                rkm.setSelective(true);

                sm.setReplyMarkup(rkm);
                sendMessage(sm);
            } else if (text.equals(FIND_SHELTER_COMMAND)) {
                Optional<TelegramUser> user = userRepository.findById(chatId);
                if (user.isPresent()) {
                    SendLocation sl = new SendLocation();
                    sl.setChatId(chatId);
                    Coordinates closetsCoords = shelterService.findClosestCoordinates(
                            new Coordinates(user.get().getLatitude(), user.get().getLongitude()));
                    sl.setLatitude(closetsCoords.getLatitude());
                    sl.setLongitude(closetsCoords.getLongitude());
                    try {
                        execute(sl);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else if (text.equals(FILTER_SHELTER_COMMAND)) {
                    //Send keyboard to filter shelters
                }
                else {
                    sendMessage(chatId, USER_NOT_FOUND);
                }
            } else {
                sendMessage(chatId, WRONG_COMMAND);
            }
        } else if (update.hasMessage() && update.getMessage().hasLocation()) {
            long chatId = update.getMessage().getChatId();
            Location location = update.getMessage().getLocation();
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            userRepository.save(new TelegramUser(chatId, lat, lng));

            SendMessage sm = new SendMessage();
            sm.setChatId(chatId);
            sm.setText("Оберіть операцію");
            List<KeyboardRow> krList = new ArrayList<>();
            KeyboardRow kr = new KeyboardRow();
            KeyboardButton kb1 = new KeyboardButton(FIND_SHELTER_COMMAND);
            KeyboardButton kb2 = new KeyboardButton(FILTER_SHELTER_COMMAND);
            kr.add(kb1);
            kr.add(kb2);
            krList.add(kr);

            ReplyKeyboardMarkup rkm = new ReplyKeyboardMarkup(krList);
            rkm.setResizeKeyboard(true);
            rkm.setSelective(true);

            sm.setReplyMarkup(rkm);
            sendMessage(sm);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    private void sendMessage(long chatId, String text) {
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.setText(text);
        sendMessage(sm);
    }

    private void sendMessage(SendMessage sm) {
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
