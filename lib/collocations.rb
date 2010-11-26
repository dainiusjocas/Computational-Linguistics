# To change this template, choose Tools | Templates
# and open the template in the editor.

class Collocations
  attr_accessor :structure

  def initialize
    @structure = Hash.new
  end

  # add a pair of words to the structure of collocations
  #
  # @param head the first word of a collocation
  # @param tail the second word of the collocation
  #
  def add_pair_of_words head, tail
    if @structure.has_key?(head)
      (@structure[head]).push tail
    else
      @structure[head] = Array.new 1, tail
    end
  end

  # gets size of the structure of the structure
  #
  def get_size
    return @structure.size
  end

  # prints structure of collocations in a nice way
  #
  def print_structure
    @structure.each_key {
      |k|
      puts k
      @structure[k].each { |item| print " #{item}"  }
      puts
    }
  end
end
