
package whiteboard;



import javax.swing.JFrame;

public class ServerFrame extends JFrame{
	public ServerFrame(ServerComponent component){
		add(component);
		pack();
	}
	
	public static void main(String[] args) {
		ServerComponent component = new ServerComponent();
		JFrame frame = new ServerFrame(component);
		frame.setTitle("服务端界面");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		component.receive();
		
		
		
	}
}
