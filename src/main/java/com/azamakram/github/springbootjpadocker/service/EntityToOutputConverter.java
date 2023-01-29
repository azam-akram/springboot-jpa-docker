package com.azamakram.github.springbootjpadocker.service;

import com.azamakram.github.springbootjpadocker.model.entity.MessageEntity;
import com.azamakram.github.springbootjpadocker.model.output.MessageOutput;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EntityToOutputConverter {

    private EntityToOutputConverter() {
        super();
    }

    public static List<MessageOutput> convertMessageEntitiesToOutput(
            Iterable<MessageEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(c -> convertMessageEntityToOutput(c))
                .collect(Collectors.toList());
    }

    public static MessageOutput convertMessageEntityToOutput(MessageEntity entity) {
        return MessageOutput.builder()
            .messageKey(entity.getMessageKey())
            .sender(entity.getSender())
            .savedAt(entity.getSavedAt())
            .build();
    }
}