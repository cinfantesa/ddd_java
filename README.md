## Code challenge

To implement this API I decided to do it in a Clean Architecture way.
I know this way adds complexity, but I wanted to show how I implement APIs in real situations.

### Merge Request
Every task in trello has a feature branch in gitlab...
- [Create User](https://gitlab.agilecontent.com/tech-challenges/cristobal-infantes-java-backend-code-challenge/-/merge_requests/1)
- [Delete User](https://gitlab.agilecontent.com/tech-challenges/cristobal-infantes-java-backend-code-challenge/-/merge_requests/2)
- [Update User](https://gitlab.agilecontent.com/tech-challenges/cristobal-infantes-java-backend-code-challenge/-/merge_requests/3)
- [Get User](https://gitlab.agilecontent.com/tech-challenges/cristobal-infantes-java-backend-code-challenge/-/merge_requests/4)
- [Get all Users](https://gitlab.agilecontent.com/tech-challenges/cristobal-infantes-java-backend-code-challenge/-/merge_requests/5)
- [Generate Users](https://gitlab.agilecontent.com/tech-challenges/cristobal-infantes-java-backend-code-challenge/-/merge_requests/6)
- [Dockerize](https://gitlab.agilecontent.com/tech-challenges/cristobal-infantes-java-backend-code-challenge/-/merge_requests/7)
- [Pagination](https://gitlab.agilecontent.com/tech-challenges/cristobal-infantes-java-backend-code-challenge/-/merge_requests/8)

### Considerations
- I used trello to organize myself (https://trello.com/b/RodWe6BO/code-challenge)
- The API documentation was written with Postman. You can check it and import it from https://documenter.getpostman.com/view/3002715/TVYF6xsT
- When our domain (user) change, an event is fired. 
  All events in the module are documented using asyncapi. 
  To check it, just visit https://playground.asyncapi.io/ and paste the content of asyncApi.yaml file.
- Added Gitlab Pipeline just to check all tests pass.  

### Running 
Just run from root folder the following command

```bash
sh ./run.sh
```

This script will:
- compile shared module
- compile user module
- build user docker image
- run generated docker image

Once docker is running you can run in Postman de documentation and start checking the api.

In case you have any problem running the script, just execute de docker from dockerhub with following command:
```bash
docker run --name agiletv-challenge -d -p8080:8080 cinfantes/code-challenge
```