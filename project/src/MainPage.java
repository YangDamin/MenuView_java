import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class MainPage extends JFrame{
   
   static public final int FRAME_WIDTH = 600;
   static public final int FRAME_HEIGHT = 500; 
   
   static public String category;
   
   JPanel panel = new JPanel();

   
   public MainPage() {
      display();
      this.setTitle("오늘의 픽");      // 타이틀 설정
      
      //시작화면 중앙에 위치하게
      Dimension frameSize = this.getSize();   //프레임(자바 화면) 크기
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();   //모니터 크기
      this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
      
      
      
      this.setVisible(true);
      
   }
   
   //=============================================================================================================
   public void display() {
      Font f2 = new Font("여기어때 잘난체",Font.BOLD,15);
      Font f1 = new Font("여기어때 잘난체",Font.BOLD,13);
      
      
      JLabel lb1 = new JLabel("-------------------------오늘의 픽-------------------------");     
      lb1.setForeground(Color.WHITE);
      lb1.setFont(f2);
      
      //아이콘 불러오기
      ImageIcon icon = new ImageIcon("이미지/한식.png");
      JButton bt1 = new JButton("한식",icon); 
      bt1.setPreferredSize(new Dimension(110,50));
      setButton(bt1, f1);
      clickBtn(bt1);      // 버튼클릭메소드
        
      
      
      ImageIcon icon2 = new ImageIcon("이미지/일식.png");   
      JButton bt2 = new JButton("일식",icon2); 
      bt2.setPreferredSize(new Dimension(110,50));
      setButton(bt2, f1);
      clickBtn(bt2);
        
      ImageIcon icon3 = new ImageIcon("이미지/중식.png");   
      JButton bt3 = new JButton("중식",icon3);  
      bt3.setPreferredSize(new Dimension(110,50));
      setButton(bt3, f1);
      clickBtn(bt3);
         
      ImageIcon icon4 = new ImageIcon("이미지/양식.png");   
      JButton bt4 = new JButton("양식",icon4); 
      bt4.setPreferredSize(new Dimension(110,50));
      setButton(bt4, f1);
      clickBtn(bt4);
         
      ImageIcon icon5 = new ImageIcon("이미지/카페.png");
      JButton bt5 = new JButton("카페",icon5);    
      bt5.setPreferredSize(new Dimension(110,50));
      setButton(bt5, f1);
      clickBtn(bt5);
         
      ImageIcon icon6 = new ImageIcon("이미지/도시락.png");
      JButton bt6 = new JButton("도시락", icon6);     
      bt6.setPreferredSize(new Dimension(110,50));
      setButton(bt6, f1);
      clickBtn(bt6);
      
      panel.setBackground(new Color(0xF5D08A));
      setSize(400,200);
      
      
      panel.add(lb1);
      panel.add(bt1);panel.add(bt2);
      panel.add(bt3);panel.add(bt4);
      panel.add(bt5);panel.add(bt6);
      add(panel);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   
   //=============================================================================================================
   // 카테고리 버튼 클릭 이벤트
   public void clickBtn(JButton btn) {
      btn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            JButton b = (JButton)e.getSource();
            category = b.getText();
            new Restaurant();
            setVisible(false);
         }
      });
   }
   
   //=============================================================================================================
   // 버튼의 배경색, 폰트색, 폰트지정
   public void setButton(JButton btn, Font f2) {
      btn.setBackground(Color.WHITE);
      btn.setForeground(Color.BLACK);
      
      btn.setFont(f2);
   }
   
  }