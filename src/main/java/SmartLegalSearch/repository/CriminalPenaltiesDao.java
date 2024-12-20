package SmartLegalSearch.repository;

import SmartLegalSearch.entity.CriminalPenalties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriminalPenaltiesDao extends JpaRepository<CriminalPenalties, Integer> {
}
