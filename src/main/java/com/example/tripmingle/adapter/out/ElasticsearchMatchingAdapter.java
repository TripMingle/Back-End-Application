package com.example.tripmingle.adapter.out;

import com.example.tripmingle.client.ElasticsearchMatchingClient;
import com.example.tripmingle.entity.UserPersonality;
import com.example.tripmingle.entity.pojo.ElasticsearchMatchingResponse;
import com.example.tripmingle.entity.pojo.ElasticsearchMatchingResponse2;
import com.example.tripmingle.port.out.MatchingSearchPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.tripmingle.common.constants.Constants.MAX_USER_MATCHING_SIZE;
import static com.example.tripmingle.common.constants.Constants.WEIGHTS;

@Component
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchMatchingAdapter implements MatchingSearchPort {
    private final ElasticsearchMatchingClient elasticsearchMatchingClient;

    @Override
    public List<Long> getSimilarUsersByUserPersonalityId(Long targetUserPersonalityId, double[] targetVector) {

        // 스크립트 소스 코드 정의
        String scriptSource = """
    List weightsList = params.weights;
    List targetVectorList = params.targetVector;
    
    double[] weights = new double[weightsList.size()];
    double[] targetVector = new double[targetVectorList.size()];
    
    for (int i = 0; i < weightsList.size(); i++) {
        weights[i] = ((Number) weightsList.get(i)).doubleValue();
        targetVector[i] = ((Number) targetVectorList.get(i)).doubleValue();
    }
    
    double[] userVector = new double[weights.length];
    userVector[0] = doc['gender'].value * weights[0];
    userVector[1] = doc['vegan'].value * weights[1];
    userVector[2] = doc['islam'].value * weights[2];
    userVector[3] = doc['hindu'].value * weights[3];
    userVector[4] = doc['smoking'].value * weights[4];
    userVector[5] = doc['budget'].value * weights[5];
    userVector[6] = doc['accommodationFlexibility'].value * weights[6];
    userVector[7] = doc['foodFlexibility'].value * weights[7];
    userVector[8] = doc['activity'].value * weights[8];
    userVector[9] = doc['photo'].value * weights[9];
    userVector[10] = doc['foodExploration'].value * weights[10];
    userVector[11] = doc['adventure'].value * weights[11];
    userVector[12] = doc['personality'].value * weights[12];
    userVector[13] = doc['schedule'].value * weights[13];
    userVector[14] = doc['drink'].value * weights[14];
    userVector[15] = doc['ageRange'].value * weights[15];
    
    double dotProduct = 0.0;
    double targetMagnitude = 0.0;
    double userMagnitude = 0.0;
    
    for (int i = 0; i < userVector.length; i++) {
        dotProduct += targetVector[i] * userVector[i];
        targetMagnitude += targetVector[i] * targetVector[i];
        userMagnitude += userVector[i] * userVector[i];
    }
    
    double denominator = Math.sqrt(targetMagnitude) * Math.sqrt(userMagnitude);
    if (denominator == 0) {
        return 0;
    }
    return dotProduct / denominator;
    """;

        // double 배열을 List<Double>로 변환
        List<Double> targetVectorList = Arrays.stream(targetVector).boxed().collect(Collectors.toList());
        List<Double> weightsList = Arrays.stream(WEIGHTS).boxed().collect(Collectors.toList());

        // 스크립트 파라미터 설정
        Map<String, Object> scriptParams = new HashMap<>();
        scriptParams.put("weights", weightsList);
        scriptParams.put("targetVector", targetVectorList);

        // 스크립트 설정
        Map<String, Object> script = new HashMap<>();
        script.put("source", scriptSource);
        script.put("params", scriptParams);

        // Bool query 설정
        Map<String, Object> mustNotClause = new HashMap<>();
        mustNotClause.put("term", Map.of("userPersonalityId", targetUserPersonalityId));

        Map<String, Object> boolQuery = new HashMap<>();
        boolQuery.put("must_not", List.of(mustNotClause));

        // 최종 쿼리 설정
        Map<String, Object> scriptScore = new HashMap<>();
        scriptScore.put("query", Map.of("bool", boolQuery));
        scriptScore.put("script", script);

        Map<String, Object> query = new HashMap<>();
        query.put("size", MAX_USER_MATCHING_SIZE);
        query.put("query", Map.of("script_score", scriptScore));

        log.info("Elasticsearch query: {}", query);

        // Elasticsearch 쿼리 실행
        ElasticsearchMatchingResponse response = elasticsearchMatchingClient.getSimilarUsers(query);

        // 결과에서 userPersonalityId 값을 추출하여 반환
        return response.getHits().getHits().stream()
                .map(hit -> hit.get_source().getUserPersonalityId())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getSimilarUsersByUserPersonalityIdByHNSW(Long targetUserPersonalityId, double[] targetVector) {
        List<Double> targetVectorList = Arrays.stream(targetVector).boxed().collect(Collectors.toList());

        Map<String, Object> vectorQuery = new HashMap<>();
        vectorQuery.put("vector", targetVectorList);
        vectorQuery.put("k", 50);

        Map<String, Object> knnQuery = new HashMap<>();
        knnQuery.put("user_vector", vectorQuery);

        // bool 쿼리로 must_not 절 추가
        Map<String, Object> boolQuery = new HashMap<>();
        boolQuery.put("must_not", List.of(Map.of("term", Map.of("user_personality_id", targetUserPersonalityId))));
        boolQuery.put("should", List.of(Map.of("knn", knnQuery)));

        Map<String, Object> query = new HashMap<>();
        query.put("size", 50);  // 반환할 문서의 수를 설정
        query.put("query", Map.of("bool", boolQuery));
        query.put("_source", List.of("user_id", "user_personality_id", "user_vector"));  // 반환할 필드 설정

        // Elasticsearch에 요청 실행
        ElasticsearchMatchingResponse2 response = elasticsearchMatchingClient.getSimilarUsersBYES(query);

        log.info("Elasticsearch query: {}", query);

        return response.getHits().getHits().stream()
                .map(hit -> hit.get_source().getUserPersonalityId())
                .collect(Collectors.toList());
    }


    @Override
    public void saveUserPersonality(UserPersonality userPersonality) {
        try {
            // Elasticsearch에 저장할 문서 생성
            Map<String, Object> document = new HashMap<>();
            document.put("user_id", userPersonality.getUser().getId());
            document.put("user_personality_id", userPersonality.getId());
            document.put("user_vector", userPersonality.toFeatureVector());

            elasticsearchMatchingClient.createOrUpdateUserPersonality(userPersonality.getId(), document);
            // Elasticsearch 클라이언트를 통해 문서 생성 또는 업데이트
        } catch (Exception e) {
            throw new RuntimeException("Failed to update Elasticsearch document for user personality ID: "
                    + userPersonality.getId(), e);
        }
    }

}
