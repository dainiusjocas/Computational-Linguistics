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
a = Array.new
collocations = Collocations.new
xml = XMLTools.new "../test/a01.xml"
xml = xml.get_xml_document
collocations.build_collocations_from_xml(xml)
collocations.sort_collocations_by_frequency
collocations.print_first_n_elements(100)

#corpus = Corpus.new "../test/a01.xml"
#corpus.extract_collocations
#
#corpus.collocations.print_structure
#puts corpus.get_array_of_words_from_sentence(0)
puts
puts "===The end of the program"