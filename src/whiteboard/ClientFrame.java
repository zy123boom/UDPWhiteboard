package whiteboard;



import java.net.DatagramSocket;

import javax.swing.JFrame;

/**
 * 
 * @author 赵宇
 * 利用UDP协议实现一个简单的白板程序
 * 用户客户端界面上单击鼠标左键后在鼠标单击位置画20*20的矩形，单击右键后程序在鼠标单击位置画20*20的圆。
 * 服务器界面上将同时显示与客户端一致的画面
 */

/*
 * 客户端（发送端）界面运行
 */
public class ClientFrame extends JFrame{
	public ClientFrame(){
		add(new ClientComponent());
		pack();
	}
	
	public static void main(String[] args) {
		JFrame frame = new ClientFrame();
		frame.setTitle("客户端界面");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
}


