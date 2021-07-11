I use postgres database in the project you can use Docker to spin a new postgres container.


DOCKER COMMAND
docker run -d --name postgres -e POSTGRES_PASSWORD=ahmad -e POSTGRES_USER=ahmad -e POSTGRES_DB=elm -p 5432:5432 postgres