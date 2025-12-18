pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'Java'
    }

    stages {

        stage('Build Project') {
            steps {
                echo 'Building project with Maven...'
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Executing testing suite...'
                sh 'mvn test'
            }
        }

        stage('Archive & Zip Reports') {
            steps {
                echo 'Zipping reports from Reports folder...'
                sh 'zip -r Reports.zip Reports'
                archiveArtifacts artifacts: 'Reports.zip', fingerprint: true
            }
        }

        stage('Publish Extent Report') {
            steps {
                echo 'Publishing Extent Report...'
                publishHTML(target: [
                    reportDir: 'Reports',
                    reportFiles: 'index.html',
                    reportName: 'API Automation Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])
            }
        }
    }
}
