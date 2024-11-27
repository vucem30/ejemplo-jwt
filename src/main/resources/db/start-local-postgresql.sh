BASE=/Users/garellano/Desktop/spring-demo/src/main/resources/db
# **************************************

mkdir -p $BASE/data
mkdir -p $BASE/scripts

DATABASE=$BASE/data
SCRIPTS=$BASE/scripts
ROOT_PASS=garellano

docker stop vucem3-db
docker rm vucem3-db

docker run -d \
--name vucem3-db \
--restart unless-stopped \
-p 15432:5432 \
-v $DATABASE:/var/lib/postgresql/data \
-v $SCRIPTS:/scripts \
-e POSTGRES_PASSWORD=$ROOT_PASS \
postgres
