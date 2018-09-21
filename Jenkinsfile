pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
              ./gradlew clean test
            } 
        }
    }
}
