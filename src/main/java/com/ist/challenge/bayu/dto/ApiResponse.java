package com.ist.challenge.bayu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonPropertyOrder({
        "code",
        "success",
        "message",
        "data"
})
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    @JsonIgnore
    private HttpStatus status;

}
