package org.springframework.batch.item.database.support;

/**
 * Sybase implementation of a  {@link PagingQueryProvider} using
 * database specific features.
 *
 * @author Thomas Risberg
 * @since 2.0
 */
public class SybasePagingQueryProvider extends SqlWindowingPagingQueryProvider {

	@Override
	public String generateFirstPageQuery(int pageSize) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append("TOP ").append(pageSize).append(" ").append(getSelectClause());
		sql.append(" FROM ").append(getFromClause());
		sql.append(getWhereClause() == null ? "" : " WHERE " + getWhereClause());
		sql.append(" ORDER BY ").append(getSortKey()).append(" ASC");

		return sql.toString();
	}

	@Override
	public String generateRemainingPagesQuery(int pageSize) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ").append("TOP ").append(pageSize).append(" ").append(getSelectClause());
		sql.append(" FROM ").append(getFromClause());
		sql.append(" WHERE ");
		if (getWhereClause() != null) {
			sql.append(getWhereClause());
			sql.append(" AND ");
		}
		sql.append(getSortKey()).append(" > ").append(getSortKeyPlaceHolder());
		sql.append(" ORDER BY ").append(getSortKey()).append(" ASC");

		return sql.toString();
	}
}
