package SmartLegalSearch.repository;

import SmartLegalSearch.entity.CriminalCase;
import SmartLegalSearch.entity.CriminalCaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriminalCaseDao extends JpaRepository<CriminalCase, CriminalCaseId> {


}
