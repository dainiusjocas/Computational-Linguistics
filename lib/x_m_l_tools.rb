=begin
  * Name: x_m_l_tools.rb
  * Description: this class is for working with xml type date
  * Author: Dainius Jocas
  * Date: 2010-11-25
  * License: Copyright (c) 2010 Dainius Jocas
  * Version: 0.1
=end

require 'rexml/document'
include REXML

class XMLTools
  @@xpath_to_sentence = "//s"
  @@xpath_to_word = "w"
  @@attribute_type = 'type'

  attr_accessor :xmlfile_name, :xml_document
  def initialize xml_file_name
    @xml_file_name = xml_file_name
    @xml_document = load_xml_document @xml_file_name
  end

  # gloads xml document by file name
  #
  # @param file_name
  #
  def load_xml_document file_name
    xml_file = File.new file_name, "r"
    return Document.new xml_file
  end

  # gets xml document
  #
  def get_xml_document
    return @xml_document
  end

  # returns an array of strings where one string is a sentence which contains
  # words where word are in format [word/type]
  #
  def get_array_of_sentences
    result = Array.new
    @xml_document.elements.each(@@xpath_to_sentence) {
      |e|
      temp = ""
      e.elements.each(@@xpath_to_word) {
        |q|
        temp << q.text << "/" << q.attributes[@@attribute_type] << " "
        }
        result.push temp
    }
    return result
  end
end
