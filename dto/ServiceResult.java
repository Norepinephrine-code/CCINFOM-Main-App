package dto;

public class ServiceResult {

    private final boolean status;
    private final String message;

    public ServiceResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean getStatus() {        // FOR THE STATUS (Success/Failed)
        return status;
    }

    public String getMessage() {        // FOR THE ERROR MESSAGE
        return message;
    }
}
