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
			//创建saxReader用来读取xml文件
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read("XML/languages.xml");
			Element rootElement = document.getRootElement();
			System.out.println("根元素是"+rootElement.getName()+"\t\t"+rootElement.attribute(0).getName()+"属性是\""+rootElement.attribute(0).getValue()+"\"");
			
			//遍历孩子节点
			Iterator iterator = rootElement.elementIterator();
			while (iterator.hasNext()) {
				System.out.println("·····································································");
				Element element = (Element) iterator.next();
				System.out.println("子元素是"+element.getName()+"\t\t"+element.attribute(0).getName()+"属性是\""+element.attribute(0).getValue()+"\"");
				
				//遍历子孩子节点
				Iterator iterator_son = element.elementIterator();
				while (iterator_son.hasNext()) {
					Node node_son =  (Node) iterator_son.next();
					System.out.println("\t"+node_son.getName()+"元素是:\""+node_son.getStringValue()+"\"");
				}
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
