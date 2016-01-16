import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Header extends JPanel implements ChangeListener {
	
	private static final long serialVersionUID = -7794340755254434308L;
	
	private static final int SLIDER_MIN = 0; //Slider value will be divided by 1000 before sending to the canvas
	private static final int SLIDER_MAX = 120;
	private static final int SLIDER_INIT = 35;
	
	//UI objects:
	private JSlider xSlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
	private JSlider ySlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
	private JSlider zSlider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
	
	private JLabel xLabel = new JLabel("X Speed:");
	private JLabel yLabel = new JLabel("Y Speed:");
	private JLabel zLabel = new JLabel("Z Speed:");
	
	private Canvas canvas;
	
	public Header(Canvas canvas) {
		this.canvas = canvas;
		
		//Initialize the canvas values
		canvas.setXSpeed(SLIDER_INIT / 1000d);
		canvas.setYSpeed(SLIDER_INIT / 1000d);
		canvas.setZSpeed(SLIDER_INIT / 1000d);
		
		xSlider.addChangeListener(this);
		ySlider.addChangeListener(this);
		zSlider.addChangeListener(this);
		
		this.setLayout(new FlowLayout());
		
		this.add(xLabel);
		this.add(xSlider);
		this.add(yLabel);
		this.add(ySlider);
		this.add(zLabel);
		this.add(zSlider);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == xSlider) {
			canvas.setXSpeed(xSlider.getValue() / 1000d);
		}
		else if(e.getSource() == ySlider) {
			canvas.setYSpeed(ySlider.getValue() / 1000d);
		}
		else if(e.getSource() == zSlider) {
			canvas.setZSpeed(zSlider.getValue() / 1000d);
		}
	}
}
