package DOM4JTest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Dom4jWriter {

	public static void main(String[] args) {

		//�����ĵ�
		Document document = DocumentHelper.createDocument();
		//��Ӹ�Ԫ��
		Element rootElement = document.addElement("People")
											.addAttribute("type", "family");
		
		//�����ڵ�Ԫ��
		Element persone1Element = rootElement.addElement("Person")
												.addAttribute("called", "father");
		persone1Element.addElement("name").addText("Lao Li");
		persone1Element.addElement("age").addText("52");

		//�����ڵ�Ԫ��
		Element persone2Element = rootElement.addElement("Person")
												.addAttribute("called", "mother");
		persone2Element.addElement("name").addText("Lao Zhao");
		persone2Element.addElement("age").addText("52");
		
		//�����ڵ�Ԫ��
		Element persone3Element = rootElement.addElement("Person")
												.addAttribute("called", "son");
		persone3Element.addElement("name").addText("Xiao Li");
		persone3Element.addElement("age").addText("27");
		
		System.out.println(document.asXML());
	}

}
