package com.example.tripmingle.adapter.in;

import static com.example.tripmingle.common.constants.Constants.*;
import static com.example.tripmingle.common.result.ResultCode.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.chat.GetAllChatMessageReqDTO;
import com.example.tripmingle.dto.res.chat.GetAllChatMessagesResDTO;
import com.example.tripmingle.port.in.ChatUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "채팅")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

	private final ChatUseCase chatUseCase;

	@Operation(summary = "채팅 메시지 조회")
	@GetMapping("/chat-rooms/{chatRoomId}/messages")
	public ResponseEntity<ResultResponse> getChatMessages(@PathVariable("chatRoomId") Long chatRoomId,
		@RequestParam("page") int page, @RequestParam("type") String type) {
		Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, SORT_CREATING_CRITERIA));
		GetAllChatMessageReqDTO getAllChatMessageReqDTO = GetAllChatMessageReqDTO.builder()
			.chatRoomId(chatRoomId)
			.chatRoomType(type)
			.build();
		GetAllChatMessagesResDTO chatMessages = chatUseCase.getChatMessages(getAllChatMessageReqDTO, pageable);
		return ResponseEntity.ok(ResultResponse.of(GET_ALL_CHAT_MESSAGES, chatMessages));
	}

}
