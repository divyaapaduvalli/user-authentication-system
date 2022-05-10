pipeline {
    agent any //the pipeline will run on any available jenkins agents. Jenkins agents are executor nodes (linux or windows)

    stages {
        stage("build") {
            steps {
                echo 'Building the application'
            }
        }

        stage("test") {
            when {
                expression {
                    BRANCH_NAME == 'dev'
                }
            }
            steps {
                echo 'Testing the application'
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