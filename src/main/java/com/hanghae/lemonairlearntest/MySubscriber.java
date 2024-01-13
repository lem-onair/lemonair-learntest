package com.hanghae.lemonairlearntest;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import reactor.core.publisher.BaseSubscriber;

@Getter
public class MySubscriber extends BaseSubscriber<Chat> {
	final String name;
	final List<Chat> receivedMessages = new ArrayList<>();
	final List<String> receivedErrors = new ArrayList<>();
	boolean isComplete = false;

	public MySubscriber(String name) {
		this.name = name;
	}

	@Override
	protected void hookOnNext(Chat chat) {
		receivedMessages.add(chat);
		System.out.println(name + " — Received: " + chat);
	}

	@Override
	protected void hookOnComplete() {
		isComplete = true;
		System.out.println(name + " — Completed");
	}

	@Override
	protected void hookOnError(Throwable throwable) {
		receivedErrors.add(throwable.getMessage());
		System.err.println(name + " — Error: " + throwable.getMessage());
	}

}
