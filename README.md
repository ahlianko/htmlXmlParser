# htmlXmlParser
Parser of html elements.

Finds the path to a modified html element. By default finds modified element
with id "make-everything-ok-button".
Outputs path to modified element like "html > body > div > div[1]"

How to run:

cd htmlXmlParser/

Run: java -jar XmlParser.jar [path_to_origin_html] [path_to_diff_html]

Run: java -jar XmlParser.jar [path_to_origin_html] [path_to_diff_html] [element_id]
