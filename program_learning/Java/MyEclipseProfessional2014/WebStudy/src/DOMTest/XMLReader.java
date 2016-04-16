/**
 * 使用最基本的w3c.dom方法解析xml文件
 */
package DOMTest;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader 
{
	public static void main(String[] args) 
	{
		try {
			//引入documentbuilderfactory对象
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new File("XML/languages.xml"));
			
			//首先读取文档中的根元素
			Element rootElement =document.getDocumentElement();
			System.out.println("文档的根元素是:"+rootElement.getTagName()+"\t\tcat属性是:"+rootElement.getAttribute("cat"));
			
			//然后读取文档中的子属性元素
			NodeList list = rootElement.getElementsByTagName("lan");
			for (int i = 0; i < list.getLength(); i++) {
				//将每个子Node转换成element
				Element element= (Element)list.item(i);
				System.out.println("······································································");
				System.out.println("第"+i+"个元素是:"+element.getNodeName()+"\t\tid属性是:"+element.getAttribute("id"));
				if (element.hasChildNodes()) {
					NodeList nodeList = element.getChildNodes();
					for (int j = 0; j < nodeList.getLength(); j++) {
						//在解析的时候回多出#text，是因为getchildnodes会将回车也判断成一个节点
						Node nodeElement = nodeList.item(j);
						if (nodeElement instanceof Element) {
							System.out.println("\t标签"+nodeElement.getNodeName()+"的值是:"+nodeElement.getTextContent());
						}
					}
				}
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
