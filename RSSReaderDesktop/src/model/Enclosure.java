package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="enclosure")

/**
 * Enclosure data model of rss.
 * Multimedia content. 
 * 
 * @author Veerapat Threeravipark 5510547022
 *
 */
public class Enclosure {
	@XmlAttribute
	private String url;
	@XmlAttribute
	private int length;
	@XmlAttribute
	private String type;
	
	public Enclosure() {
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
