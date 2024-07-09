.DEFAULT_GOAL := help

.SILENT: help
.PHONY: help # show this help message
help:
	@grep -E '^.PHONY:.+#.+' Makefile | sed 's/.PHONY: //' | awk -F ' # ' '{printf "%-15s %s\n", $$1, $$2}'

.PHONY: compile # build application target files
compile:
	@echo "Building application target files..."
	mvn compile

.PHONY: clean # clean application target files
clean:
	@echo "Cleaning application files..."
	mvn clean
	rm -rf bin

.PHONY: package # build application JAR file
package:
	@echo "Building application JAR file..."
	mvn package
	mkdir -p bin
	cp target/java-chip8-*.jar bin