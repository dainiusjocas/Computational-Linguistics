=begin
  * Name: collocations_spec.rb
  * Description: this file is for testing Collocations class
  * Author: Dainius Jocas
  * Date: 2010-12-03
  * License: Copyright (c) 2010 Dainius Jocas
  * Version: 0.1
=end
require 'collocations'
require 'x_m_l_tools'

describe Collocations do
  before(:each) do
    @collocations = Collocations.new
  end

  it "size of the list of collocation should be equal to 0 when there are no words inserted" do
    @collocations.get_size.should == 0
  end

  it "size of the list of collocations should be equal to 1 after one insertion" do
    @collocations.add_pair("head", "tail")
    @collocations.get_size.should == 1
  end

  it "size of the list of collocations should be equal to 1 after insertion of such pair which head already exists" do
    @collocations.add_pair("head", "tail")
    @collocations.add_pair("head", "tail")
    @collocations.add_pair("head", "tail")
    @collocations.get_size.should == 1
  end

  it "size of the list of collocations should be equal to 2 after insertion of such pair which head already doesn't exists" do
    @collocations.add_pair("head1", "tail")
    @collocations.add_pair("head1", "tail2")
    @collocations.get_size.should == 2
  end

  it "hash cell with key 'a_a' should be equal to 0" do
    @collocations.bigrams_with_count["a_a"].should == nil
  end

  it "hash cell with key 'a_a' should be equal to nil" do
    @collocations.add_pair "a", "a"
    @collocations.bigrams_with_count["a_a"].should == 1
  end

  it "hash cell with key 'a_a' should be equal to 2" do
    @collocations.add_pair "a", "a"
    @collocations.add_pair "a", "a"
    @collocations.bigrams_with_count["a_a"].should == 2
  end

  it "total number of bigrams should be 1 when one_bigram.xml is loaded" do
    xml = XMLTools.new "test/one_bigram.xml"
    xml = xml.get_xml_document
    @collocations.build_collocations_from_xml(xml)
    @collocations.total_number_of_bigrams.should == 1
  end

  # this test checks if bigrams_with_count are collected in appropriate way from file
  it "total number of bigrams should be 31 when loaded one_sentence.xml file" do
    xml = XMLTools.new "test/one_sentence.xml"
    xml = xml.get_xml_document
    @collocations.build_collocations_from_xml(xml)
    @collocations.total_number_of_bigrams.should == 31
  end

  it "total number of startings of bigrams should be 1 when one_bigram.xml is loaded" do
    xml = XMLTools.new "test/one_bigram.xml"
    xml = xml.get_xml_document
    @collocations.build_collocations_from_xml(xml)
    @collocations.startings_of_bigrams_with_count.size.should == 1
  end

  it "total number of startings of bigrams should be 8 when one_sentence.xml is loaded" do
    xml = XMLTools.new "test/one_sentence.xml"
    xml = xml.get_xml_document
    @collocations.build_collocations_from_xml(xml)
    @collocations.startings_of_bigrams_with_count.size.should == 8
  end

  it "total number of endings of bigrams should be 1 when one_bigram.xml is loaded" do
    xml = XMLTools.new "test/one_bigram.xml"
    xml = xml.get_xml_document
    @collocations.build_collocations_from_xml(xml)
    @collocations.endings_of_bigrams_with_count.size.should == 1
  end

  it "total number of endings of bigrams should be 8 when one_sentence.xml is loaded" do
    xml = XMLTools.new "test/one_sentence.xml"
    xml = xml.get_xml_document
    @collocations.build_collocations_from_xml(xml)
    @collocations.endings_of_bigrams_with_count.size.should == 6
  end

  it "total number of starting 'dainius/NN' should be 1 when one_bigram.xml is loaded" do
    xml = XMLTools.new "test/one_bigram.xml"
    xml = xml.get_xml_document
    @collocations.build_collocations_from_xml(xml)
    @collocations.get_starting_count("dainius/NN").should == 1
  end
  
  it "total number of starting 'not_exists/NN' should be 0 when one_bigram.xml is loaded" do
    xml = XMLTools.new "test/one_bigram.xml"
    xml = xml.get_xml_document
    @collocations.build_collocations_from_xml(xml)
    @collocations.get_starting_count("not_exists/NN").should == 0
  end

  it "total number of endings 'place/NN' should be 8 when one_bigram.xml is loaded" do
    xml = XMLTools.new "test/one_sentence.xml"
    xml = xml.get_xml_document
    @collocations.build_collocations_from_xml(xml)
    @collocations.get_ending_count("place/NN").should == 8
  end

  it "total number of endings 'non_existing/NN' should be 8 when one_bigram.xml is loaded" do
    xml = XMLTools.new "test/one_sentence.xml"
    xml = xml.get_xml_document
    @collocations.build_collocations_from_xml(xml)
    @collocations.get_ending_count("non_existing/NN").should == 0
  end
end

