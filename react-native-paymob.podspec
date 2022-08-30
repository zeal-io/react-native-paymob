require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name             = "react-native-paymob"
  s.version          = package["version"]
  s.summary          = package["description"]
  s.homepage         = package["homepage"]
  s.license          = package["license"]
  s.authors          = package["author"]
  s.swift_version    = '5.3.2'



  s.platforms    = { :ios => "12.0" }
  s.source       = { :git => "https://github.com/a-eid/react-native-paymob.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,mm,swift}"

  s.preserve_paths = [
    'ios/**/*.h'
  ]

  s.ios.vendored_frameworks = 'AcceptCardSDK/Classes/AcceptSDK.xcframework'

  s.requires_arc = true
  s.pod_target_xcconfig = { 'VALID_ARCHS' => 'arm64 arm64e armv7 armv7s x86_64' }

  s.ios.vendored_frameworks = 'AcceptCardSDK/Classes/AcceptSDK.xcframework'

  s.dependency "AcceptCardSDK", '>= 5.4.4'
  s.dependency "React-Core"
end
