pipeline {
    agent { label any }

    stages {
        stage('Test') {
            steps {
              sh './gradlew clean test'
            } 
        }
}
