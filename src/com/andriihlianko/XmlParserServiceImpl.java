package com.andriihlianko;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlParserServiceImpl implements XmlParserService {

	private File origin;
	private File diff;
	private Document docOrigin;
	private Document docDiff;
	private HashMap<Element, Integer> possibleValues;
	private Map<String, String> originAttributesMap;

	public String findPathToDifferentElementByDefault(String pathToOrigin, String pathToDiff) throws IOException {
		init(pathToOrigin, pathToDiff);

		return findPathToMostSimilarElement(TARGET_ID);
	}

	public String findPathToDifferentElementByElementId(String pathToOrigin, String pathToDiff, String elementId)
			throws IOException {
		init(pathToOrigin, pathToDiff);
		return findPathToMostSimilarElement(elementId);
	}

	private String findPathToMostSimilarElement(String targetElementId) {
		Element originElement = docOrigin.getElementById(targetElementId);

		List<Attribute> originAttributes = originElement.attributes().asList();

		for (Attribute attribute : originAttributes) {
			originAttributesMap.put(attribute.getKey(), attribute.getValue());
		}
		calculateProbabilityForAllElementsWithAttributes(docDiff, possibleValues, originAttributesMap);

		Element theMostSimilarElement = findTheMostSimilarElement(possibleValues);
		return computePathToNode(theMostSimilarElement);
	}

	private void init(String pathToOrigin, String pathToDiff) throws IOException {
		origin = getFile(pathToOrigin);
		diff = getFile(pathToDiff);
		docOrigin = Jsoup.parse(origin, "UTF-8");
		docDiff = Jsoup.parse(diff, "UTF-8");
		originAttributesMap = new HashMap<>();
		possibleValues = new HashMap<>();
	}

	private Element findTheMostSimilarElement(HashMap<Element, Integer> possibleValues) {
		int maxEntries = 0;
		Element possibleTheSameElement = (Element) possibleValues.keySet().toArray()[0];
		for (Element e : possibleValues.keySet()) {
			if (possibleValues.get(e) >= maxEntries) {
				possibleTheSameElement = e;
				maxEntries = possibleValues.get(e);
			}
		}
		return possibleTheSameElement;
	}

	private void calculateProbabilityForAllElementsWithAttributes(Document docDiff,
			HashMap<Element, Integer> possibleValues, Map<String, String> originAttributesMap) {
		for (String key : originAttributesMap.keySet()) {
			System.out.println(
					"Looking for elements with attribute: " + key + " and value: " + originAttributesMap.get(key));
			for (Element current : docDiff.getElementsByAttributeValue(key, originAttributesMap.get(key))) {
				if (possibleValues.containsKey(current)) {
					System.out.print("Current element has been already met. Previous value: "
							+ possibleValues.get(current) + ". ");
					possibleValues.put(current, possibleValues.get(current) + 1);
					System.out.println("Putting it to map with new value: " + possibleValues.get(current));
				} else {
					possibleValues.put(current, 0);
					System.out.println("Current element hasn't been met yet. Putting it to map with value 0.");
				}
			}
		}
	}

	private String computePathToNode(Element idsOrigin) {
		StringBuilder output = new StringBuilder();
		if (idsOrigin.siblingElements().size() != 0) {
			output.append(idsOrigin.tagName()).append("[").append(idsOrigin.elementSiblingIndex()).append("]");
		} else {
			output.append(idsOrigin.tagName());
		}
		Element parent;
		if (idsOrigin.hasParent()) {
			parent = idsOrigin;
			while (parent.hasParent()) {
				parent = parent.parent();
				if (parent.siblingElements().size() != 0) {
					int index = parent.elementSiblingIndex();
					output.insert(0, parent.tagName() + "[" + index + "]" + " > ");
				} else {
					output.insert(0, parent.tagName() + " > ");
				}

			}
		}
		return output.toString();
	}

	private File getFile(String path) {
		return new File(path);
	}

}
