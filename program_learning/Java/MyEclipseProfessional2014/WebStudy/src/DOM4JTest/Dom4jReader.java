package DOM4JTest;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Dom4jReader {

	public static void main(String[] args) {

		try {
			//����saxReader������ȡxml�ļ�
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read("XML/languages.xml");
			Element rootElement = document.getRootElement();
			System.out.println("��Ԫ����"+rootElement.getName()+"\t\t"+rootElement.attribute(0).getName()+"������\""+rootElement.attribute(0).getValue()+"\"");
			
			//�������ӽڵ�
			Iterator iterator = rootElement.elementIterator();
			while (iterator.hasNext()) {
				System.out.println("������������������������������������������������������������������������������������������������������������������������������������������");
				Element element = (Element) iterator.next();
				System.out.println("��Ԫ����"+element.getName()+"\t\t"+element.attribute(0).getName()+"������\""+element.attribute(0).getValue()+"\"");
				
				//�����Ӻ��ӽڵ�
				Iterator iterator_son = element.elementIterator();
				while (iterator_son.hasNext()) {
					Node node_son =  (Node) iterator_son.next();
					System.out.println("\t"+node_son.getName()+"Ԫ����:\""+node_son.getStringValue()+"\"");
				}
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
