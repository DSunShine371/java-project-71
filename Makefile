ifeq ($(OS),Windows_NT)
run-dist:
	build\install\app\bin\app file1.json file2.json

build:
	gradlew build

report:
	gradlew test jacocoTestReport

else
run-dist:
	./build/install/app/bin/app file1.json file2.json

build:
	./gradlew build

report:
	./gradlew test jacocoTestReport
endif
