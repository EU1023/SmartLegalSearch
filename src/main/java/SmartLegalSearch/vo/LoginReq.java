package SmartLegalSearch.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginReq {

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    private String password;

    public LoginReq() {
    }

    public LoginReq(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
