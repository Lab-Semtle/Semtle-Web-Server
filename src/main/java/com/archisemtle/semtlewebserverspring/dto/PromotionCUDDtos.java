package com.archisemtle.semtlewebserverspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PromotionCUDDtos {
    @Data
    @AllArgsConstructor
    public static class Create {
        private String message;
        @JsonProperty("project_id")
        private Long projectId;
        @JsonProperty("created_at")
        private String createdAt;
    }

    @Data
    @AllArgsConstructor
    public static class Delete {
        private String message;
        @JsonProperty("deleted_at")
        private String deletedAt;
    }

    @Data
    @AllArgsConstructor
    public static class Update {
        private String message;
        @JsonProperty("updated_at")
        private String updatedAt;
    }
}
