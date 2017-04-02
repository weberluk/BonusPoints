package TicTacToe_Server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class TicTacToe_XML {

	// No generics
	List myData;
	Document dom;

	public TicTacToe_XML(ArrayList<Game> inputList) {
		// create a list to hold the data
		myData = new ArrayList();

		// initialize the list
		loadData(inputList);

		// Get a DOM object
		createDocument();
		runExample();
	}

	public void runExample() {
		System.out.println("Started .. ");
		createDOMTree();
		printToFile();
		System.out.println("Generated file successfully.");
	}

	/**
	 * Add a list of gamesafes to the list In a production system you might populate
	 * the list from a DB
	 */
	private void loadData(ArrayList<Game> inputList) {
		for (int i = 0; i < inputList.size(); i++){
			myData.add(inputList.get(i));
		}
	}

	/**
	 * Using JAXP in implementation independent manner create a document object
	 * using which we create a xml tree in memory
	 */
	private void createDocument() {

		// get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// create an instance of DOM
			dom = db.newDocument();

		} catch (ParserConfigurationException pce) {
			// dump it
			System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
			System.exit(1);
		}

	}

	/**
	 * The real workhorse which creates the XML structure
	 */
	private void createDOMTree() {

		// create the root element <Books>
		Element rootGame = dom.createElement("Game");
		dom.appendChild(rootGame);

		// No enhanced for
		Iterator it = myData.iterator();
		while (it.hasNext()) {
			Game g = (Game) it.next();
			// For each Book object create <Book> element and attach it to root
			Element game = createBookElement(g);
			rootGame.appendChild(game);
		}

	}

	/**
	 * Helper method which creates a XML element <Book>
	 * 
	 * @param b
	 *            The book for which we need to create an xml representation
	 * @return XML element snippet representing a book
	 */
	private Element createBookElement(Game g) {

		Element Game = dom.createElement("id");
		Game.setAttribute("Subject", g.getId());
		
		// create gameid element and gameid text node and attach it to
		Element gameid = dom.createElement("gameid");
		Text gameIdText = dom.createTextNode(g.getGameId());
		gameid.appendChild(gameIdText);
		Game.appendChild(gameid);

		// create name element and name text node and attach it to
		Element name = dom.createElement("name");
		Text gameText = dom.createTextNode(g.getName());
		name.appendChild(gameText);
		Game.appendChild(name);

		// create points element and points text node and attach it to bookElement
		Element titleEle = dom.createElement("poitns");
		Text titleText = dom.createTextNode(g.getPoints());
		titleEle.appendChild(titleText);
		Game.appendChild(titleEle);

		return Game;

	}

	/**
	 * This method uses Xerces specific classes prints the XML document to file.
	 */
	private void printToFile() {

		try {
			// print
			OutputFormat format = new OutputFormat(dom);
			format.setIndenting(true);

			// to generate output to console use this serializer
			// XMLSerializer serializer = new XMLSerializer(System.out, format);

			// to generate a file output use fileoutputstream instead of
			// system.out
			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(new File("c:\\temp\\file.xml")), format);

			serializer.serialize(dom);

		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

//	public static void main(String args[]) {
//
//		// create an instance
//		TicTacToe_XML xce = new TicTacToe_XML();
//
//		// run the example
//		xce.runExample();
//	}
}

