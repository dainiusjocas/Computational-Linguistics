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
include REXML

puts "===The beginning of the program"
count = 1
corpus = Corpus.new "../test/a01.xml"

0.upto(corpus.get_amount_of_sentences - 1) {
  |i|
  words = corpus.get_array_of_words_from_sentence(i)
  0.upto(words.length - 2) {
    |j|
    type_of_word = corpus.get_type_of_word words[j]
    if ((type_of_word == corpus.noun) || (type_of_word == corpus.verb) || (type_of_word == corpus.adjective))
      (j+1).upto(words.length - 1){
        |k|
        if (corpus.noun == (corpus.get_type_of_word words[k])) #there should be a method to put a pair of words to callocation structure
          puts "#{j};#{k} " + words[j] + " " + words[k]
          count += 1
        end
      }
    end
  }
}
#puts corpus.get_array_of_words_from_sentence(0)

puts "===The end of the program"
