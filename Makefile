.PHONY: build

build:
	make jar

jar:
	./gradlew :lib:jar

test:
	./gradlew clean :lib:test --no-build-cache
