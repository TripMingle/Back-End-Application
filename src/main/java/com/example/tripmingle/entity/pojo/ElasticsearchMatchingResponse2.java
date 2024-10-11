package com.example.tripmingle.entity.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ElasticsearchMatchingResponse2 {
    private Hits hits;

    @Getter
    @Setter
    public static class Hits {
        private List<Hit> hits;
    }

    @Getter
    @Setter
    public static class Hit {
        private Source _source;

    }

    @Getter
    @Setter
    public static class Source {
        @JsonProperty("user_personality_id")
        private Long userPersonalityId;
        @JsonProperty("user_vector")
        private double[] userVector;
    }
}
