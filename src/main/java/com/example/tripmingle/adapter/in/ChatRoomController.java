package com.example.tripmingle.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.chat.EnterGroupChatRoomReqDTO;
import com.example.tripmingle.dto.req.chat.EnterOneOnOneChatRoomReqDTO;
import com.example.tripmingle.dto.req.chat.ExitGroupChatRoomReqDTO;
import com.example.tripmingle.dto.req.chat.ExitOneOnOneChatRoomReqDTO;
import com.example.tripmingle.dto.res.chat.EnterGroupChatRoomResDTO;
import com.example.tripmingle.dto.res.chat.EnterOneOnOneChatRoomResDTO;
import com.example.tripmingle.dto.res.chat.ExitGroupChatRoomResDTO;
import com.example.tripmingle.dto.res.chat.ExitOneOnOneChatRoomResDTO;
import com.example.tripmingle.port.in.ChatRoomUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "채팅방")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-room")
public class ChatRoomController {

	private final ChatRoomUseCase chatRoomUseCase;

	@Operation(summary = "1대1 채팅방 입장")
	@PostMapping("/one-on-one/enter")
	public ResponseEntity<ResultResponse> enterOneOnOneChatRoom(
		@RequestBody EnterOneOnOneChatRoomReqDTO enterOneOnOneChatRoomReqDTO) {
		EnterOneOnOneChatRoomResDTO enterOneOnOneChatRoomResDTO = chatRoomUseCase.enterOneOnOneChatRoom(
			enterOneOnOneChatRoomReqDTO);
		return ResponseEntity.ok(
			ResultResponse.of(ResultCode.CREATE_ONE_ON_ONE_CHAT_ROOM_SUCCESS, enterOneOnOneChatRoomResDTO));
	}

	@Operation(summary = "그룹 채팅방 입장")
	@PostMapping("/group/enter")
	public ResponseEntity<ResultResponse> enterGroupChatRoom(
		@RequestBody EnterGroupChatRoomReqDTO enterGroupChatRoomReqDTO) {
		EnterGroupChatRoomResDTO enterGroupChatRoomResDTO = chatRoomUseCase.enterGroupChatRoom(
			enterGroupChatRoomReqDTO);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_ENTER_CHAT_ROOM_SUCCESS, enterGroupChatRoomResDTO));
	}

	@Operation(summary = "그룹 채팅방 퇴장")
	@PatchMapping("/group/exit")
	public ResponseEntity<ResultResponse> exitGroupChatRoom(
		@RequestBody ExitGroupChatRoomReqDTO exitGroupChatRoomReqDTO) {
		ExitGroupChatRoomResDTO exitGroupChatRoomResDTO = chatRoomUseCase.exitGroupChatRoom(exitGroupChatRoomReqDTO);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.EXIT_GROUP_CHAT_ROOM_SUCCESS, exitGroupChatRoomResDTO));
	}

	@Operation(summary = "1대1 채팅방 퇴장")
	@PatchMapping("/one-on-one/exit")
	public ResponseEntity<ResultResponse> exitOneOnOneChatRoom(
		@RequestBody ExitOneOnOneChatRoomReqDTO exitOneOnOneChatRoomReqDTO) {
		ExitOneOnOneChatRoomResDTO exitOneOnOneChatRoomResDTO = chatRoomUseCase.exitOneOnOneChatRoom(
			exitOneOnOneChatRoomReqDTO);
		return ResponseEntity.ok(
			ResultResponse.of(ResultCode.EXIT_ONE_ON_ONE_CHAT_ROOM_SUCCESS, exitOneOnOneChatRoomResDTO));
	}

}
