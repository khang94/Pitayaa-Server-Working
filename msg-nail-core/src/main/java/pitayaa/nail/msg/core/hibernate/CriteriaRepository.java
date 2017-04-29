package pitayaa.nail.msg.core.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import pitayaa.nail.msg.core.common.CriteriaConstant;

@Service
@Repository
public class CriteriaRepository {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(CriteriaRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	public SearchCriteria extractQuery(String query) {
		String[] statements = query.split(CriteriaConstant.AND_STATEMENT);

		SearchCriteria sc = new SearchCriteria();
		List<SearchCriteriaAttribute> lstAttribute = new ArrayList<SearchCriteriaAttribute>();
		sc.setAttributes(lstAttribute);

		for (String stm : statements) {
			SearchCriteriaAttribute criteriaAttr = new SearchCriteriaAttribute();

			String[] attribute = stm.split(getSqlOperation(stm));
			String attributePath = this.validateAttributePath(attribute[0]);
			criteriaAttr.setAttributePath(attributePath);
			criteriaAttr.setAttributeName(this.getAttributeName(attributePath));
			criteriaAttr.setOperation(this.getSqlOperation(stm));
			criteriaAttr.setValue(attribute[1]);

			sc.getAttributes().add(criteriaAttr);
		}
		return sc;
	}

	private String validateAttributePath(String attributePath) {
		if (attributePath.startsWith(CriteriaConstant.CRITERIA_PATH)) {
			return attributePath;
		}
		return CriteriaConstant.CRITERIA_PATH + attributePath;
	}

	private String getSqlOperation(String statement) {
		String sqlOperation = "";
		if (statement.contains(CriteriaConstant.EQUAL)
				&& !statement.contains(CriteriaConstant.MORE_THAN)
				&& !statement.contains(CriteriaConstant.LESS_THAN)) {
			sqlOperation = CriteriaConstant.EQUAL;
		} else if (statement.contains(CriteriaConstant.COLON)) { // :
			sqlOperation = CriteriaConstant.COLON;
		} else if (statement.contains(CriteriaConstant.MORE_THAN)
				&& !statement.contains(CriteriaConstant.EQUAL)) {
			sqlOperation = CriteriaConstant.MORE_THAN;
		} else if (statement.contains(CriteriaConstant.LESS_THAN)
				&& !statement.contains(CriteriaConstant.EQUAL)) {
			sqlOperation = CriteriaConstant.LESS_THAN;
		} else if (statement.contains(CriteriaConstant.MORE_EQUAL_THAN)) {
			sqlOperation = CriteriaConstant.MORE_EQUAL_THAN;
		} else if (statement.contains(CriteriaConstant.LESS_EQUAL_THAN)) {
			sqlOperation = CriteriaConstant.LESS_EQUAL_THAN;
		}
		return sqlOperation;
	}

	public List<?> searchCriteria(SearchCriteria criteria)
			throws ClassNotFoundException {

		String query = this.buildQuery(criteria);
		TypedQuery<?> queryObject = this.executeQuery(query, criteria);
		return queryObject.getResultList();
	}

	private String buildQuery(SearchCriteria criteria) {

		LOGGER.info("Prepare for building query !");
		StringBuilder query = new StringBuilder();
		query.append("Select " + CriteriaConstant.CRITERIA + " from "
				+ criteria.getEntity() + CriteriaConstant.SPACE
				+ CriteriaConstant.CRITERIA);

		if (criteria.getAttributes() != null
				& criteria.getAttributes().size() > 0) {
			query.append(CriteriaConstant.WHERE);
			boolean isFirstStatement = true;
			for (SearchCriteriaAttribute sca : criteria.getAttributes()) {
				if (!isFirstStatement) {
					query.append(CriteriaConstant.AND);
				}
				if (sca.getOperation().equalsIgnoreCase(CriteriaConstant.COLON)) {
					query.append(sca.getAttributePath() + CriteriaConstant.LIKE
							+ CriteriaConstant.COLON + sca.getAttributeName());
				} else {
					query.append(sca.getAttributePath()
							+ CriteriaConstant.SPACE + sca.getOperation()
							+ CriteriaConstant.SPACE + CriteriaConstant.COLON
							+ sca.getAttributeName());
				}
				isFirstStatement = false;
			}
			LOGGER.info("Query after building : " + query.toString());
		}
		return query.toString();
	}

	public String getAttributeName(String attributePath) {
		int dotIndex = attributePath.lastIndexOf(CriteriaConstant.DOT);
		return attributePath.substring(dotIndex + 1, attributePath.length());
	}

	private TypedQuery<?> executeQuery(String query, SearchCriteria criteria) {

		LOGGER.info("Prepare for execute query !");
		TypedQuery<Object> queryExecute = entityManager.createQuery(query,
				Object.class);

		LOGGER.info("Create query success ! Setting param for query ...");
		for (SearchCriteriaAttribute sca : criteria.getAttributes()) {

			LOGGER.info("Setting attribute = [" + sca.getAttributePath() + "]"
					+ " with value = [" + sca.getValue() + "]");
			if (CriteriaConstant.COLON.equalsIgnoreCase(sca.getOperation())) {
				queryExecute.setParameter(sca.getAttributeName(),
						"%" + sca.getValue() + "%");
			} else {
				queryExecute.setParameter(sca.getAttributeName(),
						sca.getValue());
			}

		}

		LOGGER.info("Set attribute success !");
		return queryExecute;
	}

}
