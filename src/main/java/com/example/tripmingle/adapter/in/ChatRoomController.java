package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.chat.*;
import com.example.tripmingle.dto.res.chat.*;
import com.example.tripmingle.port.in.ChatRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-room")
public class ChatRoomController {

    private final ChatRoomUseCase chatRoomUseCase;

    @PostMapping("/group")
    public ResponseEntity<ResultResponse> createGroupChatRoom(@RequestBody CreateGroupChatRoomReqDTO createChatRoomReqDTO) {
        CreateChatRoomResDTO createChatRoomResDTO = chatRoomUseCase.createGroupChatRoom(createChatRoomReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_GROUP_CHAT_ROOM_SUCCESS, createChatRoomResDTO));
    }

    @PostMapping("/one-on-one")
    public ResponseEntity<ResultResponse> createOneOnOneChatRoom(@RequestBody CreateOneOnOneChatRoomReqDTO createOneOnOneChatRoomReqDTO) {
        CreateOneOnOneChatRoomResDTO createOneOnOneChatRoomResDTO = chatRoomUseCase.createOneOnOneChatRoom(createOneOnOneChatRoomReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_ONE_ON_ONE_CHAT_ROOM_SUCCESS, createOneOnOneChatRoomResDTO));
    }

    @PostMapping("/group/enter")
    public ResponseEntity<ResultResponse> enterGroupChatRoom(@RequestBody EnterGroupChatRoomReqDTO enterGroupChatRoomReqDTO) {
        EnterGroupChatRoomResDTO enterGroupChatRoomResDTO = chatRoomUseCase.enterGroupChatRoom(enterGroupChatRoomReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.USER_ENTER_CHAT_ROOM_SUCCESS, enterGroupChatRoomResDTO));
    }

    @PatchMapping("/group/exit")
    public ResponseEntity<ResultResponse> exitGroupChatRoom(@RequestBody ExitGroupChatRoomReqDTO exitGroupChatRoomReqDTO) {
        ExitGroupChatRoomResDTO exitGroupChatRoomResDTO = chatRoomUseCase.exitGroupChatRoom(exitGroupChatRoomReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EXIT_GROUP_CHAT_ROOM_SUCCESS, exitGroupChatRoomResDTO));
    }

    @PatchMapping("/one-on-one/exit")
    public ResponseEntity<ResultResponse> exitOneOnOneChatRoom(@RequestBody ExitOneOnOneChatRoomReqDTO exitOneOnOneChatRoomReqDTO) {
        ExitOneOnOneChatRoomResDTO exitOneOnOneChatRoomResDTO = chatRoomUseCase.exitOneOnOneChatRoom(exitOneOnOneChatRoomReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EXIT_ONE_ON_ONE_CHAT_ROOM_SUCCESS, exitOneOnOneChatRoomResDTO));
    }

}