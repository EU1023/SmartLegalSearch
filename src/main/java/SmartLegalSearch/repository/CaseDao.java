package SmartLegalSearch.repository;

import SmartLegalSearch.entity.Case;
import SmartLegalSearch.entity.CaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseDao extends JpaRepository<Case, CaseId> {


}
