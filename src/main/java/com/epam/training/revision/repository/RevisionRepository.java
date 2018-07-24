package com.epam.training.revision.repository;


import com.epam.training.revision.Revision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository

@ComponentScan("com.epam.training.*")
public class RevisionRepository extends NamedParameterJdbcDaoSupport {

    private static final String INSERT_REVISION_NEW =
            "INSERT into revision(total_quantities, total_price, revision_started, revision_ended) " +
                    "values(:total_quantities, :total_price, :revision_started, :revision_ended)";
    private static final String SELECT_REVISION_LAST_ENTERED =
            "select * from `revision` order by revision_id desc limit 1";
    private static final String SELECT_REVISIONS_FROM_TIMESTAMP =
            "select * from revision where " +
                    "(revision_started between :starting_time and revision_ended) and " +
                    "(revision_ended between revision_started and :ending_time)";

    @Autowired
    public RevisionRepository(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public Revision getLastRevisionEntered() {
        Map<String, Object> params = new HashMap<>();
        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).queryForObject(SELECT_REVISION_LAST_ENTERED,
                params,
                new RevisionRowMapper());
    }

    public List<Revision> getRevisionsInTimeInterval(Timestamp startingTime, Timestamp endingTime) {

        Map<String, Object> params = new HashMap<>();
        params.put("ending_time", endingTime.toString());
        params.put("starting_time", startingTime.toString());

        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).query(
                SELECT_REVISIONS_FROM_TIMESTAMP,
                params,
                new RevisionRowMapper());
    }

    public void insertNewRevision(Integer sumQuantity, BigDecimal sumOfPrice, Timestamp startingTime, Timestamp endingTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("total_quantities", sumQuantity);
        params.put("total_price", sumOfPrice);
        params.put("revision_started", startingTime);
        params.put("revision_ended", endingTime);
        Objects.requireNonNull(getNamedParameterJdbcTemplate()).update(INSERT_REVISION_NEW, params);
    }

    private static class RevisionRowMapper implements RowMapper<Revision> {
        @Override
        public Revision mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Revision(
                    rs.getInt("revision_id"),
                    rs.getInt("total_quantities"),
                    rs.getBigDecimal("total_price"),
                    rs.getTimestamp("revision_started"),
                    rs.getTimestamp("revision_ended"));
        }
    }
}

