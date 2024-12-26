package SmartLegalSearch.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


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

    @Column(name = "email_verification_token")
    private String emailVerificationToken; // email 驗證 token

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry; // 儲存 token 過期時間


    public AccountSystem() {
    }

    public AccountSystem(String email, String name, String password, boolean emailVerified, String role, String identity, String phone, String emailVerificationToken, LocalDateTime tokenExpiry) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.emailVerified = emailVerified;
        this.role = role;
        this.identity = identity;
        this.phone = phone;
        this.emailVerificationToken = emailVerificationToken;
        this.tokenExpiry = tokenExpiry;
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

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public LocalDateTime getTokenExpiry() {
        return tokenExpiry;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public void setTokenExpiry(LocalDateTime tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }
}
