package hello.siblings.jwt;

import org.springframework.http.HttpStatus;

public class JwtExceptionResponse {

    private HttpStatus status;
    private String message;

    public JwtExceptionResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;

    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
