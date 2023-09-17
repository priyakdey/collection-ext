.PHONY: build

build:
	make jar

jar:
	./gradlew :lib:jar

test:
	./gradlew :lib:test
