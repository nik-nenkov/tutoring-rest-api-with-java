package com.epam.training.revision.repository;


import com.epam.training.revision.Revision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
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
            "INSERT into revision(total_quantities, total_price, revision_started) " +
                    "values(:total_quantities, :total_price, :revision_started)";
    private static final String SELECT_REVISION_LAST_ENTERED =
            "select * from `revision` order by revision_id desc limit 1";
    private static final String SELECT_REVISIONS_FROM_TIMESTAMP =
            "select * from revision where " +
                    "(revision_started between unix_timestamp(:starting_time) and unix_timestamp(revision_ended)) and " +
                    "(revision_ended between unix_timestamp(revision_started) and unix_timestamp(:ending_time))";
    private DataSource dataSource;

    @Autowired
    public RevisionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public void insertNewRevision(Integer sumQuantity, Float sumOfPrice, Timestamp startingTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("total_quantities", sumQuantity);
        params.put("total_price", sumOfPrice);
        params.put("revision_started", startingTime);
        Objects.requireNonNull(getNamedParameterJdbcTemplate()).update(INSERT_REVISION_NEW, params);
    }

    public Revision getLastRevisionEntered() {
        Map<String, Object> params = new HashMap<>();
        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).queryForObject(SELECT_REVISION_LAST_ENTERED,
                params,
                new RevisionRowMapper());
    }

    public List<Revision> getRevisionsInTimeInterval(Timestamp startingTime, Timestamp endingTime) {

        Map<String, Object> params = new HashMap<>();
        params.put("ending_time", endingTime);
        params.put("starting_time", startingTime);

        return Objects.requireNonNull(getNamedParameterJdbcTemplate()).query(
                SELECT_REVISIONS_FROM_TIMESTAMP,
                params,
                new RevisionRowMapper());
    }

    private static class RevisionRowMapper implements RowMapper<Revision> {
        @Override
        public Revision mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Revision(
                    rs.getInt("revision_id"),
                    rs.getInt("total_quantities"),
                    rs.getFloat("total_price"),
                    rs.getTimestamp("revision_started"),
                    rs.getTimestamp("revision_ended"));
        }
    }
}

