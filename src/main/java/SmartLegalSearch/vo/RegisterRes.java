package SmartLegalSearch.vo;

public class RegisterRes extends BasicRes {

    public enum RegisterStatus {
        PENDING, SUCCESS, ERROR
    }

    private String email;

    private String name;

    private RegisterStatus status; // pending、success、error

    private String role;

    public RegisterRes() {
    }

    public RegisterRes(int code, String message, String email, String name, RegisterStatus status, String role) {
        super(code, message);
        this.email = email;
        this.name = name;
        this.status = status;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public RegisterStatus getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }
}
