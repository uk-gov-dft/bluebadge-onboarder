pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
              sh './gradlew --no-daemon clean test'
            } 
        }
    }
}
