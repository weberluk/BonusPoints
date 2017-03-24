package TicTacToe_Server;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TicTacToe_XMLWriter {

	Elements elements;

	public TicTacToe_XMLWriter() {
		elements = new Elements();
	}

	public void writeXML(int id, int points, String user) {

		elements.setId(id);
		elements.setName(user);
		elements.setPoints(points);

		try {

			File file = new File("C:\\temp\\file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Elements.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(elements, file);
			jaxbMarshaller.marshal(elements, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	public String readXML() {
		
		String response = "nicht geladen";

		try {

			// create file input stream
			InputStream is = new FileInputStream("C:\\temp\\file.xml");

			// XML and Java binding
			JAXBContext jaxbContext = JAXBContext.newInstance(Elements.class);

			// class responsible for the process of deserializing
			// XML data into Java object
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Elements elements = (Elements) jaxbUnmarshaller.unmarshal(is);

			// print the response for debugging
			System.out.println("Informations --->");
			System.out.println("Id: " + elements.getId());
			System.out.println("Name: " + elements.name);
			System.out.println("Points: " + elements.getPoints());
			
			response = "Points: " + elements.getPoints();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return response;

	}

}

@XmlRootElement
class Elements {

	String name;
	int points;
	int id;

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	@XmlElement
	public void setPoints(int points) {
		this.points = points;
	}

	public int getId() {
		return id;
	}

	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}

}
