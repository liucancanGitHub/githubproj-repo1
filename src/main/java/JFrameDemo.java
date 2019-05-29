import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by liucancan on 2019/5/27.
 */

public class JFrameDemo {

    public static void main(String[] args){
        //new JFrameTest();
        //new JPanelTest();
        //new GridLayoutTest();
//       System.out.println(new GridLayoutTest().parseRegex("123+23="));
        GridLayoutTest gridLayout = new GridLayoutTest();
        gridLayout.genBoHaoPan();
    }

}
 class JFrameTest extends JFrame {
    public JFrameTest(){
        setTitle("JFrame创建的第一个窗口");
        setBounds(700,260,300,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel jl = new JLabel("ContentPane内容窗口存放标签");
        Container c = getContentPane();
        c.add(jl);
        setVisible(true);
    }
}
class JPanelTest {
    public JPanelTest(){
        JFrame jf = new JFrame("JFrame创建的第二个窗口");
        //jf.setTitle("JFrame创建的第二个窗口");
        jf.setBounds(700,260,400,600);
        JPanel jp = new JPanel();
        jp.setBackground(Color.white);
        JLabel jl = new JLabel("JPanel面板存放标签");
        jp.add(jl);
        jf.add(jp);
        jf.setVisible(true);
    }
}
class GridLayoutTest{
    public static String textFieldContent = "";
    public GridLayoutTest(){
        Logger.getLogger(String.valueOf(this.getClass())).info("不生成任何东西");
    }
    public GridLayoutTest(String genSomething){
        if(genSomething.equals("计算器")){
            genCalculator();
        }else if(genSomething.equals("拨号盘")){
            genBoHaoPan();
        }

    }
    public int calResult(JTextField textField){
        String[] ins = genRes(textField);
        int res = 0;
        String expression = textField.getText();
        if(expression.contains("+")){
            for(String ele:ins){
                if(ele.matches("^[0-9]*$")){
                    res += Integer.valueOf(ele);
                }

            }
        }else if(expression.contains("-")){
            java.util.List<Integer> integers = new ArrayList<Integer>();
            for(String ele:ins){
                if(ele.matches("^[0-9]*$")){
                    integers.add(Integer.valueOf(ele));
                }
            }
            res = integers.get(0);
            for(int i = 0;i < integers.size()-1;i++){
                if((i + 1) < integers.size()){
                    res -= integers.get(i+1);
                }

            }

//            for(String ele:ins){
//                if(ele.matches("^[0-9]*$")){
//                    res = Integer.valueOf(ele);
//
//                }
//
//            }
        }else if(expression.contains("*")){
            for(String ele:ins){
                if(ele.matches("^[0-9]*$")){
                    res *= Integer.valueOf(ele);
                }
            }
        }else if(expression.contains("/")){
            for(String ele:ins){
                if(ele.matches("^[0-9]*$")){
                    res /= Integer.valueOf(ele);
                }
            }
        }
        return res;
    }
    public String[] genRes(JTextField textField){
        String expression = textField.getText();
        if(!parseRegex(expression)){
            return null;
        }
        String[] elements = expression.split("/+|/-|/*|//|/=");
        return elements;
    }
    public boolean parseRegex(String expression){
        String patterns = "^[1-9]*[+-/*//][1-9]*=$";
        return expression.matches(patterns);
    }
    public void genCalculator(){
        JFrame frame=new JFrame("GridLayou布局计算器");
        JPanel panel=new JPanel();    //创建面板
        //指定面板的布局为GridLayout，4行4列，间隙为5
        panel.setLayout(new GridLayout(4,4,5,5));
        panel.add(new JButton("7"));    //添加按钮
        panel.add(new JButton("8"));
        panel.add(new JButton("9"));
        panel.add(new JButton("/"));
        panel.add(new JButton("4"));
        panel.add(new JButton("5"));
        panel.add(new JButton("6"));
        panel.add(new JButton("*"));
        panel.add(new JButton("1"));
        panel.add(new JButton("2"));
        panel.add(new JButton("3"));
        panel.add(new JButton("-"));
        panel.add(new JButton("0"));
        panel.add(new JButton("."));
        panel.add(new JButton("="));
        panel.add(new JButton("+"));
        frame.add(panel);    //添加面板到容器
        frame.setBounds(300,200,200,150);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public  void genBoHaoPan() {
        JFrame frame=new JFrame("拨号盘");
        GridBagLayout gbaglayout=new GridBagLayout();    //创建GridBagLayout布局管理器
        GridBagConstraints constraints=new GridBagConstraints();
        frame.setLayout(gbaglayout);    //使用GridBagLayout布局管理器
        constraints.fill=GridBagConstraints.BOTH;    //组件填充显示区域
        constraints.weightx=0.0;    //恢复默认值
        constraints.gridwidth = GridBagConstraints.REMAINDER;    //结束行
        //tf需要显示按钮内容，并提交事件
//        JTextField tf=new JTextField("13612345678");
        JTextField tf = makeTextField();
        gbaglayout.setConstraints(tf, constraints);
        frame.add(tf);
        constraints.weightx=0.5;    // 指定组件的分配区域
        constraints.weighty=0.2;
        constraints.gridwidth=1;
        makeButton("7",frame,gbaglayout,constraints,tf);    //调用方法，添加按钮组件
        makeButton("8",frame,gbaglayout,constraints,tf);
        makeButton("9",frame,gbaglayout,constraints,tf);
        constraints.gridwidth=GridBagConstraints.REMAINDER;    //结束行
        makeButton("/",frame,gbaglayout,constraints,tf);
        constraints.gridwidth=1;    //重新设置gridwidth的值

        makeButton("4",frame,gbaglayout,constraints,tf);
        makeButton("5",frame,gbaglayout,constraints,tf);
        makeButton("6",frame,gbaglayout,constraints,tf);
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        makeButton("*",frame,gbaglayout,constraints,tf);
        constraints.gridwidth=1;

        makeButton("1",frame,gbaglayout,constraints,tf);
        makeButton("2",frame,gbaglayout,constraints,tf);
        makeButton("3",frame,gbaglayout,constraints,tf);
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        makeButton("-",frame,gbaglayout,constraints,tf);
        constraints.gridwidth=1;
        makeButton("0",frame,gbaglayout,constraints,tf);
        makeButton(".",frame,gbaglayout,constraints,tf);
        makeButton("=",frame,gbaglayout,constraints,tf);
        constraints.gridwidth=GridBagConstraints.REMAINDER;
        makeButton("+",frame,gbaglayout,constraints,tf);

//        makeButton("返回",frame,gbaglayout,constraints);
//        constraints.gridwidth=GridBagConstraints.REMAINDER;
//        makeButton("拨号",frame,gbaglayout,constraints);
        constraints.gridwidth=1;
        frame.setBounds(400,400,400,400);    //设置容器大小
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public  void makeButton(String title, JFrame frame, GridBagLayout gridBagLayout, GridBagConstraints constraints, final JTextField textField)
    {
        final JButton button=new JButton(title);    //创建Button对象
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                textField.add(button.getText(),button);

                textFieldContent += button.getText();
                textField.setText(textFieldContent);
                if(button.getText().equals("=")){
                    textField.setText(textFieldContent + calResult(textField));
                }
            }
        });
        gridBagLayout.setConstraints(button,constraints);
        frame.add(button);
    }
    public JTextField makeTextField(){
        final JTextField tf=new JTextField();
        tf.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                String text = tf.getText();
                if(text.matches("=$")){
                    int res = calResult(tf);
                    tf.setText(textFieldContent + res);
                }
            }

            public void removeUpdate(DocumentEvent e) {

            }

            public void changedUpdate(DocumentEvent e) {

            }
        });
        return tf;
    }

}



