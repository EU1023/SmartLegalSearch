package SmartLegalSearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SmartLegalSearch.entiy.CriminalLawArticles;

public interface CrimeDao extends JpaRepository<CriminalLawArticles, String>{

}
