package com.azamakram.github.springbootjpadocker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.azamakram.github.springbootjpadocker.repository.MessageRepository;
import com.azamakram.github.springbootjpadocker.model.entity.MessageEntity;
import com.azamakram.github.springbootjpadocker.model.exception.MessageNotFoundException;
import com.azamakram.github.springbootjpadocker.model.input.MessageInput;
import com.azamakram.github.springbootjpadocker.model.output.MessageOutput;

import javax.naming.ServiceUnavailableException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class MessageService {

    private Long TIME_TO_UPDATE = 10L;

    private Long TIME_TO_DELETE = 2L;

    @Autowired
    private MessageRepository messageRepository;

    public MessageService(MessageRepository repository) {
        this.messageRepository = repository;
    }

    @Transactional
    public MessageOutput saveMessage(final MessageInput messageInput) throws ServiceUnavailableException {

        String messageKey = UUID.randomUUID().toString();

        MessageEntity entity = MessageEntity.builder()
                .messageKey(messageKey).sender(messageInput.getSender()).savedAt(LocalDateTime.now()).build();

        return EntityToOutputConverter.convertMessageEntityToOutput(messageRepository.save(entity));
    }

    public List<MessageOutput> getAllMessages() {
        return EntityToOutputConverter.convertMessageEntitiesToOutput(messageRepository.findAll());
    }

    public List<MessageOutput> getNNumberOfMessages(Integer count) {
        return EntityToOutputConverter.convertMessageEntitiesToOutput(messageRepository.findLastNEmployees(count));
    }

    @Transactional
    public MessageOutput updateMessage(String messageKey, MessageInput messageInput) {
        LocalDateTime after = LocalDateTime.now().minusSeconds(TIME_TO_UPDATE);

        return messageRepository.findByMessageKeyAndSavedAtAfter(messageKey, after)
            .map(entity -> {
                entity.setSender(messageInput.getSender());
                MessageEntity savedEntity = messageRepository.save(entity);
                if(savedEntity == null) {
                    return null;
                }
                return EntityToOutputConverter.convertMessageEntityToOutput(savedEntity);
            }).orElseThrow( () ->
                    new MessageNotFoundException(String.format("Message not found for key %s.", messageKey)));
    }

    @Transactional
    public void deleteMessage() {
        LocalDateTime since = LocalDateTime.now().minusMinutes(TIME_TO_DELETE);
        messageRepository.deleteBySavedAtBefore(since);
    }

}
