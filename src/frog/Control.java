package frog;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Control {

    private FrogGame f;
    JButton btnF = new JButton();
    private Timer timer;
    List<JButton> list = new ArrayList<>();
    List<JButton> listSave = new ArrayList<>();
    int yfrog = 40;//tọa độ y của cóc
    int mark = 0;
    int count = 120;
    Key key = new Key();
    private boolean checkSave = false;
    ////////////////////////////////
    JButton btn1 = new JButton();
    JButton btn2 = new JButton();
    JButton btn3 = new JButton();
    JButton btn4 = new JButton();
    int x1 = 300;
    int h1 = 120;
    int x2 = 532;
    int h2 = 120;
    int kc = 90;//khoảng cách cố định 2 cột

    public Control(FrogGame f) {
        this.f = f;
        f.getjButton2().addKeyListener(key);
        f.getjButton3().addKeyListener(key);
        f.getjPanel1().add(btnF);
        Icon icon = new ImageIcon("D:\\lab_desk_full\\HappyFinal\\src\\frog\\frog.jpg");
        btnF.setIcon(icon);
        btnF.addKeyListener(key);
        //cách 2

    }

    public void addButton1() {
        btn1.setBounds(x1, 0, 40, h1);
        btn2.setBounds(x2, 0, 40, h2);
        btn3.setBounds(x1, h1 + kc, 40, 258 - h1 - kc);
        btn4.setBounds(x2, h2 + kc, 40, 258 - h2 - kc);
        f.getjPanel1().add(btn1);
        f.getjPanel1().add(btn2);
        f.getjPanel1().add(btn3);
        f.getjPanel1().add(btn4);
    }

    //mỗi lần add 2 button song song với nhau
//        public void addButton() {
//        Random r = new Random();
//        JButton btnUp = new JButton();//button bên trên
//        int hup = r.nextInt(40) + 80;//ran dom từ 80->120
//        btnUp.setBounds(424, 0, 40, hup);//setBound(int x,int y,int width,int hight )
//        //x=424 cách gốc tọa độ 424 để button nằm bên ngoài màn hình
//        
//        
//        
//        JButton btnDown = new JButton();//button bên dưới
//        int hdown = r.nextInt(20) + 40;
//        btnDown.setBounds(424, 258 - hdown, 40, hdown);
//        list.add(btnUp);//add vào list để kiểm soát
//        list.add(btnDown);
//        f.getjPanel1().add(btnUp);//add vào panel để hiển thị
//        f.getjPanel1().add(btnDown);
//    }
//        
    public void run() {
        //cột di chuyển từ phải sang trái nên tọa độ x sẽ biến thiên theo thời gian
        timer = new Timer(15, new ActionListener() {//15 là thời gian nghỉ của thread ,nó sẽ nghỉ 15 mili giây mỗi lần
            @Override
            public void actionPerformed(ActionEvent e) {
                if (key.isPress()) {//thay đổi khi ấn phím UP
                    yfrog = yfrog - 20;//cóc chỉ thay đổi tọa độ y
                    key.setPress(false);//ko set về false thì nó sẽ đi lên liên tục
                }
                yfrog++;//++y để cóc đi dần xuống dưới 
                btnF.setBounds(60, yfrog, 40, 40);//set bound cho cóc

                /*
                for (int i = 0; i < list.size(); i++) {//duyet button trong list
                    int x = list.get(i).getBounds().x - 1;//từng button trong list trừ trừ
                    int y = list.get(i).getBounds().y;
                    list.get(i).setLocation(x, y);
                    //xóa button khi đi qua panel để list ko quá nhiều
                    if (x <= -40) {//-40 pải đi hết cột, còn nếu bằng 0 sẽ mất đột ngột
                        list.remove(i);
                        i--;//đảm bảo xóa ko bị xót
                    }
                }
                 */
                addButton1();
                x1--;
                x2--;
                if (x1 == -40) {
                    x1 = 424;
                    Random r = new Random();
                    h1 = r.nextInt(20) + 40;

                    // h3=h1+50;//50 la khoảng trống cố định giữa đôi cột
                }
                if (x2 == -40) {
                    x2 = 424;
                    Random r = new Random();
                    h2 = r.nextInt(20) + 40;

                    // h4=h2+50;//50 la khoảng trống cố định giữa đôi cột
                }

                getMark();
                count++;
                //sau 121s lại tiếp tục add các cặp button=thay đổi tốc độ nhanh-chậm
                //  if (count == 121) {//số càng nhỏ tốc độ add càng nhanh
//                    addButton();
                //        count = 0;//rồi lại đưa count về 0
                //lặp liên tục
                //   }

                //khi cóc bị chạm côt
                if (checkTouch()) {
                    timer.stop();
                    showMes();
                }
            }
        });
        timer.start();
    }

//kiểm tra check chạm viền hoặc chạm cột => thua
    public boolean checkTouch() {//218=258-40// tính theo góc trên của con cóc
        if (btnF.getY() <= 0 || btnF.getY() >= 218) {//chạm trên hoặc chạm dưới
            return true;
        }
        //chuyển con cóc và cột thành hình chữ nhật vì hình đó có hàm check chạm nhau
        Rectangle bf = new Rectangle(btnF.getX(), btnF.getY(), btnF.getWidth(), btnF.getHeight());//hàm cóc
//        for (JButton btn : list) {
//            Rectangle b = new Rectangle(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight());//hàm cột
//            if (bf.intersects(b)) {//hàm check chạm nhau
//                return true;
//            }
//        }
        Rectangle c1 = new Rectangle(btn1.getX(), btn1.getY(), btn1.getWidth(), btn1.getHeight());
        Rectangle c2 = new Rectangle(btn2.getX(), btn2.getY(), btn2.getWidth(), btn2.getHeight());
        Rectangle c3 = new Rectangle(btn3.getX(), btn3.getY(), btn3.getWidth(), btn3.getHeight());
        Rectangle c4 = new Rectangle(btn4.getX(), btn4.getY(), btn4.getWidth(), btn4.getHeight());
        if (bf.intersects(c1) || bf.intersects(c2) || bf.intersects(c3) || bf.intersects(c4)) {//hàm check chạm nhau
            return true;
        }
        return false;
    }

    public void showMes() {
        if (checkSave == false) {
            Object mes[] = {"New Game", "Exit"};
            int option = JOptionPane.showOptionDialog(null, "Do you want to continue?",
                    "Notice!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, mes, mes[0]);
            if (option == 0) {
                //new game
//                f.jPanel1.removeAll();
//                f.jPanel1.repaint();
//                list.clear();
//count = 121;
//  f.getjPanel1().add(btnF);
                mark = 0;
                yfrog = 40;
                f.jLabel1.setText("Point: 0");
                //cachs 2
                x1 = 300;
                h1 = 120;
                x2 = 532;
                h2 = 120;
                kc = 90;//khoảng cách cố định 2 cột

                timer.restart();
            }
            if (option == 1) {
                System.exit(0);
            }
        } else {
            Object mes[] = {"New Game", "Continue", "Exit"};
            int option = JOptionPane.showOptionDialog(null, "Do you want to continue?",
                    "Notice!",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, mes, mes[0]);
            if (option == 0) {
//                f.jPanel1.removeAll();
//                f.jPanel1.repaint();
//                list.clear();
//                mark = 0;
//                count = 120;
//                f.jLabel1.setText("Point: 0");
//                yfrog = 40;
//                f.getjPanel1().add(btnF);
//                timer.restart();

                mark = 0;
                yfrog = 40;
                f.jLabel1.setText("Point: 0");
                //cachs 2
                x1 = 300;
                h1 = 120;
                x2 = 532;
                h2 = 120;
                kc = 90;//khoảng cách cố định 2 cột

                timer.restart();
            }
            if (option == 1) {
                
                x1=x11;
                x2=x22;
                h1=h11;
                h2=h22;
                
                yfrog=yf1;
                mark=mark1;
                f.jLabel1.setText("Point: " + mark1);
                timer.restart();
//
//                f.jPanel1.removeAll();
//                f.jPanel1.repaint();
//
//                list.clear();
//                try {
//                    FileReader fr = new FileReader("data.txt");
//                    BufferedReader br = new BufferedReader(fr);
//                    String line = "";
//                    do {
//                        line = br.readLine().trim();
//                        if (line == null) {
//                            break;
//                        }
//                        JButton btn = new JButton();
//                        String txt[] = line.split(";");//cắt data tạo thành mảng
//                        int arr[] = new int[txt.length];//ép kiểu sang int
//                        for (int i = 0; i < arr.length; i++) {
//                            arr[i] = Integer.parseInt(txt[i]);
//                        }
//                        if (arr.length == 4) {//đủ 4 thì là tọa độ cột
//                            btn.setBounds(arr[0], arr[1], arr[2], arr[3]);
//                            list.add(btn);
//                            f.jPanel1.add(btn);
//                        } else {//là count,score,yfrog
//                            count = arr[0];
//                            mark = arr[1];
//                            f.jLabel1.setText("Point: " + mark / 2);
//                            yfrog = arr[2];
//                        }
//                    } while (true);
//                    br.close();
//                } catch (Exception e) {
//                }
//                f.getjPanel1().add(btnF);
//                timer.restart();
            }
            if (option == 2) {
                System.exit(0);
            }
        }
    }
    int x11, x22, h11, h22, yf1, mark1;
   

    //lưu lại tọa độ khi save
    public void save() {
        checkSave = true;
        //////
        x11 = x1;
        x22 = x2;
        h11 = h1;
        h22 = h2;
     //   h33 = 258 - h11 - kc;
      //  h44 = 258 - h11 - kc;
        yf1 = yfrog;
        mark1 = mark;
        /////
//        try {
//            FileWriter fw = new FileWriter("data.txt");
//            BufferedWriter bw = new BufferedWriter(fw);
//            for (JButton btn : list) {
//                int x = btn.getX();
//                int y = btn.getY();
//                int width = btn.getWidth();
//                int height = btn.getHeight();
//                bw.write(x + ";" + y + ";" + width + ";" + height);
//                bw.newLine();//xuống dòng ghi tiếp
//            }
//            bw.write(count + ";" + mark + ";" + yfrog);
//            bw.newLine();
//            bw.close();
//        } catch (Exception e) {
//        }
    }
    boolean checkPause = false;

    public void pause() {
        if (checkPause == false) {
            timer.stop();
            checkPause = true;
        } else {
            timer.restart();
            checkPause = false;
        }
    }

    public void getMark() {//nếu x của cóc bằng x của cột thì đươc cộng điểm
//        for (JButton btn : list) {
//            if (btnF.getX() == btn.getX()) {
//                mark++;
//                f.jLabel1.setText("Point: " + mark / 2);
//            }
//        }

        if (btn1.getX() == 50 || btn2.getX() == 50) {
            mark++;
            f.jLabel1.setText("Point: " + mark);
        }

    }

}
