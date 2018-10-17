# onboarder

To start the server:

```
./gradlew clean build shadowJar
java -jar build/libs/onboarder-all.jar
```

To test the server:

```
curl -X POST -d '{"emailAddress":"somebody@nowhere.exists", "localAuthorityCode":"YABBADABBADOO"}' http://localhost:4567/apiConsumers


eg for pre-prod
curl -X POST -d '{"emailAddress":"andy.rea@does.not.exist", "localAuthorityCode":"ABER"}' https://onboarder.pre.does.not.exist/apiConsumers
```
