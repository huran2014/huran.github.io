/**
 * ʹ���������w3c.dom��������xml�ļ�
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
			//����documentbuilderfactory����
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(new File("XML/languages.xml"));
			
			//���ȶ�ȡ�ĵ��еĸ�Ԫ��
			Element rootElement =document.getDocumentElement();
			System.out.println("�ĵ��ĸ�Ԫ����:"+rootElement.getTagName()+"\t\tcat������:"+rootElement.getAttribute("cat"));
			
			//Ȼ���ȡ�ĵ��е�������Ԫ��
			NodeList list = rootElement.getElementsByTagName("lan");
			for (int i = 0; i < list.getLength(); i++) {
				//��ÿ����Nodeת����element
				Element element= (Element)list.item(i);
				System.out.println("��������������������������������������������������������������������������������������������������������������������������������������������");
				System.out.println("��"+i+"��Ԫ����:"+element.getNodeName()+"\t\tid������:"+element.getAttribute("id"));
				if (element.hasChildNodes()) {
					NodeList nodeList = element.getChildNodes();
					for (int j = 0; j < nodeList.getLength(); j++) {
						//�ڽ�����ʱ��ض��#text������Ϊgetchildnodes�Ὣ�س�Ҳ�жϳ�һ���ڵ�
						Node nodeElement = nodeList.item(j);
						if (nodeElement instanceof Element) {
							System.out.println("\t��ǩ"+nodeElement.getNodeName()+"��ֵ��:"+nodeElement.getTextContent());
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
