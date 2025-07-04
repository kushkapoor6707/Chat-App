package com.sproject.chatroom.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import com.sproject.chatroom.model.ChatMessage;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage send(ChatMessage message) throws Exception {
        String escapedUsername = HtmlUtils.htmlEscape(message.getUsername());

        switch (message.getType().toLowerCase()) {
            case "audio":
                return new ChatMessage(escapedUsername, message.getContent(), "audio");

            case "file":
                ChatMessage fileMessage = new ChatMessage();
                fileMessage.setUsername(escapedUsername);
                fileMessage.setContent(message.getContent());     // base64 data
                fileMessage.setType("file");
                fileMessage.setFileName(message.getFileName());   // e.g. "resume.pdf"
                fileMessage.setFileType(message.getFileType());   // e.g. "application/pdf"
                return fileMessage;

            default: // "text"
                return new ChatMessage(
                        escapedUsername,
                        HtmlUtils.htmlEscape(message.getContent()),
                        "text"
                );
        }
    }

    @MessageMapping("/leave")
    @SendTo("/topic/messages")
    public ChatMessage leave(ChatMessage message) throws Exception {
        String escapedUsername = HtmlUtils.htmlEscape(message.getUsername());
        return new ChatMessage(escapedUsername, escapedUsername + " has left the chat", "text");
    }

    @GetMapping("/")
    public String login() {
        return "login"; // loads login.html
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "chat"; // loads chat.html
    }
}
