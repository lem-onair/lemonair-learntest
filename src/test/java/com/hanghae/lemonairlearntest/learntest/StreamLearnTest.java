package com.hanghae.lemonairlearntest.learntest;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StreamLearnTest {
	@Test
	void arrayToStringMeansPrintObjectId() {
		Stream.of(new int[] {1, 2, 3, 4}).map(String::valueOf).forEach(System.out::println);
	}
}
