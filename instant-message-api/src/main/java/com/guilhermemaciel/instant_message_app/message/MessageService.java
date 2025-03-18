package com.guilhermemaciel.instant_message_app.message;

import com.guilhermemaciel.instant_message_app.chat.Chat;
import com.guilhermemaciel.instant_message_app.chat.ChatRepository;
import com.guilhermemaciel.instant_message_app.file.FileService;
import com.guilhermemaciel.instant_message_app.file.FileUtils;
import com.guilhermemaciel.instant_message_app.notification.Notification;
import com.guilhermemaciel.instant_message_app.notification.NotificationService;
import com.guilhermemaciel.instant_message_app.notification.NotificationType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.guilhermemaciel.instant_message_app.notification.NotificationType.MESSAGE;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final FileService fileService;
    private final NotificationService notificationService;
    private final MessageMapper mapper;

    /**
     * Save message
     * And send notification to the receiver
     * @param messageRequest  MessageRequest
     */
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

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .messageType(messageRequest.type())
                .content(messageRequest.content())
                .senderId(messageRequest.senderId())
                .receiverId(messageRequest.receiverId())
                .chatName(chat.getTargetChatName(message.getSenderId()))
                .type(MESSAGE)
                .build();

        notificationService.sendNotification(message.getReceiverId(), notification);
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

    /**
     * Set messages to seen
     * And send notification to the sender
     * @param chatId  String
     * @param authentication  Authentication
     */
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found with ID:: " + chatId));

        final String recipientId = getRecipientId(chat, authentication);
        messageRepository.setMessagesToSeenByChatId(chatId, MessageState.SEEN);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .type(NotificationType.SEEN)
                .receiverId(recipientId)
                .senderId(getSenderId(chat, authentication))
                .build();

        notificationService.sendNotification(recipientId, notification);
    }

    /**
     * Upload media message
     * And send notification to the receiver
     * @param chatId  String
     * @param file  MultipartFile
     * @param authentication  Authentication
     */
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

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .type(NotificationType.IMAGE)
                .messageType(MessageType.IMAGE)
                .senderId(senderId)
                .receiverId(recipientId)
                .media(FileUtils.readFileFromLocation(filePath))
                .build();

        notificationService.sendNotification(recipientId, notification);
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
