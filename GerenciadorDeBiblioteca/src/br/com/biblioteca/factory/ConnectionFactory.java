package br.com.biblioteca.factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	private static final String USERNAME = "admin";
	private static final String PASSWORD = "21A06b0@";
	private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/biblioteca";

	public static Connection createConnectionToMySql() throws Exception {
		Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
		return connection;
	}

	public static void main(String[] args) throws Exception{
		Connection con = createConnectionToMySql();
		
		if (con != null) {
			System.out.println("Conexão Obetida com Sucesso");
			con.close();
		}
	}
}
