package com.azamakram.github.springbootjpadocker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.azamakram.github.springbootjpadocker.model.input.MessageInput;
import com.azamakram.github.springbootjpadocker.model.output.MessageOutput;
import com.azamakram.github.springbootjpadocker.service.MessageService;

import javax.naming.ServiceUnavailableException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMessages() {
        log.trace("Getting all the message");
        List<MessageOutput> messageOutputList = messageService.getAllMessages();
        return new ResponseEntity<>(messageOutputList, HttpStatus.OK);
    }

    @GetMapping(path = "/{count}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNumberOfLastSavedMessages(@PathVariable(value = "count") final Integer count) {
        log.trace(String.format("Getting %d number of last saved message records", count ));
        List<MessageOutput> messageOutputList = messageService.getNNumberOfMessages(count);
        return new ResponseEntity<>(messageOutputList, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewMessage(@Valid @RequestBody final MessageInput messageInput)
            throws ServiceUnavailableException  {
        log.trace("Saving new message");

        MessageOutput output = messageService.saveMessage(messageInput);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{messageKey}").buildAndExpand(output.getMessageKey()).toUri();
        return ResponseEntity.created(location).body(output);
    }


    @PutMapping(path = "/{messageKey}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMessage(@PathVariable(value = "messageKey") final String messageKey,
                                           @Valid @RequestBody final MessageInput messageInput) {
        log.trace("Updating message");
        MessageOutput output = messageService.updateMessage(messageKey, messageInput);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMessage() {
        log.trace("Deleting old messages");
        messageService.deleteMessage();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

