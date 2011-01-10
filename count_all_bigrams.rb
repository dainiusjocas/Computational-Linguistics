#!/usr/bin/env ruby

# This script takes second part of every line, expects that this part is number
# and computes total sum of of these arguments.
# Command line parameters:
# ARGV[0] input_file

input_file = File.new(ARGV[0], "r")

total_sum = 0
while (line = input_file.gets)
  total_sum += line.split(" ")[1].to_i
end
puts "#{total_sum} bigrams found in #{ARGV[0]}"

input_file.close