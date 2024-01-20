package ua.edu.sumdu.nefodov.sheltered.tgbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.nefodov.sheltered.tgbot.model.TelegramUser;

@Repository
public interface TGUserRepository extends JpaRepository<TelegramUser, Long> {

    Boolean existsByChatId(long chatId);
    
}
