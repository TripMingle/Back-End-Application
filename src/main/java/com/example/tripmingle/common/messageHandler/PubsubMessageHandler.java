package com.example.tripmingle.common.messageHandler;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class PubsubMessageHandler {
    private final ConcurrentMap<String, DeferredResult<ResponseEntity<ResultResponse>>> pendingResults = new ConcurrentHashMap<>();

    public void handleMessage(String requestId) {
        DeferredResult<ResponseEntity<ResultResponse>> deferredResult = pendingResults.remove(requestId);
        if (deferredResult != null) {
            deferredResult.setResult(ResponseEntity.ok(ResultResponse.of(ResultCode.ADD_USER_PERSONALITY_SUCCESS)));
        }
    }

    public void handleMessageError(String requestId) {
        DeferredResult<ResponseEntity<ResultResponse>> deferredResult = pendingResults.remove(requestId);
        if (deferredResult != null) {
            deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultResponse.of(ResultCode.INTERNAL_ERROR)));
        }
    }

    public void handleCriticalError(String requestId) {
        DeferredResult<ResponseEntity<ResultResponse>> deferredResult = pendingResults.remove(requestId);
        if (deferredResult != null) {
            deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultResponse.of(ResultCode.FAIL_ERROR)));
        }
    }

    public void registerPendingResult(String requestId, DeferredResult<ResponseEntity<ResultResponse>> deferredResult) {
        pendingResults.put(requestId, deferredResult);
    }

}
