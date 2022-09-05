package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;

public class TextStompEntity extends DefaultStompFrame implements StompFrame {

    private final String textContent;

    public TextStompEntity(StompCommand command, ByteBuf content, String textContent) {
        this(command, content, textContent, null);
    }

    TextStompEntity(StompCommand command, ByteBuf content, String textContent, DefaultStompHeaders headers) {
        super(command, content, headers);
        this.textContent = content.toString();
    }
}
