# To change this template, choose Tools | Templates
# and open the template in the editor.

require 'corpus'

describe Corpus do
    @@CORPUS = Corpus.new "test/a01.xml"
  before(:each) do
    @corpus = @@CORPUS
  end

  it "type of word 'place/NN' should by 'NN'" do
    @corpus.get_type_of_word("place/NN").should == @corpus.noun
  end

  it "type of word 'bit/NN' should by 'NN'" do
    @corpus.get_type_of_word("bit/NN").should == @corpus.noun
  end

  it "type of word 'Grand/JJ' should by 'NN'" do
    @corpus.get_type_of_word("Grand/JJ").should == @corpus.adjective
  end

  it "type of word 'said/VBD' should by 'NN'" do
    @corpus.get_type_of_word("said/VBD").should_not == @corpus.verb
  end


  it "should split sentence 'The/AT Fulton/NP County/NN' into an" +
 "array['The/AT', 'Fulton/NP', 'County/NN']" do
    @corpus.array_of_sentences[0] = "The/AT Fulton/NP County/NN"
    temp_array = ['The/AT', 'Fulton/NP', 'County/NN'];
    @corpus.get_array_of_words_from_sentence(0).should == temp_array
  end
end

