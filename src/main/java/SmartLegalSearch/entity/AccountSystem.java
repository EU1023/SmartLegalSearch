package SmartLegalSearch.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "account_system")
public class AccountSystem {

    @Id
    @Column(name = "email", nullable = false)
    private String email; // 用戶帳戶，不能重複

    @Column(name = "name" , nullable = false)
    private String name; // 用戶名稱

    @Column(name = "password", nullable = false)
    private String password; // 用戶密碼（加密後儲存）

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false; // 用戶帳號狀態，預設 false，第一次需要 email 認證

    @Column(name = "role", nullable = false)
    private String role = "user"; // 用戶角色，預設 user，管理者為 admin

    @Column(name = "identity")
    private String identity; // 身分證

    @Column(name = "phone")
    private String phone; // 電話

    public AccountSystem() {
    }

    public AccountSystem(String email, String name, String password, boolean emailVerified, String role, String identity, String phone) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.emailVerified = emailVerified;
        this.role = role;
        this.identity = identity;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEmailVerified() {
        return emailVerified;
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
