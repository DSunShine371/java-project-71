ifeq ($(OS),Windows_NT)
build:
.PHONY: build
	gradlew build

report:
	gradlew test jacocoTestReport

run-dist:
	build\install\app\bin\app file1.json file2.json

else
setup:
	./gradlew wrapper --gradle-version 8.5

install:
	./gradlew install

build:
	./gradlew build

run:
	./gradlew run

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain

run-dist:
	./build/install/app/bin/app file1.json file2.json

endif

.PHONY: build