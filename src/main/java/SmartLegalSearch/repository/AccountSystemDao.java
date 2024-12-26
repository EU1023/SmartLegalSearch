package SmartLegalSearch.repository;

import SmartLegalSearch.entity.AccountSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountSystemDao extends JpaRepository<AccountSystem, Integer> {

    @Query(value = "SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM account_system a WHERE a.email = ?1", nativeQuery = true)
    boolean existsByEmail(String email);
}
