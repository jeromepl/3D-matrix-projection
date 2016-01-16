import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("WOOO!");
		
		Canvas canvas = new Canvas();
		Header header = new Header(canvas);
				
		frame.setLayout(new BorderLayout());
		frame.add(header, BorderLayout.NORTH);
		frame.add(canvas, BorderLayout.CENTER);
		
		frame.setSize(1000, 700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
