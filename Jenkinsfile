pipeline {
    agent { label 'Functional' }

   parameters {
       string(name: 'API_MGT_DB_HOST', defaultValue: 'localhost')
       string(name: 'API_MGT_DB_PORT', defaultValue: '5432')
       string(name: 'API_MGT_DB_USER', defaultValue: 'developer')
       string(name: 'API_MGT_DB_NAME', defaultValue: 'bb_dev')
       string(name: 'API_MGT_NOTIFY_TEMPLATE_ID', defaultValue: 'e2ec9749-9ed2-4cb1-a506-10dc20ab9151')
       string(name: 'API_MGT_AWS_REGION', defaultValue: 'eu-west-2')
       string(name: 'API_MGT_S3_BUCKET', defaultValue: 'dev-uk-gov-dft-client-credentials')
   }
   environment {
        DB_HOST="${params.API_MGT_DB_HOST}"
        DB_PORT="${params.API_MGT_DB_PORT}"
        DB_USER="${params.API_MGT_DB_USER}"
        DB_PASSWORD=credentials('API_MGT_DB_PASSWORD')
        DB_NAME="${params.API_MGT_DB_NAME}"
        NOTIFY_TEMPLATE_ID="${params.API_MGT_NOTIFY_TEMPLATE_ID}"
        NOTIFY_API_KEY=credentials('API_MGT_NOTIFY_API_KEY')
        AWS_REGION="${params.API_MGT_AWS_REGION}"
        S3_BUCKET="${params.API_MGT_S3_BUCKET}"
    }

    stages {
        stage('Setup') {
          steps {
            sh 'bash scripts/setup_dev_env.sh'
          }
        }

        stage('Test') {
            steps {
              sh './gradlew --no-daemon clean test'
            } 
        }
    }

    post {
        always {
          sh 'sh scripts/teardown_dev_env.sh'
        }
        success {
            echo 'I succeeeded!'
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
        changed {
            echo 'Things were different before...'
        }
    }
}
