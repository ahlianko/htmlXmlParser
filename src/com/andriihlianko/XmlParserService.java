package com.andriihlianko;

import java.io.IOException;

public interface XmlParserService {
	String TARGET_ID = "make-everything-ok-button";

	String findPathToDifferentElementByDefault(String pathToOrigin, String pathToDiff) throws IOException;

	String findPathToDifferentElementByElementId(String pathToOrigin, String pathToDiff, String elementId)
			throws IOException;

}
