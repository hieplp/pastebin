#!/usr/bin/env bash
# Dev launcher: pastebin DB + Redis (docker) + Spring Boot app.
set -euo pipefail
cd "$(dirname "$0")"

persistence="${PASTEBIN_PERSISTENCE_TYPE:-jpa}"

# --- Database -------------------------------------------------------------
# Mirrors ../docker/docker-compose.yml. Kept as docker run because compose
# hardcodes host ports, which may be taken.
if [ "$persistence" = mongo ]; then
  if ! docker ps -a --format '{{.Names}}' | grep -qx pastebin-mongo; then
    mport=27017
    if (exec 3<>/dev/tcp/127.0.0.1/27017) 2>/dev/null; then
      mport=27018
    fi
    echo "Starting mongo on host port $mport"
    docker run -d --name pastebin-mongo --restart unless-stopped \
      -p "$mport:27017" \
      -v pastebin-mongo-data:/data/db \
      mongo:7 >/dev/null
  elif [ "$(docker inspect -f '{{.State.Running}}' pastebin-mongo)" != "true" ]; then
    docker start pastebin-mongo >/dev/null
  fi
  mport=$(docker port pastebin-mongo 27017/tcp | head -1 | sed 's/.*://')
  [ "$mport" != 27017 ] && export MONGODB_URI="mongodb://localhost:$mport/pastebin"
else
  if ! docker ps -a --format '{{.Names}}' | grep -qx pastebin-postgres; then
    port=5432
    if (exec 3<>/dev/tcp/127.0.0.1/5432) 2>/dev/null; then
      port=5433   # 5432 busy (e.g. another project's postgres)
    fi
    echo "Starting postgres on host port $port"
    docker run -d --name pastebin-postgres --restart unless-stopped \
      -p "$port:5432" \
      -e POSTGRES_DB=pastebin -e POSTGRES_USER=pastebin -e POSTGRES_PASSWORD=pastebin \
      -v pastebin-postgres-data:/var/lib/postgresql/data \
      postgres:17-alpine >/dev/null
  elif [ "$(docker inspect -f '{{.State.Running}}' pastebin-postgres)" != "true" ]; then
    docker start pastebin-postgres >/dev/null
  fi

  for _ in $(seq 1 30); do
    docker exec pastebin-postgres pg_isready -U pastebin -d pastebin >/dev/null 2>&1 && break
    sleep 1
  done
  docker exec pastebin-postgres pg_isready -U pastebin -d pastebin >/dev/null

  port=$(docker port pastebin-postgres 5432/tcp | head -1 | sed 's/.*://')
  [ "$port" != 5432 ] && export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:$port/pastebin"
fi

# --- Redis ----------------------------------------------------------------
if ! docker ps -a --format '{{.Names}}' | grep -qx pastebin-redis; then
  rport=6379
  if (exec 3<>/dev/tcp/127.0.0.1/6379) 2>/dev/null; then
    rport=6380
  fi
  echo "Starting redis on host port $rport"
  docker run -d --name pastebin-redis --restart unless-stopped \
    -p "$rport:6379" \
    redis:7-alpine >/dev/null
elif [ "$(docker inspect -f '{{.State.Running}}' pastebin-redis)" != "true" ]; then
  docker start pastebin-redis >/dev/null
fi
rport=$(docker port pastebin-redis 6379/tcp | head -1 | sed 's/.*://')
[ "$rport" != 6379 ] && export SPRING_DATA_REDIS_PORT="$rport"

# --- App ------------------------------------------------------------------
if (exec 3<>/dev/tcp/127.0.0.1/8080) 2>/dev/null; then
  export SERVER_PORT=8081   # 8080 busy
  echo "Port 8080 busy -> app on $SERVER_PORT"
fi
exec ./gradlew bootRun "$@"
