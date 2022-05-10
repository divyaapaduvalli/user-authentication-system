pipeline {
    agent any //the pipeline will run on any available jenkins agents. Jenkins agents are executor nodes (linux or windows)
    environment {
        NEW_VERSION = '1.0'
        SERVER_CREDENTIALS = credentials('server-credentials')
    }
    stages {
        stage("build") {
            steps {
                echo "Building the application of version ${NEW_VERSION}"
            }
        }

        stage("test") {
            when {
                branch 'dev'
                branch 'master'
            }
            steps {
                echo "Testing the application ${SERVER_CREDENTIALS}"
            }
        }

        stage("deploy") {
            steps {
                echo 'Deploying the application'
            }
        }
//         post {
//             always{
//                 //Will run irrespective what happens in the above stages
//             }
//             failure{
//                 //When we run into any failure in any of the stages
//             }
//         }
    }
}