package com.sproject.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String username;     // Who sent the message
    private String content;      // Text OR base64 (audio/file)
    private String type = "text"; // "text", "audio", or "file"
    private String fileName;     // For file sharing (e.g., "notes.pdf")
    private String fileType;     // MIME type (e.g., "application/pdf")


    public ChatMessage(String username, String content, String type) {
        this.username = username;
        this.content = content;
        this.type = type;
    }
}
