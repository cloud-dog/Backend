# (1) base-image
FROM openjdk:17

# (2) COPY에서 사용될 경로 변수
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
ARG SERVICEACCOUNTKEY=./serviceAccountKey.json
#ARG PROD_YML=src/main/resources/application-prod.yml

# (3) jar 빌드 파일을 도커 컨테이너로 복사
COPY ${JAR_FILE} app.jar
COPY ${SERVICEACCOUNTKEY} serviceAccountKey.json
#COPY ${PROD_YML} application-prod.yml

# (4) jar 파일 실행
ENTRYPOINT ["java","-jar","/app.jar"]