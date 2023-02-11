package com.ist.challenge.bayu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MessageResponse {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

}
