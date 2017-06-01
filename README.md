# http4k-todo-backend

## Overview

This repository contains the todo list application that implemented by [http4k](https://github.com/http4k/http4k).

## Motivation

I challenge the Server Side Kotlin. The Spring Boot is great, but I want know another libraries that is based on kotlin implementation.  
So this repository selected several libraries.

* Http Services
  * [http4k](https://github.com/http4k/http4k)
* Database
  * [requery](https://github.com/requery/requery)
* DI
  * [Kodein](https://github.com/SalomonBrys/Kodein)
* Functions
  * [Result](https://github.com/kittinunf/Result)

## Running the applications

Running docker containers.
```
(http4k-todo-backend) $ docker-compose up -d
```

After running docker containers navigate to `http://localhost/?url=http://localhost:9000/api/api-docs` and try api request via swagger-ui.
