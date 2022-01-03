package com.hanul.study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {	//DB와 연동하여 트랜잭션(요청)을 처리
	private Connection conn;			//연결객체
	private PreparedStatement ps;		//전송객체
	private ResultSet rs;				//결과객체(select 쿼리)
	
	//DB 접속 : ojdbc8.jar ▶ WebContent > WEB-INF > lib : 복붙
	public Connection getConn() {
		try {
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
			String user = "hanul";
			String password = "0000";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getConn() Exception");
		}
		return conn;
	}//getConn()
	
	//DB 접속 해제
	public void dbClose() {
		try {
			if(rs != null) rs.close();
			if(ps != null) ps.close();
			if(conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("dbClose() Exception");
		}
	}//dbClose()
	
	//회원가입
	public int insertMember(MemberDTO dto) {
		conn = getConn();	//db접속
		String sql = " insert into Member values (?, ?, ?, ?, ?, ?) " ;	//sql 문장 작성
		int succ = 0;		//성공여부를 판달할 변수를 초기화
		try {
			ps = conn.prepareStatement(sql);	//전송객체 생성
			ps.setString(1, dto.getMemberName());
			ps.setString(2, dto.getMemberId());
			ps.setString(3, dto.getMemberPw());
			ps.setInt(4, dto.getMemberAge());
			ps.setString(5, dto.getMemberAddr());
			ps.setString(6, dto.getMemberTel());
			succ = ps.executeUpdate();
		} catch (Exception e) {		//예외처리
			e.printStackTrace();
			System.out.println("insertMember() Exception");
		}finally {					//DB 접속 해제
			dbClose();
		}
		return succ;
	}//insertMember()
	
	
}
