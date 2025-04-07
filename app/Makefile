ifeq ($(OS),Windows_NT)
run-dist:
	build\install\app\bin\app file1.json file2.json
else
run-dist:
	./build/install/app/bin/app file1.json file2.json
endif
build:
	./gradle build
report:
	./gradlew test jacocoTestReport
