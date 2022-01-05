# Vault-Kubernetes-Java Integration

This application is built for Vault-Kubernetes sidecar container testing. It shows Vault secrets on a page.
Here is [the documentation we follow](https://learn.hashicorp.com/tutorials/vault/kubernetes-sidecar#install-the-vault-helm-chart) to configure Vault with Kubernetes and add test secrets.

Basically, this application prints a content of a file. 
We assume that the file holds a Vault secret received via Vault Agent sidecar container.
 
## Usage
- Default secret data (database-config.txt)  - http://localhost:8080/secret/
- Custom secret data (database-config.txt)  - http://localhost:8080/secret/<secret-file-name>
- Example with custom secret data (database-config.txt)  - http://localhost:8080/secret/database-config.txt

## Run the app
```
SECRETS_LOCATION=/tmp ./gradlew bootRun
```

## Test the app
```
./gradlew test
```

## How to build the app
```
./gradlew bootBuildImage
```
We should get
Successfully built image 'docker.io/library/vault-kubernetes-java:v1'

## Tag and Push to Docker Hub (you should be logged in)
```
docker tag vault-kubernetes-java:v1 grenader/vault-kubernetes-java:v2
docker push grenader/vault-kubernetes-java:v2
```

## Start the app on Docker
```
docker container run --name vkj-test -dt --rm -e SECRETS_LOCATION=anything -p 8088:8080 grenader/vault-kubernetes-java:v2 
```

## check the docker app logs:
```
docker logs vkj-test
```

# Deployments

## Deploy this application to Kubernetes
```
k apply -f k8s/show-secret-app.yml
```

## Open the application controller page:
```
# Get External IP address:
SHOW_SECRET_SERVICE_IP=$(k get svc vkjservice -o jsonpath="{.status.loadBalancer.ingress[0].ip}")

# open a new browser to see the page
open http://$SHOW_SECRET_SERVICE_IP/secret
```
This page should show a formatted String with POSTGRES connection URL.

## Deploy another application (dockerHub: grenader/java-k8s) to Kubernetes 
```
k apply -f k8s/get-env-var-app.yml
```

## Open the application controller page:
```
SHOW_ENV_SERVICE_IP=$(k get svc springboot-service -o jsonpath="{.status.loadBalancer.ingress[0].ip}")
open http://$SHOW_ENV_SERVICE_IP/actuator/env/POSTGRESS_CONNECTION
```
This page shows POSTGRESS_CONNECTION environment variable loaded into the SpringBoot app



