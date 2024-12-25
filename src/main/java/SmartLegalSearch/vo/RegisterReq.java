package SmartLegalSearch.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterReq {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
    private String name; // 最少2個字，最多25個字

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Role cannot be blank")
    private String role;

    private String identity;

    private String phone;

    public RegisterReq() {
    }

    public RegisterReq(String email, String name, String password, String role, String identity, String phone) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.identity = identity;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getIdentity() {
        return identity;
    }

    public String getPhone() {
        return phone;
    }
}
