ifeq ($(OS),Windows_NT)
run-dist:
	build\install\app\bin\app file1.json file2.json
else
run-dist:
	./build/install/app/bin/app file1.json file2.json
endif
build:
	./gradlew build
report:
	./gradlew test jacocoTestReport
