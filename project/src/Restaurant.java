import java.awt.Color;
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
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Restaurant extends JFrame{
   
   static String category;
   static String storename;
   static String randomText;
   
   
   Vector<Vector> out;
   Vector<String> in, title;
   
   Connection conn = null;
   String user = "scott";
   String pw = "tiger";
   String url = "jdbc:oracle:thin:@localhost:1521:orcl";
   Statement stat;
   
   
   
   public Restaurant() {
      this.category = MainPage.category;
      display();
      this.setTitle("오늘의 픽");
      this.setSize(MainPage.FRAME_WIDTH, MainPage.FRAME_HEIGHT);
      
      //시작화면 중앙에 위치하게
      Dimension frameSize = this.getSize();   //프레임(자바 화면) 크기
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   //모니터 크기
      this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
      
      this.setVisible(true);
   }
   
   
   //=============================================================================================================
   public void display() {
      
      
      Font f_bold = new Font("여기어때 잘난체", Font.BOLD, 30);
      Font f_plain = new Font("여기어때 잘난체", Font.PLAIN, 13);
      Font f_plain2 = new Font("여기어때 잘난체", Font.PLAIN, 12);
      
      JPanel contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
       contentPane.setLayout(null);
       contentPane.setBackground(Color.WHITE);
      
       
       JPanel panel = new JPanel();
       panel.setBounds(0, 0, 600, 81);
       contentPane.add(panel);

      //뒤로가기 버튼
       ImageIcon icon2 = new ImageIcon("이미지/뒤로.png");
      JButton btnBack = new JButton("뒤로가기", icon2);
      btnBack.setBounds(10, 10, 110, 30);
      btnBack.setBackground(new Color(0xDCDCDC));
      btnBack.setFont(f_plain);
      backButton(btnBack);
      add(btnBack);
      
      
//    ImageIcon img = new ImageIcon("image/한식.png");      

    //음식카테고리 이름
//    JLabel rname = new JLabel(this.category, img, SwingConstants.CENTER);
//    rname.setFont(f_bold);
//    panel.add(rname);
    
      if(this.category.equals("한식")) {
          ImageIcon img = new ImageIcon("이미지/한식1.png"); 
          JLabel rname = new JLabel( img, SwingConstants.CENTER);
           rname.setFont(f_bold);
           panel.add(rname);
       }else if(this.category.equals("일식")) {
          ImageIcon img = new ImageIcon("이미지/일식1.png"); 
          JLabel rname = new JLabel( img, SwingConstants.CENTER);
           rname.setFont(f_bold);
           panel.add(rname);
       }else if(this.category.equals("중식")) {
          ImageIcon img = new ImageIcon("이미지/중식1.png"); 
          JLabel rname = new JLabel( img, SwingConstants.CENTER);
           rname.setFont(f_bold);
           panel.add(rname);
       }else if(this.category.equals("양식")) {
          ImageIcon img = new ImageIcon("이미지/양식1.png"); 
          JLabel rname = new JLabel( img, SwingConstants.CENTER);
           rname.setFont(f_bold);
           panel.add(rname);
       }else if(this.category.equals("카페")) {
          ImageIcon img = new ImageIcon("이미지/카페1.png"); 
          JLabel rname = new JLabel( img, SwingConstants.CENTER);
           rname.setFont(f_bold);
           panel.add(rname);
       }else {
          ImageIcon img = new ImageIcon("이미지/도시락1.png"); 
          JLabel rname = new JLabel( img, SwingConstants.CENTER);
           rname.setFont(f_bold);
           panel.add(rname);
       }
      
            
      //음식카테고리 이름
     
      JLabel rname = new JLabel(this.category);
      rname.setFont(f_bold);
      panel.add(rname);
      panel.setBackground(Color.WHITE);
      
      
      
      
      //식당 버튼 - 식당 갯수별로 다 출력
      JPanel panel2 = new JPanel();
      panel2.setBounds(0, 85, 600, 300);
      contentPane.add(panel2);
      panel2.setBackground(Color.WHITE);
      
      
      
      // 식당 버튼 - DB 연결
      out = new Vector<Vector>();
      getData();
      JButton[] store = new JButton[out.size()];      // * 데이터베이스에 있는 식당들 갯수로 넣으면 됨
      
      for(int i=0; i<store.length; i++) {
         Vector name = out.get(i);
         store[i] = new JButton(name.toString());   // * 데이터베이스에 있는 식당들 이름으로 바꾸기
         store[i].setPreferredSize(new Dimension(150,50));
         store[i].setBackground(new Color(0xF5D08A));
         store[i].setFont(f_plain2);
         panel2.add(store[i]);
         storeButton(store[i]);
      }
      
      // 식당 랜덤 추천 
      JPanel panel3 = new JPanel();
       panel3.setBounds(0, 380, 600, 40);
       contentPane.add(panel3);
       
       ImageIcon icon = new ImageIcon("이미지/랜덤.png");
       JButton btnRand = new JButton("식당 랜덤 추천", icon);
       btnRand.setBackground(new Color(0xfeccc9));
       btnRand.setFont(f_plain);
       btnRand.setForeground(Color.WHITE);
       panel3.add(btnRand);
       panel3.setBackground(Color.WHITE);
       
       randomButton(btnRand, store);
       
       
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       
       
       // db 연결 끊기
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
          
          String query = "select store from storelist where sid in (select sid from fulllist where cid in (select cid from categorylist where category='" + this.category + "'))";
                   
          ResultSet rs = stat.executeQuery(query);      // 쿼리문 실행하면 결과를 반환
          ResultSetMetaData rsmd = rs.getMetaData();
         
         
          // MenuView
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
   //식당 랜덤 추천 버튼 이벤트
   public void randomButton(JButton btnRandom, JButton[] store2) {
      btnRandom.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Random rnd = new Random();
            int cnt = rnd.nextInt(store2.length);   // 식당 갯수에 맞춰 랜덤 숫자 생성
            storename = store2[cnt].getText();
            new MenuView();
            setVisible(false);
            
         }
      });
      
      
   }
   
   
   //=============================================================================================================
   //뒤로가기 버튼 클릭 이벤트
   public void backButton(JButton btnBack) {         
      btnBack.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new MainPage();
            setVisible(false);
         }
      });
   }

   
   //=============================================================================================================
   // 식당 버튼 클릭 이벤트
   public void storeButton(JButton btnStore) {
      btnStore.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JButton b = (JButton)e.getSource();
            storename = b.getText();
            new MenuView();
            setVisible(false);
         }
      });
   }
   
}