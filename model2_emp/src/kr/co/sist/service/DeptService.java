package kr.co.sist.service;

import java.sql.SQLException;
import java.util.List;

import kr.co.sist.dao.Model2DAO;
import kr.co.sist.vo.DeptVO;

public class DeptService {

	/**
	 * Business Logic을 처리하는 클래스
	 * @param args
	 */
	public List<DeptVO> searchAllDept(){
			
		List<DeptVO> list= null;
			
		Model2DAO m_dao= Model2DAO.getInstance();
		try {
			list= m_dao.selectDeptList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
			
		return list;
	}

}
