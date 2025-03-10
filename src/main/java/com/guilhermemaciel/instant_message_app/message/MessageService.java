package com.guilhermemaciel.instant_message_app.message;

import com.guilhermemaciel.instant_message_app.chat.Chat;
import com.guilhermemaciel.instant_message_app.chat.ChatRepository;
import com.guilhermemaciel.instant_message_app.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final FileService fileService;
    private final MessageMapper mapper;

    public void saveMessage(MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.chatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found with ID:: " + messageRequest.chatId()));

        Message message = new Message();
        message.setContent(messageRequest.content());
        message.setChat(chat);
        message.setSenderId(messageRequest.senderId());
        message.setReceiverId(messageRequest.receiverId());
        message.setType(messageRequest.type());
        message.setState(MessageState.SENT);

        messageRepository.save(message);

        //TODO: Notification
    }

    /**
     * Find chat messages
     *
     * @param chatId  String
     * @return List<MessageResponse>
     */
    public List<MessageResponse> findChatMessages(String chatId) {
        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    }

    public void setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found with ID:: " + chatId));

        final String recipientId = getRecipientId(chat, authentication);
        messageRepository.setMessagesToSeenByChatId(chatId, MessageState.SEEN);

        //TODO: notification
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found with ID:: " + chatId));

        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);
        Message message = new Message();
        message.setChat(chat);
        message.setSenderId(senderId);
        message.setReceiverId(recipientId);
        message.setType(MessageType.IMAGE);
        message.setState(MessageState.SENT);
        message.setMediaFilePath(filePath);

        messageRepository.save(message);

        //TODO: Notification
    }

    /**
     * Get the sender ID of the chat
     *
     * @param chat            Chat
     * @param authentication  Authentication
     * @return String
     */
    private String getSenderId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }

    /**
     * Get the recipient ID of the chat
     *
     * @param chat            Chat
     * @param authentication  Authentication
     * @return String
     */
    private String getRecipientId(Chat chat, Authentication authentication) {
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }
}
