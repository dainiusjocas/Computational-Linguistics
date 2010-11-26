=begin
  * Name: main.rb
  * Description: this file is being used to run the project
  * Author: Dainius Jocas
  * Date: 2010-11-25
  * License: Copyright (c) 2010 Dainius Jocas
  * Version: 0.1
=end

require 'rexml/document'
require 'x_m_l_tools.rb'
require 'corpus.rb'
require 'collocations.rb'
include REXML

puts "===The beginning of the program"

corpus = Corpus.new "../test/a01.xml"
corpus.extract_collocations

corpus.collocations.print_structure
#puts corpus.get_array_of_words_from_sentence(0)

puts "===The end of the program"
