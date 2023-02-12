package com.ist.challenge.bayu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonPropertyOrder({
        "code",
        "success",
        "message"
})
@NoArgsConstructor
public class MessageResponse {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    public MessageResponse(Integer code, Boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }
}
