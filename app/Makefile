ifeq ($(OS),Windows_NT)
build:
.PHONY: build
	gradlew build

report:
	gradlew test jacocoTestReport

run-dist:
	build\install\app\bin\app file1.json file2.json
else
.PHONY: build
build:
	./gradlew build

report:
	./gradlew test jacocoTestReport

run-dist:
	./build/install/app/bin/app file1.json file2.json
endif
