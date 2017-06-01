FROM java:openjdk-8

COPY . /todo-server

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y ca-certificates&& \
    echo "export LANG=C.UTF-8" > /etc/profile.d/locale.sh && \
    rm -rf /etc/localtime && \
    ln -sf /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    \
    cd /todo-server && ./gradlew clean && \
    cd /todo-server && rm -rf ~/.gradle && ./gradlew clean installDist -x test && \
    mkdir -p /usr/local/todo-server/install && \
    cp -R /todo-server/build/install/* /usr/local/todo-server/install/ && \
    rm -rf /todo-server

ENTRYPOINT  sh /usr/local/todo-server/install/app/bin/app

EXPOSE 9000 9000