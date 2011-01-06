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


puts "The beginning of the program============================================="
puts Time.new
collocations = Collocations.new
xml = XMLTools.new "../test/Corpus.xml"
xml = xml.get_xml_document
puts "XML was read #{Time.new}"
collocations.build_collocations_from_xml(xml)
puts "Collocations were build #{Time.new}"
puts Time.new

collocations.print_n_most_frequent_bigrams(1000)
puts "The most frequent bigrams written to file"
puts Time.new
collocations.count_chi_square
collocations.print_n_bigrams_with_biggest_chi_square_value(1000)
puts "Chi square values written to file"
#collocations.bigrams_with_location_in_file.each { |key, value|
#  out.puts "#{key} #{value}"
#}
#collocations.count_chi_square
#puts "The most frequent bigrams"
#collocations.print_n_most_frequent_bigrams(1000)
#puts "========================================================================="
#puts "Collocations with largest chi-square values"
#collocations.print_n_bigrams_with_biggest_chi_square_value 1000

puts "The end of the program==================================================="
puts Time.new