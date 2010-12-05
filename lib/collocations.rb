=begin
  * Name: collocations.rb
  * Description: this class is for working with the bigrams_with_count of extracted
  *  collocations
  * Author: Dainius Jocas
  * Date: 2010-12-03
  * License: Copyright (c) 2010 Dainius Jocas
  * Version: 0.2
=end

class Collocations
  attr_accessor :bigrams_with_count,    # key: bigram, value: count in corpus
    :bigrams_with_chi_square_values,    # key: bigram, value: chi-square value of bigram
    :startings_of_bigrams_with_count,   # key: first word of the bigram with type, value: count in corpus
    :endings_of_bigrams_with_count,     # key: second word of the bigram with type, value: count in corpus
    :bigrams,                           # array of bigrams
    :total_number_of_bigrams
  attr_reader :noun, :verb, :adjective

  def initialize
    @bigrams_with_count = Hash.new
    @bigrams_with_chi_square_values = Hash.new
    @startings_of_bigrams_with_count = Hash.new
    @endings_of_bigrams_with_count = Hash.new
    @total_number_of_bigrams = 0
    @noun = "NN"
    @verb = "VB"
    @adjective = "JJ"
  end

  # Adds a pair of words to the bigrams_with_count of collocations.
  # Value of the hash is an array of strings where every string is head_tail of
  # collocation
  #
  # @param head of collocation in format [word/type]
  # @param tail of collocation in format [word/type]
  #
  def add_pair head, tail
    pair = head + "_" + tail
    if @bigrams_with_count.has_key?(pair)
      @bigrams_with_count[pair] = @bigrams_with_count[pair] + 1
    else
      @bigrams_with_count[pair] = 1
    end
  end

  # add member to a hash of startings of bigrams_with_count. if there exists such member
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
    count =  @startings_of_bigrams_with_count[starting]
    if (nil != count)
      return count
    end
    return 0
  end

  # prints hash of starting
  # TODO delete this method
  def print_startings
    @startings_of_bigrams_with_count.each { |key,value| puts "#{key} #{value}" }
  end

  # add member to a hash of endings of bigrams_with_count. if there exists such member
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
    count = @endings_of_bigrams_with_count[ending]
    if (nil != count)
      return count
    end
    return 0
  end

  # prints hash of starting
  # TODO delete this method
  def print_endings
    @endings_of_bigrams_with_count.each { |key,value| puts "#{key} #{value}" }
  end

  # gets size of the bigrams_with_count of collocations
  #
  def get_size
    return @bigrams_with_count.size
  end

  # parses sentence and put pairs of words into the bigrams_with_count
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
  
  # fills the gigrams, starting of the bigrams_with_count and endings of bigrams hashes
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
          sentence = ""
        end
        if (item2.attributes['type'] == @noun || item2.attributes['type'] == @verb || item2.attributes['type'] == @adjective)
          sentence += item2.text.downcase + "/" + item2.attributes['type'] + " "
        end
      }
      get_collocations_from_sentence sentence
    }
#    sort_collocations_by_frequency 
#    sort_startings_by_frequency
#    sort_endings_by_frequency

    @bigrams = @bigrams_with_count.keys
    set_total_number_of_bigrams
  end

  # sets number of bigrams_with_count constructed.
  #
  def set_total_number_of_bigrams
    @total_number_of_bigrams = @bigrams_with_count.size
  end

  # build a hash where for every bigram is assigned chi-square value
  #
  def count_chi_square
    bigrams.each { |i|
      @bigrams_with_chi_square_values[i] = get_chi_square_expected_value i
    }
  end

  # count chi-square value of the bigram
  #
  # @param bigram in format [word/type_word/type]
  #
  def get_chi_square_expected_value bigram
    starting = get_starting_of_bigram bigram
    ending = get_ending_of_bigram bigram
    starting_count = get_starting_count starting
    ending_count = get_ending_count ending
    return (starting_count * ending_count)/Float(@total_number_of_bigrams)
  end

  #gets starting of bigram in format [word/type]
  #
  def get_starting_of_bigram bigram
    return bigram.split("_")[0]
  end

  #gets ending of bigram in format [word/type]
  #
  def get_ending_of_bigram bigram
    return bigram.split("_")[1]
  end

  # file than
  # Sorts hash of collocations by occurence count
  #
  def sort_collocations_by_frequency
    @bigrams_with_count = @bigrams_with_count.sort{|a,b| a[1]<=>b[1]}.reverse
  end

  # Sorts hash of startings of bigrams_with_count by occurence count
  #
  def sort_startings_by_frequency
    @startings_of_bigrams_with_count = @startings_of_bigrams_with_count.sort{|a,b| a[1]<=>b[1]}.reverse
  end

  # Sorts hash of endings of bigrams_with_count by occurence count
  #
  def sort_endings_by_frequency
    @endings_of_bigrams_with_count = @endings_of_bigrams_with_count.sort{|a,b| a[1]<=>b[1]}.reverse
  end

  #Prints first n elements of bigrams_with_count
  #
  def print_n_most_frequent_bigrams n
    array_of_most_frequent_bigrams = @bigrams_with_count.sort{|a,b| a[1]<=>b[1]}.reverse
    for i in 0..n
      puts "#{array_of_most_frequent_bigrams[i][0]} #{array_of_most_frequent_bigrams[i][1]}"
    end
  end

  #Prints first n elements of bigrams_with_count
  #
  def print_n_bigrams_with_biggest_chi_square_value n
    array_of_bigrams_with_descending_chi_square_value = @bigrams_with_chi_square_values.sort{|a,b| a[1]<=>b[1]}.reverse
    for i in 0..n
      puts "#{array_of_bigrams_with_descending_chi_square_value[i][0]} #{array_of_bigrams_with_descending_chi_square_value[i][1]}"
    end
  end
end
