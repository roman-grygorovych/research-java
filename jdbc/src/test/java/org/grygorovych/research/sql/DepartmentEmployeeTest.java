/*
 * @(#)DepartmentEmployeeTest.java         1.00, 2017/01/09 (09 January, 2017)
 *
 * This file is part of 'sql-test' project.
 * Can not be copied and/or distributed without
 * the express permission of implementation provider.
 *
 * Copyright (C) 2017
 * All Rights Reserved.
 */
package org.grygorovych.research.sql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rgygorovych.research.jdbc.SpringBootRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DepartmentEmployeeTest
 *
 * @author Roman Grygorovych
 * @version 1.00, 01/09/2017
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringBootRunner.class)
public class DepartmentEmployeeTest {

	@Autowired
	DataSource dataSource;

	@Test
	public void findEmployeeByName() throws SQLException {
		Connection conn = dataSource.getConnection();
		String SQL = "SELECT id, nick, salary, birthdate FROM employee WHERE nick = ?";
		final String name = "joel";
		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println("Employee -> " + rs.getInt(1));
			System.out.println(" |-> nick = " + rs.getString(2));
			System.out.println(" |-> birthdate = " + rs.getDate(4));
			System.out.println(" |-----------------------------------------");
		}
		rs.close();
		ps.close();
	}

	@Test
	public void findTopSalaryInEachDepartment() throws SQLException {
		Connection conn = dataSource.getConnection();

		String SQL =
				"SELECT id, nick, salary, birthdate, department_id " +
						" FROM employee as emp " +
						" WHERE " +
						" emp.salary = (SELECT max(salary) FROM employee WHERE department_id = emp.department_id) ";

//		final String name = "joel";
		PreparedStatement ps = conn.prepareStatement(SQL);
//		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println("Employee (top salary) -> " + rs.getInt(1));
			System.out.println(" |-> nick = " + rs.getString(2));
			System.out.println(" |-> salary = " + rs.getString(3));
			System.out.println(" |-> department = " + rs.getString(5));
			System.out.println(" |-----------------------------------------");
		}
		rs.close();
		ps.close();
	}
}
