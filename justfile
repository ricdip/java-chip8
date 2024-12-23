# show help message
default:
    @just --list

# build application target files
compile:
    mvn compile

# clean application target files
clean:
    mvn clean
    rm -rf bin

# build application JAR file
package:
    mvn package
    mkdir -p bin
    cp target/JavaChip8-*.jar bin