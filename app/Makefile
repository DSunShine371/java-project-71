run-dist:
	./build/install/app/bin/app
start:
	./gradlew run
build:
	./gradlew build
clean:
	./gradlew clean
test:
	./gradlew test
report:
	./gradlew jacocoTestReport
lint:
	./gradlew checkstyleMain checkstyleTest
sonar:
	./gradlew build sonar --info

build-run: build run

.PHONY: build