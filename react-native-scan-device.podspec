require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-scan-device"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-scan-device
                   DESC
  s.homepage     = "https://github.com/github.com/mediaburg/react-native-scan-device"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "MediaBurg" => "info@mediaburg.ch" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/github.com/mediaburg/react-native-scan-device.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  # ...
  # s.dependency "..."
end

