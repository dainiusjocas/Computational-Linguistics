=begin
  * Name: main.rb
  * Description: this file is being used to run the project
  * Author: Dainius Jocas
  * Date: 2010-11-2
  * License: Copyright (c) 2010 Dainius Jocas
  * Version: 0.1
=end

require 'rexml/document'
require 'x_m_l_tools.rb'
require 'corpus.rb'
require 'collocations.rb'
include REXML


puts "===The beginning of the program"

collocations = Collocations.new
xml = XMLTools.new "../test/a01.xml"
xml = xml.get_xml_document
xml.elements.each("//s") {
  |item|
  sentence = ""
  item.elements.each("child::node()") {
    |item2|
    if (item2.text == ".")
      collocations.get_collocations_from_sentence sentence
    end
    if (item2.attributes['type'] == "NN" || item2.attributes['type'] == "VB" || item2.attributes['type'] == "JJ")
      sentence += item2.text + "/" + item2.attributes['type'] + " "
    end
    #print "#{item2.text}/#{item2.attributes['type']} "
  }
}
collocations.print_collocations_with_frequency

#corpus = Corpus.new "../test/a01.xml"
#corpus.extract_collocations
#
#corpus.collocations.print_structure
#puts corpus.get_array_of_words_from_sentence(0)
puts
puts "===The end of the program"