# app-pow


$> systemctl stop docker
$> systemctl status docker
$> docker build --file=Dockerfile \ --tag=config-server:latest --rm=true . 
$> docker volume create --name=spring-cloud-config-repo
$> docker run --name=config-server --publish=8085:8085      --volume=spring-cloud-config-repo:/var/lib/spring-cloud/config-repo      config-server:latest
$> docker inspect config-server
$> docker stop config-server
$> docker rm config-server
