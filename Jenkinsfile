@Library("my-shared-library") _
pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                helloWorld()
            }
        }
        stage("maven-build") {
            steps {
                mavenBuild()
            }
        }
    }
}
