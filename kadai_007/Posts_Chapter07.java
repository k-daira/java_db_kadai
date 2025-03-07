package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_Chapter07 {
	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement statement = null;
		Statement searchStatement = null;

		try {
			// データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"pass");

			System.out.println("データベース接続成功：com.mysql.cj.jdbc.ConnectionImpl@xxxxxxxx");

			String[][] userList = {
					{ "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
					{ "1002", "2023-02-08", "お疲れ様です！", "12" },
					{ "1003", "2023-02-09", "今日も頑張ります！", "18" },
					{ "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
					{ "1002", "2023-02-10", "明日から連休ですね！", "20" },
			};

			// SQLクエリを準備
			String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?)";
			statement = con.prepareStatement(sql);

			// レコードの追加
			System.out.println("レコード追加を実行します");
			for (String[] user : userList) {
				statement.setString(1, user[0]); // ユーザーID
				statement.setString(2, user[1]); // 投稿日時
				statement.setString(3, user[2]); // 投稿内容
				statement.setString(4, user[3]); // いいね数
				statement.addBatch();
			}
			statement.executeBatch();
			System.out.println(userList.length + "件のレコードが追加されました");

			// ユーザーID 1002 のレコード検索
			searchStatement = con.createStatement();
			String searchSql = "SELECT DISTINCT user_id, posted_at, post_content, likes FROM posts WHERE user_id = 1002";
			ResultSet result = searchStatement.executeQuery(searchSql);

			String user_id = "1002";
			// 「ユーザーIDが1002のレコードを検索しました」を1回だけ表示
			System.out.println("ユーザーIDが" + user_id + "のレコードを検索しました");

			int count = 0;
			while (result.next()) {
				count++;
				String posted_at = result.getString("posted_at");
				String post_content = result.getString("post_content");
				int likes = result.getInt("likes");

				System.out.println(count + "件目：投稿日時=" + posted_at
						+ "／投稿内容=" + post_content + "／いいね数=" + likes);
			}
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			// 使用したオブジェクトを解放
			try {
				if (statement != null)
					statement.close();
				if (searchStatement != null)
					searchStatement.close();
				if (con != null)
					con.close();
			} catch (SQLException ignore) {
			}
		}
	}
}
