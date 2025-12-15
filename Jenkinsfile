pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'Java'
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo 'Checking out code from repository...'
                git branch: 'main', url: 'https://github.com/your-username/CampusAutomationAPI.git'
            }
        }

        stage('Build Project') {
            steps {
                echo 'Building project with Maven...'
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Executing TestNG suite...'
                sh 'mvn test'
            }
        }

        stage('Archive & Zip Reports') {
            steps {
                echo 'Zipping reports from /Reports folder...'
                sh 'zip -r Reports.zip Reports'
                archiveArtifacts artifacts: 'Reports.zip', fingerprint: true
            }
        }

        stage('Publish Extent Report') {
            steps {
                echo 'Publishing Extent Report...'
                publishHTML(target: [
                    reportName : 'API Automation Report',
                    reportDir  : 'Reports',
                    reportFiles: '**/*.html',
                    keepAll    : true
                ])
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
        }
    }
}