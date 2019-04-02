package kr.co.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import kr.co.sist.vo.DeptVO;
import kr.co.sist.vo.EmpVO;

public class Model2DAO {
	private static Model2DAO jes_dao;
	
	private Model2DAO() {
		
	}
	
	public static Model2DAO getInstance() {
		if(jes_dao==null) {
			jes_dao= new Model2DAO();
		}//end if
		return jes_dao;
	}//getInstance
	
	private Connection getConn()throws SQLException{
		Connection con=null;
		
		//1. JNDI 사용객체 생성
		try {
			Context ctx = new InitialContext();
			//2. DBCP에 저장된 DataSource얻기
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/model2_dbcp");
			//3. Connection 얻기
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return con;
	}//getConn
	
	public List<EmpVO> selectEmpList(int deptno)throws SQLException{
		List<EmpVO> list =new ArrayList<EmpVO>();
		
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
			con= getConn();
		//4. 쿼리문 수행 객체 얻기
			String selectEmp="select empno,sal,ename,job,to_char(hiredate,'yyyy-mm-dd dy') hiredate from emp where deptno=?";
			pstmt = con.prepareStatement(selectEmp);
			pstmt.setInt(1, deptno);
			
		//5. 쿼리문 수행 후 결과 얻기.
			rs= pstmt.executeQuery();
			EmpVO ev= null;
			while (rs.next()) {
				ev= new EmpVO(rs.getInt("empno"),  rs.getInt("sal"),
						rs.getString("ename"), rs.getString("job"), rs.getString("hiredate"));
				list.add(ev);
			}
			
		}finally {
			//6. 연결끊기.
			if(rs!=null) {rs.close();}//end if
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally
		return list;
		
	}//selectEmpList
	
	public List<DeptVO> selectDeptList()throws SQLException{
		List<DeptVO> list =new ArrayList<DeptVO>();
		
		Connection con=null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		try {
			con= getConn();
		//4. 쿼리문 수행 객체 얻기
			String selectDept="select deptno,dname,loc from dept";
			pstmt = con.prepareStatement(selectDept);
			
		//5. 쿼리문 수행 후 결과 얻기.
			rs= pstmt.executeQuery();
			DeptVO dv= null;
			while (rs.next()) {
				dv= new DeptVO(rs.getInt("deptno"), 
						rs.getString("dname"), rs.getString("loc"));
				list.add(dv);
			}//end whiles
			
		}finally {
			//6. 연결끊기.
			if(rs!=null) {rs.close();}//end if
			if(pstmt!=null) {pstmt.close();}//end if
			if(con!=null) {con.close();}//end if
		}//end finally
		return list;
		
	}//selectDeptList
	
	
	
}//class
