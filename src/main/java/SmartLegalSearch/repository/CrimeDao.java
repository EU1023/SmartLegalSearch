package SmartLegalSearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SmartLegalSearch.entity.CriminalLawArticles;

public interface CrimeDao extends JpaRepository<CriminalLawArticles, String>{

}
