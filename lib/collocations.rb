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
    :bigrams_with_location_in_file,     # key: bigram, value: array of [xml_id + sentence_nr]
    :startings_of_bigrams_with_count,   # key: first word of the bigram with type, value: count in corpus
    :endings_of_bigrams_with_count,     # key: second word of the bigram with type, value: count in corpus
    :bigrams,                           # array of bigrams
    :total_number_of_bigrams,
    :context_of_the_bigram,             #
    :xml_document
  attr_reader :noun, :verb, :adjective

  def initialize
    @bigrams_with_count = Hash.new
    @bigrams_with_chi_square_values = Hash.new
    @bigrams_with_location_in_file = Hash.new
    @startings_of_bigrams_with_count = Hash.new
    @endings_of_bigrams_with_count = Hash.new
    @total_number_of_bigrams = 0
    @noun = "NN"
    @verb = "VB"
    @adjective = "JJ"
    @xml_document = nil
    @pp = 0
  end

  # Adds a pair of words to the bigrams_with_count of collocations.
  # Value of the hash is an array of strings where every string is head_tail of
  # collocation
  #
  # @param starting of collocation in format [word/type]
  # @param ending of collocation in format [word/type]
  #
  def add_pair starting, ending
    pair = starting + "_" + ending
    if @bigrams_with_count.has_key?(pair)
      @bigrams_with_count[pair] = @bigrams_with_count[pair] + 1
    else
      @bigrams_with_count[pair] = 1
    end
  end

  # This method builds an hash where key is a bigram and value is an array of
  # location description of bigram. This is needed because bigram can occur in
  # various contexts and this is a requirement.
  #
  # @param starting of collocation in format [word/type]
  # @param ending of collocation in format [word/type]
  # @param xml_id
  # @param sentence_nr
  #
  def keep_track_in_the_file starting, ending, xml_id, sentence_nr
    pair = starting + "_" + ending
    location = "#{xml_id}_#{sentence_nr}"
    if @bigrams_with_location_in_file.has_key?(pair)
      @bigrams_with_location_in_file[pair].push(location)
    else
      @bigrams_with_location_in_file[pair] = [location]
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

  # gets count of the specified starting of bigram
  #
  # @param starting format: [word/type]
  #
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
  #
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
  def get_collocations_from_sentence sentence, xml_id, sentence_nr
    words = sentence.split " "
    for i in 0..words.size - 2
      for j in i+1..words.size - 1
        if "NN" == (words[j].split "/")[1]
          add_pair words[i], words[j]
          keep_track_in_the_file words[i], words[j], xml_id, sentence_nr
          get_context_of_bigram words[i], words[j], xml_id, sentence_nr
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
    @xml_document = xml
    xml.elements.each("//text") {
      |text_node| # corpus xml can be divided into smaller xml files
      text_node.elements.each("//s") {
        |item|
        sentence = ""
        xml_id = item.elements['ancestor::text'].attributes['xml:id']
        item.elements.each("child::node()") {
          |item2|
          if (item2.text == ".")
            get_collocations_from_sentence sentence, text_node.attributes['xml:id'], item.attributes['n']
            sentence = ""
          end
          if (item2.attributes['type'] == @noun || item2.attributes['type'] == @verb || item2.attributes['type'] == @adjective)
            sentence += item2.text.downcase + "/" + item2.attributes['type'] + " "
          end
        }
        get_collocations_from_sentence sentence, text_node.attributes['xml:id'], item.attributes['n']
      }
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
    @bigrams.each { |bigram|
      @bigrams_with_chi_square_values[bigram] = get_chi_square_expected_value bigram
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

  # Sorts hash of collocations by occurence count
  #
  # @return array of arrays where second level array is [bigram, frequency]
  #
  def sort_collocations_by_frequency
    @bigrams_with_count = @bigrams_with_count.sort{|a,b| a[1]<=>b[1]}.reverse
  end

  # Sorts hash of startings of bigrams_with_count by occurence count
  #
  # @return array of arrays where second level array is [starting, frequency]
  #
  def sort_startings_by_frequency
    @startings_of_bigrams_with_count = @startings_of_bigrams_with_count.sort{|a,b| a[1]<=>b[1]}.reverse
  end

  # Sorts hash of endings of bigrams_with_count by occurence count
  #
  # @return array of arrays where second level array is [ending, frequency]
  #
  def sort_endings_by_frequency
    @endings_of_bigrams_with_count = @endings_of_bigrams_with_count.sort{|a,b| a[1]<=>b[1]}.reverse
  end

  # Prints first n elements of bigrams_with_count
  # search results in results/frequency.txt
  #
  def print_n_most_frequent_bigrams n
    out = File.new("../results/frequency.txt", "w+")
    array_of_most_frequent_bigrams = @bigrams_with_count.sort{|a,b| a[1]<=>b[1]}.reverse
    for i in 0..n-1
      out.puts "#{array_of_most_frequent_bigrams[i][0]} #{array_of_most_frequent_bigrams[i][1]}"
    end
    out.close
  end

  # Prints first n elements of bigrams_with_count with chi-square
  # search results in file results/chi-square.txt
  #
  def print_n_bigrams_with_biggest_chi_square_value n
    out = File.new("../results/chi-square.txt", "w+")
    array_of_bigrams_with_descending_chi_square_value = @bigrams_with_chi_square_values.sort{|a,b| a[1]<=>b[1]}.reverse
    for i in 0..n-1
      out.puts "#{array_of_bigrams_with_descending_chi_square_value[i][0]} #{array_of_bigrams_with_descending_chi_square_value[i][1]}"
    end
    out.close
  end

  # Method which get context of collocation
  # I will take approximately 200 words
  #
  # @param bigram
  #
  def get_context_of_bigram starting, ending, xml_id, sentence_nr
    #amount_of_words = 0
    #delta = 1
#    sentence = @xml_document.elements["descendant::text[attribute::xml:id='#{xml_id}']"]
#    sentence = sentence.elements["descendant::s[attribute::n='#{sentence_nr}']"]
    begin
      #puts sentence
      #sentence.elements.each("child::w") { |word| amount_of_words += 1} if amount_of_words < 200
      #sentence = text_node.elements["descendant::s[attribute::n=#{sentence_nr.to_i + delta}]"]

#      puts sentence.attributes['n']#"#{sentence_nr} #{@pp}"
#      @pp += 1
      #amount_of_words += 1
    end #while amount_of_words < 21
  end
end
