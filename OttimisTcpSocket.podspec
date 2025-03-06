
  Pod::Spec.new do |s|
    s.name = 'OttimisTcpSocket'
    s.version = '0.1.0'
    s.summary = 'Client for communicate via TCP'
    s.license = 'MIT'
    s.homepage = 'https://git.ottimis.com'
    s.author = 'Ottimis Group srls'
    s.source = { :git => 'https://git.ottimis.com', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target = '14.0'
    s.dependency 'Capacitor'
    s.dependency 'SwiftSocket'
  end