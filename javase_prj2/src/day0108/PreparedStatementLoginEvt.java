package day0108;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PreparedStatementLoginEvt extends WindowAdapter implements ActionListener {
	private PreparedStatementLogin sl;

	public PreparedStatementLoginEvt(PreparedStatementLogin sl) {
		this.sl = sl;
	}

	@Override
	public void windowClosing(WindowEvent we) {
		sl.dispose();
	}// windowClosing

	public boolean chkNull(String id, String pass) {
		boolean flag = false;
		try {
		if(id.equals("")) {
			JOptionPane.showMessageDialog(sl,"아이디는 필수 입력");
			sl.getJtfId().requestFocus();
			flag= true;
			//예외를 강제로 발생  :throw new 예외처리 클래스명();
			throw new LoginException();
		}//end if
		if(pass.equals("")) {
			JOptionPane.showMessageDialog(sl,"비밀번호는 필수 입력");
			sl.getJptPass().requestFocus();
			flag= true;
		}//end if
		}catch(LoginException le) {
			le.printStackTrace();
		}
		return !flag;
	}// chkNull

	public String login(String id, String pass) throws SQLException{
		String name = "";
		//1. 드라이버로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
		//2. Connection얻기
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String dbo_id ="scott";
			String dbo_pass="tiger";
			con = DriverManager.getConnection(url, dbo_id, dbo_pass);
		//3. 쿼리문 생성객체얻기
			StringBuilder selectName = new StringBuilder();
			selectName.append("select name ") 
			.append("from test_login ")
			.append(" where id =? and  pass =?");
			
			pstmt = con.prepareStatement(selectName.toString());
			//4. 바인드 변수에 값넣기
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			
			System.out.println(selectName);
			//5. 쿼리문수행 후 결과 얻기
			
			rs = pstmt.executeQuery();
			if(rs.next()) {//입력된 아이디와 비밀번호에 맞는 이름이 존재 =>로그인성공
				name = rs.getString("name");
			}//end if
		//5. 연결 끊기
		}finally {
			if(rs !=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(con !=null) {con.close();}
		}
		
		return name;
	}//login
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == sl.getJtfId() || ae.getSource() == sl.getJptPass()
				|| ae.getSource() == sl.getJbtLogin()) {
			String id= sl.getJtfId().getText().trim();
			String pass = new String(sl.getJptPass().getPassword()).trim();
			if(chkNull(id, pass)) {
					
				try {
					String name =login(id,pass);
					if(name.isEmpty()) {
						JOptionPane.showMessageDialog(sl, "아이디나 비밀번호를 확인해주세요.");
						sl.getJtfId().setText("");
						sl.getJptPass().setText("");
						sl.getJtfId().requestFocus();
						return;
					}//end if
					
					JDialog jd = new JDialog(sl, "로그인 성공", true);
					jd.add(new JLabel(name+"님 로그인 하셨습니다."));
					jd.setBounds(sl.getX()+100,sl.getY()+100,300,200);
					jd.setVisible(true);
					jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(sl, "DB 작업 중 문제가 발생하였습니다.");
					e.printStackTrace();
				}
			}//end if
		}
		if (ae.getSource() == sl.getJbtCancel()) {
			sl.getJtfId().setText("");
			sl.getJptPass().setText("");
			sl.getJtfId().requestFocus();
		}
	}// actionPerformed

}// class
