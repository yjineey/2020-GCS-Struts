package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBCPConn {
	
	static Connection conn =null;

	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.oracledriver").newInstance();
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "spring";
			String pw = "spring";

			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("연결성공!!!!!!!!!!!!!!!!!");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("연결 다시해주세요!!!!!!!!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return conn;
	}

}
