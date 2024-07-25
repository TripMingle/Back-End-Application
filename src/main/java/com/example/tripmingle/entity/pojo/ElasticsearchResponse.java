package com.example.tripmingle.entity.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ElasticsearchResponse {
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
                private String board_id;
                private String title;
                private String content;
                private String country_name;
                private String gender;
                private String language;
            }
        }
    }
}