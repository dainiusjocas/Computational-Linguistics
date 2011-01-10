#!/usr/bin/env ruby

# This script cuts last two symbols of every line.
# Command line parameters:
# ARGV[0] input_file
# ARGV[1] output_file

input_file = File.new(ARGV[0], "r")

if (nil != ARGV[1])
  output_file = File.new(ARGV[1], "w+")
else
  output_file = File.new("output.txt", "w+")
end

while (line = input_file.gets)
  line[-2] = line[-3] = ""
  output_file.puts line.strip
end

input_file.close
output_file.close

puts "Job succesful!"