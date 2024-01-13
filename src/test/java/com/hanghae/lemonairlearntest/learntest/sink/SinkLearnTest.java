package com.hanghae.lemonairlearntest.learntest.sink;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.hanghae.lemonairlearntest.Chat;
import com.hanghae.lemonairlearntest.MySubscriber;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class SinkLearnTest {

	@Test
	void sinksOneCanPublishJustOneMessageTest() {
		Sinks.One<Chat> oneSink = Sinks.one();
		Mono<Chat> chatMono = oneSink.asMono();

		List<MySubscriber> subscribers = new ArrayList<>();
		subscribers.add(new MySubscriber("이상문"));
		subscribers.add(new MySubscriber("서병렬"));
		subscribers.add(new MySubscriber("강민범"));

		subscribers.forEach(chatMono::subscribe);
		oneSink.tryEmitValue(Chat.getDefault("첫 메세지야"));
		oneSink.tryEmitValue(Chat.getDefault("두 번째 메세지는 못받아"));

		subscribers.forEach(sub -> {
			assert sub.isComplete();
			assertThat(sub.getReceivedMessages().size()).isEqualTo(1);
			assertThat(sub.getReceivedMessages().get(0).getMessage()).isEqualTo("첫 메세지야");
			assertThat(sub.getReceivedErrors().size()).isEqualTo(0);
		});
	}

	@Test
	void sinksManyCanPublishManyMessageTest(){
		Sinks.Many<Chat> manySink = Sinks.many().multicast().onBackpressureBuffer();
		Flux<Chat> chatFlux = manySink.asFlux();

		List<MySubscriber> subscribers = new ArrayList<>();
		subscribers.add(new MySubscriber("이상문"));
		subscribers.add(new MySubscriber("서병렬"));
		subscribers.add(new MySubscriber("강민범"));

		subscribers.forEach(chatFlux::subscribe);
		manySink.tryEmitNext(Chat.getDefault("첫 메세지야"));
		manySink.tryEmitNext(Chat.getDefault("두 번째 메세지도 받을 수 있어"));
		subscribers.forEach(sub -> {
			assert !sub.isComplete();
			assertThat(sub.getReceivedMessages().size()).isEqualTo(2);
			assertThat(sub.getReceivedMessages().get(0).getMessage()).isEqualTo("첫 메세지야");
			assertThat(sub.getReceivedMessages().get(1).getMessage()).isEqualTo("두 번째 메세지도 받을 수 있어");
			assertThat(sub.getReceivedErrors().size()).isEqualTo(0);
		});
	}
}
