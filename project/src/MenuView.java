import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class MenuView extends JFrame{
   
   JScrollPane menuList_Scroll;
   JTable table;
   JLabel rname;
   String category, phone;
   
   Vector<Vector> out;
   Vector<String> in, title;
   ArrayList<String> storename = new ArrayList<String>();
   
   Connection conn = null;
   String user = "scott";
   String pw = "tiger";
   String url = "jdbc:oracle:thin:@localhost:1521:orcl";
   Statement stat, statStore;
   
   ResultSet rs, rs_pnum;
   ResultSetMetaData rsmd;
   
   public MenuView(){
      display();
      this.setTitle("오늘의 픽");
      this.setLayout(null);      
      this.setSize(650, 650);
      
      //시작화면 중앙에 위치하게
      Dimension frameSize = this.getSize();   //프레임(자바 화면) 크기
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   //모니터 크기
      this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
         
      
      this.setVisible(true);
   }
   
   
   //=============================================================================================================
   public void display() {
      //폰트
      Font f_bold = new Font("여기어때 잘난체", Font.BOLD, 30);
      Font f_menu = new Font("여기어때 잘난체", Font.PLAIN, 15);
      Font f_plain = new Font("여기어때 잘난체", Font.PLAIN, 13);
      
      JPanel contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
       contentPane.setLayout(null);
       contentPane.setBackground(Color.WHITE);
      
       
       JPanel panel = new JPanel();
       panel.setBounds(0, 0, 650, 80);
       panel.setBackground(Color.WHITE);
       contentPane.add(panel);
      
      
       //뒤로가기 버튼
       ImageIcon icon2 = new ImageIcon("이미지/뒤로.png");
        JButton btnBack = new JButton("뒤로가기", icon2);
        btnBack.setBounds(10, 10, 110, 30);
        btnBack.setBackground(new Color(0xDCDCDC));
        btnBack.setFont(f_plain);
        backButton(btnBack);
        add(btnBack);
        
      
      //식당 이름
      rname = new JLabel(Restaurant.storename);        // * Restaurant 버튼의 이름 가져오면 될듯    
      panel.add(rname);
      rname.setFont(f_bold);
      
      panel.setBackground(Color.white);
      
      // 메뉴판
      // - 메뉴 리스트
      JPanel panel2 = new JPanel();
      panel2.setBounds(0, 80, 650, 550);
      contentPane.add(panel2);
      panel2.setBackground(Color.WHITE);
      
      title = new Vector<String>();
      out = new Vector<Vector>();
      
      title.add("메뉴");
      title.add("가격");
      
      getData();
      
      
      DefaultTableModel model = new DefaultTableModel(out,title) {
         public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
         }
      };      // 테이블 값 수정 못하게
      
      JTable table = new JTable(model);
      table.getTableHeader().setReorderingAllowed(false);      // 테이블 헤더 못움직이게
      table.setRowHeight(25);
      table.setFont(f_plain);
      
      // 테이블 헤더 폰트
      JTableHeader Theader = table.getTableHeader();
      Theader.setBackground(new Color(0xF5D08A));
      Theader.setFont(f_menu);
      
      menuList_Scroll = new JScrollPane(table);
      menuList_Scroll.setPreferredSize(new Dimension(500,450));
      panel2.add(menuList_Scroll);
      
      
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      addWindowListener(new WindowAdapter() {
         private void windowClosing() {
            try {
               stat.close();
               conn.close();
               
               setVisible(false);
               dispose();
               System.exit(0);
            }catch(Exception e) {
               
            }
         }
      });
      pack();
      setVisible(true);
   }
   
   
   //=============================================================================================================
   // DB 데이터를 가져와 out 벡터에 넣기
   public void getData() {
         try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, user, pw);
            
            
            stat = conn.createStatement();
            
            String cName=rname.getText();
            int len=cName.length()-1;
            cName=cName.substring(1, len);
            
            String query = "select menu,price from fulllist where sid in (select sid from storelist where store ='"+cName+"')";
            
            
            rs = stat.executeQuery(query);      // 쿼리문 실행하면 결과를 반환
            rsmd = rs.getMetaData();

            
            // 메뉴 출력
            while(rs.next()) {
               in = new Vector<String>();
               for(int i=1; i<= rsmd.getColumnCount(); i++) {
                  in.add(rs.getString(i));
               }
               out.add(in);
            }
         }catch(Exception e) {
            e.printStackTrace();
         }
         
      }
   
   
   //=============================================================================================================
   //뒤로가기 버튼 클릭 이벤트
   public void backButton(JButton btnBack) {         
      btnBack.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new Restaurant();
            setVisible(false);
         }
      });
   }
   
}