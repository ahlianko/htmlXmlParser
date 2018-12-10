package com.andriihlianko;

public class XmlParser {
	public static void main(String[] args) {
		if (args.length == 2) {
			System.out.println("Target element id: " + "make-everything-ok-button");
			System.out.println("Input files are: origin - " + args[0] + "; diff - " + args[1]);
			XmlParserService xmlParserService = new XmlParserServiceImpl();
			try {
				System.out.println(xmlParserService.findPathToDifferentElementByDefault(args[0], args[1]));
			} catch (Exception e) {
				System.out.println("Error inputing files: " + e);
			}
		}
		if (args.length == 3) {
			System.out.println("Target element id: " + args[2]);
			System.out.println("Input files are: origin - " + args[0] + "; diff - " + args[1]);
			XmlParserService xmlParserService = new XmlParserServiceImpl();
			try {
				System.out.println(xmlParserService.findPathToDifferentElementByElementId(args[0], args[1], args[2]));
			} catch (Exception e) {
				System.out.println("Error inputing files: " + e);
			}
		}
	}
}
