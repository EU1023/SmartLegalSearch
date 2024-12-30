package SmartLegalSearch.repository;

import SmartLegalSearch.entity.AccountSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountSystemDao extends JpaRepository<AccountSystem, Integer> {

    @Query(value = "SELECT COUNT(*) FROM account_system WHERE email = ?1", nativeQuery = true)
    Long existsByEmail(String email);

    @Query(value = "SELECT * FROM account_system WHERE email_verification_token = ?1", nativeQuery = true)
    AccountSystem findByEmailVerificationToken(String token);
}
