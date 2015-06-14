# Small script to rename the connected texture files after using Photoshop Batch processing.
tier = 0

Dir.entries('out').each do |f|
  if f =~ /^solar/
    puts f
    suffix = f.scan(/_(\d+)\.png/).first.first
    index = suffix.length - 1
    Dir.chdir('out') do
      File.rename(f, "solar#{tier}_#{index}.png")
    end
  end
end
