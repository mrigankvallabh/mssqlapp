package com.example.mssqlapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PoProcessor implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoProcessor.class);

    @Autowired
	JdbcTemplate jdbcTemplate;

    @Autowired PoDBCreationService service;
    
    @Override
    public void run(String... args) throws Exception {
        long startTime = System.currentTimeMillis();
        /*
        // Do something
        LOGGER.info("Fetching from part_2");

		int numRows = jdbcTemplate.queryForObject(
				"SELECT count(1) FROM INGREDIENT;",
				Integer.class);

		LOGGER.info("numRows = {}", numRows);

		// 1st way to call SP - Easiest
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("usp_get_ingredient");

		jdbcCall.addDeclaredParameter(new SqlParameter("@id", Types.VARCHAR));
		Map<String, Object> result = jdbcCall.execute("CARN");
		LOGGER.info("1. result-set-1 = {}", result.get("#result-set-1"));

		// 2nd way - Using CallableStatementCreator functional interface and override CallableStatement createCallableStatement(Connection con) throws SQLException;
		result = jdbcTemplate.call(
			con -> {
				CallableStatement statement = con.prepareCall("{ call usp_get_ingredient (?) }");
				statement.setString(1, "CARN");
				return statement;
			},
			Arrays.asList(new SqlParameter("@id", Types.VARCHAR))
		);

		LOGGER.info("2. result-set-1 = {}", result.get("#result-set-1"));

		MySp mySp = new MySp(jdbcTemplate, "usp_get_ingredient");
		mySp.declareParameter(new SqlParameter("@id", Types.VARCHAR));
		mySp.compile();
		result = mySp.execute("CARN");
		LOGGER.info("3. result-set-1 = {}", result.get("#result-set-1"));

		String json = "{\"po_no\":\"5500658916\",\"client_no\":\"FCM\",\"id\":\"FCM5500658916146070\",\"dc_no\":\"146070\",\"dc_nm\":\"DC Yusen, Strančice\",\"po_items\":[{\"id\":\"FCM55006589162172819X2022-06-152022-06-15\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":6,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-06-15T00:00:00\",\"dely_end_dt\":\"2022-06-15T00:00:00\",\"dely_window_unit\":\"D\",\"updt_dt\":\"2022-05-27T18:27:25\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-06-162022-06-16\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":18,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-06-16T00:00:00\",\"dely_end_dt\":\"2022-06-16T00:00:00\",\"dely_window_unit\":\"D\",\"updt_dt\":\"2022-05-27T18:27:25\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-06-172022-06-17\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":12,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-06-17T00:00:00\",\"dely_end_dt\":\"2022-06-17T00:00:00\",\"dely_window_unit\":\"D\",\"updt_dt\":\"2022-05-27T18:27:25\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-06-202022-06-26\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":96,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-06-20T00:00:00\",\"dely_end_dt\":\"2022-06-26T00:00:00\",\"dely_window_unit\":\"W\",\"updt_dt\":\"2022-05-27T18:27:26\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-06-272022-07-03\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":108,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-06-27T00:00:00\",\"dely_end_dt\":\"2022-07-03T00:00:00\",\"dely_window_unit\":\"W\",\"updt_dt\":\"2022-05-27T18:27:26\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-07-042022-07-10\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":90,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-07-04T00:00:00\",\"dely_end_dt\":\"2022-07-10T00:00:00\",\"dely_window_unit\":\"W\",\"updt_dt\":\"2022-05-27T18:27:26\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-07-112022-07-17\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":72,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-07-11T00:00:00\",\"dely_end_dt\":\"2022-07-17T00:00:00\",\"dely_window_unit\":\"W\",\"updt_dt\":\"2022-05-27T18:27:26\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-07-182022-07-24\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":120,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-07-18T00:00:00\",\"dely_end_dt\":\"2022-07-24T00:00:00\",\"dely_window_unit\":\"W\",\"updt_dt\":\"2022-05-27T18:27:27\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-08-082022-08-14\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":42,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-08-08T00:00:00\",\"dely_end_dt\":\"2022-08-14T00:00:00\",\"dely_window_unit\":\"W\",\"updt_dt\":\"2022-05-27T18:27:27\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-08-152022-08-21\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":42,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-08-15T00:00:00\",\"dely_end_dt\":\"2022-08-21T00:00:00\",\"dely_window_unit\":\"W\",\"updt_dt\":\"2022-05-27T18:27:27\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-08-222022-08-28\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":42,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-08-22T00:00:00\",\"dely_end_dt\":\"2022-08-28T00:00:00\",\"dely_window_unit\":\"W\",\"updt_dt\":\"2022-05-27T18:27:28\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-08-292022-09-04\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":42,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-08-29T00:00:00\",\"dely_end_dt\":\"2022-09-04T00:00:00\",\"dely_window_unit\":\"W\",\"updt_dt\":\"2022-05-27T18:27:28\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"},{\"id\":\"FCM55006589162172819X2022-09-052022-09-11\",\"del_flg\":false,\"line_no\":\"1\",\"sku_no\":\"2172819X\",\"sku_nm\":\"LATERAL BRACKET SYST. 1B - 8 D1B\",\"sku_qty1\":18,\"sku_qty_unit1\":\"PCE\",\"dely_begin_dt\":\"2022-09-05T00:00:00\",\"dely_end_dt\":\"2022-09-11T00:00:00\",\"dely_window_unit\":\"W\",\"updt_dt\":\"2022-05-27T18:27:29\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\"}],\"business_party\":[{\"type\":\"4\",\"code\":\"0000229581\",\"address1\":\"15 &16 NOIDA SPECIAL ECONOMIC ZONE\",\"address2\":\"\",\"address3\":\"\",\"address4\":\"\",\"address5\":\"\",\"name\":\"VICTORA AUTO PRIVATE LIMITED\",\"street\":\"\",\"city\":\"\",\"state\":\"\",\"zip\":\"\",\"cntry\":\"IN\"},{\"type\":\"22\",\"code\":\"1511\",\"address1\":\"Keulsebaan 505-507\",\"address2\":\"\",\"address3\":\"\",\"address4\":\"6045 GG ROERMOND\",\"address5\":\"\",\"name\":\"FECT Europe\",\"street\":\"\",\"city\":\"\",\"state\":\"\",\"zip\":\"\",\"cntry\":\"NL\",\"buyer_contact_nm\":\"Sebastian Lukaszew\"},{\"type\":\"3\",\"code\":\"1511\",\"address1\":\"Keulsebaan 505-507\",\"address2\":\"\",\"address3\":\"\",\"address4\":\"6045 GG ROERMOND\",\"address5\":\"\",\"name\":\"FECT NETHERLANDS B.V.\",\"street\":\"\",\"city\":\"\",\"state\":\"\",\"zip\":\"\",\"cntry\":\"NL\"}],\"etry_dt\":\"2022-02-28T04:33:31\",\"etry_usr\":\"EDI_FAURECIA_DELFOR\",\"po_dt\":\"2022-05-27T00:01:00\",\"po_qty\":708,\"po_qty_unit\":\"PCE\",\"updt_dt\":\"2022-05-27T18:27:01\",\"updt_usr\":\"EDI_FAURECIA_DELFOR\",\"vendor_cd\":\"0000229581\",\"version\":\"31\"}";
		MySp mySp2 = new MySp(jdbcTemplate, "mg_create_fcm_po");
		mySp2.declareParameter(new SqlParameter("@jsonPo", Types.NVARCHAR));
		mySp2.compile();
		result = mySp2.execute(json);

		LOGGER.info("4. result-set-1 = {}", result.get("#result-set-1"));

        // Something done
        */

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Object>> typeReference = new TypeReference<List<Object>>() { };

        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/trialPoFile.json");
        List<Object> objects = mapper.readValue(inputStream, typeReference);
        List<CompletableFuture<Object>> resultFutures = new ArrayList<>();
        for (Object object : objects) {
            String jsonPoString = mapper.writeValueAsString(object);
            resultFutures.add(service.createPO(jsonPoString));
        }

        CompletableFuture<?>[] cfArray = new CompletableFuture<?>[resultFutures.size()];
        CompletableFuture.allOf(resultFutures.toArray(cfArray)).join();
		/*
		 * 
		 for (CompletableFuture<?> r : resultFutures) {
			 LOGGER.debug("{}", r.get());
			}
			
			*/
        LOGGER.info("Loaded DB in {} ms", System.currentTimeMillis() - startTime);
    }

    public class MySp extends StoredProcedure {

		public MySp(JdbcTemplate jdbcTemplate, String name) {
			super(jdbcTemplate, name);
			setFunction(false);
		}

	}

}
