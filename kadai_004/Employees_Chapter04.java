package kadai_004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Employees_Chapter04 {
	public static void main(String[] args) {

		Connection con = null;
		Statement statement = null;

		try {
			// データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"pass");

			System.out.println("データベース接続成功：com.mysql.cj.jdbc.ConnectionImpl@xxxxxxxx");
			statement = con.createStatement();

			// SQLクエリを準備
			String sql = """
					CREATE TABLE emplyees (
					  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
					  name VARCHAR(60) NOT NULL,
					  email VARCHAR(255) NOT NULL,
					  age INT(11),
					  address VARCHAR(255)
					);
					""";

			//　SQLクエリを実行（DBMSに送信）
			int rowCnt = statement.executeUpdate(sql);
			System.out.println("社員テーブルを作成しました:更新レコード数=" + rowCnt);
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			// 使用したオブジェクトを解放
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}
}