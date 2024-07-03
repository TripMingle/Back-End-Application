package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.MatchingService;
import com.example.tripmingle.application.service.UserPersonalityService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.MatchingServerException;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.matching.PostUserPersonalityReqDTO;
import com.example.tripmingle.dto.res.matching.AddUserResDTO;
import com.example.tripmingle.dto.res.matching.MatchingUserResDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.port.in.MatchingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MatchingFacadeService implements MatchingUseCase {
    private final MatchingService matchingService;
    private final UserService userService;
    private final UserPersonalityService userPersonalityService;
    private final ConcurrentMap<String, DeferredResult<ResponseEntity<ResultResponse>>> pendingResults = new ConcurrentHashMap<>();

    @Override
    public Page<MatchingUserResDTO> getMyMatchingUsers(Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        userPersonalityService.existsUserPersonalityByUser(currentUser);
        List<Long> similarUserIds = matchingService.getSimilarUserIds(currentUser.getId());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), similarUserIds.size());
        if (start > similarUserIds.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, similarUserIds.size()); // 요청한 페이지가 범위를 벗어나는 경우 빈 리스트 반환
        }
        similarUserIds = similarUserIds.subList(start, end);

        List<MatchingUserResDTO> matchingUserResDTOList = similarUserIds.stream().map(userId->{
            User user = userService.getUserById(userId);
            UserPersonality userPersonality = userPersonalityService.getUserPersonalityById(user.getId());
            return MatchingUserResDTO.builder()
                    .userId(user.getId())
                    .nickName(user.getNickName())
                    .ageRange(user.getAgeRange())
                    .gender(user.getGender())
                    .nationality(user.getNationality())
                    .selfIntroduction(user.getSelfIntroduction())
                    .userPersonalityId(userPersonality.getId())
                    .vegan(userPersonality.getVegan())
                    .islam(userPersonality.getIslam())
                    .hindu(userPersonality.getHindu())
                    .smoking(userPersonality.getSmoking())
                    .budget(userPersonality.getBudget())
                    .accommodationFlexibility(userPersonality.getAccommodationFlexibility())
                    .foodFlexibility(userPersonality.getFoodFlexibility())
                    .activity(userPersonality.getActivity())
                    .instagramPicture(userPersonality.getInstagramPicture())
                    .foodExploration(userPersonality.getFoodExploration())
                    .adventure(userPersonality.getAdventure())
                    .personality(userPersonality.getPersonality())
                    .schedule(userPersonality.getSchedule())
                    .build();

        }).collect(Collectors.toList());

        return new PageImpl<>(matchingUserResDTOList, pageable, matchingUserResDTOList.size());
    }

    @Override
    @Transactional
    public AddUserResDTO postUserPersonality(UserPersonality userPersonality) {
        String messageId = UUID.randomUUID().toString();
        String response = "";
        try {
            CompletableFuture<String> future = matchingService.addUser(userPersonality.getId(), messageId);
            response = future.get();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(response.equals("FAIL")){
            throw new MatchingServerException("matching server error", ErrorCode.MATCHING_SERVER_EXCEPTION);
        }

        return AddUserResDTO.builder().resultMessage(response).build();
    }

    @Override
    @Transactional
    public UserPersonality saveUserPersonality(PostUserPersonalityReqDTO postUserPersonalityReqDTO) {
        User currentUser = userService.getCurrentUser();
        return userPersonalityService.saveUserPersonality(postUserPersonalityReqDTO, currentUser);
    }

    public void handleCriticalError(String requestId) {
        DeferredResult<ResponseEntity<ResultResponse>> deferredResult = pendingResults.remove(requestId);
        if (deferredResult != null) {
            deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultResponse.of(ResultCode.FAIL_ERROR)));
        }
    }


}
