package com.hanghae.lemonairlearntest;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Chat {
	private String message;
	private String sender;
	private String roomId;
	private LocalDateTime createdAt;

	@Override
	public String toString() {
		return message;
	}

	public static Chat getDefault(String message) {
		return Chat.builder().message(message).roomId("방번호").sender("보낸사람").createdAt(LocalDateTime.now()).build();
	}
}

