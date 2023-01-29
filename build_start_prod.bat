:: ./gradlew :api:clean :api:bootJar --stacktrace


:: TODO stop port or stop jar 

del D:\git\LP-PLAYLIST-BACKEND\api\build\libs\api_lastest.jar
copy D:\git\LP-PLAYLIST-BACKEND\api\build\libs\api.jar D:\git\LP-PLAYLIST-BACKEND\api\build\libs\api_lastest.jar
java -jar api/build/libs/api_lastest.jar --spring.profiles.active=prod