/*
def call(Map config = [:]) {
    def mvnCmd = config.get('mvnCmd', 'mvn clean install')
    def javaHome = config.get('javaHome', '')
    
    pipeline {
        agent any

        stages {
            stage('Build with Maven') {
                steps {
                    script {
                        if (javaHome) {
                            env.JAVA_HOME = javaHome
                            env.PATH = "${javaHome}/bin:${env.PATH}"
                        }

                        echo "Running Maven command: ${mvnCmd}"
                        sh "${mvnCmd}"
                    }
                }
            }
        }
    }
}
*/
