package by.mark.twofa.service;

import by.mark.twofa.model.Message;
import by.mark.twofa.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepo messageRepo;

    public List<Message> list() {
        return messageRepo.findAll();
    }

    public Message addOne(Message message) {
        return messageRepo.save(message);
    }
}
