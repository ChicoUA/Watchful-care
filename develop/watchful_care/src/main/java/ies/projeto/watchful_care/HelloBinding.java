package com.example.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface HelloBinding {
	String GREETING = "message_queue";

    @Input(GREETING)
    SubscribableChannel greeting();
}
