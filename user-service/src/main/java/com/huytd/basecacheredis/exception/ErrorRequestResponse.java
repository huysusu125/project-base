package com.huytd.basecacheredis.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorRequestResponse {

    private List<String> errorCodes;
    private String url;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    public ErrorRequestResponse() {
        this.timestamp = LocalDateTime.now();
    }
}
