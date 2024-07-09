package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.res.chat.GetAllChatMessagesResDTO;
import com.example.tripmingle.port.in.ChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.example.tripmingle.common.result.ResultCode.GET_ALL_CHAT_MESSAGES;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatUseCase chatUseCase;

    @GetMapping("/chat-rooms/{chatRoomId}/messages")
    public ResponseEntity<ResultResponse> getChatMessages(@PathVariable("chatRoomId") Long chatRoomId,
                                                          @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        GetAllChatMessagesResDTO chatMessages = chatUseCase.getChatMessages(chatRoomId, pageable);
        return ResponseEntity.ok(ResultResponse.of(GET_ALL_CHAT_MESSAGES, chatMessages));
    }

}
