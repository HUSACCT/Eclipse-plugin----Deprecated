package plugin.views.internalframes;

import husacct.ServiceProvider;
import husacct.control.ControlServiceImpl;
import husacct.control.IControlService;
import husacct.control.task.MainController;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class LoadingDialog extends JDialog implements Runnable {

	private static final long serialVersionUID = 1L;
	private String progressInfoText;
	private IControlService controlService = ServiceProvider.getInstance().getControlService();

	public LoadingDialog(MainController mainController, String progressInfoText) {
		super(mainController.getMainGui(), true);
		setTitle(controlService.getTranslatedString("Loading"));
		this.progressInfoText = progressInfoText;
		setup();
		addComponents();

	}
	
	public LoadingDialog(String progressInfoText){
		ControlServiceImpl controlService = (ControlServiceImpl) ServiceProvider.getInstance().getControlService();
		new LoadingDialog(controlService.getMainController(), progressInfoText);
	}

	private void setup() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setSize(new Dimension(400, 130));
		this.setResizable(false);
		final Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - this.getWidth()) / 2;
		final int y = (screenSize.height - this.getHeight()) / 2;
		this.setLocation(x, y);
	}

	private void addComponents() {
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel progressPanel = new JPanel(new GridLayout(1, 1));
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JLabel progressLabel = new JLabel(progressInfoText);
		JProgressBar progressBar = new JProgressBar();

		progressPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		progressBar.setIndeterminate(true);

		JLabel waitLabel = new JLabel(controlService.getTranslatedString("Wait"));

		labelPanel.add(progressLabel);
		progressPanel.add(progressBar);
		buttonsPanel.add(waitLabel);

		add(labelPanel);
		add(progressPanel);
		add(buttonsPanel);
	}

	@Override
	public void run() {
		this.setVisible(true);
	}
}
