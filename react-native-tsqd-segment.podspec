require 'json'
package_json = JSON.parse(File.read('package.json'))

Pod::Spec.new do |spec|
  spec.name           = "react-native-tsqd-segment"
  spec.version        = package_json["version"]
  spec.summary        = package_json["description"]
  spec.homepage       = package_json["repository"]
  spec.license        = package_json["license"]
  spec.author         = { package_json["author"] => package_json["author"] }
  spec.platform       = :ios, "9.0"
  spec.source         = { :git => package_json["repository"] }
  spec.dependency 'React'
  spec.dependency 'segment-appsflyer-ios'
  spec.source_files = 'ios/*.{h,m}'
end
