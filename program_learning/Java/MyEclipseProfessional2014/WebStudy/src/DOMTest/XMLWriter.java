/**
 * 使用最基本的w3c.dom方法编写xml文件
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
			//首先创建DocumentBuilderFactory并将其转换成DocumentBuilder
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//创建一个新的xml内容
			Document document = builder.newDocument();
			
			/**
			 * 接下来开始创建各个标签内容。
			 * 这个创建过程类似于Java面板的创建，将各个标签创建后进行appendchild就可以了
			 */
			//首先创建根标签
			Element rootElement = document.createElement("People");
			rootElement.setAttribute("type", "family");
			
			//然后逐个创建各自的子标签
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
			
			//开始使用appendchild将各个标签添加进来
			rootElement.appendChild(person1);
			rootElement.appendChild(person2);
			rootElement.appendChild(person3);
			
			//最后形成最终的xml内容
			document.appendChild(rootElement);
			
			
			/**
			 * 将最终的内容打印出来
			 * 如果要将内容打印出来或者进行进一步处理需要对内容进行进一步转换才能转换成其他格式
			 */
			//首先创建TransformerFactory，并将其实例化城Transformer
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			//创建字符串转换Writer
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
