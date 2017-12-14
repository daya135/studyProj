package org.jzz.study.io;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nu.xom.*;

/**
 * @author Merin
 * 使用xml保存bean的例子，用到了第三方XOM包（比较古老）
 */
public class XMLPerson {
	private String first, last;
	public XMLPerson (String first, String last) {
		this.first = first;
		this.last = last;
	}
	public Element getXML() {
		Element person = new Element("person");
		Element firstName = new Element("first");
		firstName.appendChild(first);
		Element lastName = new Element("last");
		lastName.appendChild(last);
		person.appendChild(firstName);
		person.appendChild(lastName);
		return person;
	}
	public XMLPerson(Element person) {
		first = person.getFirstChildElement("first").getValue();
		last = person.getFirstChildElement("last").getValue();
	}
	public String toString () {
		return first + last;
	}
	public static void format(OutputStream os, Document doc) throws Exception{
		Serializer serializer = new Serializer(os, "ISO-8859-1");
		serializer.setIndent(4);
		serializer.setMaxLength(60);
		serializer.write(doc);
		serializer.flush();
	}
	public static void main(String[] args) throws Exception {
		List<XMLPerson> persons = Arrays.asList(
				new XMLPerson("j", "zz"),
				new XMLPerson("j", "zl"),
				new XMLPerson("j", "zq")
		);
		System.out.println(persons);
		Element root = new Element("people");
		for (XMLPerson person : persons) {
			root.appendChild(person.getXML());
		}
		Document doc = new Document(root);
		format(System.out, doc);
		format(new BufferedOutputStream(new FileOutputStream("XMLPerson.xml")), doc);
	}
}
