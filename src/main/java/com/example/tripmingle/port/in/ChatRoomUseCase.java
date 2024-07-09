package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.chat.*;
import com.example.tripmingle.dto.res.chat.*;

public interface ChatRoomUseCase {
    CreateChatRoomResDTO createGroupChatRoom(CreateGroupChatRoomReqDTO createGroupChatRoomReqDTO);

    CreateOneOnOneChatRoomResDTO createOneOnOneChatRoom(CreateOneOnOneChatRoomReqDTO createOneOnOneChatRoomReqDTO);

    EnterGroupChatRoomResDTO enterGroupChatRoom(EnterGroupChatRoomReqDTO enterGroupChatRoomReqDTO);

    ExitGroupChatRoomResDTO exitGroupChatRoom(ExitGroupChatRoomReqDTO exitGroupChatRoomReqDTO);

    ExitOneOnOneChatRoomResDTO exitOneOnOneChatRoom(ExitOneOnOneChatRoomReqDTO exitOneOnOneChatRoomReqDTO);
}
