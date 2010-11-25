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
  attr_accessor :array_of_sentences, :corpus_file_name

  # constructor. Saves corpus file name and builds array of sentences from corpus
  #
  # @param corpus_file_name xml file where the corpus is writen
  #
  def initialize corpus_file_name
    @corpus_file_name = corpus_file_name
    @array_of_sentences = build_array_of_sentences @corpus_file_name
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
end
