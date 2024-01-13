package com.hanghae.lemonairlearntest.learntest.memory;

import java.nio.ByteOrder;

import org.junit.jupiter.api.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ReferenceCount {

	@Test
	void ByteBufReferenceCountTest(){
		ByteBuf buf = Unpooled.buffer(16);
		printAndRelease(returnNewAllocByteBufAndReleaseOld(writeAndReturn(buf)));
		// buf 생성,
		// writeAndReturn 함수는 buf에 쓰기만하고 리턴한다
		// 리턴된 buf를 변수로 returnNewAllocByteBufAndReleaseOld 호출
		// returnNewAllocByteBufAndReleaseOld는 기존 buf가 깊은복사된 copyBuf를 리턴하고 buf를 release한다
		// 리턴된 copyBuf를 변수로 printAndRelease 호출
		// c가 copyBuf를 swallow한다. -> copyBuf를 release한다.
		assert buf.refCnt() == 0;
	}

	/**
	 * Derived buffers
	 *
	 * ByteBuf.duplicate(), ByteBuf.slice() and ByteBuf.order(ByteOrder) create a derived buffer which shares the memory region of the parent buffer. A derived buffer does not have its own reference count,
	 * 	 * but shares the reference count of the parent buffer.
	 * Derived Buffer는 부모 Buffer와 참조횟수를 공유한다.
	 *
	 * In contrast, ByteBuf.copy() and ByteBuf.readBytes(int) are not derived buffers. The returned ByteBuf is allocated and will need to be released.
	 * 그런데 copy 메서드나 readBytes 메서드는 derived buffer를 리턴하지 않고 새로 allocated 되기때문에 이전의
	 */





	private ByteBuf writeAndReturn(ByteBuf input) {
		input.writeByte(42);
		return input;
	}

	private ByteBuf returnNewAllocByteBufAndReleaseOld(ByteBuf input) {
		ByteBuf output;
		try {
			output = input.alloc().directBuffer(input.readableBytes() + 1);
			output.writeBytes(input);
			output.writeByte(42);
			return output;
		} finally {
			input.release();
		}
	}

	private void printAndRelease(ByteBuf input) {
		System.out.println(input);
		input.release();
	}
}
