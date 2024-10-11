package com.example.tripmingle.entity.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ElasticsearchMatchingResponse {
    private Hits hits;

    @Getter
    @Setter
    public static class Hits {
        private List<Hit> hits;

        @Getter
        @Setter
        public static class Hit {
            private String _index;
            private String _id;
            private double _score;
            private Source _source;

            @Getter
            @Setter
            public static class Source {
                private Long userPersonalityId;
                private Long userId;
                private double gender;
                private double vegan;
                private double islam;
                private double hindu;
                private double smoking;
                private double budget;
                private double accommodationFlexibility;
                private double foodFlexibility;
                private double activity;
                private double photo;
                private double foodExploration;
                private double adventure;
                private double personality;
                private double schedule;
                private double drink;
                private double ageRange;
            }
        }
    }
}
