package SmartLegalSearch.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SmartLegalSearch.entity.Case;
import SmartLegalSearch.entity.CaseId;

@Repository
public interface CaseDao extends JpaRepository<Case, CaseId> {

	// 搜尋
	@Query(value = "select group_id, id, court, date, url, charge, judge_name, " //
			+ " defendant_name, text, law, case_type, doc_type " //
			+ " from criminal_case" //
			+ " where text like '%:name%' " //
			+ " and date >= :startDate " //
			+ " and date <= :endDate " //
			+ " and id like '%:id%' " //
			+ " and charge like '%:charge%' " //
			+ " and case_type like '%:caseType%' " //
			+ " and doc_type like '%:docType%' " //
			+ " and court in (:courtList) " //
			+ " and law in (:lawList)", nativeQuery = true) //
	public List<Case> searchByConditions( //
			@Param("name") String name, //
			@Param("startDate") LocalDate startDate, //
			@Param("endDate") LocalDate endDate, //
			@Param("id") String id, //
			@Param("charge") String charge, //
			@Param("caseType") String caseType, //
			@Param("docType") String docType, //
			@Param("courtList") List<String> courtList, //
			@Param("lawList") List<String> lawList);
}
