/**
 * ʹ���������w3c.dom������дxml�ļ�
 */
package DOMTest;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter {

	public static void main(String[] args) {

		try {
			//���ȴ���DocumentBuilderFactory������ת����DocumentBuilder
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//����һ���µ�xml����
			Document document = builder.newDocument();
			
			/**
			 * ��������ʼ����������ǩ���ݡ�
			 * �����������������Java���Ĵ�������������ǩ���������appendchild�Ϳ�����
			 */
			//���ȴ�������ǩ
			Element rootElement = document.createElement("People");
			rootElement.setAttribute("type", "family");
			
			//Ȼ������������Ե��ӱ�ǩ
			Element person1 = document.createElement("Person");
			person1.setAttribute("called", "father");
			Element name1 = document.createElement("name");
			name1.setTextContent("Lao Li");
			Element age1 = document.createElement("age");
			age1.setTextContent("52");
			person1.appendChild(name1);
			person1.appendChild(age1);
			
			Element person2 = document.createElement("Person");
			person2.setAttribute("called", "mother");
			Element name2 = document.createElement("name");
			name2.setTextContent("Lao Dai");
			Element age2 = document.createElement("age");
			age2.setTextContent("52");
			person2.appendChild(name2);
			person2.appendChild(age2);
			
			Element person3 = document.createElement("Person");
			person3.setAttribute("called", "son");
			Element name3 = document.createElement("name");
			name3.setTextContent("Xiao Li");
			Element age3 = document.createElement("age");
			age3.setTextContent("27");
			person3.appendChild(name3);
			person3.appendChild(age3);
			
			//��ʼʹ��appendchild��������ǩ��ӽ���
			rootElement.appendChild(person1);
			rootElement.appendChild(person2);
			rootElement.appendChild(person3);
			
			//����γ����յ�xml����
			document.appendChild(rootElement);
			
			
			/**
			 * �����յ����ݴ�ӡ����
			 * ���Ҫ�����ݴ�ӡ�������߽��н�һ��������Ҫ�����ݽ��н�һ��ת������ת����������ʽ
			 */
			//���ȴ���TransformerFactory��������ʵ������Transformer
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			//�����ַ���ת��Writer
			StringWriter stringWriter = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
			System.out.println(stringWriter.toString());
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
