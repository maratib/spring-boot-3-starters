package com.mak.exceptions;

import java.util.List;
// import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @XmlRootElement(name = "error")

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    // URI if available
    private String uri;

    // General error message about nature of error
    private String message;

    // Specific errors in API request processing
    private List<String> details;

    @Override
    public String toString() {
        String response = "{";
        response += getUri() != null ? " uri='" + getUri() + "', " : "";
        return response +
                "message='" + getMessage() + "'" +
                ", details='" + getDetails() + "'" +
                "}";
    }

}
