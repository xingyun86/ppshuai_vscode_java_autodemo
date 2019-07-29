==========================================================================
1.安装java:
https://github-production-release-asset-2e65be.s3.amazonawhttps://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk8u222-b10/OpenJDK8U-jdk_x64_windows_hotspot_8u222b10.msi
https://github-production-release-asset-2e65be.s3.amazonawhttps://github.com/AdoptOpenJDK/openjdk8-binaries/releases/download/jdk8u222-b10/OpenJDK8U-jdk_x64_windows_hotspot_8u222b10.zip
1.安装maven：
mvn install:install-file -Dfile=selenium-server-standalone-3.141.59.jar -DgroupId=selenium-server-standalone -DartifactId=selenium-server-standalone -Dversion=3.141.59 -Dpackaging=jar

2.安装firefox：

3.编译打包
mvn assembly:assembly