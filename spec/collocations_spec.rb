# To change this template, choose Tools | Templates
# and open the template in the editor.

require 'collocations'

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
    @collocations.structure["a_a"].should == nil
  end

  it "hash cell with key 'a_a' should be equal to nil" do
    @collocations.add_pair "a", "a"
    @collocations.structure["a_a"].should == 1
  end

  it "hash cell with key 'a_a' should be equal to 2" do
    @collocations.add_pair "a", "a"
    @collocations.add_pair "a", "a"
    @collocations.structure["a_a"].should == 2
  end
end

