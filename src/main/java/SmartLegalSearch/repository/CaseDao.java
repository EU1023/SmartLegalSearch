package SmartLegalSearch.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SmartLegalSearch.entity.LegalCase;
import SmartLegalSearch.entity.CaseId;

@Repository
public interface CaseDao extends JpaRepository<LegalCase, CaseId> {

	// 搜尋
	/**
	 * 使用 concat 是因為 :name 站位符號不能使用 '%:name%' 這種寫法 <br>
	 * 會被 SQL 視為一個單字，而不是 %模糊搜尋內容%。 
	 * 
	 * @param name 模糊搜尋名稱
	 * @param startDate 開始時間
	 * @param endDate 結束時間
	 * @param id 案號
	 * @param charge 法院
	 * @param caseType 案件類型
	 * @param docType 文件類型
	 * @param courtList 法院陣列
	 * @param lawList 法條陣列
	 * @return Case 陣列
	 */
	@Query(value = "select group_id, id, court, date, url, charge, judge_name, " //
	        + " defendant_name, text, law, case_type, doc_type " //
	        + " from legal_case " //
	        + " where text like concat('%', :name, '%') " // 
	        + " and date between :startDate and :endDate" // date 在開始時間跟結束時間之間
	        + " and id like :id " // 
	        + " and charge like :charge " // 
	        + " and case_type like :caseType " // 
	        + " and doc_type like :docType " //
	        + " and (:courtList is null or court in (:courtList)) " // 如果 courtList 是 null 時就不會搜尋
	        + " and (:lawList is null or law in (:lawList))", // 如果 lawList 是 null 時就不會搜尋
	        nativeQuery = true) //
	public List<LegalCase> searchByConditions( //
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
