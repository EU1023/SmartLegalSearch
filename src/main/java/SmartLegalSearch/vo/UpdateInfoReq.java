package SmartLegalSearch.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateInfoReq extends BasicRes {

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Role cannot be blank.")
    private String role;

    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters.")
    private String name;

    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 10, max = 10, message = "Phone must be exactly 10 digits")
    private String phone;

    public UpdateInfoReq() {
    }

    public UpdateInfoReq(int code, String message, String email, String role, String name, String phone) {
        super(code, message);
        this.email = email;
        this.role = role;
        this.name = name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
