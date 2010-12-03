=begin
  * Name: collocations.rb
  * Description: this class is for working with the bigrams of extracted
  *  collocations
  * Author: Dainius Jocas
  * Date: 2010-12-03
  * License: Copyright (c) 2010 Dainius Jocas
  * Version: 0.2
=end

class Collocations
  attr_accessor :bigrams,    # key: bigram, value: count in corpus
    :startings_of_bigrams_with_count,   # key: first word of the bigram with type, value: count in corpus
    :endings_of_bigrams_with_count,     # key: second word of the bigram with type, value: count in corpus
    :total_number_of_bigrams
  attr_reader :noun, :verb, :adjective

  def initialize
    @bigrams = Hash.new
    @startings_of_bigrams_with_count = Hash.new
    @endings_of_bigrams_with_count = Hash.new
    @total_number_of_bigrams = 0
    @noun = "NN"
    @verb = "VB"
    @adjective = "JJ"
  end

  # Adds a pair of words to the bigrams of collocations.
  # Value of the hash is an array of strings where every string is head_tail of
  # collocation
  #
  # @param head of collocation in format [word/type]
  # @param tail of collocation in format [word/type]
  #
  def add_pair head, tail
    pair = head + "_" + tail
    if @bigrams.has_key?(pair)
      @bigrams[pair] = @bigrams[pair] + 1
    else
      @bigrams[pair] = 1
    end
  end

  # add member to a hash of startings of bigrams. if there exists such member
  # then just increase the count of that starting
  #
  # @param word_with_type
  #
  def add_starting word_with_type
    if @startings_of_bigrams_with_count.has_key?(word_with_type)
      @startings_of_bigrams_with_count[word_with_type] = @startings_of_bigrams_with_count[word_with_type] + 1
    else
      @startings_of_bigrams_with_count[word_with_type] = 1
    end
  end

  # gets count of the starting
  #
  # @param starting format: [word/type]
  def get_starting_count starting
    return @startings_of_bigrams_with_count[starting]
  end

  # prints hash of starting
  # TODO delete this method
  def print_startings
    @startings_of_bigrams_with_count.each { |key,value| puts "#{key} #{value}" }
  end

  # add member to a hash of endings of bigrams. if there exists such member
  # then just increase the count of that starting
  #
  # @param word_with_type
  #
  def add_ending word_with_type
    if @endings_of_bigrams_with_count.has_key?(word_with_type)
      @endings_of_bigrams_with_count[word_with_type] = @endings_of_bigrams_with_count[word_with_type] + 1
    else
      @endings_of_bigrams_with_count[word_with_type] = 1
    end
  end

  # gets count of the ending
  #
  # @param ending format: [word/type]
  def get_ending_count ending
    return @endings_of_bigrams_with_count[ending]
  end

  # prints hash of starting
  # TODO delete this method
  def print_endings
    @endings_of_bigrams_with_count.each { |key,value| puts "#{key} #{value}" }
  end

  # gets size of the bigrams of collocations
  #
  def get_size
    return @bigrams.size
  end

  # parses sentence and put pairs of words into the bigrams
  #
  # @param sentence string of words [word/type]
  #
  def get_collocations_from_sentence sentence
    words = sentence.split " "
    for i in 0..words.size - 2
      for j in i+1..words.size - 1
        if "NN" == (words[j].split "/")[1]
          add_pair words[i], words[j]
          add_starting words[i]
          add_ending words[j]
        end
      end
    end
  end
  
  # fills the gigrams, starting of the bigrams and endings of bigrams hashes
  # from the xml file
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
          sentence += item2.text.downcase + "/" + item2.attributes['type'] + " "
        end
      }
    }
    sort_collocations_by_frequency
    sort_startings_by_frequency
    sort_endings_by_frequency
  end

  # Sorts hash of collocations by occurence count
  #
  def sort_collocations_by_frequency
    @bigrams = @bigrams.sort{|a,b| a[1]<=>b[1]}.reverse
  end

  # Sorts hash of startings of bigrams by occurence count
  #
  def sort_startings_by_frequency
    @startings_of_bigrams_with_count = @startings_of_bigrams_with_count.sort{|a,b| a[1]<=>b[1]}.reverse
  end

  # Sorts hash of endings of bigrams by occurence count
  #
  def sort_endings_by_frequency
    @endings_of_bigrams_with_count = @endings_of_bigrams_with_count.sort{|a,b| a[1]<=>b[1]}.reverse
  end

  #Prints first n elements of bigrams
  #
  def print_first_n_elements n
    for i in 0..n
      puts @bigrams[i]
    end
  end

end
