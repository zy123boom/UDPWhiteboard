package whiteboard;



import java.net.DatagramSocket;

import javax.swing.JFrame;

/**
 * 
 * @author ����
 * ����UDPЭ��ʵ��һ���򵥵İװ����
 * �û��ͻ��˽����ϵ���������������굥��λ�û�20*20�ľ��Σ������Ҽ����������굥��λ�û�20*20��Բ��
 * �����������Ͻ�ͬʱ��ʾ��ͻ���һ�µĻ���
 */

/*
 * �ͻ��ˣ����Ͷˣ���������
 */
public class ClientFrame extends JFrame{
	public ClientFrame(){
		add(new ClientComponent());
		pack();
	}
	
	public static void main(String[] args) {
		JFrame frame = new ClientFrame();
		frame.setTitle("�ͻ��˽���");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
}


