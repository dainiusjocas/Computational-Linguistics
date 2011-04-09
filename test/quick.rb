require 'rexml/document'
include REXML
puts "Begining================================================================" 
xml = File.new "a01.xml", "r"
xml = Document.new xml

a = 3
xml.elements.each("//text") {
  |xml_text|
  sentence = xml_text.elements["descendant::s[attribute::n=#{20}]"]
  puts sentence
  #sentence.elements['following-sibling']

}

puts "End====================================================================="