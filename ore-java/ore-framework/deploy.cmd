REM Build Ore Framework script
@echo off
REM mvn clean deploy -Dmaven.test.skip=true  -DaltDeploymentRepository=nexus-releases::default::http://maven.yunovo.yunovo.cn:8081/nexus/content/repositories/thirdparty/

mvn clean deploy -Dmaven.test.skip=true  -DaltDeploymentRepository=nexus-snapshots::default::http://maven.yunovo.yunovo.cn:8081/nexus/content/repositories/snapshots/