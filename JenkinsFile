pipeline {
    agent any //the pipeline will run on any available jenkins agents. Jenkins agents are executor nodes (linux or windows)

    stages {
        stage("build") {
            steps {
                echo 'Building the application'
            }
        }

        stage("test") {
            steps {
                echo 'Testing the application'
            }
        }

        stage("deploy") {
            steps {
                echo 'Deploying the application'
            }
        }
    }
}