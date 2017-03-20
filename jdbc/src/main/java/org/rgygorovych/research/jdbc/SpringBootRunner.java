/*
 * @(#)SpringBootRunner.java         1.00, 2017/01/09 (09 January, 2017)
 *
 * This file is part of 'sql-test' project.
 * Can not be copied and/or distributed without
 * the express permission of implementation provider.
 *
 * Copyright (C) 2017
 * All Rights Reserved.
 */
package org.rgygorovych.research.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * SpringBootRunner
 *
 * @author Roman Grygorovych
 * @version 1.00, 01/09/2017
 * @since 1.0
 */
@Configuration
@SpringBootApplication
public class SpringBootRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRunner.class, args);
	}

	@Bean
	public NamedParameterJdbcTemplate getJdbcTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public DataSource dataSource() {
		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
				.setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
				.addScript("db/sql/create-db.sql")
				.addScript("db/sql/insert-data.sql")
				.build();
		return db;
	}

}
