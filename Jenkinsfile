pipeline {
    agent any

   parameters {
       string(name: 'API_MGT_DB_HOST', defaultValue: 'localhost')
       string(name: 'API_MGT_DB_PORT', defaultValue: '5432')
       string(name: 'API_MGT_DB_USER', defaultValue: 'developer')
       string(name: 'API_MGT_DB_NAME', defaultValue: 'bb_dev')
       string(name: 'API_MGT_NOTIFY_TEMPLATE_ID', defaultValue: 'test__live_api_creds-70a18c16-bfa1-4d8c-83ac-c8d16f425ea7-3f31435d-07ea-4cd1-b373-a50e93441628')
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
        stage('Test') {
            steps {
              sh './gradlew --no-daemon clean test'
            } 
        }
    }
}
