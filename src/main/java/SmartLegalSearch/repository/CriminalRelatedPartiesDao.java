package SmartLegalSearch.repository;

import SmartLegalSearch.entity.CriminalRelatedParties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriminalRelatedPartiesDao extends JpaRepository<CriminalRelatedParties, Integer> {
}
