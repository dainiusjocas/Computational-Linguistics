=begin
  * Name: text_tools.rb
  * Description: this class is for working with text parts extractes from corpus
  * Author: Dainius Jocas
  * Date: 2010-11-25
  * License: Copyright (c) 2010 Dainius Jocas
  * Version: 0.1
=end

require 'x_m_l_tools.rb'
require 'rexml/document'
include REXML

class Corpus
  attr_reader :noun, :verb, :adjective
  attr_accessor :array_of_sentences, :corpus_file_name, :collocations

  # constructor. Saves corpus file name and builds array of sentences from corpus
  #
  # @param corpus_file_name xml file where the corpus is writen
  #
  def initialize corpus_file_name
    @noun = "NN"
    @verb = "VB"
    @adjective = "JJ"
    @corpus_file_name = corpus_file_name
    @array_of_sentences = build_array_of_sentences @corpus_file_name
    @collocations = Collocations.new
  end

  # returns an array of strings where one string is a sentence which contains
  # words where word are in format [word/type]
  #
  # @param xml_file_name
  #
  def build_array_of_sentences xml_file_name
    return XMLTools.new(xml_file_name).get_array_of_sentences
  end

  # returns an array of strings where one string is a sentence which contains
  # words where word are in format [word/type]
  #
  # @param sentence string which present a sentence of words in format
  # [word/type]
  #
  def split_sentence_to_words sentence
    return sentence.split " "
  end

  # returns an array of words that contains a sentence with nr = @param nr
  #
  # @param
  #
  def get_array_of_words_from_sentence nr
    return split_sentence_to_words @array_of_sentences[nr]
  end

  # gets type of word
  #
  # @param word
  #
  def get_type_of_word word
    return word.split("/")[1]
  end

  # gets amount of sentences stored in corpus
  #
  def get_amount_of_sentences
    return @array_of_sentences.length
  end

  # Build a bigrams for collocations. Iterate through every sentence, take
  # every word from sentence and check if it is JJ, NN, VB type, if it is try to
  # match a pair for him of the following words.
  #
  def extract_collocations
    0.upto(self.get_amount_of_sentences - 1) {
      |i|
      words = get_array_of_words_from_sentence(i)
      0.upto(words.length - 2) {
        |j|
        type_of_word = get_type_of_word words[j]
        if ((type_of_word == @noun) || (type_of_word == @verb) || (type_of_word == @adjective))
          (j+1).upto(words.length - 1){
            |k|
            if (@noun == (get_type_of_word words[k])) #there should be a method to put a pair of words to callocation bigrams
              @collocations.add_pair_of_words(words[j], words[k])
            end
          }
        end
      }
    }
  end
end
