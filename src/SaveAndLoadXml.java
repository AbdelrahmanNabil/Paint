import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.Attributes;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaveAndLoadXml {
	public static void save(String FileName) {
		LinkedList<MyShape> pointer = MyShape.his;
		Iterator<MyShape> current = pointer.iterator();
		MyShape point;
		try {

			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = fact.newDocumentBuilder();
			Document doc = build.newDocument();
			Element root = doc.createElement("MyShape");
			while (current.hasNext()) {
				point = current.next();
				Element e1 = doc.createElement(point.getClass().getName());

				e1.setAttribute("drawCol", "" + point.drawCol);
				e1.setAttribute("fill", "" + point.fill);
				e1.setAttribute("endX", "" + point.endX);
				e1.setAttribute("endY", "" + point.endY);

				e1.setAttribute("topLeftX", "" + point.topLeftX);
				e1.setAttribute("topLeftY", "" + point.topLeftY);

				root.appendChild(e1);
			}
			doc.appendChild(root);
			Transformer t = TransformerFactory.newInstance().newTransformer();
			DOMSource dom = new DOMSource(doc);
			File h = new File(FileName);
			h.canWrite();

			FileOutputStream out = new FileOutputStream(h);
			StreamResult result = new StreamResult(out);
			t.transform(dom, result);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void loadList(File f) {
		MyShape.his = null;
		MyShape.his = new LinkedList<MyShape>();
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();

			factory.setValidating(true);
			SAXParser parser = factory.newSAXParser();
			f.canRead();
			parser.parse(f, new handeler());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int[] getColorPar(String s) {
		int last = s.length() - 1;
		int count = 0;
		int[] array = new int[3];
		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == '[') {
				break;
			} else {
				if (s.charAt(i) == '=') {
					String d = s.substring(i + 1, last);
					array[count] = Integer.parseInt(d);
					count++;
					last = i - 2;
				}
			}
		}
		return array;

	}

	private class handeler extends DefaultHandler {

		public void characters(char[] arg0, int arg1, int arg2)
				throws SAXException {

		}

		@Override
		public void endDocument() throws SAXException {

		}

		@Override
		public void endElement(String arg0, String arg1, String arg2)
				throws SAXException {

		}

		@Override
		public void endPrefixMapping(String arg0) throws SAXException {
			// TODO Auto-generated method stub

		}

		@Override
		public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
				throws SAXException {
			// TODO Auto-generated method stub

		}

		@Override
		public void processingInstruction(String arg0, String arg1)
				throws SAXException {
			// TODO Auto-generated method stub

		}

		@Override
		public void setDocumentLocator(Locator arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void skippedEntity(String arg0) throws SAXException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub

		}

		public void startElement(String arg0, String arg1, String arg2,
				Attributes arg3) throws SAXException {

			// Scanner sc = new Scanner("java.awt.Color[r=0,g=0,b=0]");
			// sc.useDelimiter("\\D+");
			// Color c = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());

			if (arg3.getValue(3) != null) {
				String color = arg3.getValue(0);
				System.out.println("String  " + arg3.getValue(4));
				System.out.println("String  " + arg3.getValue(0));
				int topX = Integer.parseInt(arg3.getValue(4));
				int topY = Integer.parseInt(arg3.getValue(5));

				char c = arg3.getValue(3).charAt(0);
				boolean fil = true;
				if (c == 'f') {
					fil = false;
				}
				int[] array = getColorPar(color);
				Color col = new Color(array[0], array[1], array[2]);
				int endX = Integer.parseInt(arg3.getValue(1));
				int endY = Integer.parseInt(arg3.getValue(2));


				MyShape sh;
				try {
					sh = (MyShape) Class.forName(arg2).getConstructor(new Class[]{Integer.TYPE,Integer.TYPE}).newInstance(topX, topY);
					sh.endX = endX;
					sh.endY = endY;
					sh.drawCol = col;
					sh.fill = fil;
					sh.resizeBottom(endX, endY);
				MyShape.his.addLast(MyShape.his.remove());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			
		}

		@Override
		public void startPrefixMapping(String arg0, String arg1)
				throws SAXException {
			// TODO Auto-generated method stub

		}
	}

}