package controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.Rss;

/**
 * Read RSS from URL by using JAXB. 
 * Use unmarshal to convert xml to object.
 * 
 * @author Veerapat Threeravipark 5510547022
 * 
 */
public class Reader {

	public Reader() {

	}

	/**
	 * Get RSS from URL.
	 * 
	 * @param urlText
	 *            url from website.
	 * @return rss if correct url , null if incorrect url.
	 */
	public Rss getRss(String urlText) {
		try {
			JAXBContext ctx = JAXBContext.newInstance(Rss.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			URL url = new URL(urlText);
			Object obj = unmarshaller.unmarshal(url);
			Rss rss = (Rss) obj;
			return rss;
		} catch (JAXBException | MalformedURLException
				| IllegalArgumentException e) {
			notifyError();
		}
		return null;
	}

	/**
	 * Notify when incorrect url.
	 */
	public void notifyError() {
		JOptionPane.showMessageDialog(null, "Invalid url.", "Error Message",
				JOptionPane.ERROR_MESSAGE);
	}
}
