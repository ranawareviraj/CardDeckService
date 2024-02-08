package com.acme.carddeckservice.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The ErrorResponse class represents an error response.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_type")
    private String type;

    @JsonProperty("error_message")
    private String message;

    private Long timestamp;

}

