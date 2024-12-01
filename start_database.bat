#!/bin/bash

docker run -d --name oracle-db -p 1521:1521 container-registry.oracle.com/database/free:23.5.0.0-lite
