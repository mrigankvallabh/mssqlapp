package com.example.mssqlapp;

import java.sql.Types;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PoDBCreationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoDBCreationService.class);

    @Autowired JdbcTemplate jdbcTemplate;

    @Async
    public CompletableFuture<Object> createPO(String jsonPoString) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("mg_create_fcm_po");

		jdbcCall.addDeclaredParameter(new SqlParameter("@jsonPo", Types.NVARCHAR));
		Map<String, Object> result = jdbcCall.execute(jsonPoString);
        LOGGER.debug("result-set-1 = {}", result.get("#result-set-1"));
        return CompletableFuture.completedFuture(result.get("#result-set-1"));
    }
}
