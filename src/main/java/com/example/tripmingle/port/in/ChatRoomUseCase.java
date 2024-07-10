package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.chat.*;
import com.example.tripmingle.dto.res.chat.*;

public interface ChatRoomUseCase {

    EnterOneOnOneChatRoomResDTO enterOneOnOneChatRoom(EnterOneOnOneChatRoomReqDTO enterOneOnOneChatRoomReqDTO);

    EnterGroupChatRoomResDTO enterGroupChatRoom(EnterGroupChatRoomReqDTO enterGroupChatRoomReqDTO);

    ExitGroupChatRoomResDTO exitGroupChatRoom(ExitGroupChatRoomReqDTO exitGroupChatRoomReqDTO);

    ExitOneOnOneChatRoomResDTO exitOneOnOneChatRoom(ExitOneOnOneChatRoomReqDTO exitOneOnOneChatRoomReqDTO);
}
