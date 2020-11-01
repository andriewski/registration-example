package by.mark.twofa.service;

import by.mark.twofa.model.Message;
import by.mark.twofa.repo.MessageRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepo;

    public List<Message> list() {
        return messageRepo.findAll();
    }

    public Message addOne(Message message) {
        return messageRepo.save(message);
    }
}
