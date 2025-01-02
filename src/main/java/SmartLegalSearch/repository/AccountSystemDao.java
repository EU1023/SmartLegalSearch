package SmartLegalSearch.repository;

import SmartLegalSearch.entity.AccountSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountSystemDao extends JpaRepository<AccountSystem, Integer> {

    // 查詢 email是否有存在
    @Query(value = "SELECT COUNT(*) FROM account_system WHERE email = ?1", nativeQuery = true)
    Long existsByEmail(String email);

    // 根據 token 查找用戶
    @Query(value = "SELECT * FROM account_system WHERE email_verification_token = ?1", nativeQuery = true)
    AccountSystem findByEmailVerificationToken(String token);

    // 依 email 獲取用戶資料
    @Query(value = "SELECT * FROM account_system WHERE email = ?1", nativeQuery = true)
    AccountSystem findByEmail(String email);
}
