=begin
  * Name: collocations.rb
  * Description: this class is for working with the structure of extracted
  *  collocations
  * Author: Dainius Jocas
  * Date: 2010-11-26
  * License: Copyright (c) 2010 Dainius Jocas
  * Version: 0.1
=end

class Collocations
  attr_accessor :structure
  attr_reader :noun, :verb, :adjective

  def initialize
    @structure = Hash.new
    @noun = "NN"
    @verb = "VB"
    @adjective = "JJ"
  end

  # Adds a pair of words to the structure of collocations.
  # Value of the hash is an array of strings where every string is tail of
  # collocation
  #
  # @param head of collocation
  # @param tail of collocation
  #
  def add_pair head, tail
    pair = head + "_" + tail
    if @structure.has_key?(pair)
      @structure[pair] = @structure[pair] + 1
    else
      @structure[pair] = 1
    end
  end

  # gets size of the structure of collocations
  #
  def get_size
    return @structure.size
  end

  # parses sentence and put pairs of words into the structure
  #
  # @param sentence string of words [word/type]
  #
  def get_collocations_from_sentence sentence
    words = sentence.split " "
    for i in 0..words.size - 2
      for j in i+1..words.size - 1
        add_pair words[i], words[j] if "NN" == (words[j].split "/")[1]
      end
    end
  end
  
  # fills the collocation list from the xml file
  #
  # @param xml xml file
  #
  def build_collocations_from_xml xml
    xml.elements.each("//s") {
      |item|
      sentence = ""
      item.elements.each("child::node()") {
        |item2|
        if (item2.text == ".")
          get_collocations_from_sentence sentence
        end
        if (item2.attributes['type'] == @noun || item2.attributes['type'] == @verb || item2.attributes['type'] == @adjective)
          sentence += item2.text + "/" + item2.attributes['type'] + " "
        end
      }
    }
  end

  # Sorts hash of collocations by accurency count
  #
  def sort_collocations_by_frequency
    @structure = @structure.sort{|a,b| a[1]<=>b[1]}.reverse
  end

  def print_first_n_elements n
    for i in 0..n
      puts @structure[i]
    end
  end

end
