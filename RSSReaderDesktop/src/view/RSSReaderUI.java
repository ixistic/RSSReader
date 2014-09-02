package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Item;
import model.Rss;
import controller.Reader;

/**
 * Channel data model of rss.
 * Contain items. 
 * 
 * @author Veerapat Threeravipark 5510547022
 *
 */
public class RSSReaderUI extends JFrame{
	private Reader reader;
	private Rss rss;
	private Item[] itemArray;
	private String link;

	private JLabel welcome = new JLabel("Enter the feed URL");
	private JPanel panel = new JPanel();
	private JTextField urlInput = new JTextField();
	private JButton fetchButton = new JButton("Fetch");
	private JLabel channelLabel = new JLabel();
	private JScrollPane itemPane = new JScrollPane();
	private JScrollPane descriptionItemPane = new JScrollPane();
	private JScrollPane descriptionChannelPane = new JScrollPane();
	private JTextArea descriptionItemArea = new JTextArea();
	private JTextArea descriptionChannelArea = new JTextArea();
	private final JLabel lbdLabel = new JLabel("");
	private final JLabel linkItemLabel = new JLabel("");
	private final JLabel copyrightLabel = new JLabel("");
	private JList<Item> itemList;
	private MouseListener mouse;

	/**
	 * Constructor of this class.
	 * Naming Jframe.
	 * Init user interface.
	 */
	public RSSReaderUI() {
		super("RSSReader");
		initUI();
	}
	
	/**
	 * Init user interface.
	 */
	public void initUI(){
		setSize(800, 600);
		setLocation(50, 50);
		setResizable(false);
		panel.setLayout(null);

		welcome.setBounds(28, 18, 150, 40);
		urlInput.setBounds(149, 23, 516, 30);
		urlInput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fetchData();
			}
		});

		fetchButton.setBounds(677, 29, 94, 21);
		lbdLabel.setBounds(28, 101, 320, 30);
		linkItemLabel.setBounds(371, 540, 400, 21);
		copyrightLabel.setBounds(28, 540, 319, 21);
		channelLabel.setText("Channel's Name");

		channelLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		channelLabel.setBounds(27, 70, 320, 30);

		itemPane.setBackground(Color.WHITE);
		itemPane.setBounds(28, 137, 320, 391);

		descriptionItemPane.setBounds(371, 137, 400, 391);

		descriptionChannelPane.setBounds(371, 70, 400, 51);
		descriptionChannelArea.setText("Description of channel.");
		descriptionChannelArea.setEditable(false);
		descriptionChannelArea.setLineWrap(true);
		descriptionChannelArea.setWrapStyleWord(true);
		descriptionChannelArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		descriptionItemArea.setEditable(false);
		descriptionItemArea.setLineWrap(true);
		descriptionItemArea.setWrapStyleWord(true);
		descriptionItemArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		fetchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fetchData();
			}
		});

		descriptionItemPane.setViewportView(descriptionItemArea);
		descriptionChannelPane.setViewportView(descriptionChannelArea);

		panel.add(welcome);
		panel.add(urlInput);
		panel.add(fetchButton);
		panel.add(channelLabel);
		panel.add(itemPane);
		panel.add(descriptionItemPane);
		panel.add(descriptionChannelPane);
		panel.add(lbdLabel);
		panel.add(linkItemLabel);
		panel.add(copyrightLabel);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Fetch data from url.
	 */
	public void fetchData() {
		clearOldData();
		reader = new Reader();
		rss = reader.getRss(urlInput.getText());
		if (!(rss == null)) {
			channelLabel.setText(rss.getChannel().getTitle());
			copyrightLabel.setText(rss.getChannel().getCopyright());
			descriptionChannelArea.setText(rss.getChannel().getDescription());
			lbdLabel.setText(rss.getChannel().getLastBuildDate());
			initLink();
			initListItem();
		}
	}

	/**
	 * Clear old data.
	 */
	private void clearOldData() {
		channelLabel.setText("Channel's Name");
		descriptionItemArea.setText("");
		descriptionChannelArea.setText("Description of channel.");
		lbdLabel.setText("");
		link = "";
		linkItemLabel.setText("");
		linkItemLabel.removeMouseListener(mouse);
		copyrightLabel.setText("");
		Item[] emptyArray = new Item[0];
		itemList = new JList<Item>(emptyArray);
		itemPane.setViewportView(itemList);
	}

	/**
	 * Init list of items by using JList.
	 */
	public void initListItem() {
		itemArray = new Item[rss.getChannel().getItems().size()];
		itemArray = rss.getChannel().getItems().toArray(itemArray);
		itemList = new JList<Item>(itemArray);
		itemList.setBounds(0, 0, 400, 400);
		itemList.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		itemPane.setViewportView(itemList);
		itemList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				itemList.getSelectedIndex();
				Item item = itemList.getSelectedValue();
				showDescription(item);
				link = item.getLink();
				linkItemLabel.setText(link);
			}
		});
	}

	/**
	 * Init hyperlink of JLabel.
	 */
	public void initLink() {
		mouse = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (Desktop.isDesktopSupported() && !link.equals("")) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI(link));
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				linkItemLabel.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				linkItemLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				linkItemLabel.setForeground(Color.BLUE);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		};
		linkItemLabel.addMouseListener(mouse);
	}

	/**
	 * Show description of item by using JTextArea.
	 */
	public void showDescription(Item item) {
		descriptionItemArea.setText("Title : " + item.getTitle() + "\n\n"
				+ item.getDescription() + "\n\n" + item.getPubDate());
	}

}
