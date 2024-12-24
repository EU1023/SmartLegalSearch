package SmartLegalSearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriminalRelatedPartiesDao extends JpaRepository<CriminalRelatedParties, Integer> {
}
